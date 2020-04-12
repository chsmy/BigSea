package com.chs.module_wan.ui.login

import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_core.constant.WanBusKey
import com.chs.lib_core.event.LiveDataBus
import com.chs.module_wan.R
import kotlinx.android.synthetic.main.wan_fragment_register.*

/**
 * author：chs
 * date：2020/4/11
 * des：
 */
class RegisterFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.wan_fragment_register

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun initView() {

    }

    override fun initListener() {
        super.initListener()
        tv_go_to_login.setOnClickListener{
            LiveDataBus.get<String>(WanBusKey.KEY_CLOSE_FRAGMENT).postValue("close")
        }
    }

    override fun initData() {
    }
}