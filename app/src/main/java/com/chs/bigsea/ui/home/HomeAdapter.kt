package com.chs.bigsea.ui.home

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chs.bigsea.R
import com.chs.lib_common_ui.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.module_wan.model.DataX
import kotlinx.android.synthetic.main.item_home_list.*

/**
 * @author：chs
 * date：2020/2/4
 * des：
 */
class WanAdapter : AbsPageListAdapter<DataX,WanViewHolder>{

    constructor():super(object : DiffUtil.ItemCallback<DataX>(){
        override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem.id == newItem.id
         }
        override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem == newItem
        }
    })

    override fun createCurrentViewHolder(view: View, viewType: Int): WanViewHolder {
        return WanViewHolder(view)
    }

    override fun getLayoutId(): Int = R.layout.item_home_list

}

class WanViewHolder(itemView: View) : BaseViewHolder<DataX>(itemView){
    override fun setContent(item: DataX) {
        tv_share_user.text = item.shareUser
        tv_publish_time.text = item.niceShareDate
        tv_title.text = item.title
        tv_classify.text = String.format("%1s / %2s",item.superChapterName,item.chapterName)
    }
}
