package com.chs.lib_common_ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * @author：chs
 * date：2020/2/5
 * des：
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    override val containerView: View?
        get() = itemView


    abstract fun setContent(item:T,position:Int)
}