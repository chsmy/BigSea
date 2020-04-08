package com.chs.lib_core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 *  @author chs
 *  date: 2020-01-04 16:38
 *  des:
 */
abstract class BaseFragment<VM : BaseViewModel> : Fragment(), SimpleImmersionOwner {

    private var hasLoaded = false
//    val mLoadService: LoadService<Any> by lazy { setLoadingViewWrap() }
    var loadView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLoadingViewWrap()
        initView()
        initData()
        initListener()
    }

    private fun setLoadingViewWrap() : LoadService<Any>? {
        return if(loadView!=null){
            LoadSir.getDefault().register(loadView, Callback.OnReloadListener {
                onNetReload(it)
            })
        }else{
            null
        }
    }

    protected open fun onNetReload(v: View?) {}

    override fun onResume() {
        super.onResume()
        if(!hasLoaded){
            onVisible()
        }
    }

    private fun onVisible(){
        if(lifecycle.currentState == Lifecycle.State.STARTED){
            lazyLoad()
            hasLoaded = true
        }
    }

    /**
     * 懒加载
     */
    open fun lazyLoad() {}

    abstract fun layoutId():Int

    abstract fun initView()

    abstract fun initData()

    open fun initListener(){}

    open fun <T : ViewModel?> getViewModel(clazz: Class<T>): T {
        return ViewModelProvider(this).get(clazz)
    }

    open fun <T : ViewModel?> getViewModel(
        owner: ViewModelStoreOwner?,
        clazz: Class<T>
    ): T {
        return ViewModelProvider(owner!!).get(clazz)
    }
}