package com.chs.bigsea.an

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *  @author chs
 *  date: 2020-01-18 11:05
 *  des:
 */
class HomeData : MultiItemEntity{

    companion object{
        const val HEADER = 1
    }

    var title:String = ""



    constructor(itemType: Int){
        this.itemType = itemType
    }
    override var itemType: Int
}

class HomeBanner(var url: String, to: String)