package com.chs.lib_core.cloud

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.chs.lib_core.constant.SpConstant
import com.chs.lib_core.extension.getSpValue
import com.chs.lib_core.extension.logI
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Message

/**
 * author：chs
 * date：2020/7/4
 * des：融云 服务
 */
class CloudService: Service() {

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        connectCloud()
    }

    /**
     * 连接融云
     */
    private fun connectCloud() {
        val token = getSpValue(SpConstant.IM_TOKEN,"")
        logI(token)
        CloudManager.connect(token)
        /**
         * 接收实时或者离线消息。
         * 注意:
         * 1. 针对接收离线消息时，服务端会将 200 条消息打成一个包发到客户端，客户端对这包数据进行解析。
         * 2. hasPackage 标识是否还有剩余的消息包，left 标识这包消息解析完逐条抛送给 App 层后，剩余多少条。
         * 如何判断离线消息收完：
         * 1. hasPackage 和 left 都为 0；
         * 2. hasPackage 为 0 标识当前正在接收最后一包（200条）消息，left 为 0 标识最后一包的最后一条消息也已接收完毕。
         *
         * @param message    接收到的消息对象
         * @param left       每个数据包数据逐条上抛后，还剩余的条数
         * @param hasPackage 是否在服务端还存在未下发的消息包
         * @param offline    消息是否离线消息
         * @return 是否处理消息。 如果 App 处理了此消息，返回 true; 否则返回 false 由 SDK 处理。
         */
        CloudManager.setOnReceiveMessageListener(object :RongIMClient.OnReceiveMessageWrapperListener(){
            override fun onReceived(message: Message?, left: Int, hasPackage: Boolean, offline: Boolean): Boolean {
                logI("message:$message")
                return false
            }
        })
    }

}