package com.chs.module_wan.api

import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.model.Banner
import com.chs.module_wan.model.Data
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  @author chs
 *  date: 2020-01-03 16:49
 *  des:
 */
interface WanService{

    companion object{
        const val WAN_BASE_URL = "https://www.wanandroid.com/"
    }

    @GET("banner/json")
    suspend fun getBanner():WanBaseResponse<List<Banner>>

    @GET("article/list/{page}/json")
    suspend fun getHomeList(@Path("page")page:Int) : WanBaseResponse<Data>

}