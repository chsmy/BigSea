package com.chs.module_wan.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.lib_core.base.BaseActivity
import com.chs.module_wan.R
import kotlinx.android.synthetic.main.wan_activity_navigation.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.QTabView
import q.rorbin.verticaltablayout.widget.TabView

/**
 * author：chs
 * date：2020/4/5
 * des： 导航
 */
class NavigationActivity : BaseActivity() {

    private val mViewModel: NavigationViewModel by lazy { getViewModel(NavigationViewModel::class.java) }
    private val mLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }
    private lateinit var mAdapter: NavAdapter
    private var mCurrentPosition = 0

    companion object{
        fun start(content: Context){
            val intent = Intent(content, NavigationActivity::class.java)
            content.startActivity(intent)
        }
    }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.wan_activity_navigation

    override fun initView() {
        recycler_view.layoutManager = mLayoutManager
    }

    override fun initListener() {
        super.initListener()
        tab_layout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabView?, position: Int) {}
            override fun onTabSelected(tab: TabView?, position: Int) {
                if (mCurrentPosition != position) {
                    mLayoutManager.scrollToPositionWithOffset(position, 0)
                }
            }
        })
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scroll()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun scroll() {
        val firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition()
        if (mCurrentPosition != firstVisibleItemPosition) {
            tab_layout.setTabSelected(firstVisibleItemPosition)
            mCurrentPosition = firstVisibleItemPosition
        }
    }

    override fun initData() {
        mViewModel.mNavigation.observe(this, Observer {
            mAdapter = NavAdapter(it)
            recycler_view.adapter = mAdapter
            for (item in it) {
                val itemTitleTab = ITabView.TabTitle.Builder()
                    .setContent(item.name)
                    .setTextColor(ContextCompat.getColor(this,R.color.colorPrimary),
                        ContextCompat.getColor(this, R.color.text_gray))
                    .build()
                tab_layout.addTab(QTabView(this).setTitle(itemTitleTab))
            }
        })
        mViewModel.getNavigationData()
    }
}