package com.chs.lib_core.navigation

import android.content.ComponentName
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import com.chs.lib_core.R
import com.chs.lib_core.utils.AppUtil
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
            setNavController()
            return sMavManager
        }
        private fun setNavController() {
            if(sNavController == null){
                val navController = NavController(AppUtil.getApp())
                val navigatorProvider = navController.navigatorProvider
                val navGraph = NavGraph(NavGraphNavigator(navigatorProvider))
                val activityNavigator = navigatorProvider.getNavigator(ActivityNavigator::class.java)
                val destinationMap = NavConfig.getDestinationMap()
                val activityDestinationStart:ActivityNavigator.Destination = getStartDestination(activityNavigator)
                navGraph.addDestination(activityDestinationStart)
                for ((key, destination) in destinationMap) {
                    if (!destination.isFragment&&!destination.isBelongTab){
                        val activityDestination = activityNavigator.createDestination()
                        activityDestination.id = destination.id
                        activityDestination.setComponentName(
                            ComponentName(AppUtil.getApp().packageName, destination.className!!)
                        )
                        activityDestination.addDeepLink(destination.pageUrl!!)
                        navGraph.addDestination(activityDestination)
                    }
                }
                navGraph.startDestination = activityDestinationStart.id
                navController.graph = navGraph
                sNavController = navController
            }
        }

        private fun getStartDestination(activityNavigator:ActivityNavigator): ActivityNavigator.Destination {
            val activityDestination = activityNavigator.createDestination()
            activityDestination.id = R.id.bottom_start_activity
            activityDestination.setComponentName(
                ComponentName(AppUtil.getApp().packageName, "com.chs.lib_core.navigation.EmptyActivity")
            )
            return activityDestination
        }
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