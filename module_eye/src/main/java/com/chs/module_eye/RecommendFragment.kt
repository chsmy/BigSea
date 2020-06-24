package com.chs.module_eye

import com.chs.lib_common_ui.base.BaseFragment

/**
 * author：chs
 * date：2020/6/24
 * des：推荐
 */
class RecommendFragment:BaseFragment() {

    companion object{
        fun newInstance() = RecommendFragment()
    }

    override fun layoutId(): Int = R.layout.eye_fragment_refresh

    override fun initView() {
    }

    override fun initData() {
    }
}