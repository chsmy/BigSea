package com.chs.lib_core

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.chs.lib_core.cloud.CloudManager
import com.chs.lib_core.utils.AppUtil

/**
 * author：chs
 * date：2020/4/9
 * des： 初始化服务
 */
class InitializeService : IntentService("InitializeService") {

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
        CloudManager.initCloud(AppUtil.getApp())
    }

}