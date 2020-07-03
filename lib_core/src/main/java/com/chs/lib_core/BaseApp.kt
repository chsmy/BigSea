package com.chs.lib_core

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import androidx.multidex.MultiDex
import com.bumptech.glide.Glide

/**
 *  @author chs
 *  date: 2019-12-13 17:20
 *  des:
 */
open class BaseApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).trimMemory(level)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

}