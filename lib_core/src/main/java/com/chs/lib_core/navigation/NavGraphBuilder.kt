package com.chs.lib_core.navigation

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.*
import com.chs.lib_core.utils.AppUtil

/**
 * author：chs
 * date：2020/3/29
 * des： 构建一个首页的导航图
 */
class NavGraphBuilder {

    companion object {

        fun build(navController: NavController, activity: FragmentActivity, containerId: Int) {
            val navigatorProvider = navController.navigatorProvider
            val fragmentNavigator = CustomFragmentNavigator(
                activity, activity.supportFragmentManager,
                containerId
            )
            navigatorProvider.addNavigator(fragmentNavigator)
            val activityNavigator = navigatorProvider.getNavigator(ActivityNavigator::class.java)

            val destinationMap = NavConfig.getDestinationMap()
            val navGraph = NavGraph(NavGraphNavigator(navigatorProvider))

            for ((key, destination) in destinationMap) {
                if (destination.isFragment&&destination.isBelongTab) {
                    val fragmentDestination = fragmentNavigator.createDestination()
                    fragmentDestination.className = destination.className!!
                    fragmentDestination.id = destination.id
                    fragmentDestination.addDeepLink(destination.pageUrl!!)
                    navGraph.addDestination(fragmentDestination)
                } else {
                    if(destination.isBelongTab){
                        val activityDestination = activityNavigator.createDestination()
                        activityDestination.id = destination.id
                        activityDestination.setComponentName(
                            ComponentName(
                                AppUtil.getApp().packageName,
                                destination.className!!
                            )
                        )
                        activityDestination.addDeepLink(destination.pageUrl!!)
                        navGraph.addDestination(activityDestination)
                    }
                }
                if (destination.asStarter) {
                    navGraph.startDestination = destination.id
                }
            }
            navController.graph = navGraph
        }
    }
}