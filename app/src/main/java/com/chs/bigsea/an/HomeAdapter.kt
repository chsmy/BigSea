package com.chs.bigsea.an

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chs.bigsea.R
import com.chs.bigsea.an.HomeData.Companion.LIST_ITEM

/**
 *  @author chs
 *  date: 2020-01-18 11:02
 *  des:
 */
class HomeAdapter(data: MutableList<HomeData>?) :
    BaseMultiItemQuickAdapter<HomeData, BaseViewHolder>(data) {

    init {
        addItemType(LIST_ITEM,R.layout.item_home_list)
    }

    override fun convert(helper: BaseViewHolder, item: HomeData?) {
       when(item?.itemType){
           LIST_ITEM ->{
               setListItem(helper,item)
           }
       }
    }

    private fun setListItem(helper: BaseViewHolder, item: HomeData) {
        helper.setText(R.id.tv_item,item.title)
    }

}