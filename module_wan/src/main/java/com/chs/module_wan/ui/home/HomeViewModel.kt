package com.chs.module_wan.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_common_ui.model.Banner
import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Article
import com.chs.module_wan.model.HomeEntity
import com.kingja.loadsir.core.LoadService
import java.lang.Exception

/**
 * @author：chs
 * date：2020/2/5
 * des：
 */
class HomeViewModel : BaseListViewModel<Int,Article>(){

    val mBanner: MutableLiveData<List<Banner>> = MutableLiveData()

    override fun createDataSource(): PagingSource<Int, Article> {
          return WanDataSource(this)
    }

    fun getBannerData(){
        launch {
            val banner = WanRetrofitClient.service.getBanner()
            mBanner.value = banner.data
            Log.i("banner",mBanner.value.toString())
        }
    }

    override fun onNetReload(it: View?) {
        super.onNetReload(it)
        getBannerData()
        createDataSource().invalidate()
    }

}

class WanDataSource(private val viewModel:BaseListViewModel<Int,Article>):PagingSource<Int,Article>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key?:0
            val result =
                WanRetrofitClient.service.getHomeList(page)
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
