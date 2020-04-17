package com.chs.lib_core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *  @author chs
 *  date: 2020-04-17 15:40
 *  des: 空的activity 作为activity导航图的起始页面
 */
class EmptyActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
    }
}