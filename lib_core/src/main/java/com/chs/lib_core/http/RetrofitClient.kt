package com.chs.lib_core.http

import com.chs.lib_core.BuildConfig
import com.chs.lib_core.utils.AppUtil
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  @author chs
 *  date: 2019-12-16 15:12
 *  des:
 */
abstract class RetrofitClient{

    private val okHttpClient : OkHttpClient

    init {

        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        if(BuildConfig.DEBUG){
            builder.addInterceptor(logging)
        }

        val cookieJar = PersistentCookieJar(SetCookieCache(),SharedPrefsCookiePersistor(AppUtil.getApp()))

        okHttpClient = builder
            .writeTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .connectTimeout(20,TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            .build()
    }

    fun getOkHttpClient():OkHttpClient{
        return okHttpClient
    }

    fun<T> getService(serviceClass:Class<T>,baseUrl:String):T{
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(serviceClass)
    }

}