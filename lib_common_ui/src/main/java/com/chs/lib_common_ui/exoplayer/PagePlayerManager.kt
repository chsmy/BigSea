package com.chs.lib_common_ui.exoplayer

import android.net.Uri
import com.blankj.utilcode.util.Utils
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util

/**
 * author：chs
 * date：2020/6/28
 * des： 用来管理多个视频播放器 方便每个页面的播放 暂停等操作
 */
class PagePlayerManager {

    companion object{
        val sPlayers = HashMap<String,PagePlayer>()
        lateinit var mediaSourceFactory:ProgressiveMediaSource.Factory
    }

    init {
        val app = Utils.getApp()
        //创建http视频资源工厂
        val dataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(app,app.packageName))
        //创建缓存 指定缓存位置和缓存策略 设置最大缓存200M
        val cache = SimpleCache(app.cacheDir, LeastRecentlyUsedCacheEvictor(1024 * 1024 * 200),
                ExoDatabaseProvider(app)
        )
        //将cache和cache工厂相关联 CacheDataSinkFactory用来对cache进行读写
        val cacheDataSinkFactory = CacheDataSinkFactory(cache, Long.MAX_VALUE)
        //创建一个可以边播放 边缓存的本地工厂类
        val cacheDataSourceFactory = CacheDataSourceFactory(cache,dataSourceFactory,
            FileDataSource.Factory(),cacheDataSinkFactory,
            CacheDataSource.FLAG_BLOCK_ON_CACHE,null)
        //创建一个媒体资源MediaSource来加载工厂,由它创建的mediaSource可以实现边缓冲边播放的效果
        //如果需要播放hls,m3u8格式的视频，需要创建DashMediaSource.Factory()
        mediaSourceFactory = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
    }


    fun createMediaSource(url:String): MediaSource {
        return mediaSourceFactory.createMediaSource(Uri.parse(url))
    }

    fun getPagePlayer(pageName:String):PagePlayer{
        var pagePlayer = sPlayers[pageName]
        if(pagePlayer == null){
            pagePlayer = PagePlayer()
            sPlayers[pageName] = pagePlayer
        }
        return pagePlayer
    }

    fun release(pageName:String){
        var pagePlayer = sPlayers.remove(pageName)
        pagePlayer?.release()
    }

}