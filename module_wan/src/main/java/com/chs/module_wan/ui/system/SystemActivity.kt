package com.chs.module_wan.ui.system

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.module_wan.R
import kotlinx.android.synthetic.main.wan_fragment_wan.*
import kotlinx.android.synthetic.main.wan_include_page_title.*

/**
 * author：chs
 * date：2020/4/5
 * des： 体系
 */
class SystemActivity: BaseActivity() {

    private val mAdapter: SystemAdapter by lazy { SystemAdapter(arrayListOf()) }
    private val mSystemViewModel:SystemViewModel by lazy { getViewModel(SystemViewModel::class.java) }

    companion object{
        fun start(content:Context){
            val intent = Intent(content,SystemActivity::class.java)
            content.startActivity(intent)
        }
    }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.wan_activity_system

    override fun initView() {
        tv_title_name.text = "体系"
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = mAdapter
    }

    override fun initListener() {

    }

    override fun initData() {
        mSystemViewModel.setLoadingViewWrap(refreshview)
        mSystemViewModel.mSystemEntity.observe(this, Observer {
            mAdapter.setDataAndRefresh(it)
        })
    }
}