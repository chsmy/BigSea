package com.chs.lib_common_ui.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.lib_common_ui.loading.EmptyCallback
import com.chs.lib_common_ui.loading.LoadingCallback
import com.chs.lib_common_ui.loading.TimeoutCallback
import com.chs.lib_core.event.SingleLiveEvent
import com.chs.lib_core.http.WanBaseResponse
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import kotlinx.coroutines.*
import java.util.concurrent.TimeoutException

/**
 *  @author chs
 *  date: 2020-01-03 17:55
 *  des:
 */
abstract class BaseViewModel : ViewModel(){
    var mLoadService: LoadService<Any>? = null
    val mException: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var isShowLoading: Boolean = true

    /**
     * viewModelScope 是官方提供的ViewModel的扩展，继承CoroutineScope
     * CoroutineScope会跟踪所有它所创建的协程 当当前的ViewModel结束的时候，它所执行的异步任务也需要结束，防止内存泄露
     * 之前我们需要在ViewModel的onCleared方法中通过SupervisorJob的cancel方法来销毁
     * 使用viewModelScope可以简化这个操作，它会自动调用ViewModel的onCleared取消当前操作
     * viewModelScope 默认的是使用 Dispatchers.Main
     */
    private fun launchOnUI(block:suspend CoroutineScope.()->Unit){
        viewModelScope.launch { block() }
    }

    fun launch(block: suspend CoroutineScope.() -> Unit){
         if (isShowLoading) {
             mLoadService?.showCallback(LoadingCallback::class.java)
         }
          launchOnUI{
              doNetRequest(
                  withContext(Dispatchers.IO){block},
                  { handleException(it) },
                  {}
              )
          }
    }

    /**
     * 请求网络
     * @param block 请求体
     * @param exceptionBlock 异常
     * @param complete 请求完成
     */
    private suspend fun doNetRequest(
        block: suspend CoroutineScope.() -> Unit,
        exceptionBlock: suspend CoroutineScope.(e: Exception) -> Unit,
        complete:suspend CoroutineScope.()->Unit
    ){
          coroutineScope {
                try {
                    block()
                }catch (e:Exception){
                    mException.value = e
                    e.printStackTrace()
                    exceptionBlock(e)
                }finally {
                    complete()
                    mLoadService?.showSuccess()
                }
          }
    }

    private fun handleException(e: Exception) {
        if (e is TimeoutException) {
            mLoadService?.showCallback(TimeoutCallback::class.java)
        }
    }

    /**
     * 设置页面加载中的动画
     */
    fun setLoadingViewWrap(view: View){
        mLoadService =  LoadSir.getDefault().register(view) {
            onNetReload(it)
        }
    }

    open fun onNetReload(it: View?) {}


    fun <T> handleResponse(result: WanBaseResponse<T>) : T? {
        if(result.errorCode == 0){
            return result.data
        }else{
            mLoadService?.showCallback(EmptyCallback::class.java)
        }
        return null
    }
}