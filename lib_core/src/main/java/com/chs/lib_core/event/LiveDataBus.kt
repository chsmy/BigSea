package com.chs.lib_core.event

import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

/**
 * author：chs
 * date：2020/2/21
 * des：
 */

class LiveDataBus{
    companion object {
        fun <T>get(key: String): BusLiveData<T> {
           return LiveDataEvent.instance.with(key) as BusLiveData<T>
        }
    }
}

class LiveDataEvent {

    companion object{
        val instance = LiveDataEvent()
    }

    val mMap:ConcurrentHashMap<String,BusLiveData<Any>> by lazy { ConcurrentHashMap<String,BusLiveData<Any>>() }

    fun with(key:String):BusLiveData<Any>{
       var busLiveData = mMap[key]
        if(busLiveData == null){
            busLiveData = BusLiveData(key, instance)
            mMap[key] = busLiveData
        }
        return busLiveData
    }

}

class BusLiveData<T>(val eventName:String,val instance:LiveDataEvent) : LiveData<T>(){

    var mCurrentVersion = 0

    public override fun setValue(value: T) {
        mCurrentVersion ++
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        mCurrentVersion ++
        super.postValue(value)
    }

    fun setStickyData(stickData:T){
        setValue(stickData)
    }

    fun postStickyData(stickData:T){
        postValue(stickData)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
       observe(owner,observer,false)
    }

    fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>){
        observe(owner,observer,true)
    }

    private fun observe(owner: LifecycleOwner, observer: Observer<in T>, isSticky:Boolean) {
        super.observe(owner, ObserverWrapper(mCurrentVersion,this,observer,isSticky))
        owner.lifecycle.addObserver(object : LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    //自动反注册
                    instance.mMap.remove(eventName)
                }
            }
        })
    }
}

class ObserverWrapper<T>(
    private var mLastVersion:Int,
    private val mBusLiveData: BusLiveData<T>,
    private val mObserver: Observer<in T>,
    private val isSticky: Boolean):Observer<T>{

    override fun onChanged(t: T) {
        //刚new出来的时候 mLastVersion和mBusLiveData.mCurrentVersion是相等的
        //经过一次setValue或者postValue的时候，mBusLiveData.mCurrentVersion会+1，这时候就需要分发事件了
        if (mLastVersion < mBusLiveData.mCurrentVersion) {
            mLastVersion = mBusLiveData.mCurrentVersion
            mObserver.onChanged(t)
        } else {
            //如果是粘性事件 就分发事件
            if (isSticky && t != null) {
                mObserver.onChanged(t)
            }
        }
    }

}