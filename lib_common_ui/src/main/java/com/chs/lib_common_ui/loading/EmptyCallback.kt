package com.chs.lib_common_ui.loading

import com.chs.lib_common_ui.R
import com.kingja.loadsir.callback.Callback

/**
 *  @author chs
 *  date: 2020-04-08 17:25
 *  des:
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int = R.layout.net_layout_empty
}