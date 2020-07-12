package com.chs.bigsea

import android.os.Bundle
import androidx.lifecycle.Observer
import com.chs.bigsea.ui.todo.CreateToDoActivity
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanRouterKey
import com.chs.lib_core.navigation.NavManager
import com.chs.module_wan.ui.CollectManager
import com.chs.module_wan.ui.login.UserManager
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.dialog_fragment_add.*

/**
 * author：chs
 * date：2020/6/20
 * des： 首页中间点击添加图标后弹出的界面
 */
@ActivityDestination(pageUrl = WanRouterKey.DIALOG_ACTIVITY_HOME, isBelongTab = true)
class HomeDialogActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.bottom_in, 0)
        super.onCreate(savedInstanceState)
    }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.dialog_fragment_add

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar().fitsSystemWindows(false).init()
    }

    override fun initData() {
    }

    override fun initListener() {
        super.initListener()
        ivToDo.setOnClickListener {
            if (!UserManager.get().isNotLogin()) {
                UserManager.get().gotoLogin(this).observe(this, Observer {
                    //登录成功去执行原来要执行的操作
                    CreateToDoActivity.start(this)
                })
            }else{
                CreateToDoActivity.start(this)
            }
        }
        ivVideo.setOnClickListener {
            NavManager.get()
                .build(WanRouterKey.ACTIVITY_VIDEO_PUBLISH)
                .navigate()
        }
        ivClose.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.bottom_out)
    }
}