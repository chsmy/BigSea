package com.chs.module_video.api

import com.chs.lib_core.http.VideoBaseResponse
import com.chs.module_video.model.VideoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * author：chs
 * date：2020/6/17
 * des：
 */
interface VideoService {

    companion object {
        const val VIDEO_BASE_URL = "https://api.apiopen.top/"
    }

    //列表接口 https://api.apiopen.top/getJoke?page=1&count=5&type=video
    @GET("getJoke")
    suspend fun getVideoList(
        @Query("page") page: Int, @Query("count")
        count: Int, @Query("type") type: String
    ):VideoBaseResponse<List<VideoList>>

}