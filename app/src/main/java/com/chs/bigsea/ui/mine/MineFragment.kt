package com.chs.bigsea.ui.mine

import androidx.core.content.ContextCompat
import com.chs.bigsea.R
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.module_wan.ui.login.UserManager
import kotlinx.android.synthetic.main.fragment_mine.*

@FragmentDestination(pageUrl = "main/tabs/MineFragment")
class MineFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_mine

    override fun initView() {

        iv_head.background = ContextCompat.getDrawable(requireContext(),R.drawable.icon_head)
        tv_name.text = UserManager.get().getUser()?.nickname
    }

    override fun initData() {
    }

}
