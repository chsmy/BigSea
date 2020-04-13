package com.chs.bigsea.ui.mine

import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.chs.bigsea.NavManager
import com.chs.bigsea.R
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_core.constant.WanRouterKey
import com.chs.lib_core.navigation.NavGraphBuilder
import com.chs.module_wan.ui.login.UserManager
import kotlinx.android.synthetic.main.fragment_mine.*

@FragmentDestination(pageUrl = WanRouterKey.WAN_MAIN_TABLES_MINE_FRAGMENT)
class MineFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_mine

    override fun initView() {

        iv_head.background = ContextCompat.getDrawable(requireContext(),R.drawable.icon_head)
        tv_name.text = UserManager.get().getUser()?.nickname
    }

    override fun initListener() {
        super.initListener()
        tv_integral.setOnClickListener{
            NavManager.sNavController?.navigate(NavGraphBuilder.getItemId(WanRouterKey.WAN_MAIN_MINE_RANK_ACTIVITY))
        }
    }

    override fun initData() {
    }

}
