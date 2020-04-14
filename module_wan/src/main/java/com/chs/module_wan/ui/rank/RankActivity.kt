package com.chs.module_wan.ui.rank

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_wan.R
import kotlinx.android.synthetic.main.wan_activity_rank.*
import kotlinx.android.synthetic.main.wan_activity_rank.recyclerview
import kotlinx.android.synthetic.main.wan_activity_rank.refreshview
import kotlinx.android.synthetic.main.wan_fragment_wan.*
import kotlinx.android.synthetic.main.wan_include_page_title.*

/**
 * author：chs
 * date：2020/4/13
 * des：
 */
@ActivityDestination(pageUrl = WanRouterKey.ACTIVITY_MAIN_MINE_RANK)
class RankActivity : BaseActivity() {

    private val mAdapter by lazy { RankAdapter() }
    private val mViewModel by lazy { getViewModel(RankViewModel::class.java) }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.wan_activity_rank

    override fun initView() {
        tv_title_name.text = "排名"
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = mAdapter
    }

    override fun initListener() {
        super.initListener()
        refreshview.setOnRefreshListener {
            mViewModel.dataSource?.invalidate()
        }
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(refreshview)
        mViewModel.pageData.observe(this, Observer {
            mAdapter.submitList(it)
        })
    }
}