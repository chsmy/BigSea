package com.chs.module_video

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_core.imageloader.ImageLoader
import com.chs.module_video.model.VideoList
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
    override fun setContent(item: VideoList, position: Int) {
        tv_name.text = item.name
        tv_title.text = item.text
        play_view.backButton.visibility = View.GONE
        val imageView = ImageView(itemView.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageLoader.url(item.thumbnail).into(imageView)
        play_view.thumbImageView = imageView
        play_view.setUp(item.video,true,"ce")
    }
}