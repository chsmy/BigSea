package com.chs.bigsea.an

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.bigsea.R
import com.chs.lib_core.base.BaseFragment
import com.gyf.immersionbar.ImmersionBar
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.constants.PageStyle
import kotlinx.android.synthetic.main.fragment_wan.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AnFragment : BaseFragment<AnViewModel>() {

    private val mViewModel:AnViewModel by viewModel()
    private val mAdapter:HomeAdapter by lazy { HomeAdapter(ArrayList()) }
    private lateinit var bannerViewPager:BannerViewPager<HomeBanner,NetViewHolder>
    companion object {
        fun newInstance() = AnFragment()
    }


    override fun layoutId(): Int {
        return R.layout.fragment_wan
    }

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = mAdapter
        mAdapter.setNewData(ArrayList())
        addBannerView()

        ImmersionBar.with(this).statusBarColorTransformEnable(false)
            .keyboardEnable(false)
            .navigationBarColor(R.color.colorPrimary)
            .init()
    }

    private fun addBannerView() {
        val bannerView = LayoutInflater.from(requireContext()).inflate(R.layout.header_home,recyclerview,false)
        bannerViewPager = bannerView.findViewById<BannerViewPager<HomeBanner,NetViewHolder>>(R.id.banner)
        val list = getList<HomeBanner>()
        bannerViewPager.setCanLoop(true)
            .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
            .setPageMargin(resources.getDimensionPixelOffset(R.dimen.dp_10))
            .setRevealWidth(resources.getDimensionPixelOffset(R.dimen.dp_10))
            .setPageStyle(PageStyle.MULTI_PAGE)
            .setHolderCreator{NetViewHolder()}
            .setInterval(5000)
            .create(list)
        bannerViewPager.startLoop()
        mAdapter.addHeaderView(bannerView)
    }

    override fun onPause() {
        super.onPause()
        bannerViewPager.stopLoop()
    }

    private fun <T> getList(): MutableList<HomeBanner> {
        val res = mutableListOf<HomeBanner>()
        res.add(HomeBanner("https://www.wanandroid.com/blogimgs/74a84e45-7f93-476d-bc85-446e1d118b6f.png",""))
        res.add(HomeBanner("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",""))
        res.add(HomeBanner("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",""))
        return res
    }

    override fun initData() {
        mViewModel.getBannerData()

    }

}
