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
class PagePlayerDetector(private val owner: LifecycleOwner, private val recyclerView: RecyclerView) {

    /**
     * 可播放的对象的集合
     */
    private val mTargets = ArrayList<IPlayTarget>()

    /**
     * recyclerView的位置
     */
    private var mRvLocation:Pair<Int,Int>?=null

    /**
     * 当前正在播放的对象
     */
    private var mPlayingTarget:IPlayTarget?=null

    init {
        //监听生命周期 当生命周期为DESTROY的时候 做一些释放工作
        owner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                //监听view添加到了RecyclerView之上
                recyclerView.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        super.onItemRangeInserted(positionStart, itemCount)
                        autoPlay()
                    }
                })
                if (event == Lifecycle.Event.ON_DESTROY){
                    mPlayingTarget = null
                    mTargets.clear()
                    owner.lifecycle.removeObserver(this)
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

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //判断当前播放的playerTarget是否还满足播放条件，否则就给它关闭
                if(mPlayingTarget?.isPlaying() == true &&!isTargetInBounds(mPlayingTarget)){
                    mPlayingTarget?.inActive()
                }
            }
        })

    }

    fun addTarget(target:IPlayTarget){
        mTargets.add(target)
    }

    fun removeTarget(target:IPlayTarget){
        mTargets.remove(target)
    }

    private fun autoPlay() {

      if(mTargets.size>0&&recyclerView.childCount>0){
          //如果有一个对象正在播放，并且还在规定范围内，就不需要重新找播放对象了
          if(mPlayingTarget?.isPlaying() == true &&isTargetInBounds(mPlayingTarget)){
              return
          }
          //循环找到可以播放的对象
          var activeTarget:IPlayTarget?=null
          run breaking@ {
              mTargets.forEach {
                  //判断容器是否在可以播放发范围内
                  val inBounds = isTargetInBounds(it)
                  if(inBounds){
                      activeTarget = it
                      return@breaking
                  }
              }
          }
          activeTarget?.let {
              //如果当前有正在播放的，让它停止
              mPlayingTarget?.inActive()
              mPlayingTarget = it
              it.onActive()
          }
      }
    }

    private fun isTargetInBounds(target: IPlayTarget?): Boolean {
        target?.let {
            //获取当前播放的容器
            val container = target.getOwner()
            //如果该容器还没显示 直接返回false
            if(!container.isShown||!container.isAttachedToWindow){
                return false
            }
            //获取该容器在屏幕上的位置
            val location = IntArray(2)
            container.getLocationOnScreen(location)
            //确定RecyclerView在屏幕上的位置
            ensureRecyclerViewLocation()
            val center = location[1] + container.height/2
            //当前播放的view 最少有一半的位置在recyclerView的范围内
            return center>=mRvLocation?.first!!&&center<=mRvLocation?.second!!
        }
        return false
    }


    private fun ensureRecyclerViewLocation() {
        if(mRvLocation == null){
            val location = IntArray(2)
            recyclerView.getLocationOnScreen(location)
            val top = location[1]
            val bottom = top + recyclerView.height
            mRvLocation = Pair(top,bottom)
        }
    }

    fun onResume() {
        mPlayingTarget?.onActive()
    }

    fun onPause() {
        mPlayingTarget?.inActive()
    }

}