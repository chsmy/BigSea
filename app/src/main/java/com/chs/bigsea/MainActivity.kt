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

     private var splashFragment:SplashFragment? = null

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.activity_main

    override fun setContentView(layoutResID: Int) {
        //由于 启动时style中设置了 windowBackground属性，所以要在进入主页后,把窗口背景清理掉
        setTheme(R.style.AppTheme)
        super.setContentView(layoutResID)
    }

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar().init()
        showSplash()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavManager.get().setNavController(navController)
        NavGraphBuilder.build(navController,this,R.id.nav_host_fragment)
        nav_view.setNavController(navController)
    }

    private fun showSplash() {
        val transaction = supportFragmentManager.beginTransaction()
        splashFragment = supportFragmentManager.findFragmentByTag(SplashFragment::class.java.simpleName) as SplashFragment?
        if(splashFragment!=null){
            if(splashFragment!!.isAdded){
                transaction.show(splashFragment!!).commitAllowingStateLoss()
            }else{
                transaction.remove(splashFragment!!).commitAllowingStateLoss()
                splashFragment = SplashFragment.newInstance()
                transaction.add(R.id.container, splashFragment!!,SplashFragment::class.java.simpleName).commitAllowingStateLoss()
            }
        }else{
            splashFragment = SplashFragment.newInstance()
            transaction.add(R.id.container, splashFragment!!,SplashFragment::class.java.simpleName).commitAllowingStateLoss()
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
            transaction.remove(fragment).commitAllowingStateLoss()
        }
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}
