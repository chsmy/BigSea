package com.chs.module_wan.ui.system

import android.view.View
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_common_ui.base.OnItemClickListener
import com.chs.module_wan.R
import com.chs.module_wan.model.SystemEntity
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.wan_item_flex_box.*
import kotlinx.android.synthetic.main.wan_item_system.*

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
class SystemAdapter(data:List<SystemEntity>) : BaseAdapter<SystemEntity>(data){

    override fun getLayoutId(): Int = R.layout.wan_item_system

    override fun createCurrentViewHolder(view: View, viewType: Int): BaseViewHolder<SystemEntity> {
        return SystemViewHolder(view)
    }

}
class SystemViewHolder(itemView: View) :BaseViewHolder<SystemEntity>(itemView) {
    override fun setContent(item: SystemEntity) {
        tv_sys_title?.text = item.name
        tv_name?.text = item.name
        if(item.children.isNotEmpty()){
            val layoutManager = FlexboxLayoutManager(itemView.context)
            layoutManager.flexWrap = FlexWrap.WRAP
            layoutManager.alignItems = AlignItems.STRETCH
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.FLEX_START
            rv_flexbox.layoutManager = layoutManager
            val adapter = FlexBoxAdapter(item.children)
            rv_flexbox.adapter = adapter
            adapter.onItemClickListener = object : OnItemClickListener{
                override fun onItemClick(view: View, position: Int) {

                }
            }
        }
    }
}
class FlexBoxAdapter(data:List<SystemEntity>):BaseAdapter<SystemEntity>(data){
    override fun getLayoutId(): Int = R.layout.wan_item_flex_box

    override fun createCurrentViewHolder(view: View, viewType: Int): BaseViewHolder<SystemEntity> {
        return SystemViewHolder(view)
    }

}