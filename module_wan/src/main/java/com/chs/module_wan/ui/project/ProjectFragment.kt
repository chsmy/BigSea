package com.chs.module_wan.ui.project

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_common_ui.base.OnItemClickListener
import com.chs.lib_common_ui.webview.BrowserActivity
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.module_wan.R
import com.chs.module_wan.model.Article
import kotlinx.android.synthetic.main.wan_fragment_wan.*

/**
 * author：chs
 * date：2020/4/5
 * des： 项目
 */
class ProjectFragment : BaseFragment(){

    private val mAdapter : ProjectAdapter by lazy { ProjectAdapter() }
    private val mViewModel:ProjectViewModel by lazy { getViewModel(ProjectViewModel::class.java) }
    private val projectId:Int by lazy { getIdFromArguments() }

    companion object{
        private const val PARAMETER_ID = "parameter_id"
        fun newInstance(id:Int):ProjectFragment{
             val bundle = Bundle()
            bundle.putInt(PARAMETER_ID,id)
            val projectFragment = ProjectFragment()
            projectFragment.arguments = bundle
            return projectFragment
        }
    }

    private fun getIdFromArguments():Int {
        return arguments?.getInt(PARAMETER_ID,0)?:0
    }

    override fun layoutId(): Int = R.layout.wan_fragment_project

    override fun initView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = mAdapter
    }

    override fun initListener() {
        super.initListener()
        mAdapter.onItemClickListener = object : OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                BrowserActivity.start(requireContext(), mAdapter.currentList?.get(position)?.link)
            }
        }
        refreshview.setOnRefreshListener {
            mViewModel.dataSource?.invalidate()
        }
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(refreshview)
        mViewModel.projectId = projectId
        mViewModel.pageData.observe(this,
            Observer<PagedList<Article>> { t ->
                refreshview.finishRefresh()
                mAdapter.submitList(t)
            })
    }
}