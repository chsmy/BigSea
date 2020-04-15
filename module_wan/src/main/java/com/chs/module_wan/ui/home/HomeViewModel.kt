package com.chs.module_wan.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_common_ui.model.Banner
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Article
import com.kingja.loadsir.core.LoadService

/**
 * @author：chs
 * date：2020/2/5
 * des：
 */
class HomeViewModel : BaseListViewModel<Article>(){

    val mBanner: MutableLiveData<List<Banner>> = MutableLiveData()

    override fun createDataSource(): DataSource<Int, Article> {
          return WanDataSource(this,mLoadService)
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

class WanDataSource(private val viewModel:BaseListViewModel<Article>, private val loadService: LoadService<Any>?)
    : PageKeyedDataSource<Int,Article>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        getHomeListData(1,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        getHomeListData(params.key,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
     }

    private fun getHomeListData(page:Int, iniCallback: LoadInitialCallback<Int, Article>?,
                                callback: LoadCallback<Int, Article>?){
        val homeList = WanRetrofitClient.service.getHomeList(page)
            .execute().body()
        if(iniCallback!=null){
            homeList?.data?.datas?.let { iniCallback.onResult(it,-1,2) }
        }else{
            homeList?.data?.datas?.let { callback?.onResult(it,page+1) }
        }
        viewModel.mLoadService?.showSuccess()
        viewModel.isShowLoading = false
    }

}