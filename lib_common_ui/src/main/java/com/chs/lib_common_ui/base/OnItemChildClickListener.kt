package com.chs.lib_common_ui.base

import android.view.View

/**
 *  @author chs
 *  date: 2020-04-13 10:01
 *  des:  列表中的子view被点击
 */
interface OnItemChildClickListener {
    fun onClick(view: View,position: Int)
}