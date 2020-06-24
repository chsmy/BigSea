package com.chs.bigsea.ui.todo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.PhoneUtils
import com.blankj.utilcode.util.ToastUtils
import com.chs.bigsea.R
import com.chs.lib_common_ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_create_todo.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * author：chs
 * date：2020/6/21
 * des：创建一条todo
 */
class CreateToDoActivity : BaseActivity() {

    private  var pvCustomLunar: TimePickerView? = null

    companion object {
        fun start(context:Context){
            val intent = Intent(context,CreateToDoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.activity_create_todo

    override fun initView() {
        initLunarPicker()
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
        btn_confirm.setOnClickListener {  }
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