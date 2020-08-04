package com.chs.bigsea

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_common_ui.webview.BaseWebActivity
import com.chs.lib_core.cloud.CloudService
import com.chs.lib_core.constant.Constant
import com.chs.lib_core.constant.SpConstant
import com.chs.lib_core.extension.logI
import com.chs.lib_core.extension.showLong
import com.chs.lib_core.navigation.NavGraphBuilder
import com.chs.lib_core.utils.SPUtils
import com.gyf.immersionbar.ImmersionBar
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val viewModel by lazy { getViewModel(MainViewModel::class.java) }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.activity_main

    override fun setContentView(layoutResID: Int) {
        //由于 启动时style中设置了 windowBackground属性，所以要在进入主页后,把窗口背景清理掉
        setTheme(R.style.AppTheme)
        super.setContentView(layoutResID)
    }

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar().fitsSystemWindows(false).init()
        showSplash()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavGraphBuilder.build(navController,this,R.id.nav_host_fragment)
        nav_view.setNavController(navController)
    }

    /**
     * 显示广告页面
     */
    private fun showSplash() {
        val transaction = supportFragmentManager.beginTransaction()
        var splashFragment = supportFragmentManager.findFragmentByTag(SplashFragment::class.java.simpleName) as SplashFragment?
        if(splashFragment!=null){
            if(splashFragment.isAdded){
                transaction.show(splashFragment).commitAllowingStateLoss()
            }else{
                transaction.remove(splashFragment).commitAllowingStateLoss()
                splashFragment = SplashFragment.newInstance()
                transaction.add(R.id.container, splashFragment,SplashFragment::class.java.simpleName).commit()
            }
        }else{
            splashFragment = SplashFragment.newInstance()
            transaction.add(R.id.container, splashFragment,SplashFragment::class.java.simpleName).commit()
        }
        splashFragment.mOnTime.observe(this, Observer {
            if(it == 0L){
                closeSplashFragment()
            }
        })
    }

    private fun closeSplashFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(SplashFragment::class.java.simpleName)
        if(fragment!=null){
            transaction.remove(fragment).commit()
        }
    }

    override fun initListener() {
    }

    override fun initData() {
        checkToken()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_SCAN_ONE) {
            val obj = data?.getParcelableExtra(ScanUtil.RESULT) as HmsScan
            if(obj!=null){
                val originalValue = obj.originalValue
                if(originalValue.contains("http")){
                    BaseWebActivity.start(this, originalValue)
                }else{
                    showLong(originalValue)
                }
            }
        }
    }
    /**
     * 检查并获取im的token
     */
    private fun checkToken() {
        val token = SPUtils.getInstance().getString(SpConstant.IM_TOKEN)
        if(!TextUtils.isEmpty(token)){
            //连接服务
            startCloudService()
        }else{
            //创建token
            createToken()
        }
    }

    /**
     * 通过融云的接口获取token
     */
    private fun createToken() {
        viewModel.tokenData.observe(this, Observer {
            if(it.code == 200){
                if(!TextUtils.isEmpty(it.token)){
                    SPUtils.getInstance().put(SpConstant.IM_TOKEN,it.token)
                    //连接服务
                    startCloudService()
                }
            }
        })
        //模拟用户 chs2018 id 1000
        //模拟用户 chs2019 id 1001
        viewModel.getCloudToken("1000","chs2018","https://pic3.zhimg.com/v2-e08df1d10ae9003a3d85972edaa04304_is.jpg")
    }

    private fun startCloudService() {
        logI("startCloudService")
        startService(Intent(this, CloudService::class.java))
    }
}
