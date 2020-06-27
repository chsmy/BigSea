package com.chs.module_eye.ui.recommend

import androidx.paging.DataSource
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_core.http.EyeBaseResponse
import com.chs.module_eye.api.EyeRetrofitClient
import com.chs.module_eye.model.RecommendItem
import java.lang.Exception

/**
 * author：chs
 * date：2020/6/25
 * des：
 */
class RecommendViewModel: BaseListViewModel<String,RecommendItem>() {

    override fun createDataSource(): PagingSource<String, RecommendItem> {
        return RecommendDataSource(this)
    }

}

class RecommendDataSource(private val viewModel:BaseListViewModel<String,RecommendItem>)
    :PagingSource<String,RecommendItem>(){
    override suspend fun load(params: LoadParams<String>): LoadResult<String, RecommendItem> {
        return try {
            val url = params.key?:"http://baobab.kaiyanapp.com/api/v7/community/tab/rec"
            val result =
                EyeRetrofitClient.service.getRecData(url)
            viewModel.mLoadService?.showSuccess()
            viewModel.isShowLoading = false
            LoadResult.Page(
                //需要加载的数据
                data = result.itemList,
                //如果可以往上加载更多就设置该参数，否则不设置
                prevKey = null,
                //加载下一页的key 如果传null就说明到底了
                nextKey = result.nextPageUrl
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}
