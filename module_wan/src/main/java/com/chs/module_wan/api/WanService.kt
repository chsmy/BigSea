package com.chs.module_wan.api

import com.chs.lib_core.http.WanBaseResponse
import com.chs.lib_common_ui.model.Banner
import com.chs.module_wan.model.Data
import com.chs.module_wan.model.ProjectData
import com.chs.module_wan.model.ProjectListData
import com.chs.module_wan.model.SystemData
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
    suspend fun getHomeList(@Path("page")page:Int) : WanBaseResponse<Data>

    //系统
    @GET("/tree/json")
    suspend fun getSystemData():WanBaseResponse<List<SystemData>>

    //项目种类
    @GET("project/tree/json")
    suspend fun getProjectData():WanBaseResponse<List<ProjectData>>

    //项目列表 /project/list/1/json?cid=294
    @GET("/project/list/{page}/json")
    suspend fun getProjectListData(@Path("page")page:Int,@Query("cid")id:Int):
            WanBaseResponse<ProjectListData>

}