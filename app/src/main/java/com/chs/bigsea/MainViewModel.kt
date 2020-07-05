package com.chs.bigsea

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.chs.lib_common_ui.base.BaseViewModel
import com.chs.lib_core.cloud.CloudManager
import com.chs.lib_core.cloud.SHA1
import com.chs.lib_core.cloud.TokenBean
import com.chs.lib_core.extension.logI
import com.chs.lib_core.utils.GsonUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

/**
 * author：chs
 * date：2020/7/4
 * des：
 */
class MainViewModel : BaseViewModel() {

    val tokenData = MutableLiveData<TokenBean>()

    /**
     * 获取融云的token
     */
    fun getCloudToken(userId:String,name:String,portraitUri:String){
        launch {
             val json = withContext(Dispatchers.IO){postCloudToken(userId,name,portraitUri)}
             logI(json?:"")
            if(!TextUtils.isEmpty(json)){
                val tokenBean = GsonUtil.fromJson(json!!, TokenBean::class.java)
                tokenData.postValue(tokenBean)
            }
        }
    }

    private fun postCloudToken(userId:String,name:String,portraitUri:String): String? {
        val okHttpClient = OkHttpClient()
        //参数
        val timestamp = (System.currentTimeMillis() / 1000).toString()
        val nonce = Math.floor(Math.random() * 100000).toString()
        val signature: String =
            SHA1.sha1(CloudManager.CLOUD_SECRET + nonce + timestamp)
        //参数填充
        val builder = FormBody.Builder()
        builder.add("userId",userId)
        builder.add("name",name)
        builder.add("portraitUri",portraitUri)
        val requestBody: RequestBody = builder.build()
        //添加签名规则
        val request: Request = Request.Builder()
            .url(CloudManager.TOKEN_URL)
            .addHeader("Timestamp", timestamp)
            .addHeader("App-Key", CloudManager.CLOUD_KEY)
            .addHeader("Nonce", nonce)
            .addHeader("Signature", signature)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .post(requestBody)
            .build()
        try {
            return okHttpClient.newCall(request).execute().body?.string()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

}