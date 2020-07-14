package com.chs.module_wan.ui.todo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.chs.lib_annotation.ActivityDestination
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_common_ui.base.OnItemClickListener
import com.chs.lib_core.constant.WanRouterKey
import com.chs.lib_core.utils.KeyboardUtils
import com.chs.lib_core.utils.ToastUtils
import com.chs.module_wan.model.TypeBean
import com.chs.module_wan.ui.login.UserManager
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.wan_activity_create_todo.*
import kotlinx.android.synthetic.main.wan_include_page_title.*
import java.text.SimpleDateFormat
import com.chs.module_wan.R
import java.util.*

/**
 * author：chs
 * date：2020/6/21
 * des：创建一条todo
 */
@ActivityDestination(pageUrl = WanRouterKey.ACTIVITY_WAN_CREATE_TODO)
class CreateToDoActivity : BaseActivity() {

    private  var pvCustomLunar: TimePickerView? = null
    private val mViewModel by lazy { getViewModel(CreateToDoModel::class.java) }
    val types = listOf<TypeBean>(TypeBean("工作"), TypeBean("学习"), TypeBean("生活"))
    private var mType = -1;

    companion object {
        fun start(context:Context){
            val intent = Intent(context,CreateToDoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.wan_activity_create_todo

    override fun initView() {
        tv_title_name.text = "创建待办"
        initLunarPicker()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.alignItems = AlignItems.STRETCH
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        rv_type.layoutManager = layoutManager
        val adapter = TypeAdapter(types)
        rv_type.adapter = adapter
        adapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                mType = position+1;
                for ((index) in types.withIndex()){
                    val item = types[index]
                    item.isClicked = index == position
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun initData() {
    }


    override fun initListener() {
        super.initListener()
        rl_select_time.setOnClickListener {
            KeyboardUtils.hideSoftInput(this)
            pvCustomLunar?.show()
        }
        btn_cancel.setOnClickListener { finish() }
        btn_confirm.setOnClickListener {
            checkEmpty()
            mViewModel.createRemoteToDo(et_title.text.toString(),et_content.text.toString(),
            tv_time_res.text.toString(),mType)
        }
        mViewModel.createRes.observe(this, androidx.lifecycle.Observer {
            when (it) {
                0 -> {
                    ToastUtils.showShort(getString(R.string.create_todo_success))
                    finish()
                }
                -1001 -> {
                    UserManager.get().gotoLogin(this)
                }
                else -> {
                    ToastUtils.showShort(getString(R.string.create_todo_failed))
                }
            }
        })
    }

    private fun checkEmpty() {
        if(TextUtils.isEmpty(et_title.text.toString())){
            ToastUtils.showShort(getString(R.string.home_create_todo_title_ness))
            return
        }
        if(TextUtils.isEmpty(tv_time_res.text.toString())){
            ToastUtils.showShort(getString(R.string.home_create_todo_time_ness))
            return
        }
        if(mType == -1){
            ToastUtils.showShort(getString(R.string.home_create_todo_type_ness))
            return
        }
    }

    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private fun initLunarPicker() {
        val selectedDate = Calendar.getInstance() //系统当前时间
        val startDate = Calendar.getInstance()
        startDate[2014, 1] = 23
        val endDate = Calendar.getInstance()
        endDate[2069, 2] = 28
        //时间选择器 ，自定义布局
        pvCustomLunar = TimePickerBuilder(this,
            OnTimeSelectListener { date, v -> //选中事件回调
                tv_time_res.text = getTime(date)
            })
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setLayoutRes(com.chs.lib_common_ui.R.layout.pickerview_custom_lunar) {
                val tvFinish = it.findViewById<TextView>(R.id.tv_finish)
                val tvCancel = it.findViewById<TextView>(R.id.tv_cancel)
                tvFinish.setOnClickListener {
                    pvCustomLunar!!.returnData()
                    pvCustomLunar!!.dismiss()
                }
                tvCancel.setOnClickListener { pvCustomLunar!!.dismiss() }
            }
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
            .setDividerColor(Color.RED)
            .build()
    }

    private fun getTime(date: Date): String? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }

}