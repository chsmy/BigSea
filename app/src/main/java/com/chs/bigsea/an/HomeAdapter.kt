package com.chs.bigsea.an

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *  @author chs
 *  date: 2020-01-18 11:02
 *  des:
 */
class HomeAdapter(data: MutableList<HomeData>) :
    BaseMultiItemQuickAdapter<HomeData, BaseViewHolder>(data) {

    init {
    }

    override fun convert(helper: BaseViewHolder, item: HomeData?) {
        when (item?.itemType) {
            HomeData.HEADER -> {
                setHeaderUI(helper, item)
            }
        }
    }

    private fun setHeaderUI(helper: BaseViewHolder, item: HomeData) {

    }
}