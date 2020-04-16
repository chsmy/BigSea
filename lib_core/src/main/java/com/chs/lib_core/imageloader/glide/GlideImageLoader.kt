package com.chs.lib_core.imageloader.glide

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.NonNull
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
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

    /**
     * 加载一个图片
     */
    override fun loadImage(imageConfig: ImageConfig) {
          Glide.with(imageConfig.imageview.context)
              .asBitmap()
              .load(imageConfig.url)
              .placeholder(imageConfig.placeholder)
              .error(imageConfig.errorPic)
              .apply(options)
              .into(imageConfig.imageview)
    }

    /**
     * 获取一个图片上的颜色，并设置给指定控件
     */
    override fun loadBg(imageConfig: ImageConfig) {
        Glide.with(imageConfig.imageview.context)
            .load(imageConfig.url)
            .placeholder(imageConfig.placeholder)
            .error(imageConfig.errorPic)
            .apply(options)
            .transition( DrawableTransitionOptions().crossFade())
            .into(object : DrawableImageViewTarget(imageConfig.imageview){
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    super.onResourceReady(resource, transition)
                    val bitmap = (resource as BitmapDrawable).bitmap
                    setBgColor(bitmap,imageConfig.bgView)
                }
            })
    }

    val targets = listOf<Target>(Target.LIGHT_VIBRANT, Target.DARK_MUTED, Target.DARK_VIBRANT,
        Target.LIGHT_MUTED, Target.MUTED, Target.VIBRANT)

    fun setBgColor(bitmap:Bitmap,view: View?){
        Palette.from(bitmap).maximumColorCount(10)
            .generate(object : Palette.PaletteAsyncListener{
                override fun onGenerated(palette: Palette?) {
                    var vibrantSwatch : Palette.Swatch? = null
                    targets.forEach {
                        if(null == vibrantSwatch){
                            vibrantSwatch = palette?.getSwatchForTarget(it)
                        }
                    }
                    if(null == vibrantSwatch){
                        return
                    }
                    view?.setBackgroundColor(vibrantSwatch!!.rgb)
                }
            })
    }

//    override fun loadBlurry(imageConfig: ImageConfig) {
//        Glide.with(imageConfig.imageview.context)
//            .load(imageConfig.url)
//            .placeholder(imageConfig.placeholder)
//            .error(imageConfig.errorPic)
//            .apply(options)
//            .transition( DrawableTransitionOptions().crossFade())
//            .into(object : DrawableImageViewTarget(imageConfig.imageview){
//                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                    super.onResourceReady(resource, transition)
//                    val bitmap = (resource as BitmapDrawable).bitmap
//                    Blurry.with(imageConfig.imageview.context).from(bitmap).into(imageConfig.imageview)
//                }
//            })
//    }

}