package com.chs.bigsea

import android.content.Intent
import android.os.Bundle
import com.chs.lib_core.base.BaseActivity

/**
 * author：chs
 * date：2020/3/29
 * des：
 */
class SplashActivity : BaseActivity() {

    override fun getContentView(savedInstanceState: Bundle?): Int = 0
    override fun initView() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun initListener() {
    }

    override fun initData() {
    }

}