package com.chs.lib_common_ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

/**
 * @author：chs
 * date：2020/2/5
 * des：RecyclerView + Paging组件的时候使用
 */
abstract class BaseListViewModel<T> : BaseViewModel(){

    private val pageSize:Int = 20
    val boundaryPageData = MutableLiveData<Boolean>()

    private val config:PagedList.Config
    var dataSource: DataSource<Int, T>? = null
    val pageData:LiveData<PagedList<T>>

    private var factory = object :DataSource.Factory<Int,T>(){
        override fun create(): DataSource<Int, T> {
              if (dataSource == null||(dataSource!=null&& dataSource!!.isInvalid)){
                  dataSource = createDataSource()
              }
            return dataSource!!
        }
    }

    init {
        config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .build()
        pageData = LivePagedListBuilder(factory,config).build()
    }

    abstract fun createDataSource(): DataSource<Int, T>


}