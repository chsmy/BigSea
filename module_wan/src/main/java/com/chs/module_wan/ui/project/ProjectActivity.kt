package com.chs.module_wan.ui.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.module_wan.R
import com.chs.module_wan.databinding.WanActivityProjectBinding
import com.chs.module_wan.model.ProjectEntity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.wan_activity_project.*
import kotlinx.android.synthetic.main.wan_include_page_title.*

/**
 * author：chs
 * date：2020/4/5
 * des： 项目
 */
class ProjectActivity : BaseActivity<WanActivityProjectBinding>() {

    private val mViewModel: ProjectViewModel by viewModels()

    companion object{
        fun start(content: Context){
            val intent = Intent(content, ProjectActivity::class.java)
            content.startActivity(intent)
        }
    }

    override fun onCreateBinding(savedInstanceState: Bundle?): WanActivityProjectBinding {
        return WanActivityProjectBinding.inflate(layoutInflater)
    }

    override fun WanActivityProjectBinding.onViewCreated() {
        tv_title_name.text = "项目"
    }

    override fun initListener() {}

    override fun initData() {
        mViewModel.setLoadingViewWrap(viewpager)
        mViewModel.mProjectKinds.observe(this, Observer {
            setViewPagerAdapter(it)
            TabLayoutMediator(tablayout, viewpager) { tab, position ->
                tab.text = mViewModel.mProjectKinds.value?.get(position)?.name
            }.attach()
        })
        mViewModel.getProjectKindData()
    }

    private fun setViewPagerAdapter(list: List<ProjectEntity>) {
        viewpager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return list.size
            }

            override fun createFragment(position: Int): Fragment {
               return ProjectFragment.newInstance(list[position].id)
            }
        }
    }

}