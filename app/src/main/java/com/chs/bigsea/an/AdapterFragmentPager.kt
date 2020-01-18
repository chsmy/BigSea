package com.chs.bigsea.an

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chs.bigsea.gan.GanFragment
import com.chs.bigsea.mine.MineFragment
import com.chs.bigsea.third.ThirdFragment

/**
 *  @author chs
 *  date: 2020-01-18 11:05
 *  des:
 */
class AdapterFragmentPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 ->{ return AnFragment.newInstance()}
            1 ->{ return GanFragment.newInstance()}
            2 ->{ return ThirdFragment.newInstance()}
            3 ->{ return MineFragment.newInstance()}
        }
        return AnFragment.newInstance()
    }
}
