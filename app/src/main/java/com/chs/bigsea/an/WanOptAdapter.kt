package com.chs.bigsea.an

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.bigsea.R
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.module_wan.model.DataX
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home_list.*
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