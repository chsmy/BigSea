package com.chs.bigsea.an

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chs.lib_core.base.BaseViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Banner

class AnViewModel : BaseViewModel() {

    val mBanner:MutableLiveData<List<Banner>> = MutableLiveData()

    fun getBannerData(){
           launch {
               val banner = WanRetrofitClient.service.getBanner()
               mBanner.value = banner.data
               Log.i("banner",mBanner.value.toString())
           }
    }

}
