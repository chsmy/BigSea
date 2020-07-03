package com.chs.lib_common_ui.exoplayer

import android.view.LayoutInflater
import com.chs.lib_common_ui.R
import com.chs.lib_core.utils.AppUtil
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
    var playerView:PlayerView?
    var controlView:PlayerControlView?
    var playUrl:String? = null
    init {
        val app = AppUtil.getApp()
        //初始化exoplayer
        exoplayer = SimpleExoPlayer.Builder(app).build()
        //ExoPlayer库提供了一个PlayerView，其中封装了 PlayerControlView，
        //SubtitleView和a Surface渲染视频。可以用来展示视频画面
        playerView = LayoutInflater.from(app).inflate(R.layout.layout_exo_player_view,
            null,false) as PlayerView
        //视频播放控制器
        controlView = LayoutInflater.from(app).inflate(R.layout.layout_exo_player_contorller_view,
            null,false) as PlayerControlView
        //把播放器和控制器跟exoplayer相关联
        playerView?.player = exoplayer
        controlView?.player = exoplayer
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

    /**
     * 用于视图切换的时候 无缝续播
     */
    fun switchPlayerView(currentPlayerView: PlayerView, attach: Boolean) {
        playerView?.player = if(attach) null else exoplayer
        currentPlayerView.player = if(attach) exoplayer else null
    }

}