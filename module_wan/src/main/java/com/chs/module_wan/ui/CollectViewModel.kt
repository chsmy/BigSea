package com.chs.module_wan.ui

import androidx.lifecycle.MutableLiveData
import com.chs.lib_common_ui.base.BaseViewModel
import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.api.WanRetrofitClient

/**
 *  @author chs
 *  date: 2020-04-13 10:16
 *  des:  收藏
 */
class CollectViewModel : BaseViewModel() {


    val mCollectRes = MutableLiveData<WanBaseResponse<Any>>()

    fun collectArticle(id: Int) {
        launch {
            val result = WanRetrofitClient.service.doCollection(id)
            mCollectRes.value = result
        }
    }

    fun unCollectArticle(id: Int) {
        launch {
            val result = WanRetrofitClient.service.doUnCollection(id)
            mCollectRes.value = result
        }
    }

}