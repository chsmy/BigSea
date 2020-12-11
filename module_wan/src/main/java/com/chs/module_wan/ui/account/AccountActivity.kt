package com.chs.module_wan.ui.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.module_wan.R
import com.chs.module_wan.databinding.WanActivityProjectBinding
import com.chs.module_wan.model.AccountNameEntity
import com.google.android.material.tabs.TabLayoutMediator

/**
 * author：chs
 * date：2020/4/5
 * des： 公众号
 */
class AccountActivity : BaseActivity<WanActivityProjectBinding>() {

    private val mViewModel:AccountViewModel by lazy{getViewModel(AccountViewModel::class.java)}

    companion object{
        fun start(content: Context){
            val intent = Intent(content, AccountActivity::class.java)
            content.startActivity(intent)
        }
    }

    override fun onCreateBinding(savedInstanceState: Bundle?): WanActivityProjectBinding {
       return WanActivityProjectBinding.inflate(layoutInflater)
    }

    override fun WanActivityProjectBinding.onViewCreated() {
         includeHead.tvTitleName.text = "公众号"
    }

    override fun initListener() {
    }

    override fun initData() {
        mViewModel.setLoadingViewWrap(binding.viewpager)
        mViewModel.mAccountName.observe(this, Observer {
            setViewPagerAdapter(it)
            TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, position ->
                tab.text = mViewModel.mAccountName.value?.get(position)?.name
            }.attach()
        })
        mViewModel.getAccountNameData()
    }

    private fun setViewPagerAdapter(list: List<AccountNameEntity>) {
        binding.viewpager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return list.size
            }

            override fun createFragment(position: Int): Fragment {
                return AccountFragment.newInstance(list[position].id)
            }
        }
    }


}