package com.chs.bigsea

import com.chs.lib_common_ui.loading.EmptyCallback
import com.chs.lib_common_ui.loading.ErrorCallback
import com.chs.lib_common_ui.loading.LoadingCallback
import com.chs.lib_common_ui.loading.TimeoutCallback
import com.chs.lib_core.BaseApp
import com.kingja.loadsir.core.LoadSir


/**
 *  @author chs
 *  date: 2019-12-13 17:26
 *  des:  application
 */
class App : BaseApp() {



    override fun onCreate() {
        super.onCreate()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback()) //添加各种状态页
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .setDefaultCallback(LoadingCallback::class.java) //设置默认状态页
            .commit()
//        startKoin {
//            androidContext(this@App)
//            modules(appModule)
//        }

    }

}