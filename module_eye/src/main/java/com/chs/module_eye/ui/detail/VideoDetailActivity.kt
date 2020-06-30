package com.chs.module_eye.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.module_eye.R
import kotlinx.android.synthetic.main.eye_video_detail.*

/**
 * author：chs
 * date：2020/6/28
 * des： 视频 图片详情
 */
class VideoDetailActivity:BaseActivity() {

    private val mViewModel by lazy { getViewModel(VideoDetailViewModel::class.java) }

    companion object{
        private const val KEY_VIDEO_DETAIL = "VideoDetailActivity"
        private const val KEY_VIDEO_PLAY_KEY = "key_video_play_key"
        fun start(context: Context,videoId:Long,videoPlayKey:String){
            val intent = Intent(context,VideoDetailActivity::class.java)
            intent.putExtra(KEY_VIDEO_DETAIL,videoId)
            intent.putExtra(KEY_VIDEO_PLAY_KEY,videoPlayKey)
            context.startActivity(intent)
        }
    }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.eye_video_detail

    override fun initView() {

    }

    override fun initData() {
        val videoId = intent.getLongExtra(KEY_VIDEO_DETAIL,0)
        val videoPlayKey = intent.getStringExtra(KEY_VIDEO_PLAY_KEY)?:""
        if(videoId!=0L){
            mViewModel.getDetailData(videoId)
        }
        mViewModel.detailPageData.observe(this, Observer {
            play_view.bindData(videoPlayKey,0,0,it.detail.cover.feed,
            it.detail.playUrl)
            play_view.onActive()
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