package com.chs.bigsea.an

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chs.module_wan.model.DataX

/**
 *  @author chs
 *  date: 2020-01-18 11:05
 *  des:
 */
class HomeData : MultiItemEntity{

    companion object{
        const val LIST_ITEM = 1
    }

    var title:String = ""



    constructor(itemType: Int,title:String){
        this.itemType = itemType
        this.title = title
    }
    override var itemType: Int
}

class HomeBanner(var url: String, to: String)