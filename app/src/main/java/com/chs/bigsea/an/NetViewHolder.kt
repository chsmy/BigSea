package com.chs.bigsea.an

import android.view.View
import android.widget.ImageView
import com.chs.bigsea.R
import com.chs.lib_core.imageloader.ImageLoader
import com.zhpan.bannerview.holder.ViewHolder


/**
 *  @author chs
 *  date: 2020-01-18 15:59
 *  des:
 */
class NetViewHolder : ViewHolder<HomeBanner> {

    override fun getLayoutId(): Int {
        return R.layout.item_net
    }

    override fun onBind(itemView: View?, data: HomeBanner, position: Int, size: Int) {
        val imageView: ImageView = itemView!!.findViewById(R.id.banner_image)
        ImageLoader.url(data.url).into(imageView)
    }


}