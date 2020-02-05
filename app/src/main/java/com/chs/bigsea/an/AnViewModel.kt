package com.chs.bigsea.an

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chs.bigsea.R
import com.chs.lib_core.base.BaseViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Banner
import com.chs.module_wan.model.DataX

class AnViewModel : BaseViewModel() {

    val mBanner:MutableLiveData<List<Banner>> = MutableLiveData()
    val mHomeList:MutableLiveData<List<DataX>> = MutableLiveData()
    val mHomeRecyclerData:MutableLiveData<MutableList<HomeData>> = MutableLiveData()

    init {
        mHomeRecyclerData.value = mutableListOf()
        val optList = ArrayList<HomeOpt>()
        optList.add(HomeOpt("体系", R.mipmap.home_opt_1,""))
        optList.add(HomeOpt("导航", R.mipmap.home_opt_2,""))
        optList.add(HomeOpt("项目", R.mipmap.home_opt_3,""))
        optList.add(HomeOpt("公众号", R.mipmap.home_opt_4,""))
        mHomeRecyclerData.value?.add(HomeData(HomeData.OPT_ITEM,optList))
    }

    fun getBannerData(){
           launch {
               val banner = WanRetrofitClient.service.getBanner()
               mBanner.value = banner.data
               Log.i("banner",mBanner.value.toString())
           }
    }
    fun getHomeListData(){
           launch {
               val homeList = WanRetrofitClient.service.getHomeList(0)
               mHomeList.value = homeList.data.datas
               val homeData  = mutableListOf<HomeData>()
               homeList.data.datas.forEach{
                   homeData.add(HomeData(HomeData.LIST_ITEM,it.title))
               }
//               val allList =  mHomeRecyclerData.value
//               allList?.addAll(homeData)
               mHomeRecyclerData.value = homeData
           }
    }

}
