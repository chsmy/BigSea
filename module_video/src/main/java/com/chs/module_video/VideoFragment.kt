package com.chs.module_video

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_core.constant.WanRouterKey
import com.chs.lib_core.video.ScrollCalculatorHelper
import com.gyf.immersionbar.ImmersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import kotlinx.android.synthetic.main.video_fragment_video.*

@FragmentDestination(pageUrl = WanRouterKey.FRAGMENT_MAIN_TABLES_APPLY,isBelongTab = true)
class VideoFragment : BaseFragment() {

    private val mViewModel by lazy { getViewModel(VideoViewModel::class.java) }
    private val mAdapter: VideoListAdapter by lazy { VideoListAdapter() }

    private val scrollCalculatorHelper: ScrollCalculatorHelper by lazy { setScrollHelper() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    private fun setScrollHelper(): ScrollCalculatorHelper {
        val playTop = CommonUtil.getScreenHeight(requireContext()) / 2 - CommonUtil.dip2px(
            requireContext(), 200f)
        val playBottom = CommonUtil.getScreenHeight(requireContext()) / 2 + CommonUtil.dip2px(
            requireContext(), 200f)
        return ScrollCalculatorHelper(R.id.play_view, playTop, playBottom)
    }

    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun layoutId(): Int = R.layout.video_fragment_video

    override fun initView() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = mAdapter
        ImmersionBar.with(requireActivity()).transparentStatusBar().init()
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(refreshview)
        mViewModel.pageData.observe(this, Observer { t ->
            refreshview.finishRefresh()
            mAdapter.submitList(t)
        })
    }

    override fun initListener() {
        super.initListener()
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstVisibleItem = 0
            var lastVisibleItem: Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                //这是滑动自动播放的代码
                scrollCalculatorHelper.onScroll(
                    recyclerView, firstVisibleItem, lastVisibleItem,
                    lastVisibleItem - firstVisibleItem
                )
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden){
            GSYVideoManager.onPause()
        }else{
            ImmersionBar.with(requireActivity()).transparentStatusBar().init()
            GSYVideoManager.onResume(true)
        }
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
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
