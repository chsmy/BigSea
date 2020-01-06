package com.chs.lib_core.imageloader.glide

import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chs.lib_core.imageloader.base.IImageLoader
import com.chs.lib_core.imageloader.base.ImageConfig

/**
 *  @author chs
 *  date: 2019-12-13 15:46
 *  des:
 */
class GlideImageLoader : IImageLoader{

    companion object{
        private val options =  RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false)
            .priority(Priority.NORMAL)
    }

    override fun loadImage(imageConfig: ImageConfig) {
          Glide.with(imageConfig.imageview.context)
              .asBitmap()
              .load(imageConfig.url)
              .placeholder(imageConfig.placeholder)
              .error(imageConfig.errorPic)
              .apply(options)
              .into(imageConfig.imageview)
    }
}