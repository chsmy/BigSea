package com.chs.module_eye.ui.recommend

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_core.http.EyeBaseResponse
import com.chs.module_eye.api.EyeRetrofitClient
import com.chs.module_eye.model.RecommendItem

/**
 * author：chs
 * date：2020/6/25
 * des：
 */
class RecommendViewModel: BaseListViewModel<RecommendItem>() {

    override fun createDataSource(): DataSource<Int, RecommendItem> {
        return RecommendDataSource(this)
    }

}

class RecommendDataSource(private val viewModel:BaseListViewModel<RecommendItem>)
    :ItemKeyedDataSource<Int,RecommendItem>(){

    var url = "http://baobab.kaiyanapp.com/api/v7/community/tab/rec"
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<RecommendItem>
    ) {
        loadData(callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<RecommendItem>) {
        loadData(callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<RecommendItem>) {
    }

    override fun getKey(item: RecommendItem): Int {
       return item.id
    }
    private fun loadData(callback: LoadCallback<RecommendItem>){
        val resData:EyeBaseResponse<List<RecommendItem>>? = viewModel.executeEye {
            EyeRetrofitClient.service.getRecData(url)
        }
        url = resData?.nextPageUrl?:""
        callback.onResult(resData?.itemList!!)
        viewModel.mLoadService?.showSuccess()
        viewModel.isShowLoading = false
    }
}
