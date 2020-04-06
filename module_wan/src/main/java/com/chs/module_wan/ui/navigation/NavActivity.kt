package com.chs.module_wan.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chs.lib_core.base.BaseActivity
import com.chs.module_wan.R

/**
 * author：chs
 * date：2020/4/5
 * des： 导航
 */
class NavActivity : BaseActivity() {

    companion object{
        fun start(content: Context){
            val intent = Intent(content, NavActivity::class.java)
            content.startActivity(intent)
        }
    }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.wan_activity_navigation

    override fun initView() {
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}