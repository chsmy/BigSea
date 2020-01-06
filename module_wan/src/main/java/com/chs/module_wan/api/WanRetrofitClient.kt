package com.chs.module_wan.api

import com.chs.lib_core.http.RetrofitClient

/**
 *  @author chs
 *  date: 2020-01-03 16:45
 *  des:
 */
object WanRetrofitClient : RetrofitClient(){

    val service by lazy { getService(
        com.chs.module_wan.api.WanService::class.java,
        com.chs.module_wan.api.WanService.WAN_BASE_URL) }

}