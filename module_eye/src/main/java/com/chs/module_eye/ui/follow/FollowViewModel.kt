package com.chs.module_eye.ui.follow

import androidx.paging.PagingSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.module_eye.api.EyeRetrofitClient
import com.chs.module_eye.model.Item
import java.lang.Exception

/**
 * author：chs
 * date：2020/6/27
 * des：
 */
class FollowViewModel: BaseListViewModel<String, Item>() {


    override fun createDataSource(): PagingSource<String, Item> {
        return FollowDataSource(this)
    }

}

class FollowDataSource(private val viewModel:BaseListViewModel<String, Item>)
    :PagingSource<String,Item>(){
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Item> {
        return try {
            val url = params.key?:"http://baobab.kaiyanapp.com/api/v6/community/tab/follow"
            val result =
                EyeRetrofitClient.service.getFollowData(url)
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