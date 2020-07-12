package com.chs.bigsea.ui.mine

import androidx.core.content.ContextCompat
import com.chs.lib_core.navigation.NavManager
import com.chs.bigsea.R
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_wan.ui.login.UserManager
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.fragment_mine.*

@FragmentDestination(pageUrl = WanRouterKey.FRAGMENT_MAIN_TABLES_MINE,isBelongTab = true)
class MineFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_mine

    override fun initView() {
        ImmersionBar.with(requireActivity())
            .statusBarColor(com.chs.lib_common_ui.R.color.colorPrimary)
            .autoStatusBarDarkModeEnable(true)
            .init()
        iv_head.background = ContextCompat.getDrawable(requireContext(),R.drawable.icon_head)
        tv_name.text = UserManager.get().getUser()?.nickname
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            ImmersionBar.with(requireActivity())
                .statusBarColor(com.chs.lib_common_ui.R.color.colorPrimary)
                .autoStatusBarDarkModeEnable(true)
                .init()
        }
    }

    override fun initListener() {
        super.initListener()
        tv_integral.setOnClickListener{
            NavManager.get()
                .build(WanRouterKey.ACTIVITY_MAIN_MINE_RANK)
                .navigate()
        }
        tv_todo.setOnClickListener {
            NavManager.get()
                .build(WanRouterKey.ACTIVITY_MAIN_MINE_TODO)
                .navigate()
        }
    }

    override fun initData() {
//        play_view.bindData("mine",1080,720,
//            "http://a0.att.hudong.com/30/88/01100000000000144726882521407_s.jpg",
//            "rtmp://203.170.59.43/live/livestream")
    }

}
