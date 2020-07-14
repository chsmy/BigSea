package com.chs.module_video.play

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.chs.lib_core.extension.dp2px

/**
 *  @author chs
 *  date: 2020-07-14 10:46
 *  des:
 */
class HookActionUpRecyclerView:RecyclerView {

    var startScroll = false
    private var curDownX = 0f
    private var curDownY = 0f
    private var scrollMax = dp2px(8f)

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    /**
     * 具体规则查 [com.effective.android.panel.view.content.ContentContainerImpl]
     *
     * @return
     */
    override fun onTouchEvent(e: MotionEvent): Boolean {
        val curX = e.x
        val curY = e.y
        if (e.action == MotionEvent.ACTION_DOWN) {
            startScroll = false
            curDownX = curX
            curDownY = curY
        }
        if (e.action == MotionEvent.ACTION_MOVE) {
            startScroll =
                Math.abs(curX - curDownX) > scrollMax || Math.abs(curY - curDownY) > scrollMax
        }
        return if (e.action == MotionEvent.ACTION_UP && !startScroll) {
            false
        } else super.onTouchEvent(e)
    }
}