package com.chs.bigsea.ui.apply

import com.bumptech.glide.Glide
import com.chs.bigsea.R
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_core.constant.WanRouterKey
import com.chs.lib_core.imageloader.ImageLoader
import kotlinx.android.synthetic.main.fragment_gan.*
import org.koin.androidx.viewmodel.ext.android.viewModel
@FragmentDestination(pageUrl = WanRouterKey.FRAGMENT_MAIN_TABLES_APPLY)
class ApplyFragment : BaseFragment() {

    private val mViewModel:ApplyViewModel by viewModel()

    companion object {
        fun newInstance() = ApplyFragment()
    }

    override fun layoutId(): Int = R.layout.fragment_gan

    override fun initView() {
        ImageLoader.url("https://www.wanandroid.com/blogimgs/74a84e45-7f93-476d-bc85-446e1d118b6f.png").into(image)
        Glide.with(requireActivity()).asBitmap().load("https://www.wanandroid.com/blogimgs/74a84e45-7f93-476d-bc85-446e1d118b6f.png").into(image)
    }

    override fun initData() {

    }
}
