package com.chs.module_wan.ui.todo

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_wan.R
import kotlinx.android.synthetic.main.wan_activity_rank.*
import kotlinx.android.synthetic.main.wan_include_page_title.*

/**
 * author：chs
 * date：2020/7/12
 * des： 待办事物列表
 */
@ActivityDestination(pageUrl = WanRouterKey.ACTIVITY_MAIN_MINE_TODO)
class TodoListActivity:BaseActivity() {

    private val mAdapter :ToDoAdapter by lazy { ToDoAdapter() }
    private val mViewModel by lazy { getViewModel(TodoViewModel::class.java) }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.wan_activity_todo

    override fun initView() {
        tv_title_name.text = "待办事项"
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = mAdapter.getLoadStateAdapter(mAdapter)
    }

    override fun initListener() {
        super.initListener()
        refreshview.setOnRefreshListener {
            mAdapter.refresh()
        }
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(refreshview)
        mViewModel.pageData.observe(this, Observer {
            refreshview.finishRefresh()
            mAdapter.submitData(lifecycle,it)
        })
    }
}