package com.chs.lib_common_ui.exoplayer

import android.view.LayoutInflater
import com.blankj.utilcode.util.Utils
import com.chs.lib_common_ui.R
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView

/**
 * author：chs
 * date：2020/6/28
 * des： 单个页面的视频播放器
 */
class PagePlayer {
    var exoplayer:SimpleExoPlayer?
    private var playerView:PlayerView?
    private var controlView:PlayerControlView?
    init {
        val app = Utils.getApp()
        //初始化exoplayer
        exoplayer = SimpleExoPlayer.Builder(app).build()
        //ExoPlayer库提供了一个PlayerView，其中封装了 PlayerControlView，
        //SubtitleView和a Surface渲染视频。可以用来展示视频画面
        playerView = LayoutInflater.from(app).inflate(R.layout.layout_exo_player_view,
            null,false) as PlayerView
        //视频播放控制器
        controlView = LayoutInflater.from(app).inflate(R.layout.layout_exo_player_view,
            null,false) as PlayerControlView
    }

    fun release() {
        exoplayer?.let {
            it.playWhenReady = false
            it.stop(true)
            it.release()
        }
        exoplayer = null

        playerView?.let {
            it.player = null
        }
        playerView = null

        controlView?.let {
            it.player = null
            it.removeVisibilityListener {  }
        }
        controlView = null
    }


}