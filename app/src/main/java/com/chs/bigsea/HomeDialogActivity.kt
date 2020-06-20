package com.chs.bigsea

import android.os.Bundle
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.gyf.immersionbar.ImmersionBar

/**
 * author：chs
 * date：2020/6/20
 * des：
 */
@ActivityDestination(pageUrl = WanRouterKey.DIALOG_ACTIVITY_HOME)
class HomeDialogActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.bottom_in,0)
        super.onCreate(savedInstanceState)
    }

    override fun getContentView(savedInstanceState: Bundle?): Int  = R.layout.dialog_fragment_add

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar().init()
    }

    override fun initData() {
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0,R.anim.bottom_out)
    }
}