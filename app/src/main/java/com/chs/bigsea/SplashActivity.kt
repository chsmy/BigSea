package com.chs.bigsea

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.chs.bigsea.databinding.ActivitySplashBinding
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_common_ui.utils.DrawableUtil
import com.gyf.immersionbar.ImmersionBar

/**
 *  @author chs
 *  date: 2020-04-10 11:01
 *  des:  闪屏广告页
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>(){

    private val countDownTimer: CountDownTimer by lazy { createCountDownTimer() }

    override fun onCreateBinding(savedInstanceState: Bundle?): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun ActivitySplashBinding.onViewCreated() {
        ImmersionBar.with(this@SplashActivity).transparentStatusBar().fitsSystemWindows(false).init()
        tvTime.background = DrawableUtil.getRoundDrawable(applicationContext,R.color.colorPrimary,20)
        tvTime.setOnClickListener {
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            finish()
        }
    }

    override fun initData() {
        countDownTimer.start()
    }

    private fun createCountDownTimer():CountDownTimer {
        return object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val value: String = (millisUntilFinished / 1000).toString()
                binding.tvTime.text = (String.format("%ss", value))
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

}