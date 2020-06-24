package com.chs.module_eye.api

import com.chs.lib_core.http.RetrofitClient

/**
 *  @author chs
 *  date: 2020-01-03 16:45
 *  des:
 */
object EyeRetrofitClient : RetrofitClient(){

    val service by lazy {
        getService(EyeService::class.java, EyeService.EYE_BASE_URL)
    }



}