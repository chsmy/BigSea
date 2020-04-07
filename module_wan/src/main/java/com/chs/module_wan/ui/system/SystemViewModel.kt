package com.chs.module_wan.ui.system

import androidx.lifecycle.MutableLiveData
import com.chs.lib_core.base.BaseViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.SystemEntity

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
class SystemViewModel : BaseViewModel() {

    val mSystemEntity:MutableLiveData<List<SystemEntity>> = MutableLiveData<List<SystemEntity>>()

    fun getSystemData(){
        launch {
           val systemRes = WanRetrofitClient.service.getSystemData()
            mSystemEntity.value = systemRes.data
        }
    }

}