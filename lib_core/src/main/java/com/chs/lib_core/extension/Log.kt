package com.chs.lib_core.extension

import android.util.Log
import com.chs.lib_core.BuildConfig

/**
 * author：chs
 * date：2020/7/4
 * des： Log工具类
 */

fun logI(text: String) {
    if (BuildConfig.LOG_DEBUG) {
        Log.i(BuildConfig.LOG_TAG, text)
    }
}

fun logI(tag: String, text: String) {
    if (BuildConfig.LOG_DEBUG) {
        Log.i(tag, text)
    }
}

fun logE(text: String) {
    if (BuildConfig.LOG_DEBUG) {
        Log.e(BuildConfig.LOG_TAG, text)
    }
}

fun logE(tag: String, text: String) {
    if (BuildConfig.LOG_DEBUG) {
        Log.e(tag, text)
    }
}