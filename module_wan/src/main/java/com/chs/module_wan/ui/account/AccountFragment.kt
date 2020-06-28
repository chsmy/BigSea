package com.chs.module_wan.ui.account

import PostLoadStateAdapter
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_common_ui.base.OnItemClickListener
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_common_ui.webview.BaseWebActivity
import com.chs.module_wan.R
import com.chs.module_wan.model.Article
import kotlinx.android.synthetic.main.wan_fragment_wan.recyclerview
import kotlinx.android.synthetic.main.wan_fragment_wan.refreshview

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class AccountFragment : BaseFragment(){

    private val mAdapter : AccountAdapter by lazy { AccountAdapter() }
    private val mViewModel:AccountViewModel by lazy { getViewModel(AccountViewModel::class.java) }
    private val accountId:Int by lazy { getIdFromArguments() }

    companion object{
        private const val PARAMETER_ID = "parameter_id"
        fun newInstance(id:Int):AccountFragment{
            val bundle = Bundle()
            bundle.putInt(PARAMETER_ID,id)
            val projectFragment = AccountFragment()
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
        recyclerview.adapter = mAdapter.getLoadStateAdapter(mAdapter)
    }

    override fun initListener() {
        super.initListener()
        mAdapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                BaseWebActivity.start(requireContext(), mAdapter.getCurrentItem(position)?.link)
            }
        }
        refreshview.setOnRefreshListener {
            mAdapter.refresh()
        }
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(refreshview)
        mViewModel.accountId = accountId
        mViewModel.pageData.observe(this,
                 Observer  { t ->
                mAdapter.submitData(lifecycle,t)
                refreshview.finishRefresh()
            })
    }
}