package com.chs.module_wan.ui.home

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.module_wan.R
import com.chs.module_wan.model.Article
import kotlinx.android.synthetic.main.wan_item_home_list.*

/**
 * @author：chs
 * date：2020/2/4
 * des：
 */
class WanAdapter :
    AbsPageListAdapter<Article, WanViewHolder> {

    constructor():super(object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
         }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    })

    override fun createCurrentViewHolder(view: View, viewType: Int): WanViewHolder {
        return WanViewHolder(view)
    }

    override fun getLayoutId(): Int = R.layout.wan_item_home_list

}

class WanViewHolder(itemView: View) : BaseViewHolder<Article>(itemView){
    override fun setContent(item: Article) {
        tv_share_user.text = item.shareUser
        tv_publish_time.text = item.niceShareDate
        tv_title.text = item.title
        tv_classify.text = String.format("%1s / %2s",item.superChapterName,item.chapterName)
    }
}