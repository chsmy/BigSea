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
           return LiveDataEvent.instence.with(key) as BusLiveData<T>
        }
    }
}

class LiveDataEvent {

    companion object{
        val instence = LiveDataEvent()
    }

    val mMap:ConcurrentHashMap<String,BusLiveData<Any>> by lazy { ConcurrentHashMap<String,BusLiveData<Any>>() }

    fun with(key:String):BusLiveData<Any>{
       var busLiveData = mMap[key]
        if(busLiveData == null){
            busLiveData = BusLiveData(key, instence)
            mMap[key] = busLiveData
        }
        return busLiveData
    }

}

class BusLiveData<T>(val eventName:String,val instence:LiveDataEvent) : LiveData<T>(){

    var mCurrentVersion = 0

    override fun setValue(value: T) {
        mCurrentVersion ++
        super.setValue(value)
    }

    override fun postValue(value: T) {
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
                    instence.mMap.remove(eventName)
                }
            }
        })
    }
}

class ObserverWrapper<T>(var mLastVersion:Int, private val mBusLiveData: BusLiveData<T>,
                         private val mObserver: Observer<in T>, private val isSticky: Boolean):Observer<T>{

    override fun onChanged(t: T) {
        if (mLastVersion < mBusLiveData.mCurrentVersion) {
            mLastVersion = mBusLiveData.mCurrentVersion
            mObserver.onChanged(t)
        } else {
            if (isSticky && t != null) {
                mObserver.onChanged(t)
            }
        }
    }

}