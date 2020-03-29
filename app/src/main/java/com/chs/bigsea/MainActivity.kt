package com.chs.bigsea

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.Navigation
import com.chs.lib_core.base.BaseActivity
import com.chs.lib_core.navigation.NavGraphBuilder
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.activity_main

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar().init()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavGraphBuilder.build(navController,this,R.id.nav_host_fragment)
        nav_view.setNavController(navController)
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}
