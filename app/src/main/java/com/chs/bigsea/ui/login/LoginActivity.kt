package com.chs.bigsea.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chs.bigsea.R
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.room.Cache
import kotlinx.android.synthetic.main.activity_login.*

/**
 * author：chs
 * date：2020/4/11
 * des：
 */
class LoginActivity : BaseActivity() {

    val mViewModel:LoginViewModel by lazy { getViewModel(LoginViewModel::class.java) }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.activity_login

    override fun initView() {
        btn_submit.setOnClickListener{
            doLogin()
        }
    }

    private fun doLogin() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        mViewModel.mLoginEntity.observe(this, Observer {
        })
        mViewModel.doLogin(username,password)
    }

    override fun initData() {
    }
}