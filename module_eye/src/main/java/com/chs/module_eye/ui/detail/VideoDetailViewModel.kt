package com.chs.module_eye.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chs.lib_common_ui.base.BaseViewModel
import com.chs.module_eye.api.EyeRetrofitClient
import com.chs.module_eye.model.DetailPageData
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * author：chs
 * date：2020/6/30
 * des：
 */
class VideoDetailViewModel : BaseViewModel() {

    val detailPageData = MutableLiveData<DetailPageData>()

    fun getDetailData(id: Long) {
        viewModelScope.launch {
            val detailResAsync = async { EyeRetrofitClient.service.getVideoDetail(id) }
            val detailRecommendAsync = async { EyeRetrofitClient.service.getDetailRecommend(id) }
            val detailCommonAsync = async { EyeRetrofitClient.service.getDetailComm(id) }

            val detailRes = detailResAsync.await()
            val detailRecommend = detailRecommendAsync.await()
            val detailCommon = detailCommonAsync.await()

            val detailData = DetailPageData(detailRes,detailRecommend.itemList,detailCommon.itemList)
            detailPageData.postValue(detailData)
        }
    }

}