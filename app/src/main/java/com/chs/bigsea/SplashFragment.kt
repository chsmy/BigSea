package com.chs.bigsea

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.chs.lib_common_ui.base.BaseFragment
import com.chs.lib_common_ui.utils.DrawableUtil
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 *  @author chs
 *  date: 2020-04-10 11:01
 *  des:
 */
class SplashFragment : BaseFragment(){

    private val countDownTimer: CountDownTimer by lazy { createCountDownTimer() }
    val mOnTime = MutableLiveData<Long>()

    companion object{
        fun newInstance():SplashFragment{
            return SplashFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_splash

    override fun initView() {
        tv_time.background = DrawableUtil.getRoundDrawable(context,R.color.text_alpha,10)
        tv_time.setOnClickListener {
            mOnTime.value = 0
        }
    }

    override fun initData() {
        countDownTimer.start()
    }

    private fun createCountDownTimer():CountDownTimer {
        return object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val value: String = (millisUntilFinished / 1000).toString()
                tv_time?.text = (String.format("%ss", value))
                mOnTime.value = millisUntilFinished / 1000
            }

            override fun onFinish() {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}