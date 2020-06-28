package com.chs.lib_common_ui.exoplayer

import android.view.ViewGroup

/**
 * author：chs
 * date：2020/6/28
 * des：
 */
interface IPlayTarget {

    /**
     * 当前播放器所在的容器
     */
    fun getOwner(): ViewGroup

    /**
     * 活跃状态视频可以播放
     */
    fun onActive()

    /**
     * 非活跃状态 视频不能播放
     */
    fun inActive()

    /**
     * 释放是在播放中
     */
    fun isPlaying():Boolean

}