package com.chs.module_wan.ui.account

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.module_wan.R
import com.chs.module_wan.model.Article
import kotlinx.android.synthetic.main.wan_item_account.*

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class AccountAdapter : AbsPageListAdapter<Article,AccountHolder>(object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}) {

    override fun getLayoutId(): Int = R.layout.wan_item_account

    override fun createCurrentViewHolder(view: View, viewType: Int): AccountHolder {
       return AccountHolder(view)
    }

}
class AccountHolder(itemView: View) : BaseViewHolder<Article>(itemView) {
    override fun setContent(item: Article,position:Int) {
        tv_name.text = item.author
        tv_title.text = item.title
        tv_publish_time.text = item.niceShareDate
        tv_account_name.text = String.format("%s-%s",item.superChapterName,item.chapterName)
    }
}