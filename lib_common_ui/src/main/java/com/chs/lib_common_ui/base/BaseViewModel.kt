package com.chs.lib_common_ui.base

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.lib_common_ui.loading.LoadingCallback
import com.chs.lib_core.event.SingleLiveEvent
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import kotlinx.coroutines.*

/**
 *  @author chs
 *  date: 2020-01-03 17:55
 *  des:
 */
abstract class BaseViewModel : ViewModel(){
    var mLoadService: LoadService<Any>? = null
    val mException: SingleLiveEvent<Throwable> = SingleLiveEvent()

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
         mLoadService?.showCallback(LoadingCallback::class.java)
          launchOnUI{
              handleException(
                  withContext(Dispatchers.IO){block},
                  {},
                  {}
              )
          }
    }


    /**
     * 统一处理异常
     * @param block 请求体
     * @param exceptionBlock 异常
     * @param complete 请求完成
     */
    private suspend fun handleException(
        block: suspend CoroutineScope.() -> Unit,
        exceptionBlock: suspend CoroutineScope.() -> Unit,
        complete:suspend CoroutineScope.()->Unit
    ){
          coroutineScope {
                try {
                    block()
                }catch (e:Exception){
                    mException.value = e
                    e.printStackTrace()
                    exceptionBlock()
                }finally {
                    complete()
                    mLoadService?.showSuccess()
                }
          }
    }

    fun setLoadingViewWrap(view: View){
        mLoadService =  LoadSir.getDefault().register(view) {
            onNetReload(it)
        }
    }

    private fun onNetReload(it: View?) {}

}