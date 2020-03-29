package com.chs.bigsea.ui

import android.view.View
import android.widget.ImageView
import com.chs.bigsea.R
import com.chs.lib_core.imageloader.ImageLoader
import com.chs.module_wan.model.Banner
import com.zhpan.bannerview.holder.ViewHolder


/**
 *  @author chs
 *  date: 2020-01-18 15:59
 *  des:  banner 使用
 */
class NetViewHolder : ViewHolder<Banner> {

    override fun getLayoutId(): Int {
        return R.layout.item_net
    }

    override fun onBind(itemView: View?, data: Banner, position: Int, size: Int) {
        val imageView: ImageView = itemView!!.findViewById(R.id.banner_image)
        ImageLoader.url(data.imagePath).into(imageView)
    }
}