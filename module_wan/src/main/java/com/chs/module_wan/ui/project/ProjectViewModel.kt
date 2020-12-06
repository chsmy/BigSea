package com.chs.module_wan.ui.project

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Article
import com.chs.module_wan.model.ArticleEntity
import com.chs.module_wan.model.ProjectEntity
import com.kingja.loadsir.core.LoadService
import java.lang.Exception

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
class ProjectViewModel  @ViewModelInject constructor(): BaseListViewModel<Int,Article>() {
    var projectId:Int = 0
    val mProjectKinds:MutableLiveData<List<ProjectEntity>> = MutableLiveData()

    fun getProjectKindData(){
        launch {
            val projectKins = WanRetrofitClient.service.getProjectData()
            mProjectKinds.value = projectKins.data
        }
    }

    override fun createDataSource(): PagingSource<Int, Article> {
        return ProjectDataSource(this,projectId)
    }

}

class ProjectDataSource(private val viewModel:BaseListViewModel<Int,Article>,
                        private val projectId:Int):PagingSource<Int,Article>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key?:0
            val result =
                WanRetrofitClient.service.getProjectListData(page,projectId)
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