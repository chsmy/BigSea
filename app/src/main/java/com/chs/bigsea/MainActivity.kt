package com.chs.bigsea

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.navigation.NavGraphBuilder
import com.chs.lib_core.navigation.NavManager
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.activity_main

    override fun setContentView(layoutResID: Int) {
        //由于 启动时style中设置了 windowBackground属性，所以要在进入主页后,把窗口背景清理掉
        setTheme(R.style.AppTheme)
        super.setContentView(layoutResID)
    }

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar().fitsSystemWindows(false).init()
        showSplash()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavGraphBuilder.build(navController,this,R.id.nav_host_fragment)
        nav_view.setNavController(navController)
    }

    private fun showSplash() {
        val transaction = supportFragmentManager.beginTransaction()
        var splashFragment = supportFragmentManager.findFragmentByTag(SplashFragment::class.java.simpleName) as SplashFragment?
        if(splashFragment!=null){
            if(splashFragment!!.isAdded){
                transaction.show(splashFragment!!).commitAllowingStateLoss()
            }else{
                transaction.remove(splashFragment!!).commitAllowingStateLoss()
                splashFragment = SplashFragment.newInstance()
                transaction.add(R.id.container, splashFragment!!,SplashFragment::class.java.simpleName).commit()
            }
        }else{
            splashFragment = SplashFragment.newInstance()
            transaction.add(R.id.container, splashFragment!!,SplashFragment::class.java.simpleName).commit()
        }
        splashFragment?.mOnTime?.observe(this, Observer {
            if(it == 0L){
                closeSplashFragment()
            }
        })
    }

    private fun closeSplashFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(SplashFragment::class.java.simpleName)
        if(fragment!=null){
            transaction.remove(fragment).commit()
        }
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}
