package com.chs.module_wan.ui.navigation
import android.view.View
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.module_wan.R
import com.chs.module_wan.model.NavigationData

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class NavAdapter(data:List<NavigationData>) : BaseAdapter<NavigationData>(data) {

    override fun getLayoutId(): Int = R.layout.wan_item_navigaton

    override fun createCurrentViewHolder(view: View, viewType: Int): BaseViewHolder<NavigationData> {
        return NavHolder(view)
    }
}

class NavHolder(itemView: View) : BaseViewHolder<NavigationData>(itemView) {
    override fun setContent(item: NavigationData) {

    }
}