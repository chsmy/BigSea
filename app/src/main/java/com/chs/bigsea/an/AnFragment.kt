package com.chs.bigsea.an

import com.chs.bigsea.R
import com.chs.lib_core.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class AnFragment : BaseFragment<AnViewModel>() {

    private val mViewModel:AnViewModel by viewModel()

    companion object {
        fun newInstance() = AnFragment()
    }


    override fun layoutId(): Int {
        return R.layout.wan_fragment
    }

    override fun initView() {

    }

    override fun initData() {
        mViewModel.getBannerData()
    }

}
