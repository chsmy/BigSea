package com.chs.bigsea.an

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chs.bigsea.R
import com.chs.bigsea.an.HomeData.Companion.LIST_ITEM
import com.chs.bigsea.an.HomeData.Companion.OPT_ITEM

/**
 *  @author chs
 *  date: 2020-01-18 11:02
 *  des:
 */
class HomeAdapter(data: MutableList<HomeData>?) :
    BaseMultiItemQuickAdapter<HomeData, BaseViewHolder>(data) {

    init {
        addItemType(LIST_ITEM,R.layout.item_home_list)
        addItemType(OPT_ITEM,R.layout.item_option)
    }

    override fun convert(helper: BaseViewHolder, item: HomeData?) {
       when(item?.itemType){
           LIST_ITEM ->{
               setListItem(helper,item)
           }
           OPT_ITEM ->{
               setOptItem(helper,item)
           }
       }
    }

    private fun setOptItem(helper: BaseViewHolder, item: HomeData) {
        val rvOpt = helper.getView<RecyclerView>(R.id.rv_opt)
        rvOpt.layoutManager = GridLayoutManager(context,4)
        rvOpt.adapter = object : BaseQuickAdapter<HomeOpt,BaseViewHolder>(R.layout.item_opt_sub,item.opts){
            override fun convert(helper: BaseViewHolder, item: HomeOpt?) {
                helper.setImageResource(R.id.iv_icon,item!!.iconId)
                helper.setText(R.id.tv_opt_name,item.title)
            }
        }
    }

    private fun setListItem(helper: BaseViewHolder, item: HomeData) {
        helper.setText(R.id.tv_item,item.title)
    }

}