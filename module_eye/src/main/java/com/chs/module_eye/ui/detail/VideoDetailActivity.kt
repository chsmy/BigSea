package com.chs.module_eye.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.extension.loadCircle
import com.chs.lib_core.extension.logI
import com.chs.module_eye.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.eye_video_detail.*

/**
 * author：chs
 * date：2020/6/28
 * des： 视频 图片详情
 */
class VideoDetailActivity: BaseActivity() {

    private val mViewModel:VideoDetailViewModel by viewModels()

    private val mAdapter by lazy { DetailCommAdapter(ArrayList()) }

    companion object{
        private const val KEY_VIDEO_DETAIL = "VideoDetailActivity"
        private const val KEY_VIDEO_PLAY_KEY = "key_video_play_key"
        private const val VIDEO_URL = "video_url"
        private const val VIDEO_COVER_URL = "video_cover_url"
        fun start(context: Context, videoId:Long, videoPlayKey:String,videoUrl:String,videoCoverUrl:String ,bundle: Bundle?){
            val intent = Intent(context,VideoDetailActivity::class.java)
            intent.putExtra(KEY_VIDEO_DETAIL,videoId)
            intent.putExtra(KEY_VIDEO_PLAY_KEY,videoPlayKey)
            intent.putExtra(VIDEO_URL,videoUrl)
            intent.putExtra(VIDEO_COVER_URL,videoCoverUrl)
            context.startActivity(intent,bundle)
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
        val videoUrl = intent.getStringExtra(VIDEO_URL)?:""
        val videoCoverUrl = intent.getStringExtra(VIDEO_COVER_URL)?:""
        logI("intent: videoPlayKey:$videoPlayKey videoUrl:$videoUrl  videoCoverUrl:$videoCoverUrl")
        play_view.bindData(videoPlayKey,720,1280,videoCoverUrl,videoUrl)
        play_view.onActive()
        if(videoId!=0L){
            mViewModel.getDetailData(videoId)
        }
        mViewModel.detailPageData.observe(this, Observer {
            iv_head.loadCircle(it.detail.author.icon)
            tv_name.text = it.detail.author.name
            tv_des.text = it.detail.author.description
            mAdapter.setDataAndRefresh(it.detailRecommend)
        })
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
//        play_view.inActive()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //按了返回键后需要 恢复 播放控制器的位置。否则回到列表页时 可能会不正确的显示
//        play_view.getPlayController()?.translationY = 0f
    }

}