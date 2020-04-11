package com.chs.bigsea.ui.login

import androidx.lifecycle.MutableLiveData
import com.chs.module_wan.model.LoginEntity
import com.chs.lib_common_ui.base.BaseViewModel
import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.api.WanRetrofitClient

/**
 * author：chs
 * date：2020/4/11
 * des：
 */
class LoginViewModel : BaseViewModel() {

    val mLoginEntity = MutableLiveData<LoginEntity>()

    fun doLogin(userName:String , password:String){
        launch {
            val loginRes = WanRetrofitClient.service.doLogin(userName,password)
            mLoginEntity.value = handleResponse(loginRes)
        }
    }

}