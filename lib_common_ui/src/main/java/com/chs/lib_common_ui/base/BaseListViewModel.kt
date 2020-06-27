package com.chs.lib_common_ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.blankj.utilcode.util.ThreadUtils

/**
 * @author：chs
 * date：2020/2/5
 * des：RecyclerView + Paging组件的时候使用
 */
abstract class BaseListViewModel<T : Any, D:Any> : BaseViewModel(){

    val pageData:LiveData<PagingData<D>> by lazy { getPagingData().asLiveData() }

    private fun getPagingData() = Pager(PagingConfig(pageSize = 20)){
        createDataSource()
    }.flow

    abstract fun createDataSource(): PagingSource<T, D>

}