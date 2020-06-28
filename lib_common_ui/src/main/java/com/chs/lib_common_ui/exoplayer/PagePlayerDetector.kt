package com.chs.lib_common_ui.exoplayer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

/**
 * author：chs
 * date：2020/6/28
 * des： 列表自动播放的检测器
 */
class PagePlayerDetector(private val owner: LifecycleOwner, recyclerView: RecyclerView) {

    private val mTargets = ArrayList<IPlayTarget>()

    fun addTarget(target:IPlayTarget){
        mTargets.add(target)
    }

    fun removeTarget(target:IPlayTarget){
        mTargets.remove(target)
    }

    init {
        //监听生命周期 当生命周期为DESTROY的时候 做一些释放工作
        owner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                //监听view添加到了RecyclerView之上
                recyclerView.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        super.onItemRangeInserted(positionStart, itemCount)
                    }
                })
                if (event == Lifecycle.Event.ON_DESTROY){
                    autoPlay()
                }
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                //当停止滑动的时候，开始自动播放
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    autoPlay()
                }
            }
        })

    }

    private fun autoPlay() {

    }

}