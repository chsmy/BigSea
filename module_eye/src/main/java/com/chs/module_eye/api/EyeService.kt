package com.chs.module_eye.api

import com.chs.lib_core.http.EyeBaseResponse
import com.chs.module_eye.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * author：chs
 * date：2020/6/24
 * des：开眼api
 */
interface EyeService {

    companion object{
        const val EYE_BASE_URL = "http://baobab.kaiyanapp.com/"
    }

    //首页
    //1.发现更多
    //请求地址： http://baobab.kaiyanapp.com/api/v7/index/tab/discovery
    //2.每日推荐
    //请求地址： http://baobab.kaiyanapp.com/api/v5/index/tab/allRec
    //3.日报精选
    //请求地址 ： http://baobab.kaiyanapp.com/api/v5/index/tab/feed
    //社区
    //1.推荐
    //请求地址： http://baobab.kaiyanapp.com/api/v7/community/tab/rec
    @GET
    suspend fun getRecData(@Url url:String):EyeBaseResponse<List<RecommendItem>>
    //2.关注
    //请求地址： http://baobab.kaiyanapp.com/api/v6/community/tab/follow
    @GET
    suspend fun getFollowData(@Url url:String):EyeBaseResponse<List<Item>>

    /**
     * 视频详情
     */
    @GET("api/v2/video/{id}")
    suspend fun getVideoDetail(@Path("id") videoId: Long): Detail
    //通知
    //1.主题
    //请求地址： http://baobab.kaiyanapp.com/api/v7/tag/tabList
    //2.通知
    //请求地址 ： http://baobab.kaiyanapp.com/api/v3/messages
    //3.互动
    //请求地址 ： http://baobab.kaiyanapp.com/api/v7/topic/list
    /**
     * 1.相关推荐
     * 请求地址 ：http://baobab.kaiyanapp.com/api/v4/video/related?id=186856
     */
    @GET("api/v4/video/related")
    suspend fun getDetailRecommend(@Query("id") id:Long):EyeBaseResponse<List<DetailRecomm>>
    /**
     * 评论
     * 请求地址 ：http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=186856
     */
    @GET("api/v2/replies/video")
    suspend fun getDetailComm(@Query("videoId") id:Long): EyeBaseResponse<List<DetailCommItem>>
}