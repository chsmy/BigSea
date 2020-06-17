package com.chs.module_video.api

import com.chs.lib_core.http.RetrofitClient

/**
 * author：chs
 * date：2020/6/17
 * des：
 */
object VideoRetrofitClient : RetrofitClient() {

    val service by lazy {
        getService(VideoService::class.java, VideoService.VIDEO_BASE_URL)
    }

}