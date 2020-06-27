package com.chs.module_video

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_common_ui.base.BaseViewModel
import com.chs.lib_core.http.VideoBaseResponse
import com.chs.module_video.api.VideoRetrofitClient
import com.chs.module_video.model.VideoList
import com.kingja.loadsir.core.LoadService
import java.lang.Exception

class VideoViewModel : BaseListViewModel<Int,VideoList>() {

    override fun createDataSource(): PagingSource<Int, VideoList> {
        return VideoDataSource(this)
    }

}

class VideoDataSource(private val viewModel:BaseListViewModel<Int,VideoList>):PagingSource<Int,VideoList>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoList> {
        return try {
            val page = params.key?:1
            val result =
                VideoRetrofitClient.service.getVideoList(page,5,"video")
            viewModel.mLoadService?.showSuccess()
            viewModel.isShowLoading = false
            LoadResult.Page(
                //需要加载的数据
                data = result.result,
                //如果可以往上加载更多就设置该参数，否则不设置
                prevKey = null,
                //加载下一页的key 如果传null就说明到底了
                nextKey = if(result.code != 200) null else page+1
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}