package com.chs.module_video

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_core.imageloader.ImageLoader
import com.chs.module_video.model.VideoList
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import kotlinx.android.synthetic.main.video_item_display.*

/**
 * author：chs
 * date：2020/6/17
 * des：
 */
class VideoListAdapter : AbsPageListAdapter<VideoList, VideoListViewHolder> {

    constructor() : super(object : DiffUtil.ItemCallback<VideoList>() {
        override fun areItemsTheSame(oldItem: VideoList, newItem: VideoList): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: VideoList, newItem: VideoList): Boolean {
            return oldItem == newItem
        }
    })

    override fun createCurrentViewHolder(view: View, viewType: Int): VideoListViewHolder {
        return VideoListViewHolder(view)
    }

    override fun getLayoutId(): Int = R.layout.video_item_display
}

class VideoListViewHolder(itemView: View) : BaseViewHolder<VideoList>(itemView) {
    private val TAG = javaClass.name
    private val gsyVideoOptionBuilder: GSYVideoOptionBuilder = GSYVideoOptionBuilder()
    override fun setContent(item: VideoList, position: Int) {
        tv_name.text = item.name
        tv_title.text = item.text
        ImageLoader.url(item.header).circleInto(iv_head)

        val imageView = ImageView(itemView.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageLoader.url(item.thumbnail).into(imageView)

        play_view.backButton.visibility = View.GONE

        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder
            .setIsTouchWiget(false)
            .setThumbImageView(imageView)
            .setUrl(item.video)
            .setVideoTitle(item.text)
            .setCacheWithPlay(false)
            .setRotateViewAuto(true)
            .setLockLand(true)
            .setPlayTag(TAG)
            .setShowFullAnimation(true)
            .setNeedLockFull(true)
            .setPlayPosition(position)
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
//                    if (!play_view.isIfCurrentIsFullscreen()) {
//                        //静音
//                        GSYVideoManager.instance().isNeedMute = true
//                    }
                }

                override fun onQuitFullscreen(
                    url: String,
                    vararg objects: Any
                ) {
                    super.onQuitFullscreen(url, *objects)
                    //全屏不静音
                    GSYVideoManager.instance().isNeedMute = true
                }

                override fun onEnterFullscreen(
                    url: String,
                    vararg objects: Any
                ) {
                    super.onEnterFullscreen(url, *objects)
                    GSYVideoManager.instance().isNeedMute = false
                    play_view.getCurrentPlayer().getTitleTextView()
                        .setText(objects[0] as String)
                }
            }).build(play_view)
    }
}