package com.chs.module_eye.ui.recommend

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.module_eye.R
import com.chs.module_eye.model.RecommendItem
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.eye_fragment_find.*
import kotlinx.android.synthetic.main.eye_fragment_refresh.*

/**
 * author：chs
 * date：2020/6/24
 * des：推荐
 */
class RecommendFragment:BaseFragment() {

    private val mViewModel by lazy { getViewModel(RecommendViewModel::class.java) }
    private val mAdapter by lazy { RecommendAdapter() }
    companion object{
        fun newInstance() = RecommendFragment()
    }

    override fun layoutId(): Int = R.layout.eye_fragment_refresh

    override fun initView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mAdapter.getLoadStateAdapter(mAdapter)
        recyclerView.addItemDecoration(RecommendItemDecoration())
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(refreshView)
        mViewModel.pageData.observe(this,
            Observer { t ->
                refreshView.finishRefresh()
                mAdapter.submitData(lifecycle,t)
            })
    }
}