package com.chs.lib_common_ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.chs.lib_core.base.BaseViewModel

/**
 * @author：chs
 * date：2020/2/5
 * des：RecyclerView + Paging组件的时候使用
 */
abstract class BaseListViewModel<T> : BaseViewModel(){

    val boundaryPageData = MutableLiveData<Boolean>()

    private var config:PagedList.Config = PagedList.Config.Builder()
        .setPageSize(20)
        .build()
    var dataSource: DataSource<Int, T>? = null
    var pageData:LiveData<PagedList<T>>

    private var factory = object :DataSource.Factory<Int,T>(){
        override fun create(): DataSource<Int, T> {
              if (dataSource == null||(dataSource!=null&& dataSource!!.isInvalid)){
                  dataSource = createDataSource()
              }
            return dataSource!!
        }
    }

    init {
        pageData = LivePagedListBuilder<Int,T>(factory,config).build()
    }

    abstract fun createDataSource(): DataSource<Int, T>


}