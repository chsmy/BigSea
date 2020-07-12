package com.chs.module_wan.ui.todo

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Rank
import com.chs.module_wan.model.RankList
import com.chs.module_wan.model.ToDoEntity
import java.lang.Exception

/**
 *  @author chs
 *  date: 2020-04-14 15:08
 *  des:
 */
class TodoViewModel: BaseListViewModel<Int, ToDoEntity>() {

    override fun createDataSource(): PagingSource<Int, ToDoEntity> {
        return TodoDataSource(this)
    }

}

class TodoDataSource(private val viewModel:BaseListViewModel<Int,ToDoEntity>):PagingSource<Int,ToDoEntity>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ToDoEntity> {
        return try {
            val page = params.key?:1
            val result =
                WanRetrofitClient.service.getTodoList(page)
            viewModel.mLoadService?.showSuccess()
            viewModel.isShowLoading = false
            LoadResult.Page(
                //需要加载的数据
                data = result.data.datas,
                //如果可以往上加载更多就设置该参数，否则不设置
                prevKey = null,
                //加载下一页的key 如果传null就说明到底了
                nextKey = if(result.data.curPage==result.data.pageCount) null else page+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}