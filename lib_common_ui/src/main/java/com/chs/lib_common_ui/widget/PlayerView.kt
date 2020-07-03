package com.chs.lib_common_ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import com.chs.lib_common_ui.R
import com.chs.lib_common_ui.exoplayer.IPlayTarget
import com.chs.lib_common_ui.exoplayer.PagePlayerManager
import com.chs.lib_core.extension.load
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerControlView

/**
 * author：chs
 * date：2020/6/29
 * des： 视频播放的容器
 */
open class PlayerView : FrameLayout,IPlayTarget, Player.EventListener,
    PlayerControlView.VisibilityListener {

    /**
     * 保存播放对象target的key
     */
    var targetKey: String? = null

    /**
     * 视频的链接地址
     */
    lateinit var videoUrl: String

    /**
     * 是否正在播放
     */
    var isTargetPlaying: Boolean = false

    var widthPx = 0
    var heightPx = 0
    var loadingView: View

    /**
     * 暂停/播放按钮
     */
    private var playBtn: ImageView
    var cover: ImageView

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_player_view, this, true)
        loadingView = findViewById(R.id.loading_view)
        playBtn = findViewById(R.id.play_btn)
        cover = findViewById(R.id.cover)
        playBtn.setOnClickListener {
              if(isPlaying()){
                  inActive()
              }else{
                  onActive()
              }
        }
        transitionName = "PlayerView"
    }

    fun bindData(key:String,with:Int,height:Int,coverUrl:String,videoUrl:String){
        targetKey = key
        widthPx = with
        heightPx = height
        this.videoUrl = videoUrl

        cover.load(coverUrl)
        if(with!=0&&height!=0){
            setSize(widthPx, heightPx)
        }
//        val builder = Glide.with(context).load(coverUrl)
//        val layoutParams = layoutParams
//        builder.override(layoutParams.width,layoutParams.height).into(cover)
    }

    /**
     * 视频宽高等比缩放
     */
    open fun setSize(widthPx: Int, heightPx: Int) {
//        if(heightPx!=0){
//            val maxWidth = ScreenUtils.getScreenWidth()
//            val maxHeight = ScreenUtils.getScreenWidth()
//            //当前布局的宽高
//            var layoutWidth = 0
//            var layoutHeight = 0
//            if(widthPx>=heightPx){
//                //如果视频的宽大于等于高
//                layoutWidth= maxWidth
//                layoutHeight = widthPx/heightPx*maxWidth
//            }else{
//                //如果视频的宽小于高
//                layoutWidth =  widthPx/heightPx*maxHeight
//                layoutHeight = maxHeight
//            }
//            val params = layoutParams
//            params.width = layoutWidth
//            params.height = layoutHeight
//            layoutParams = params

//            val coverParams = cover.layoutParams as LayoutParams
//            coverParams.width = layoutWidth
//            coverParams.height = layoutHeight
//            coverParams.gravity = Gravity.CENTER
//            cover.layoutParams = coverParams

//        }
    }

    override fun getOwner(): ViewGroup {
        return this
    }

    override fun onActive() {
         //播放或者恢复播放视频
        //通过key 取出当前页面的播放器实例
        val pagePlayer = PagePlayerManager.getPagePlayer(targetKey!!)
        val exoplayer = pagePlayer.exoplayer
        val playerView = pagePlayer.playerView
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
            val params = LayoutParams(MATCH_PARENT,WRAP_CONTENT)
            params.gravity = Gravity.BOTTOM
            addView(controlView,params)
        }
        //如果是同一个视频资源 不需要重新创建mediaSource
        //不过需要调用onPlayerStateChanged重新触发播放
        if(TextUtils.equals(pagePlayer.playUrl,videoUrl)){
             onPlayerStateChanged(true,Player.STATE_READY)
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
        //暂停播放视频，并让暂停按钮显示出来
        val pagePlayer = PagePlayerManager.getPagePlayer(targetKey!!)
        if(pagePlayer.controlView==null || pagePlayer.exoplayer==null || pagePlayer.playerView==null)return
        pagePlayer.exoplayer?.playWhenReady = false
        pagePlayer.exoplayer?.removeListener(this)
        pagePlayer.controlView?.removeVisibilityListener(this)
        cover.visibility = View.VISIBLE
        playBtn.visibility = View.VISIBLE

        playBtn.setImageResource(R.drawable.icon_video_play)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //点击视频区域的时候，让进控制器显示出来
        val pagePlayer = PagePlayerManager.getPagePlayer(targetKey!!)
        pagePlayer.controlView?.show()
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        //当试图和窗口分离时
        isTargetPlaying = false
        loadingView.visibility = View.GONE
        playBtn.visibility = View.VISIBLE
        cover.visibility = View.VISIBLE
        playBtn.setImageResource(R.drawable.icon_video_play)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        //监听视频播放器的状态
        val pagePlayer = PagePlayerManager.getPagePlayer(targetKey!!)
        val exoplayer = pagePlayer.exoplayer
        //如果已经准备好了 并且缓冲区不为0
        if(playbackState == Player.STATE_READY&&playWhenReady&&exoplayer?.bufferedPosition!=0L){
            cover.visibility = View.GONE
            loadingView.visibility = View.GONE
        }else if(playbackState == Player.STATE_BUFFERING){
            //当需要加载更多的时候
            loadingView.visibility = View.VISIBLE
        }
        isTargetPlaying = playbackState == Player.STATE_READY&&playWhenReady&&exoplayer?.bufferedPosition!=0L
        playBtn.setImageResource(if (isPlaying()) R.drawable.icon_video_pause else R.drawable.icon_video_play)
    }

    override fun isPlaying(): Boolean {
       return isTargetPlaying
    }

    override fun onVisibilityChange(visibility: Int) {
        playBtn.visibility = visibility
        playBtn.setImageResource(if (isPlaying()) R.drawable.icon_video_pause else R.drawable.icon_video_play)

    }
}