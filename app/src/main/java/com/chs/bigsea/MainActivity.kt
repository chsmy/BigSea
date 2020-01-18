package com.chs.bigsea

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.viewpager2.widget.ViewPager2
import com.chs.bigsea.an.AdapterFragmentPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vp_fragment.adapter =  AdapterFragmentPager(this)
        vp_fragment.offscreenPageLimit = 3
        vp_fragment.isUserInputEnabled = false
        vp_fragment.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                rg_tab?.check(getCheckedId(position))
            }
        })
        bottomNavigationView.setOnBottomClickListener {
            when (it) {
               0 -> vp_fragment.setCurrentItem(it, false)
                1 -> vp_fragment.setCurrentItem(it, false)
               2 -> vp_fragment.setCurrentItem(it, false)
                3 -> vp_fragment.setCurrentItem(it, false)
            }
        }
    }
}
