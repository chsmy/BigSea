package com.chs.lib_common_ui.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.chs.lib_common_ui.R
import com.gyf.immersionbar.ImmersionBar

/**
 * author：chs
 * date：2020/3/29
 * des：
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView(savedInstanceState))
//        ImmersionBar.with(this)
//            .statusBarColor(R.color.colorPrimary)
//            .autoStatusBarDarkModeEnable(true)
//            .fitsSystemWindows(true)
//            .init()

        initView()
        initListener()
        initData()
    }

     private fun getStatusBarView(): View{
         val view  = View(this)
         val paramas = ViewGroup.LayoutParams(MATCH_PARENT,ImmersionBar.getStatusBarHeight(this))
         view.layoutParams = paramas
         return view
     }

    /**
     * 返回一个用于显示界面的布局id
     *
     * @return 视图id
     */
    abstract fun getContentView(savedInstanceState: Bundle?): Int

    /**
     * 初始化View的代码写在这个方法中
     */
    abstract fun initView()

    /**
     * 初始化监听器的代码写在这个方法中
     */
    open fun initListener(){}

    /**
     * 初始数据的代码写在这个方法中，用于从服务器获取数据
     */
    abstract fun initData()

    /**
     * @param view 标题栏左侧按钮点击事件
     */
    open fun goBack(view: View) {
        onBackPressed()
    }

    open fun <T : ViewModel?> getViewModel(clazz: Class<T>): T {
        return ViewModelProvider(this).get(clazz)
    }

    open fun <T : ViewModel?> getViewModel(owner: ViewModelStoreOwner?, clazz: Class<T>): T {
        return ViewModelProvider(owner!!).get(clazz)
    }
}