package com.chs.module_wan.ui.todo

import androidx.lifecycle.MutableLiveData
import com.chs.lib_common_ui.base.BaseViewModel
import com.chs.module_wan.api.WanRetrofitClient

/**
 * author：chs
 * date：2020/7/12
 * des： 创建一条todo
 */
class CreateToDoModel:BaseViewModel() {

    val createRes = MutableLiveData<Int>()

    /**
     *  title: 新增标题（必须）
     *  content: 新增详情（必须）
     *  date: 2018-08-01 预定完成时间（不传默认当天，建议传）
     *  type: 大于0的整数（可选）； 1工作 2 学习 3生活
     *  priority 大于0的整数（可选）；
     */
    fun createRemoteToDo(title:String,content:String,time:String,type:Int,priority:Int = 2){

        launch {
            val res = WanRetrofitClient.service.createToDo(title,content,time,type,priority)
            createRes.postValue(res.errorCode)
        }

    }

}