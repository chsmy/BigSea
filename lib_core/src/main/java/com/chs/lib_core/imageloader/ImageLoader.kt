package com.chs.lib_core.imageloader

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.chs.lib_core.imageloader.base.ImageConfig

/**
 *  @author chs
 *  date: 2019-12-13 15:34
 *  des:
 */
object ImageLoader{

    fun url(url:String): ImageConfig.Builder {
         return ImageConfig.Builder().url(url)
    }

    fun trimMemory(application: Application?, level: Int) {
        Glide.get(application!!).trimMemory(level)
    }

    fun clearMemoryCache(application: Application?) {
        Glide.get(application!!).clearMemory()
    }

    fun pauseRequest(context: Context){
        Glide.with(context).pauseRequests()
    }

    fun resumeRequest(context: Context){
        Glide.with(context).resumeRequests()
    }

}