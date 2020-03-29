package com.chs.bigsea.ui.home

import android.view.View
import com.chs.bigsea.R
import com.chs.bigsea.model.HomeOpt
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_opt_sub.*

/**
 * @author：chs
 * date：2020/2/5
 * des：
 */
class WanOptAdapter(data:List<HomeOpt>) : BaseAdapter<HomeOpt>(data){

    override fun getLayoutId(): Int = R.layout.item_opt_sub

    override fun createCurrentViewHolder(view: View,viewType: Int): BaseViewHolder<HomeOpt> {
        return WanOptViewHolder(view)
    }
}

class WanOptViewHolder(itemView: View) :BaseViewHolder<HomeOpt>(itemView) {
    override fun setContent(item: HomeOpt) {
            iv_icon.setImageResource(item.iconId)
            tv_opt_name.text = item.title
    }
}