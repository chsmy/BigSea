package com.chs.lib_core

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSessionConfig

/**
 * author：chs
 * date：2020/4/9
 * des： 初始化服务
 */
class InitializeService : IntentService("InitializeService") {

    private val DEMO_URL: String = "https://www.wanandroid.com/"

    companion object{
        private const val ACTION_INIT = "initApplication"
        fun start(context:Context){
            val intent = Intent(context,InitializeService::class.java)
            intent.action = ACTION_INIT
            context.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
          val action = intent?.action
        if(TextUtils.equals(action, ACTION_INIT)){
            initApplication()
        }
    }

    /**
     * application中可以延时初始化的任务
     */
    private fun initApplication() {
        //初始化webview
        initWebView()

    }

    private fun initWebView() {
        val sessionConfigBuilder = SonicSessionConfig.Builder()
        sessionConfigBuilder.setSupportLocalServer(true)
        val preloadSuccess=
            SonicEngine.getInstance().preCreateSession(DEMO_URL, sessionConfigBuilder.build())
    }

}