package com.chs.module_video

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_common_ui.base.BaseViewModel
import com.chs.lib_core.http.VideoBaseResponse
import com.chs.module_video.api.VideoRetrofitClient
import com.chs.module_video.model.VideoList
import com.kingja.loadsir.core.LoadService

class VideoViewModel : BaseListViewModel<VideoList>() {

    override fun createDataSource(): DataSource<Int, VideoList> {
        return VideoDataSource(this,mLoadService)
    }

}

class VideoDataSource(private val viewModel:BaseListViewModel<VideoList>, private val loadService: LoadService<Any>?)
: PageKeyedDataSource<Int, VideoList>(){
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, VideoList>
    ) {
        getVideoList(1,10,"video",callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, VideoList>) {
        getVideoList(params.key,10,"video",null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, VideoList>) {
        TODO("Not yet implemented")
    }

    fun getVideoList(page: Int, count: Int, type: String,iniCallback: LoadInitialCallback<Int, VideoList>?,
                     callback: LoadCallback<Int, VideoList>?) {
        val itemList: VideoBaseResponse<List<VideoList>>? = viewModel.executeVideo {
            VideoRetrofitClient.service.getVideoList(page,count,type)
        }
        if(iniCallback!=null){
            itemList?.result?.let { iniCallback.onResult(it,-1,2) }
        }else{
            itemList?.result?.let { callback?.onResult(it,page+1) }
        }
        viewModel.mLoadService?.showSuccess()
        viewModel.isShowLoading = false
    }

}