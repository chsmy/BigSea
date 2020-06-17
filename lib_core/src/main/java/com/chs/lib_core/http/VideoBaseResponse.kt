package com.chs.lib_core.http

/**
 * author：chs
 * date：2020/6/17
 * des：
 */
data class VideoBaseResponse<out T>(val code:Int,val message:String,val result:T) {
}