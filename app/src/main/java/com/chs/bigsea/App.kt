package com.chs.bigsea

import com.chs.lib_common_ui.loading.EmptyCallback
import com.chs.lib_common_ui.loading.ErrorCallback
import com.chs.lib_common_ui.loading.LoadingCallback
import com.chs.lib_common_ui.loading.TimeoutCallback
import com.chs.lib_core.BaseApp
import com.chs.lib_core.InitializeService
import com.kingja.loadsir.core.LoadSir
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader


/**
 *  @author chs
 *  date: 2019-12-13 17:26
 *  des:  application
 */
class App : BaseApp() {

    companion object{
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
                ClassicsHeader(context).setArrowResource(R.drawable.recyclerview_header_arrow)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                ClassicsFooter(context).setArrowDrawable(null)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        InitializeService.start(this)

        LoadSir.beginBuilder()
            .addCallback(ErrorCallback()) //添加各种状态页
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .setDefaultCallback(LoadingCallback::class.java) //设置默认状态页
            .commit()
    }

}