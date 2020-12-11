package com.chs.module_wan.ui.rank

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_wan.R
import com.chs.module_wan.databinding.WanActivityRankBinding
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
class RankActivity : BaseActivity<WanActivityRankBinding>() {

    private val mAdapter by lazy { RankAdapter() }
    private val mViewModel:RankViewModel by viewModels()

    override fun onCreateBinding(savedInstanceState: Bundle?): WanActivityRankBinding {
        return WanActivityRankBinding.inflate(layoutInflater)
    }

    override fun WanActivityRankBinding.onViewCreated() {
        tv_title_name.text = "排名"
        setRecyclerView(recyclerview);
    }

    private fun setRecyclerView(recyclerview: RecyclerView) {
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = mAdapter.getLoadStateAdapter(mAdapter)
    }

    override fun initListener() {
        super.initListener()
        refreshview.setOnRefreshListener {
            mAdapter.refresh()
        }
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(refreshview)
        mViewModel.pageData.observe(this, Observer {
            refreshview.finishRefresh()
            mAdapter.submitData(lifecycle,it)
        })
    }

}