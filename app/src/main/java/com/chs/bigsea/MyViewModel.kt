package com.chs.bigsea

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chs.lib_core.base.BaseViewModel
import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.api.WanRetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *  @author chs
 *  date: 2020-01-02 16:57
 *  des:
 */
class MyViewModel :BaseViewModel(){
    private val error by lazy { MutableLiveData<Exception>() }

    private val finally by lazy { MutableLiveData<Int>() }

    suspend fun <T : Any> request(call: suspend () -> WanBaseResponse<T>): WanBaseResponse<T> {
        return withContext(Dispatchers.IO) {
            call.invoke()
        }.apply {
            //这儿可以对返回结果errorCode做一些特殊处理，比如token失效等，可以通过抛出异常的方式实现
            //例：当token失效时，后台返回errorCode 为 100，下面代码实现,再到baseActivity通过观察error来处理

        }
    }

    fun loadDatas() {
        launch{
           val resben =  WanRetrofitClient.service.getHomeList()
            Log.i("resben",resben.toString())
        }
    }
}