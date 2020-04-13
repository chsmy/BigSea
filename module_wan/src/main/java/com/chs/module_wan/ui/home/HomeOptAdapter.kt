package com.chs.module_wan.ui.home

import android.view.View
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.module_wan.R
import com.chs.module_wan.model.HomeOpt
import kotlinx.android.synthetic.main.wan_item_opt_sub.*
/**
 * @author：chs
 * date：2020/2/5
 * des：
 */
class WanOptAdapter(data:List<HomeOpt>) : BaseAdapter<HomeOpt>(data){

    override fun getLayoutId(): Int = R.layout.wan_item_opt_sub

    override fun createCurrentViewHolder(view: View,viewType: Int): BaseViewHolder<HomeOpt> {
        return WanOptViewHolder(view)
    }
}

class WanOptViewHolder(itemView: View) :BaseViewHolder<HomeOpt>(itemView) {
    override fun setContent(item: HomeOpt,position:Int) {
            iv_icon.setImageResource(item.iconId)
            tv_opt_name.text = item.title
    }
}