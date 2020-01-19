package com.chs.bigsea.an

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chs.lib_core.base.BaseViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Banner
import com.chs.module_wan.model.Data
import com.chs.module_wan.model.DataX
import kotlin.collections.forEachIndexed as forEachIndexed1

class AnViewModel : BaseViewModel() {

    val mBanner:MutableLiveData<List<Banner>> = MutableLiveData()
    val mHomeList:MutableLiveData<List<DataX>> = MutableLiveData()
    val mHomeRecyclerData:MutableLiveData<MutableList<HomeData>> = MutableLiveData()

    fun getBannerData(){
           launch {
               val banner = WanRetrofitClient.service.getBanner()
               mBanner.value = banner.data
               Log.i("banner",mBanner.value.toString())
           }
    }
    fun getHomeListData(){
           launch {
               val homeList = WanRetrofitClient.service.getHomeList()
               mHomeList.value = homeList.data.datas
               val homeData  = mutableListOf<HomeData>()
               homeList.data.datas.forEach{
                   homeData.add(HomeData(HomeData.LIST_ITEM,it.title))
               }
               mHomeRecyclerData.value = homeData
           }
    }

}
