package com.chs.module_wan.api

import com.chs.lib_common_ui.model.Banner
import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    //首页
    @GET("article/list/{page}/json")
    suspend fun getHomeList(@Path("page")page:Int) : WanBaseResponse<HomeEntity>

    //系统
    @GET("/tree/json")
    suspend fun getSystemData():WanBaseResponse<List<SystemEntity>>

    //项目种类
    @GET("project/tree/json")
    suspend fun getProjectData():WanBaseResponse<List<ProjectEntity>>

    //项目列表 /project/list/1/json?cid=294
    @GET("/project/list/{page}/json")
    suspend fun getProjectListData(@Path("page")page:Int,@Query("cid")id:Int):
            WanBaseResponse<ArticleEntity>

    //公众号名字
    @GET("wxarticle/chapters/json")
    suspend fun getAccountNameData():WanBaseResponse<List<AccountNameEntity>>

    //公众号列表
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getAccountListData(@Path("id")id:Int,@Path("page")page:Int):
            WanBaseResponse<ArticleEntity>

    //导航
    @GET("navi/json")
    suspend fun getNavigationData():WanBaseResponse<List<NavigationEntity>>

}