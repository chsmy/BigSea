package com.chs.module_wan.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.lib_common_ui.model.Banner
import com.chs.module_wan.model.DataX

/**
 * @author：chs
 * date：2020/2/5
 * des：
 */
class WanViewModel : BaseListViewModel<DataX>(){

    val mBanner: MutableLiveData<List<Banner>> = MutableLiveData()

    override fun createDataSource(): DataSource<Int, DataX> {
          return WanDataSource(this)
    }

    fun getBannerData(){
        launch {
            val banner = WanRetrofitClient.service.getBanner()
            mBanner.value = banner.data
            Log.i("banner",mBanner.value.toString())
        }
    }
}

class WanDataSource(private val viewModel:BaseListViewModel<DataX>) : PageKeyedDataSource<Int,DataX>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DataX>) {
        getHomeListData(0,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataX>) {
        getHomeListData(params.key+1,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataX>) {
     }

    private fun getHomeListData(page:Int, iniCallback: LoadInitialCallback<Int, DataX>?,
                                callback: LoadCallback<Int, DataX>?){
        viewModel.launch {
            val homeList = WanRetrofitClient.service.getHomeList(page)

            if(iniCallback!=null){
                iniCallback.onResult(homeList.data.datas,-1,0)
            }else{
                callback?.onResult(homeList.data.datas,page)
            }
        }
    }

}