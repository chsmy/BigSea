package com.chs.module_wan.ui.project

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_core.imageloader.ImageLoader
import com.chs.module_wan.R
import com.chs.module_wan.model.Article
import kotlinx.android.synthetic.main.wan_item_project.*

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
class ProjectAdapter() :
    AbsPageListAdapter<Article, ProjectHolder>(object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun getLayoutId(): Int = R.layout.wan_item_project

    override fun createCurrentViewHolder(view: View, viewType: Int): ProjectHolder {
        return ProjectHolder(view)
    }
}

class ProjectHolder(itemView: View) : BaseViewHolder<Article>(itemView) {
    override fun setContent(item: Article,position:Int) {
        tv_name.text = item.author
        tv_title.text = item.title
        tv_publish_time.text = item.niceShareDate
        ImageLoader.url(item.envelopePic).roundRadius(6).roundInto(iv_img)
        tv_content.text = item.desc
        tv_kind.text = item.superChapterName
    }
}