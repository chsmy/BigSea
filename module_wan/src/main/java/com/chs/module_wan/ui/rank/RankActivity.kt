package com.chs.module_wan.ui.rank

import android.os.Bundle
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_wan.R

/**
 * author：chs
 * date：2020/4/13
 * des：
 */
@ActivityDestination(pageUrl = WanRouterKey.WAN_MAIN_MINE_RANK_ACTIVITY)
class RankActivity : BaseActivity() {
    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.wan_activity_rank

    override fun initView() {
    }

    override fun initData() {
    }
}