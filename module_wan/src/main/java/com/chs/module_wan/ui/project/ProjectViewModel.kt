package com.chs.module_wan.ui.project

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Article
import com.chs.module_wan.model.ProjectEntity
import com.kingja.loadsir.core.LoadService

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
class ProjectViewModel : BaseListViewModel<Article>() {
    var projectId:Int = 0
    val mProjectKinds:MutableLiveData<List<ProjectEntity>> = MutableLiveData()

    fun getProjectKindData(){
        launch {
            val projectKins = WanRetrofitClient.service.getProjectData()
            mProjectKinds.value = projectKins.data
        }
    }

    override fun createDataSource(): DataSource<Int, Article> {
        return ProjectDataSource(this,projectId,mLoadService)
    }

}

class ProjectDataSource(private val viewModel:BaseListViewModel<Article>,
                        private val projectId:Int,private val loadService: LoadService<Any>?) : PageKeyedDataSource<Int, Article>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        getProjectData(1,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        getProjectData(params.key+1,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    }

    private fun getProjectData(page:Int, iniCallback: LoadInitialCallback<Int, Article>?,
                               callback: LoadCallback<Int, Article>?){
        viewModel.isShowLoading = page == 1
        viewModel.launch {
            val projectData =
                WanRetrofitClient.service.getProjectListData(page,projectId)
            if(iniCallback!=null){
                iniCallback.onResult(projectData.data.datas,-1,0)
            }else{
                callback?.onResult(projectData.data.datas,page)
            }
        }
    }
}