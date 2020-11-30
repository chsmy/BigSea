package com.chs.lib_common_ui.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.VideoCapture;
import androidx.camera.view.CameraView;
import androidx.camera.view.video.OnVideoSavedCallback;
import androidx.camera.view.video.OutputFileResults;
import androidx.core.app.ActivityCompat;

import com.chs.lib_common_ui.R;
import com.chs.lib_common_ui.widget.RecordView;
import com.chs.lib_core.utils.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author：chs
 * date：2020/5/29
 * des： 使用cameraview 实现拍照
 */
public class CameraViewActivity extends AppCompatActivity {
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private String TAG = this.getClass().getSimpleName();
    private RecordView mRecordView;
    private ImageButton mBtnCameraSwitch;
    private CameraView mCameraView;
    private ExecutorService mExecutorService;
    private String outputFilePath;
    /**
     * 是否是照相
     */
    private boolean takingPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
        ImmersionBar.with(this).init();
        mCameraView = findViewById(R.id.view_finder);
        mRecordView = findViewById(R.id.record_view);
        mBtnCameraSwitch = findViewById(R.id.camera_switch_button);

        PermissionX.init(this).permissions(PERMISSIONS)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mCameraView.bindToLifecycle(CameraViewActivity.this);
                    }else {
                        ToastUtils.showShort("没有相机权限、存储、录制权限");
                    }
                });



        mExecutorService = Executors.newSingleThreadExecutor();

        takePictureAndVideo();

        mBtnCameraSwitch.setOnClickListener(v -> {
            Integer cameraLensFacing = mCameraView.getCameraLensFacing();
            if(cameraLensFacing == CameraSelector.LENS_FACING_FRONT){
                cameraLensFacing = CameraSelector.LENS_FACING_BACK;
            }else {
                cameraLensFacing = CameraSelector.LENS_FACING_FRONT;
            }
            mCameraView.setCameraLensFacing(cameraLensFacing);
        });
    }

    private void takePictureAndVideo() {
        mRecordView.setOnRecordListener(new RecordView.OnRecordListener() {
            @Override
            public void onTackPicture() {
                //拍照
                takingPicture = true;
                takePicture();
            }

            @Override
            public void onRecordVideo() {
                //视频
                takingPicture = false;
                takeVideo();
            }

            @Override
            public void onFinish() {
                mCameraView.stopRecording();
            }
        });
    }

    private void takePicture() {
        //创建图片保存的文件地址
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(),
                System.currentTimeMillis() + ".jpeg");
        mCameraView.setCaptureMode(CameraView.CaptureMode.IMAGE);
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        mCameraView.takePicture(outputFileOptions, mExecutorService, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Uri savedUri = outputFileResults.getSavedUri();
                if(savedUri == null){
                    savedUri = Uri.fromFile(file);
                }
                outputFilePath = file.getAbsolutePath();
                ToastUtils.showShort("拍照完毕存储在："+outputFilePath);
                onFileSaved(savedUri);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e(TAG, "Photo capture failed: "+exception.getMessage(), exception);
            }
        });
    }

    private void takeVideo() {
        //创建视频保存的文件地址
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(),
                System.currentTimeMillis() + ".mp4");
        mCameraView.setCaptureMode(CameraView.CaptureMode.VIDEO);
        mCameraView.startRecording(file, Executors.newSingleThreadExecutor(), new OnVideoSavedCallback() {
            @Override
            public void onVideoSaved(@NonNull OutputFileResults outputFileResults) {
                outputFilePath = file.getAbsolutePath();
                ToastUtils.showShort("录制完毕存储在："+outputFilePath);
                onFileSaved(Uri.fromFile(file));
            }

            @Override
            public void onError(int videoCaptureError, @NonNull String message, @Nullable Throwable cause) {
                Log.i(TAG,message);
            }
        });
    }

    private void onFileSaved(Uri savedUri) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            sendBroadcast(new Intent(android.hardware.Camera.ACTION_NEW_PICTURE, savedUri));
        }
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap
                .getFileExtensionFromUrl(savedUri.getPath()));
        MediaScannerConnection.scanFile(getApplicationContext(),
                new String[]{new File(savedUri.getPath()).getAbsolutePath()},
                new String[]{mimeTypeFromExtension}, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d(TAG, "Image capture scanned into media store: $uri"+uri);
                    }
                });
        PreviewActivity.start(this, outputFilePath, !takingPicture);
    }

    @Override
    protected void onDestroy() {
        mExecutorService.shutdown();
        super.onDestroy();
    }

}
