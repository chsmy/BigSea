package com.chs.module_wan.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.BarUtils
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.banner.NetViewHolder
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_common_ui.base.OnItemChildClickListener
import com.chs.lib_common_ui.base.OnItemClickListener
import com.chs.lib_common_ui.model.Banner
import com.chs.lib_common_ui.webview.BrowserActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_wan.R
import com.chs.module_wan.model.Article
import com.chs.module_wan.model.HomeOpt
import com.chs.module_wan.ui.CollectManager
import com.chs.module_wan.ui.CollectViewModel
import com.chs.module_wan.ui.account.AccountActivity
import com.chs.module_wan.ui.login.UserManager
import com.chs.module_wan.ui.navigation.NavigationActivity
import com.chs.module_wan.ui.project.ProjectActivity
import com.chs.module_wan.ui.system.SystemActivity
import com.gyf.immersionbar.ImmersionBar
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.constants.PageStyle
import kotlinx.android.synthetic.main.wan_fragment_wan.*
import kotlinx.android.synthetic.main.wan_title_bar.*

@FragmentDestination(pageUrl = WanRouterKey.WAN_MAIN_TABLES_HOME_FRAGMENT, asStarter = true)
class HomeFragment : BaseFragment(){

    private val mHomeViewModel:HomeViewModel by lazy {getViewModel(HomeViewModel::class.java)}
    private val mCollectModel:CollectViewModel by lazy {getViewModel(CollectViewModel::class.java)}
    private val mAdapter:WanAdapter by lazy { WanAdapter() }
    private lateinit var mBannerViewPager:BannerViewPager<Banner, NetViewHolder>
    private var bannerHeight:Int = 0
    private var clickPosition : Int = 0
    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutId(): Int {
        return R.layout.wan_fragment_wan
    }

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = mAdapter
        addBannerView()
        addOptionsView()
        ImmersionBar.with(requireActivity()).transparentStatusBar().init()
        ImmersionBar.setTitleBar(this, toolbar)
    }

    private fun addOptionsView() {
        val optRootView = LayoutInflater.from(requireContext()).inflate(R.layout.wan_item_option,recyclerview,false)
        val optList = ArrayList<HomeOpt>()
        optList.add(HomeOpt("体系", R.drawable.home_opt_1, ""))
        optList.add(HomeOpt("导航", R.drawable.home_opt_2, ""))
        optList.add(HomeOpt("项目", R.drawable.home_opt_3, ""))
        optList.add(HomeOpt("公众号", R.drawable.home_opt_4, ""))
        val rvOpt = optRootView.findViewById<RecyclerView>(R.id.rv_opt)
        rvOpt.layoutManager = GridLayoutManager(context,4)
        val wanOptAdapter = WanOptAdapter(optList)
        rvOpt.adapter = wanOptAdapter
        wanOptAdapter.onItemClickListener = object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                when(position){
                    0 -> SystemActivity.start(requireContext())
                    1 -> NavigationActivity.start(requireContext())
                    2 -> ProjectActivity.start(requireContext())
                    3 -> AccountActivity.start(requireContext())
                }

            }
        }
        mAdapter.addHeaderView(optRootView)
    }

    override fun initListener() {
        super.initListener()
        refreshview.setOnRefreshListener {
             mHomeViewModel.dataSource?.invalidate()
        }
        refreshview.setOnMultiPurposeListener(object : SimpleMultiPurposeListener(){
            override fun onHeaderMoving(
                header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight)
                if(isDragging&&toolbar.visibility==View.VISIBLE&&offset>100){
                    toolbar.visibility = View.GONE
                }
                if(offset == 0 && toolbar.visibility==View.GONE){
                    toolbar.visibility = View.VISIBLE
                }
            }

            override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {
                super.onHeaderFinish(header, success)
                toolbar.visibility = View.VISIBLE
            }
        })
        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var totalDy = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalDy += dy
                if (totalDy <= bannerHeight) {
                    val alpha: Float = totalDy.toFloat() / bannerHeight
                    toolbar.setBackgroundColor(
                        ColorUtils.blendARGB(
                            Color.TRANSPARENT
                            , ContextCompat.getColor(requireActivity(), R.color.colorPrimary), alpha
                        )
                    )
                } else {
                    toolbar.setBackgroundColor(
                        ColorUtils.blendARGB(
                            Color.TRANSPARENT
                            , ContextCompat.getColor(requireActivity(), R.color.colorPrimary), 1f
                        )
                    )
                }
            }
        })
        mAdapter.onItemClickListener = object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                BrowserActivity.start(requireContext(), mAdapter.currentList?.get(position)?.link)
            }
        }
        mAdapter.onItemChildClickListener = object : OnItemChildClickListener {
            override fun onClick(view: View,position: Int) {
                clickPosition = position
                val article = mAdapter.currentList!![position]
                handleCollect(article!!)
            }
        }
        mCollectModel.mCollectRes.observe(this, Observer {
            if(it.errorCode == 0){
                val item = mAdapter.currentList?.get(clickPosition)
                mAdapter.currentList?.get(clickPosition)?.collect = !item!!.collect
                mAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun handleCollect(article: Article) {
        if (UserManager.get().isNotLogin()) {
            CollectManager.goToLogin(article,this,requireContext(),mCollectModel)
        } else {
            CollectManager.toggleCollect(article,mCollectModel)
        }
    }

    private fun addBannerView() {
        val bannerView = LayoutInflater.from(requireContext()).inflate(R.layout.wan_header_home,recyclerview,false)
        mBannerViewPager = bannerView.findViewById(R.id.banner)
        mHomeViewModel.mBanner.observe(this, Observer<List<Banner>> {
            mBannerViewPager.setCanLoop(true)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setPageMargin(resources.getDimensionPixelOffset(R.dimen.dp_10))
                .setRevealWidth(resources.getDimensionPixelOffset(R.dimen.dp_10))
                .setPageStyle(PageStyle.MULTI_PAGE)
                .setHolderCreator{ NetViewHolder() }
                .setInterval(3000)
                .setOnPageClickListener { position -> BrowserActivity.start(requireContext(),it[position].url) }
                .create(it)
            mBannerViewPager.startLoop()
        })
        mHomeViewModel.getBannerData()
        mAdapter.addHeaderView(bannerView)
        val bannerParams = mBannerViewPager.layoutParams
        val titleBarParams = toolbar.layoutParams
        bannerHeight = bannerParams.height - titleBarParams.height - BarUtils.getStatusBarHeight()
    }

    override fun onPause() {
        super.onPause()
        mBannerViewPager.stopLoop()
    }

    override fun initData() {
//        mHomeViewModel.setLoadingViewWrap(refreshview)
        mHomeViewModel.pageData.observe(this,
            Observer<PagedList<Article>> { t ->
                refreshview.finishRefresh()
                mAdapter.submitList(t) })
    }
}
