package com.chs.module_wan.ui.rank

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.module_wan.R
import com.chs.module_wan.model.RankList
import kotlinx.android.synthetic.main.wan_item_rank.*

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class RankAdapter :
    AbsPageListAdapter<RankList, RankHolder>(object : DiffUtil.ItemCallback<RankList>() {
        override fun areItemsTheSame(oldItem: RankList, newItem: RankList): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: RankList, newItem: RankList): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun getItemViewType2(position: Int): Int  = R.layout.wan_item_rank

    override fun createCurrentViewHolder(view: View, viewType: Int): RankHolder {
        return RankHolder(view)
    }

}

class RankHolder(itemView: View) : BaseViewHolder<RankList>(itemView) {
    override fun setContent(item: RankList, position: Int) {
        when(item.rank){
            1 -> tv_num.background = ContextCompat.getDrawable(itemView.context,R.drawable.icon_rank_first)
            2 -> tv_num.background = ContextCompat.getDrawable(itemView.context,R.drawable.icon_rank_second)
            3 -> tv_num.background = ContextCompat.getDrawable(itemView.context,R.drawable.icon_rank_third)
            else ->{
                tv_num.background = null
                tv_num.text = item.rank.toString()
            }
        }
        tv_name.text = item.username
        tv_integral.text = item.coinCount.toString()
    }
}