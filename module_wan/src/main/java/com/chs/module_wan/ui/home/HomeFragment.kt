package com.chs.module_wan.ui.home

import android.Manifest
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.lib_annotation.FragmentDestination
import com.chs.lib_common_ui.banner.BannerAdapter
import com.chs.lib_common_ui.banner.NetViewHolder
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_common_ui.base.OnItemChildClickListener
import com.chs.lib_common_ui.base.OnItemClickListener
import com.chs.lib_common_ui.model.Banner
import com.chs.lib_common_ui.webview.BaseWebActivity
import com.chs.lib_core.constant.Constant
import com.chs.lib_core.constant.WanRouterKey
import com.chs.lib_core.extension.getStatusBarHeight
import com.chs.lib_core.extension.showShort
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
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.permissionx.guolindev.PermissionX
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.wan_fragment_wan.*
import kotlinx.android.synthetic.main.wan_title_bar.*

@FragmentDestination(pageUrl = WanRouterKey.FRAGMENT_MAIN_TABLES_HOME, asStarter = true,isBelongTab = true)
class HomeFragment : BaseFragment() {
    private val mHomeViewModel: HomeViewModel by viewModels()
    private val mCollectModel: CollectViewModel by viewModels()
    private val mAdapter: WanAdapter by lazy { WanAdapter() }
    private lateinit var mBannerViewPager: BannerViewPager<Banner, NetViewHolder>
    private var bannerHeight: Int = 0
    private var clickPosition: Int = 0

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutId(): Int {
        return R.layout.wan_fragment_wan
    }

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = mAdapter.getLoadStateAdapter(mAdapter)
        addBannerView()
        addOptionsView()
        ImmersionBar.with(requireActivity()).transparentStatusBar().init()
        ImmersionBar.setTitleBar(this, toolbar)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            ImmersionBar.with(requireActivity()).transparentStatusBar().init()
            ImmersionBar.setTitleBar(this, toolbar)
        }
    }

    private fun addOptionsView() {
        val optRootView = LayoutInflater.from(requireContext())
            .inflate(R.layout.wan_item_option, recyclerview, false)
        val optList = ArrayList<HomeOpt>()
        optList.apply {
            add(HomeOpt("体系", R.drawable.home_opt_1, ""))
            add(HomeOpt("导航", R.drawable.home_opt_2, ""))
            add(HomeOpt("项目", R.drawable.home_opt_3, ""))
            add(HomeOpt("公众号", R.drawable.home_opt_4, ""))
        }
        val rvOpt = optRootView.findViewById<RecyclerView>(R.id.rv_opt)
        rvOpt.layoutManager = GridLayoutManager(context, 4)
        val wanOptAdapter = WanOptAdapter(optList)
        rvOpt.adapter = wanOptAdapter
        wanOptAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                when (position) {
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
            mAdapter.refresh()
        }
        refreshview.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
                super.onHeaderMoving(
                    header,
                    isDragging,
                    percent,
                    offset,
                    headerHeight,
                    maxDragHeight
                )
                if (isDragging && toolbar.visibility == View.VISIBLE && offset > 100) {
                    toolbar.visibility = View.GONE
                }
                if (offset == 0 && toolbar.visibility == View.GONE) {
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
        mAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                BaseWebActivity.start(requireContext(), mAdapter.getCurrentItem(position)?.link)
            }
        }
        mAdapter.onItemChildClickListener = object : OnItemChildClickListener {
            override fun onClick(view: View, position: Int) {
                clickPosition = position
                val article = mAdapter.getCurrentItem(position)
                handleCollect(article!!)
            }
        }
        mCollectModel.mCollectRes.observe(this, Observer {
            if (it.errorCode == 0) {
                val item = mAdapter.getCurrentItem(clickPosition)
                mAdapter.getCurrentItem(clickPosition)?.collect = !item!!.collect
                mAdapter.notifyDataSetChanged()
            } else if (it.errorCode == -1001) {
                 UserManager.get().gotoLogin(requireContext())
            }
        })
        iv_scan.setOnClickListener {
            PermissionX.init(activity)
                .permissions(Constant.QR_CODE_PERMISSION)
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        ScanUtil.startScan(requireActivity(), Constant.REQUEST_CODE_SCAN_ONE, HmsScanAnalyzerOptions.Creator().create())
                    } else {
                        requireContext().showShort("没有相机权限权限")
                    }
                }
        }
    }

    private fun handleCollect(article: Article) {
        if (!UserManager.get().isNotLogin()) {
            CollectManager.goToLogin(article, this, requireContext(), mCollectModel)
        } else {
            CollectManager.toggleCollect(article, mCollectModel)
        }
    }

    private fun addBannerView() {
        val bannerView = LayoutInflater.from(requireContext())
            .inflate(R.layout.wan_header_home, recyclerview, false)
        mBannerViewPager = bannerView.findViewById(R.id.banner)

        mHomeViewModel.mBanner.observe(this, Observer<List<Banner>> {
            mBannerViewPager.apply {
                adapter = BannerAdapter()
                setAutoPlay(true)
                setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                setLifecycleRegistry(lifecycle)
                setIndicatorMargin(0, 0, 0, resources.getDimension(R.dimen.dp_10).toInt())
                setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                setIndicatorSliderRadius(resources.getDimension(R.dimen.dp_3).toInt(), resources.getDimension(R.dimen.dp_4_5).toInt())
                setIndicatorSliderColor(ContextCompat.getColor(requireContext(), R.color.white_),
                    ContextCompat.getColor(requireContext(), R.color.white))
               .setOnPageClickListener { position ->
                        BaseWebActivity.start(
                            requireContext(),
                            it[position].url
                        )
                    }
            }.create(it)
            mBannerViewPager.startLoop()
        })
        mHomeViewModel.getBannerData()
        mAdapter.addHeaderView(bannerView)
        val bannerParams = mBannerViewPager.layoutParams
        val titleBarParams = toolbar.layoutParams
        bannerHeight = bannerParams.height - titleBarParams.height - getStatusBarHeight()
    }

    override fun initData() {
        mHomeViewModel.setLoadingViewWrap(refreshview)
        mHomeViewModel.pageData.observe(this,
            Observer { t ->
                refreshview.finishRefresh()
                mAdapter.submitData(lifecycle,t)
            })
    }
}
