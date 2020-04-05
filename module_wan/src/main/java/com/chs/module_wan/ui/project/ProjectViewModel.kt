package com.chs.module_wan.ui.project

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_core.base.BaseViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.DataX
import com.chs.module_wan.model.ProjectData
import com.chs.module_wan.model.ProjectListData
import com.chs.module_wan.model.ProjectListItemData

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
class ProjectViewModel : BaseListViewModel<ProjectListItemData>() {
    var projectId:Int = 0
    val mProjectKinds:MutableLiveData<List<ProjectData>> = MutableLiveData()

    fun getProjectKindData(){
        launch {
            val projectKins = WanRetrofitClient.service.getProjectData()
            mProjectKinds.value = projectKins.data
        }
    }

    override fun createDataSource(): DataSource<Int, ProjectListItemData> {
        return ProjectDataSource(this,projectId)
    }

}

class ProjectDataSource(private val viewModel:BaseListViewModel<ProjectListItemData>,
                        private val projectId:Int) : PageKeyedDataSource<Int, ProjectListItemData>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ProjectListItemData>) {
        getProjectData(0,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ProjectListItemData>) {
        getProjectData(params.key+1,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ProjectListItemData>) {
    }

    private fun getProjectData(page:Int,iniCallback: LoadInitialCallback<Int, ProjectListItemData>?,
                               callback: LoadCallback<Int, ProjectListItemData>?){
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