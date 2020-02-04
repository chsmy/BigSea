package com.chs.bigsea.an

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *  @author chs
 *  date: 2020-01-18 11:05
 *  des:
 */
class HomeData : MultiItemEntity{

    companion object{
        const val LIST_ITEM = 1
        const val OPT_ITEM = 2
    }

    override var itemType: Int
    var title:String = ""
    var opts: ArrayList<HomeOpt>? = null
    var id:Int = 0
    constructor(itemType: Int,title:String){
        this.itemType = itemType
        this.title = title
    }
    constructor(itemType: Int,opts:ArrayList<HomeOpt>){
        this.itemType = itemType
        this.opts = opts
    }

    override fun equals(other: Any?): Boolean {
        if(other == null) return false
        val oth =  other as HomeData
        return oth.id == id && oth.title == title
    }

}

class HomeBanner(var url: String, to: String)

class HomeOpt(var title:String,var iconId:Int,var to:String)