package com.chs.lib_core.http

import com.chs.lib_core.BuildConfig
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

        okHttpClient = builder
            .writeTimeout(10,TimeUnit.SECONDS)
            .readTimeout(10,TimeUnit.SECONDS)
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()
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