package com.chs.lib_common_ui.banner

import android.view.View
import android.widget.ImageView
import com.chs.lib_common_ui.R
import com.chs.lib_common_ui.model.Banner
import com.chs.lib_core.imageloader.ImageLoader
import com.zhpan.bannerview.BaseViewHolder


/**
 *  @author chs
 *  date: 2020-01-18 15:59
 *  des:  banner 使用
 */
class NetViewHolder(itemView:View) : BaseViewHolder<Banner>(itemView) {
    override fun bindData(data: Banner?, position: Int, pageSize: Int) {
        val imageView: ImageView = itemView.findViewById(R.id.banner_image)
        ImageLoader.url(data?.imagePath!!).into(imageView)
    }
}