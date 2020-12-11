package com.chs.lib_common_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.chs.lib_common_ui.R
import com.gyf.immersionbar.ImmersionBar

/**
 * author：chs
 * date：2020/3/29
 * des：
 */
abstract class BaseActivity<T: ViewBinding> : AppCompatActivity() {

    private var _binding: T? = null

    val binding: T
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateBinding(savedInstanceState).also { _binding = it }
        val view = binding.root
        setContentView(view)
        binding.onViewCreated()
        initListener()
        initData()
    }

    abstract fun onCreateBinding(savedInstanceState: Bundle?): T

    abstract fun T.onViewCreated()

     private fun getStatusBarView(): View{
         val view  = View(this)
         val paramas = ViewGroup.LayoutParams(MATCH_PARENT,ImmersionBar.getStatusBarHeight(this))
         view.layoutParams = paramas
         return view
     }

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

}