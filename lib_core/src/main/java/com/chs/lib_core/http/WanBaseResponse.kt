package com.chs.lib_core.http

/**
 *  @author chs
 *  date: 2019-12-19 15:51
 *  des:
 */
data class WanBaseResponse<out T>(val errorCode:Int=-1,val errorMsg:String,val data:T):BaseResponse(){}