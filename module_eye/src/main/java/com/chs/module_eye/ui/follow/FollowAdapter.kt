package com.chs.module_eye.ui.follow

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_common_ui.base.OnItemChildClickListener
import com.chs.lib_core.imageloader.ImageLoader
import com.chs.module_eye.R
import com.chs.module_eye.model.FollowItem
import com.chs.module_eye.model.Item
import com.chs.module_eye.model.RecommendItem
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import kotlinx.android.synthetic.main.eye_item_follow.*

/**
 * author：chs
 * date：2020/6/27
 * des：
 */
class FollowAdapter:AbsPageListAdapter<Item,BaseViewHolder<Item>>{

    constructor() : super(object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    })

    override fun getItemViewType(position: Int): Int = R.layout.eye_item_follow

    override fun createCurrentViewHolder(view: View, viewType: Int): BaseViewHolder<Item> {
        return FollowViewHolder(view,onItemChildClickListener)
    }
}

class FollowViewHolder(itemView: View,
                        private val onItemChildClickListener: OnItemChildClickListener?)
    :BaseViewHolder<Item>(itemView){
    private val TAG = javaClass.name
    private val gsyVideoOptionBuilder: GSYVideoOptionBuilder = GSYVideoOptionBuilder()
    override fun setContent(item: Item, position: Int) {
        val subData = item.data
        tv_name.text = subData.header.issuerName
        tv_title.text = subData.content.data.title
        tv_des.text = subData.content.data.description
        ImageLoader.url(item.data.header.icon).circleInto(iv_head)

        val imageView = ImageView(itemView.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageLoader.url(item.data.content.data.cover.feed).into(imageView)
        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder
            .setIsTouchWiget(false)
            .setThumbImageView(imageView)
            .setUrl(subData.content.data.playUrl)
            .setVideoTitle("")
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
                    if (!play_view.isIfCurrentIsFullscreen()) {
                        //静音
                        GSYVideoManager.instance().isNeedMute = true
                    }
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