package com.chs.lib_core.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.*

/**
 * author：chs
 * date：2020/3/29
 * des： 默认的FragmentNavigator是使用replace来切换fragment
 *       这里复制一下FragmentNavigator的代码，改成hide和show是形式
 */
@Navigator.Name("customfragment")
class CustomFragmentNavigator(context: Context, manager: FragmentManager, containerId: Int) :
    Navigator<FragmentNavigator.Destination>() {

    private val TAG = "CustomFragmentNavigator"
    private val KEY_BACK_STACK_IDS = "androidx-nav-fragment:navigator:backStackIds"

    private var mContext: Context = context
    private var mFragmentManager: FragmentManager = manager
    private var mContainerId = containerId
    private val mBackStack = ArrayDeque<Int>()

    override fun navigate(
        destination: FragmentNavigator.Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {
        if (mFragmentManager!!.isStateSaved) {
            Log.i(
                TAG,
                "Ignoring navigate() call: FragmentManager has already"
                        + " saved its state"
            )
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = mContext!!.packageName + className
        }
        val ft = mFragmentManager!!.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        val frg = mFragmentManager!!.primaryNavigationFragment
        if (frg != null) {
            ft.hide(frg)
        }

        val tag = destination.id.toString()
        var fragment = mFragmentManager!!.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = mFragmentManager.getFragmentFactory().instantiate(mContext.classLoader, className)
            fragment!!.arguments = args
            ft.add(mContainerId, fragment, tag)
        } else {
            ft.show(fragment)
        }
        ft.setPrimaryNavigationFragment(fragment)

        @IdRes val destId = destination.id
        val initialNavigation = mBackStack.isEmpty()

        var np = navOptions
        np = NavOptions.Builder().setLaunchSingleTop(true).build()
        val isSingleTopReplacement = (!initialNavigation
                && np.shouldLaunchSingleTop())

        val isAdded: Boolean
        isAdded = if (initialNavigation) {
            true
        } else if (isSingleTopReplacement) { // Single Top means we only want one instance on the back stack
            if (mBackStack.size > 1) { // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                mFragmentManager!!.popBackStack(
                    generateBackStackName(mBackStack.size, mBackStack.peekLast()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
            }
            false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
            true
        }
        if (navigatorExtras is FragmentNavigator.Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        (return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        })
    }

    override fun createDestination(): FragmentNavigator.Destination {
        return FragmentNavigator.Destination(this)//To change body of created functions use File | Settings | File Templates.
    }

    override fun popBackStack(): Boolean {
        if (mBackStack.isEmpty()) {
            return false
        }
        if (mFragmentManager!!.isStateSaved) {
            Log.i(
                TAG,
                "Ignoring popBackStack() call: FragmentManager has already"
                        + " saved its state"
            )
            return false
        }
        mFragmentManager?.popBackStack(
            generateBackStackName(mBackStack.size, mBackStack.peekLast()),
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        mBackStack.removeLast()
        return true
    }

    override fun onSaveState(): Bundle? {
        val b = Bundle()
        val backStack = IntArray(mBackStack.size)
        var index = 0
        for (id in mBackStack) {
            backStack[index++] = id
        }
        b.putIntArray(
            KEY_BACK_STACK_IDS,
            backStack
        )
        return b
    }

    override fun onRestoreState(savedState: Bundle) {
        if (savedState != null) {
            val backStack =
                savedState.getIntArray(KEY_BACK_STACK_IDS)
            if (backStack != null) {
                mBackStack.clear()
                for (destId in backStack) {
                    mBackStack.add(destId)
                }
            }
        }
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}