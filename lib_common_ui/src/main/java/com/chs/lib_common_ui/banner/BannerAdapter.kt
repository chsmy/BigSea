package com.chs.lib_common_ui.banner

import android.view.View
import com.chs.lib_common_ui.R
import com.chs.lib_common_ui.model.Banner
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * author：chs
 * date：2020/6/25
 * des：
 */
class BannerAdapter : BaseBannerAdapter<Banner, NetViewHolder>() {
    override fun getLayoutId(viewType: Int): Int = R.layout.item_net

    override fun createViewHolder(itemView: View, viewType: Int): NetViewHolder {
        return NetViewHolder(itemView)
    }

    override fun onBind(holder: NetViewHolder?, data: Banner?, position: Int, pageSize: Int) {
        holder?.bindData(data,position,pageSize)
    }
}