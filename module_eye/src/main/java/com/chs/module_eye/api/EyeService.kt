package com.chs.module_eye.api

/**
 * author：chs
 * date：2020/6/24
 * des：开眼api
 */
interface EyeService {

    companion object{
        const val EYE_BASE_URL = "https://www.wanandroid.com/"
    }


    //首页
    //1.发现更多
    //
    //请求地址： http://baobab.kaiyanapp.com/api/v7/index/tab/discovery
    //2.每日推荐
    //
    //请求地址： http://baobab.kaiyanapp.com/api/v5/index/tab/allRec
    //3.日报精选
    //
    //请求地址 ： http://baobab.kaiyanapp.com/api/v5/index/tab/feed
    //社区
    //1.推荐
    //
    //请求地址： http://baobab.kaiyanapp.com/api/v7/community/tab/rec
    //2.关注
    //
    //请求地址： http://baobab.kaiyanapp.com/api/v6/community/tab/follow
    //通知
    //1.主题
    //
    //请求地址： http://baobab.kaiyanapp.com/api/v7/tag/tabList
    //2.通知
    //
    //请求地址 ： http://baobab.kaiyanapp.com/api/v3/messages
    //3.互动
    //
    //请求地址 ： http://baobab.kaiyanapp.com/api/v7/topic/list
    //视频详情页
    //1.相关推荐
    //
    //请求地址 ：http://baobab.kaiyanapp.com/api/v4/video/related?id=186856
    //
    //评论
    //
    //请求地址 ：http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=186856
    //

}