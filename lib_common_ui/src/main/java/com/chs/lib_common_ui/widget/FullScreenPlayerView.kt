package com.chs.lib_common_ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.ScreenUtils
import com.chs.lib_common_ui.R
import com.chs.lib_common_ui.exoplayer.PagePlayerManager
import com.google.android.exoplayer2.Player

/**
 * author：chs
 * date：2020/6/30
 * des： 视频详情 全屏播放
 */
class FullScreenPlayerView : PlayerView{

    var exoPlayerView:com.google.android.exoplayer2.ui.PlayerView

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    init {
        exoPlayerView =
            LayoutInflater.from(context).inflate(R.layout.layout_exo_player_view, null, false)
                    as com.google.android.exoplayer2.ui.PlayerView
    }

    override fun setSize(widthPx: Int, heightPx: Int) {
        if(widthPx>heightPx){
            super.setSize(widthPx, heightPx)
            return
        }
        val screenWidth = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()

        val layoutParams = layoutParams
        layoutParams.height = screenHeight
        layoutParams.width = screenWidth

        val covLayoutParams = cover.layoutParams as LayoutParams
        covLayoutParams.width = (widthPx / (heightPx * 1.0f / screenHeight)).toInt()
        covLayoutParams.height = screenHeight
        covLayoutParams.gravity = Gravity.CENTER
        cover.layoutParams = covLayoutParams
    }

    override fun onActive() {
        //播放或者恢复播放视频
        //通过key 取出当前页面的播放器实例
        val pagePlayer = PagePlayerManager.getPagePlayer(targetKey!!)
        val exoplayer = pagePlayer.exoplayer
        val playerView = exoPlayerView
        val controlView = pagePlayer.controlView
        if(playerView == null) return

        //将当前的playerView跟exoplayer绑定
        //因为列表和详情使用同一个exoplayer才能实现无缝续播，但是他们的playerView是不同的
        //所以每次要让exoplayer跟当前的playerView相关联
        pagePlayer.switchPlayerView(playerView,true)

        //将playerView添加到当前容器中
        val parent = playerView.parent
        if(parent!=this){
            if(parent!=null){
                (parent as ViewGroup).removeView(playerView)
                (parent as PlayerView).inActive()
            }
            addView(playerView,0,cover.layoutParams)
        }
        //将控制器添加到当前容器的底部
        val ctrParent = controlView?.parent
        if(ctrParent!=this){
            if(ctrParent!=null){
                (ctrParent as ViewGroup).removeView(controlView)
            }
            val params = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.BOTTOM
            addView(controlView,params)
        }
        //如果是同一个视频资源 不需要重新创建mediaSource
        //不过需要调用onPlayerStateChanged重新触发播放
        if(TextUtils.equals(pagePlayer.playUrl,videoUrl)){
            onPlayerStateChanged(true, Player.STATE_READY)
        }else{
            //通过url创建一个媒体播放源
            val mediaSource = PagePlayerManager.createMediaSource(videoUrl)
            //exoplayer准备
            exoplayer?.prepare(mediaSource)
            //设置循环播放
            exoplayer?.repeatMode = Player.REPEAT_MODE_ONE
            pagePlayer.playUrl = videoUrl
        }
        controlView?.show()
        controlView?.addVisibilityListener(this)
        exoplayer?.addListener(this)
        exoplayer?.playWhenReady = true
    }

    override fun inActive() {
        super.inActive()
        val pagePlayer = PagePlayerManager.getPagePlayer(targetKey!!)
        pagePlayer.switchPlayerView(exoPlayerView,false)
    }

}