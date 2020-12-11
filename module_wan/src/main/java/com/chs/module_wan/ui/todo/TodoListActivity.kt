package com.chs.module_wan.ui.todo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.module_wan.R
import com.chs.module_wan.databinding.WanActivityTodoBinding
import kotlinx.android.synthetic.main.wan_activity_rank.*
import kotlinx.android.synthetic.main.wan_include_page_title.*

/**
 * author：chs
 * date：2020/7/12
 * des： 待办事物列表
 */
@ActivityDestination(pageUrl = WanRouterKey.ACTIVITY_MAIN_MINE_TODO)
class TodoListActivity:BaseActivity<WanActivityTodoBinding>() {

    private val mAdapter :ToDoAdapter by lazy { ToDoAdapter() }
    private val mViewModel:TodoViewModel by viewModels()

    override fun onCreateBinding(savedInstanceState: Bundle?): WanActivityTodoBinding {
        return WanActivityTodoBinding.inflate(layoutInflater)
    }

    override fun WanActivityTodoBinding.onViewCreated() {
        tv_title_name.text = "待办事项"
        setRecyclerView(recyclerview)
    }

    private fun setRecyclerView(recyclerview: RecyclerView) {
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