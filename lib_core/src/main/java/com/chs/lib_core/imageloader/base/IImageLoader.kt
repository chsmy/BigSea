package com.chs.lib_core.imageloader.base

/**
 *  @author chs
 *  date: 2019-12-13 15:43
 *  des:
 */
interface IImageLoader{

    fun loadImage(imageConfig: ImageConfig)

    fun loadCircleImage(imageConfig: ImageConfig)

    fun loadRoundImage(imageConfig: ImageConfig)

    fun loadBg(imageConfig: ImageConfig)

}