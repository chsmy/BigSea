package com.chs.module_eye.ui.follow

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_common_ui.base.OnItemChildClickListener
import com.chs.lib_common_ui.exoplayer.PagePlayerDetector
import com.chs.lib_core.imageloader.ImageLoader
import com.chs.module_eye.R
import com.chs.module_eye.model.Item
import kotlinx.android.synthetic.main.eye_item_follow.*

/**
 * author：chs
 * date：2020/6/27
 * des：
 */
class FollowAdapter:AbsPageListAdapter<Item,FollowViewHolder>{

    private var mPagePlayerDetector:PagePlayerDetector

    constructor(pagePlayerDetector:PagePlayerDetector) : super(object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }){
        mPagePlayerDetector = pagePlayerDetector
    }

    override fun getItemViewType(position: Int): Int = R.layout.eye_item_follow

    override fun createCurrentViewHolder(view: View, viewType: Int): FollowViewHolder {
        return FollowViewHolder(view,onItemChildClickListener)
    }

    override fun onViewAttachedToWindow2(holder: FollowViewHolder) {
        super.onViewAttachedToWindow2(holder)
        mPagePlayerDetector.addTarget(holder.play_view)
    }

    override fun onViewDetachedFromWindow2(holder: FollowViewHolder) {
        super.onViewDetachedFromWindow2(holder)
        mPagePlayerDetector.removeTarget(holder.play_view)
    }
}

class FollowViewHolder(itemView: View,
                        private val onItemChildClickListener: OnItemChildClickListener?)
    :BaseViewHolder<Item>(itemView){

    override fun setContent(item: Item, position: Int) {
        val subData = item.data
        tv_name.text = subData.header.issuerName
        tv_title.text = subData.content.data.title
        tv_des.text = subData.content.data.description
        ImageLoader.url(item.data.header.icon).circleInto(iv_head)

        val imageView = ImageView(itemView.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageLoader.url(item.data.content.data.cover.feed).into(imageView)
        play_view.bindData(FollowFragment.KEY_FOLLOW_VIDEO_NAME,subData.content.data.width,subData.content.data.height,
        subData.content.data.cover.feed,subData.content.data.playUrl)
    }
}