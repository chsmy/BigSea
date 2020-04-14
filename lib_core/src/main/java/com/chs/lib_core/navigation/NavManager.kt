package com.chs.lib_core.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavController
import java.io.Serializable

/**
 * author：chs
 * date：2020/4/13
 * des：路由导航管理类
 */
class NavManager {

    companion object{
        private var sNavController:NavController? = null
        private val sMavManager = NavManager()
        fun get():NavManager{
            return sMavManager
        }
    }

    fun setNavController(navController:NavController){
        sNavController = navController
    }

    fun build(toWhere: String) : Builder{
        val bundle = Bundle()
        return Builder(toWhere,bundle)
    }

    class Builder(private val toWhere: String,private val bundle: Bundle){

        fun withString(key:String,value:String):Builder{
            bundle.putString(key, value)
            return this
        }

        fun withInt(key:String,value:Int):Builder{
            bundle.putInt(key, value)
            return this
        }

        fun withLong(key:String,value:Long):Builder{
            bundle.putLong(key, value)
            return this
        }

        fun withDouble(key:String,value:Double):Builder{
            bundle.putDouble(key, value)
            return this
        }

        fun withBoolean(key:String,value:Boolean):Builder{
            bundle.putBoolean(key, value)
            return this
        }

        fun withByte(key:String,value:Byte):Builder{
            bundle.putByte(key, value)
            return this
        }

        fun withSerializable(key:String,value: Serializable):Builder{
            bundle.putSerializable(key, value)
            return this
        }

        fun withParcelable(key:String,value: Parcelable):Builder{
            bundle.putParcelable(key, value)
            return this
        }

        private fun getItemId(pageUrl: String): Int {
            val destination = NavConfig.getDestinationMap()[pageUrl]
            return destination?.id ?: -1
        }

        fun navigate(){
            sNavController?.navigate(getItemId(toWhere),bundle)
        }
    }
}