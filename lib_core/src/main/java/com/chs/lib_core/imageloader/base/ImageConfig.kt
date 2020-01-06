package com.chs.lib_core.imageloader.base

import android.widget.ImageView
import com.chs.lib_core.imageloader.glide.GlideImageLoader

/**
 *  @author chs
 *  date: 2019-12-13 15:40
 *  des:
 */
class ImageConfig(builder: Builder) {

    var url: String
    var imageview:ImageView
    var placeholder = 0
    var errorPic = 0
    var imageloader:IImageLoader? = null
    init {
        url = builder.url
        imageview = builder.imageView
        placeholder = builder.placeholder
        errorPic = builder.errorPic
    }

    fun getImageLoader():IImageLoader{
        if(imageloader == null){
            imageloader = GlideImageLoader()
        }
        return imageloader as IImageLoader
    }

    fun show(){
      getImageLoader().loadImage(this)
    }

    object Builder{
         lateinit var url: String
         lateinit var imageView:ImageView
         var placeholder = 0
         var errorPic = 0

        fun url(url:String):Builder{
            this.url = url
            return this
        }
        fun placeholder(placeholder: Int): Builder{
            this.placeholder = placeholder
            return this
        }
        fun errorPic(errorPic:Int): Builder{
            this.errorPic = errorPic
            return this
        }

        fun into(imageView: ImageView){
            this.imageView = imageView
            ImageConfig(this).show()
        }

    }
}