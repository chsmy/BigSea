package com.chs.bigsea.gan

import com.bumptech.glide.Glide
import com.chs.bigsea.R
import com.chs.lib_core.base.BaseFragment
import com.chs.lib_core.imageloader.ImageLoader
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.fragment_gan.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GanFragment : BaseFragment<GanViewModel>() {

    private val mViewModel:GanViewModel by viewModel()

    companion object {
        fun newInstance() = GanFragment()
    }

    override fun layoutId(): Int = R.layout.fragment_gan

    override fun initView() {
        ImageLoader.url("https://www.wanandroid.com/blogimgs/74a84e45-7f93-476d-bc85-446e1d118b6f.png").into(image)
        Glide.with(requireActivity()).asBitmap().load("https://www.wanandroid.com/blogimgs/74a84e45-7f93-476d-bc85-446e1d118b6f.png").into(image)
    }

    override fun initData() {

    }

    override fun immersionBarEnabled(): Boolean {
        return true
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init()
        ImmersionBar.with(this).statusBarColorTransformEnable(false)
            .keyboardEnable(false)
            .navigationBarColor(R.color.colorPrimary)
            .init()
    }

}
