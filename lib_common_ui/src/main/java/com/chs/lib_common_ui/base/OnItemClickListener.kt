package com.chs.lib_common_ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
interface OnItemClickListener {
    fun onItemClick(
        view: View,
        position: Int
    )
}