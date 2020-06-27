package com.chs.module_eye.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_eye.R
import com.chs.module_eye.ui.follow.FollowFragment
import com.chs.module_eye.ui.recommend.RecommendFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.eye_fragment_find.*

@FragmentDestination(pageUrl = WanRouterKey.FRAGMENT_MAIN_TABLES_FIND,isBelongTab = true)
class FindFragment : BaseFragment() {

    private val titles:List<String> by lazy {
        ArrayList<String>().apply {
            add("推荐")
            add("关注")
        }
    }

    override fun layoutId(): Int = R.layout.eye_fragment_find

    override fun initView() {
        ImmersionBar.with(this)
            .statusBarColor(com.chs.lib_common_ui.R.color.color_f0f2f5)
            .autoStatusBarDarkModeEnable(true)
            .init()
        setStatusBarViewHeight(status_bar_find)
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            ImmersionBar.with(requireActivity())
            .statusBarColor(com.chs.lib_common_ui.R.color.color_f0f2f5)
            .autoStatusBarDarkModeEnable(true)
            .init()
        }
    }

    override fun initData() {
        setViewPagerAdapter(titles)
        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun setViewPagerAdapter(list: List<String>) {
        viewpager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return list.size
            }

            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 -> RecommendFragment.newInstance()
                    1 -> FollowFragment.newInstance()
                    else -> RecommendFragment.newInstance()
                }
            }
        }
    }
}
