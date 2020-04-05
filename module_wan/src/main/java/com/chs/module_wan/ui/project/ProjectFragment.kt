package com.chs.module_wan.ui.project

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_core.base.BaseFragment
import com.chs.module_wan.R
import com.chs.module_wan.model.DataX
import com.chs.module_wan.model.ProjectListItemData
import kotlinx.android.synthetic.main.wan_fragment_wan.*

/**
 * author：chs
 * date：2020/4/5
 * des： 项目
 */
class ProjectFragment : BaseFragment<ProjectViewModel>(){

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

    override fun initData() {
        mViewModel.projectId = projectId
        mViewModel.pageData.observe(this,
            Observer<PagedList<ProjectListItemData>> { t -> mAdapter.submitList(t) })
    }

    override fun immersionBarEnabled(): Boolean {
        return true
    }

    override fun initImmersionBar() {
    }

}