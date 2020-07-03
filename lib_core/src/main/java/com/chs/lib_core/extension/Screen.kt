package com.chs.lib_core.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import com.chs.lib_core.utils.AppUtil


/**
 * author：chs
 * date：2020/7/3
 * des：屏幕相关的工具
 */

fun dp2px(deValue:Float):Int{
     val scale = Resources.getSystem().displayMetrics.density
    return (scale * deValue + 0.5f).toInt()
}

fun px2dp(pxValue:Float):Int{
    val scale = Resources.getSystem().displayMetrics.density
    return (pxValue/scale + 0.5f).toInt()
}

fun sp2px(spValue:Float):Int{
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (fontScale * spValue + 0.5f).toInt()
}

fun px2sp(pxValue:Float):Int{
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (pxValue/fontScale + 0.5f).toInt()
}

fun getScreenWidth():Int{
    val wm = AppUtil.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager ?: return -1
    val point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.x
}
fun getScreenHeight(): Int {
    val wm = AppUtil.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager ?: return -1
    val point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.y
}

fun getStatusBarHeight():Int{
    val resources: Resources = AppUtil.getApp().resources
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}
