package com.chs.lib_core.http

/**
 *  @author chs
 *  date: 2019-12-19 15:51
 *  des:
 */
data class EyeBaseResponse<out T>(val count:Int, val total:Int,
                                  val nextPageUrl:String, val adExist:Boolean,
                                  val itemList:T):BaseResponse(){}