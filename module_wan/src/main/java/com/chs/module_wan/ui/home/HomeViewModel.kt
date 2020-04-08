package com.chs.module_wan.ui.home

import android.util.Log
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
}

class WanDataSource(private val viewModel:BaseListViewModel<Article>, private val loadService: LoadService<Any>?)
    : PageKeyedDataSource<Int,Article>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        getHomeListData(0,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        getHomeListData(params.key+1,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
     }

    private fun getHomeListData(page:Int, iniCallback: LoadInitialCallback<Int, Article>?,
                                callback: LoadCallback<Int, Article>?){
        viewModel.launch {
            val homeList = WanRetrofitClient.service.getHomeList(page)

            if(iniCallback!=null){
                iniCallback.onResult(homeList.data.datas,-1,0)
            }else{
                callback?.onResult(homeList.data.datas,page)
            }
            loadService?.showSuccess()
        }
    }

}