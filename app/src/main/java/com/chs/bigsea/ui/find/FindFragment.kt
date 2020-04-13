package com.chs.bigsea.ui.find

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chs.bigsea.R
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_core.constant.WanRouterKey

@FragmentDestination(pageUrl = WanRouterKey.WAN_MAIN_TABLES_FIND_FRAGMENT)
class FindFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_third

    override fun initView() {
    }

    override fun initData() {
    }

}
