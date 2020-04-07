package com.chs.module_wan.ui.navigation

import androidx.lifecycle.MutableLiveData
import com.chs.lib_core.base.BaseViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.NavigationEntity

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class NavigationViewModel : BaseViewModel() {

    val mNavigation : MutableLiveData<List<NavigationEntity>> = MutableLiveData()

    fun getNavigationData(){
        launch {
            val navData = WanRetrofitClient.service.getNavigationData()
            mNavigation.value = navData.data
        }
    }

}