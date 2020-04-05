package com.chs.module_wan.ui.system

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.chs.lib_core.base.BaseViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.SystemData

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
class SystemViewModel : BaseViewModel() {

    val mSystemData:MutableLiveData<List<SystemData>> = MutableLiveData<List<SystemData>>()

    fun getSystemData(){
        launch {
           val systemRes = WanRetrofitClient.service.getSystemData()
            mSystemData.value = systemRes.data
        }
    }

}