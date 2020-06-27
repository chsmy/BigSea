package com.chs.module_eye.ui.follow

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.module_eye.R
import com.chs.module_eye.ui.recommend.RecommendAdapter
import com.chs.module_eye.ui.recommend.RecommendFragment
import com.chs.module_eye.ui.recommend.RecommendItemDecoration
import com.chs.module_eye.ui.recommend.RecommendViewModel
import com.gyf.immersionbar.ImmersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.eye_fragment_refresh.*

/**
 * author：chs
 * date：2020/6/27
 * des：
 */
class FollowFragment : BaseFragment(){
    private val mViewModel by lazy { getViewModel(FollowViewModel::class.java) }
    override fun layoutId(): Int = R.layout.eye_fragment_refresh
    private val mAdapter by lazy { FollowAdapter() }

    companion object{
        fun newInstance() = FollowFragment()
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mAdapter
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            GSYVideoManager.onPause()
        } else {
            GSYVideoManager.onResume(true)
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}