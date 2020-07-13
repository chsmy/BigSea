package com.chs.module_video.play

import android.os.Bundle
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_video.R
import kotlinx.android.synthetic.main.video_activity_play.*

/**
 * author：chs
 * date：2020/7/13
 * des： 观看直播的页面
 */
@ActivityDestination(pageUrl = WanRouterKey.ACTIVITY_VIDEO_PLAY)
class RtmpPlayActivity:BaseActivity() {

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.video_activity_play

    override fun initView() {
    }

    override fun initData() {
        //湖南卫视直播源  rtmp://58.200.131.2:1935/livetv/hunantv
        //自己的直播源    rtmp://203.170.59.43/live/livestream
                play_view.bindData("mine",1080,720,
            "http://a0.att.hudong.com/30/88/01100000000000144726882521407_s.jpg",
            "rtmp://58.200.131.2:1935/livetv/hunantv")
    }

    override fun onDestroy() {
        super.onDestroy()
        play_view.inActive()
    }

}