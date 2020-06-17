package com.chs.module_video

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_core.constant.WanRouterKey
import kotlinx.android.synthetic.main.video_fragment_video.*

@FragmentDestination(pageUrl = WanRouterKey.FRAGMENT_MAIN_TABLES_APPLY)
class VideoFragment : BaseFragment() {

    private val mViewModel by lazy { getViewModel(VideoViewModel::class.java) }
    private val mAdapter:VideoListAdapter by lazy { VideoListAdapter() }

    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun layoutId(): Int = R.layout.video_fragment_video

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = mAdapter
    }

    override fun initData() {
        mViewModel.pageData.observe(this, Observer{ t->
            refresh.finishRefresh()
            mAdapter.submitList(t)
        })
    }
}
