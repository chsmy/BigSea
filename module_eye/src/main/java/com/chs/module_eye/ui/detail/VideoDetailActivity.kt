package com.chs.module_eye.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_common_ui.base.BaseDetailActivity
import com.chs.lib_common_ui.tran.TransformationCompat
import com.chs.lib_common_ui.tran.TransformationLayout
import com.chs.lib_core.extension.loadCircle
import com.chs.module_eye.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.eye_video_detail.*

/**
 * author：chs
 * date：2020/6/28
 * des： 视频 图片详情
 */
class VideoDetailActivity: BaseDetailActivity() {

    private val mViewModel by lazy { getViewModel(VideoDetailViewModel::class.java) }

    private val mAdapter by lazy { DetailCommAdapter(ArrayList()) }

    companion object{
        private const val KEY_VIDEO_DETAIL = "VideoDetailActivity"
        private const val KEY_VIDEO_PLAY_KEY = "key_video_play_key"
        fun start(context: Context, videoId:Long, videoPlayKey:String, transformationLayout: TransformationLayout){
            val intent = Intent(context,VideoDetailActivity::class.java)
            intent.putExtra(KEY_VIDEO_DETAIL,videoId)
            intent.putExtra(KEY_VIDEO_PLAY_KEY,videoPlayKey)
//            context.startActivity(intent)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.eye_video_detail

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar()
            .init()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = mAdapter
    }
    override fun initData() {
        val videoId = intent.getLongExtra(KEY_VIDEO_DETAIL,0)
        val videoPlayKey = intent.getStringExtra(KEY_VIDEO_PLAY_KEY)?:""
        if(videoId!=0L){
            mViewModel.getDetailData(videoId)
        }
        mViewModel.detailPageData.observe(this, Observer {
            play_view.bindData(videoPlayKey,720,1280,it.detail.cover.feed,
            it.detail.playUrl)
            iv_head.loadCircle(it.detail.author.icon)
            tv_name.text = it.detail.author.name
            tv_des.text = it.detail.author.description
            play_view.onActive()
            mAdapter.setDataAndRefresh(it.detailRecommend)
        })
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        play_view.inActive()
    }

}