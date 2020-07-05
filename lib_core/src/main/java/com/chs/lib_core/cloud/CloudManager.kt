package com.chs.lib_core.cloud

import android.content.Context
import com.chs.lib_core.extension.logE
import com.chs.lib_core.extension.logI
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.imlib.model.MessageContent
import io.rong.message.TextMessage

/**
 * author：chs
 * date：2020/7/4
 * des：融云的管理类
 */
object CloudManager {

    val TOKEN_URL: String = "http://api-cn.ronghub.com/user/getToken.json"
    val CLOUD_KEY: String = "82hegw5u8voex"
    val CLOUD_SECRET: String = "9EOLoVsTVlOQI0"

    /**
     * 初始换融云sdk
     */
    fun initCloud(context: Context){
        RongIMClient.init(context,CLOUD_KEY)
    }

    /**
     * 连接融云服务
     */
    fun connect(token:String){
        logI("connect")
        RongIMClient.connect(token,object :RongIMClient.ConnectCallback(){
            /**
             * 成功回调
             * @param userId 当前用户 ID
             */
            override fun onSuccess(s: String?) {
                logI("连接成功 $s")
            }
            /**
             * 数据库回调.
             * @param code 数据库打开状态. DATABASE_OPEN_SUCCESS 数据库打开成功; DATABASE_OPEN_ERROR 数据库打开失败
             */
            override fun onDatabaseOpened(p0: RongIMClient.DatabaseOpenStatus?) {
                logI("database:$p0")
            }
            /**
             * 错误回调
             * @param errorCode 错误码
             */
            override fun onError(errorCode: RongIMClient.ConnectionErrorCode) {
                if(errorCode == RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_INCORRECT) {
                    //从 APP 服务获取新 token，并重连
                } else {
                    //无法连接 IM 服务器，请根据相应的错误码作出对应处理
                }
                logE("连接出错 ${errorCode.toString()}")
            }
        })
    }

    fun disconnect(){
        RongIMClient.getInstance().disconnect()
    }

    fun logout(){
        RongIMClient.getInstance().logout()
    }
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
    fun setOnReceiveMessageListener(listener:RongIMClient.OnReceiveMessageListener){
        RongIMClient.setOnReceiveMessageListener(listener)
    }

    /**
     * 发送消息的回调
     */
    private val messageCallBack = object : IRongCallback.ISendMessageCallback{
        override fun onAttached(p0: Message?) {
            logI("消息保存到了数据库中")
        }

        override fun onSuccess(message: Message?) {
            logI("消息发送成功 $message")
        }

        override fun onError(message: Message?, p1: RongIMClient.ErrorCode?) {
            logI("消息发送失败$message")
        }

    }

    /**
     * 发送文本消息
     */
    fun sendTextMessage(text:String,targetId:String){
        val textMessageContent = TextMessage.obtain(text)
        val message = Message.obtain(targetId, Conversation.ConversationType.PRIVATE,textMessageContent)
        RongIMClient.getInstance().sendMessage(message,null,null,messageCallBack)
    }

}