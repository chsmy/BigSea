package com.chs.module_video.live

import android.os.Bundle
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.lib_core.extension.showLong
import com.chs.lib_core.extension.showShort
import com.chs.module_video.R
import com.chs.module_video.databinding.VideoActivityLiveBinding
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.video_activity_live.*
import kotlinx.android.synthetic.main.video_layout_activity_live_ui.*
import me.lake.librestreaming.core.listener.RESConnectionListener
import me.lake.librestreaming.filter.hardvideofilter.BaseHardVideoFilter
import me.lake.librestreaming.filter.hardvideofilter.HardVideoGroupFilter
import me.lake.librestreaming.ws.StreamAVOption
import me.lake.librestreaming.ws.filter.hardfilter.GPUImageBeautyFilter
import me.lake.librestreaming.ws.filter.hardfilter.extra.GPUImageCompatibleFilter
import java.util.*

/**
 *  @author chs
 *  date: 2020-07-13 17:00
 *  des:  直播界面
 *  //测试直播可以在这个网址  该网址可以播放rtmp实时推流的视频
 *  http://ossrs.net/srs.release/trunk/research/players/srs_player.html?vhost=__defaultVhost__&autostart=true&server=192.168.1.170&app=live&stream=livestream&port=1935
 */
@ActivityDestination(pageUrl = WanRouterKey.ACTIVITY_VIDEO_LIVE)
class LiveActivity:BaseActivity<VideoActivityLiveBinding>() {

    private val rtmpUrl = "rtmp://203.170.59.43/live/livestream"
    private val streamAVOption: StreamAVOption by lazy { StreamAVOption() }

    override fun onCreateBinding(savedInstanceState: Bundle?): VideoActivityLiveBinding {
        return VideoActivityLiveBinding.inflate(layoutInflater)
    }

    override fun VideoActivityLiveBinding.onViewCreated() {
        setStatusBar()
        initConfig()
    }

    private fun setStatusBar() {
        ImmersionBar.with(this).transparentBar()
    }

    /**
     * 设置推流参数
     */
    private fun initConfig() {
        streamAVOption.streamUrl = rtmpUrl

        stream_previewView.init(this,streamAVOption)
        stream_previewView.addStreamStateListener(resConnectionListener)
        val files = LinkedList<BaseHardVideoFilter>()
        //设置滤镜
        files.add(GPUImageCompatibleFilter(GPUImageBeautyFilter()))
        //添加图片
//        files.add(WatermarkFilter(BitmapFactory.decodeResource(resources, R.drawable.icon_cell_comment), Rect(100, 100, 200, 200)))
        stream_previewView.setHardVideoFilter(HardVideoGroupFilter(files))
    }

    private val resConnectionListener: RESConnectionListener = object : RESConnectionListener{
        override fun onOpenConnectionResult(result: Int) {
            //result 0成功  1 失败
            showLong("打开推流连接 状态：$result 推流地址：$rtmpUrl")
        }

        override fun onWriteError(errno: Int) {
            showLong("推流出错,请尝试重连")
        }

        override fun onCloseConnectionResult(result: Int) {
            //result 0成功  1 失败
            showLong("关闭推流连接 状态：$result")
            //result 0成功  1 失败
        }
    }

    override fun initListener() {
        super.initListener()
        //开始推流
        btn_startStreaming.setOnClickListener {
            if (!stream_previewView.isStreaming) {
                stream_previewView.startStreaming(rtmpUrl)
            }
        }
        //停止推流
        btn_stopStreaming.setOnClickListener {
            if (stream_previewView.isStreaming) {
                stream_previewView.stopStreaming()
            }
        }
        //切换摄像头
        btn_swapCamera.setOnClickListener {
            stream_previewView.swapCamera()
        }
        //开始录制
        btn_startRecord.setOnClickListener {
            if(!stream_previewView.isRecord){
                showShort("开始录制视频")
                stream_previewView.startRecord();
            }
        }
       //停止录制
        btn_stopRecord.setOnClickListener {
            if(stream_previewView.isRecord){
                stream_previewView.stopRecord();
                showShort("视频已成功保存至系统根目录的 Movies/WSLive文件夹中")
            }
        }
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        stream_previewView.destroy()
    }

}