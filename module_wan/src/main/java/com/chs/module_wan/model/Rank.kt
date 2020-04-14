package com.chs.module_wan.model
import com.google.gson.annotations.SerializedName


/**
 *  @author chs
 *  date: 2020-04-14 15:10
 *  des:
 */

data class Rank(
    @SerializedName("curPage")
    val curPage: Int = 0,
    @SerializedName("datas")
    val datas: List<RankList> = listOf(),
    @SerializedName("offset")
    val offset: Int = 0,
    @SerializedName("over")
    val over: Boolean = false,
    @SerializedName("pageCount")
    val pageCount: Int = 0,
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("total")
    val total: Int = 0
)

data class RankList(
    @SerializedName("coinCount")
    val coinCount: Int = 0,
    @SerializedName("level")
    val level: Int = 0,
    @SerializedName("rank")
    val rank: Int = 0,
    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("username")
    val username: String = ""
)