package com.chs.module_eye.ui.follow

import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_common_ui.base.OnItemClickListener
import com.chs.lib_common_ui.exoplayer.PagePlayerDetector
import com.chs.lib_common_ui.exoplayer.PagePlayerManager
import com.chs.lib_core.common.BusKey
import com.chs.lib_core.event.LiveDataBus
import com.chs.module_eye.R
import com.chs.module_eye.ui.detail.VideoDetailActivity
import kotlinx.android.synthetic.main.eye_fragment_refresh.*

/**
 * author：chs
 * date：2020/6/27
 * des：
 */
class FollowFragment : BaseFragment(){
    private val mViewModel:FollowViewModel by viewModels()
    override fun layoutId(): Int = R.layout.eye_fragment_refresh
    private val mAdapter by lazy { FollowAdapter(pagePlayerDetector) }
    private lateinit var pagePlayerDetector: PagePlayerDetector
    /**
     * 如果是进入详情界面，播放器就不用暂停播放
     */
    private var shouldPause = true
    companion object{
        fun newInstance() = FollowFragment()
        const val KEY_FOLLOW_VIDEO_NAME = "FollowFragment"
    }

    override fun initView() {
        pagePlayerDetector = PagePlayerDetector(this,recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = mAdapter.getLoadStateAdapter(mAdapter)
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

    override fun initListener() {
        super.initListener()
        //由于该fragment在viewpager中 无法监听到onHiddenChanged方法，所以从父类传过来使用
        LiveDataBus.get<Boolean>(BusKey.KEY_ON_HIDDEN_CHANGED).observe(this, Observer {
            if (it) {
                pagePlayerDetector.onPause()
            } else {
                pagePlayerDetector.onResume()
            }
        })
        mAdapter.onItemClickListener = object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val currentItem = mAdapter.getCurrentItem(position)
                val id = currentItem?.data?.header?.id?:0
                val videoUrl = currentItem?.data?.content?.data?.playUrl?:""
                val videoCoverUrl = currentItem?.data?.content?.data?.cover?.feed?:""
                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        requireActivity(),
                        view,
                        "play_view"
                    )
                shouldPause = false
                VideoDetailActivity.start(requireContext(),id,KEY_FOLLOW_VIDEO_NAME,videoUrl,videoCoverUrl,options.toBundle())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        shouldPause = true
        if(isVisible){
            pagePlayerDetector.onResume()
        }
    }

    override fun onPause() {
        if(shouldPause){
            pagePlayerDetector.onPause()
        }
        super.onPause()
    }

    override fun onDestroy() {
        PagePlayerManager.release(KEY_FOLLOW_VIDEO_NAME)
        super.onDestroy()
    }
}