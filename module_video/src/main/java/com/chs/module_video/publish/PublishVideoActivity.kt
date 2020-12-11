package com.chs.module_video.publish

import android.content.Intent
import android.os.Bundle
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_common_ui.camera.CameraViewActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_video.R
import com.chs.module_video.databinding.VideoActivityPublishBinding
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.video_activity_publish.*

/**
 * author：chs
 * date：2020/6/21
 * des： 发布一条视频动态
 */
@ActivityDestination(pageUrl = WanRouterKey.ACTIVITY_VIDEO_PUBLISH)
class PublishVideoActivity:BaseActivity<VideoActivityPublishBinding>() {


    override fun onCreateBinding(savedInstanceState: Bundle?): VideoActivityPublishBinding {
       return VideoActivityPublishBinding.inflate(layoutInflater)
    }

    override fun VideoActivityPublishBinding.onViewCreated() {
        setStatusBar()
    }

    private fun setStatusBar() {
        ImmersionBar.with(this).statusBarColor(R.color.white).init()
    }

    override fun initData() {
    }

    override fun initListener() {
        super.initListener()
        action_add_file.setOnClickListener {
            val intent = Intent(this,CameraViewActivity::class.java)
            startActivity(intent)
        }
    }

}