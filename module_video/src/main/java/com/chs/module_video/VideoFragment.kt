package com.chs.module_video

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_common_ui.exoplayer.PagePlayerDetector
import com.chs.lib_common_ui.exoplayer.PagePlayerManager
import com.chs.lib_core.constant.WanRouterKey
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.video_fragment_video.*

@FragmentDestination(pageUrl = WanRouterKey.FRAGMENT_MAIN_TABLES_APPLY, isBelongTab = true)
class VideoFragment : BaseFragment() {

    private val mViewModel by lazy { getViewModel(VideoViewModel::class.java) }
    private val mAdapter: VideoListAdapter by lazy { VideoListAdapter(pagePlayerDetector) }
    private lateinit var pagePlayerDetector: PagePlayerDetector
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun newInstance() = VideoFragment()
        const val KEY_VIDEO_VIDEO_NAME = "VideoFragment"
    }

    override fun layoutId(): Int = R.layout.video_fragment_video

    override fun initView() {
        pagePlayerDetector = PagePlayerDetector(this,recyclerview)
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerview.layoutManager = linearLayoutManager
        recyclerview.adapter = mAdapter.getLoadStateAdapter(mAdapter)
        ImmersionBar.with(this)
            .statusBarColor(com.chs.lib_common_ui.R.color.white)
            .autoStatusBarDarkModeEnable(true)
            .init()
        setStatusBarViewHeight(status_bar_video)
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(refreshview)
        mViewModel.pageData.observe(this, Observer { t ->
            refreshview.finishRefresh()
            mAdapter.submitData(lifecycle,t)
        })
    }

    override fun initListener() {
        super.initListener()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            pagePlayerDetector.onPause()
        } else {
            ImmersionBar.with(requireActivity())
                .statusBarColor(com.chs.lib_common_ui.R.color.white)
                .autoStatusBarDarkModeEnable(true)
                .init()
            pagePlayerDetector.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if(!isHidden){
            pagePlayerDetector.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        pagePlayerDetector.onPause()
    }

    override fun onDestroy() {
        PagePlayerManager.release(KEY_VIDEO_VIDEO_NAME)
        super.onDestroy()
    }

}
