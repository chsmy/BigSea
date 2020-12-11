package com.chs.module_video.play

import android.os.Bundle
import android.util.Log
import android.view.View
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_common_ui.emotion.DisplayUtils
import com.chs.lib_common_ui.emotion.EmotionPagerView
import com.chs.lib_common_ui.emotion.Emotions
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_video.R
import com.chs.module_video.databinding.VideoActivityPlayBinding
import com.effective.android.panel.PanelSwitchHelper
import com.effective.android.panel.interfaces.listener.OnEditFocusChangeListener
import com.effective.android.panel.interfaces.listener.OnKeyboardStateListener
import com.effective.android.panel.interfaces.listener.OnPanelChangeListener
import com.effective.android.panel.interfaces.listener.OnViewClickListener
import com.effective.android.panel.view.panel.IPanelView
import com.effective.android.panel.view.panel.PanelView
import com.rd.PageIndicatorView
import kotlinx.android.synthetic.main.video_activity_play.*

/**
 * author：chs
 * date：2020/7/13
 * des： 观看直播的页面
 */
@ActivityDestination(pageUrl = WanRouterKey.ACTIVITY_VIDEO_PLAY)
class RtmpPlayActivity:BaseActivity<VideoActivityPlayBinding>() {
    private var mHelper: PanelSwitchHelper? = null
    val videoPopWindow:PcHuyaCommentPopWindow by lazy { PcHuyaCommentPopWindow(this) }

    override fun onCreateBinding(savedInstanceState: Bundle?): VideoActivityPlayBinding {
        return VideoActivityPlayBinding.inflate(layoutInflater)
    }

    override fun VideoActivityPlayBinding.onViewCreated() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        //湖南卫视直播源  rtmp://58.200.131.2:1935/livetv/hunantv
        //自己的直播源    rtmp://203.170.59.43/live/livestream
                play_view.bindData("mine",1080,720,
            "http://a0.att.hudong.com/30/88/01100000000000144726882521407_s.jpg",
            "rtmp://58.200.131.2:1935/livetv/hunantv")
    }

    override fun initListener() {
        super.initListener()
//        input.setOnClickListener {
////            videoPopWindow.showAtLocation(root, Gravity.NO_GRAVITY, 0, 0)
//        }
    }
    private fun scrollToBottom() {
//        root.post({ mLinearLayoutManager.scrollToPosition(mAdapter.getItemCount() - 1) })
    }
    override fun onStart() {
        super.onStart()
        if (mHelper == null) {
            mHelper = PanelSwitchHelper.Builder(this) //可选
                .addKeyboardStateListener(object :OnKeyboardStateListener {
                    override fun onKeyboardChange(visible: Boolean, height: Int) {

                    }
                })
                .addEditTextFocusChangeListener(object : OnEditFocusChangeListener {
                    override fun onFocusChange(view: View?, hasFocus: Boolean) {
                    }
                }) //可选
                .addViewClickListener(object :OnViewClickListener {
                    override fun onClickBefore(view: View?) {
                    }
                }) //可选
                .addPanelChangeListener(object : OnPanelChangeListener {
                    override fun onKeyboard() {
                        Log.d(
                            "keybord",
                            "唤起系统输入法"
                        )
                        emotion_btn.isSelected = false
                        scrollToBottom()
                    }

                    override fun onNone() {
                        Log.d(
                           "keybord",
                            "隐藏所有面板"
                        )
                        emotion_btn.isSelected = false
                    }

                    override fun onPanel(view: IPanelView?) {
                        Log.d(
                            "keybord",
                            "唤起面板 : $view"
                        )
                        if (view is PanelView) {
                            emotion_btn.isSelected = if (view.id == R.id.panel_emotion) true else false
                            scrollToBottom()
                        }
                    }

                    override fun onPanelSizeChange(
                        panelView: IPanelView?,
                        portrait: Boolean,
                        oldWidth: Int,
                        oldHeight: Int,
                        width: Int,
                        height: Int
                    ) {
                        if (panelView is PanelView) {
                            when (panelView.id) {
                                R.id.panel_emotion -> {
                                    val pagerView: EmotionPagerView = root.findViewById(R.id.view_pager)
                                    val viewPagerSize: Int =
                                        height - DisplayUtils.dip2px(applicationContext, 30f)
                                    pagerView.buildEmotionViews(
                                       root
                                            .findViewById(R.id.pageIndicatorView) as PageIndicatorView,
                                       input,
                                        Emotions.getEmotions(), width, viewPagerSize
                                    )
                                }
                            }
                        }
                    }
                })
                .logTrack(true) //output log
                .build()
    }}

    override fun onDestroy() {
        super.onDestroy()
        play_view.inActive()
    }

}