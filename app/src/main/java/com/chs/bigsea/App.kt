package com.chs.bigsea

import com.chs.lib_core.BaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 *  @author chs
 *  date: 2019-12-13 17:26
 *  des:  application
 */
class App : BaseApp() {



    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidContext(this@App)
//            modules(appModule)
//        }

    }

}