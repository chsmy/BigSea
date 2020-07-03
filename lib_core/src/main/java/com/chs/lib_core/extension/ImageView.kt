package com.chs.lib_core.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chs.lib_core.utils.AppUtil

/**
 * author：chs
 * date：2020/7/3
 * des：
 */


private val options =  RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .skipMemoryCache(false)
        .priority(Priority.NORMAL)


fun ImageView.load(url:String){
    Glide.with(this.context).load(url).apply(options).into(this)
}

fun ImageView.loadCircle(url:String){
    Glide.with(this.context)
        .asBitmap()
        .load(url)
        .apply(options)
        .circleCrop()
        .into(this)
}
fun ImageView.loadRound(url:String,roundRadius:Int){
    val roundedCorners = RoundedCorners(dp2px(roundRadius.toFloat()))
    val  options=RequestOptions.bitmapTransform(roundedCorners)
    Glide.with(this.context)
        .asBitmap()
        .load(url)
        .apply(options)
        .into(this)
}

fun resumeRequest(){
    Glide.with(AppUtil.getApp()).resumeRequests()
}

fun pauseRequest(){
    Glide.with(AppUtil.getApp()).pauseRequests()
}