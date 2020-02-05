package com.chs.lib_common_ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author：chs
 * date：2020/2/5
 * des：
 */
abstract class BaseAdapter<T>(private var data:List<T>) : RecyclerView.Adapter<BaseViewHolder<T>>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(),parent,false)
        return createCurrentViewHolder(view,viewType)
    }

    abstract fun getLayoutId(): Int

    abstract fun createCurrentViewHolder(view: View,viewType: Int): BaseViewHolder<T>

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.setContent(data[position])
    }

    fun getData():List<T>{
        return data
    }

}