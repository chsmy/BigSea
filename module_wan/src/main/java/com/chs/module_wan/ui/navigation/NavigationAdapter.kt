package com.chs.module_wan.ui.navigation
import android.view.View
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_common_ui.base.OnItemClickListener
import com.chs.lib_common_ui.webview.BrowserActivity
import com.chs.module_wan.R
import com.chs.module_wan.model.Article
import com.chs.module_wan.model.NavigationEntity
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.wan_item_flex_box.*
import kotlinx.android.synthetic.main.wan_item_navigaton.*

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class NavAdapter(data:List<NavigationEntity>) : BaseAdapter<NavigationEntity>(data) {

    override fun getLayoutId(): Int = R.layout.wan_item_navigaton

    override fun createCurrentViewHolder(view: View, viewType: Int): BaseViewHolder<NavigationEntity> {
        return NavHolder(view)
    }
}

class NavHolder(itemView: View) : BaseViewHolder<NavigationEntity>(itemView) {
    override fun setContent(item: NavigationEntity) {
        tv_item_title.text = item.name
        if(item.articles.isNotEmpty()){
            val layoutManager = FlexboxLayoutManager(itemView.context)
            layoutManager.flexWrap = FlexWrap.WRAP
            layoutManager.alignItems = AlignItems.STRETCH
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.FLEX_START
            rv_tag.layoutManager = layoutManager
            val adapter = NavFlexBoxAdapter(item.articles)
            rv_tag.adapter = adapter
            adapter.onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    BrowserActivity.start(itemView.context, item.articles[position].link)
                }
            }
        }
    }
}

class NavFlexBoxHolder(itemView: View)  : BaseViewHolder<Article>(itemView){
    override fun setContent(item: Article) {
        tv_name.text = item.title
    }
}

class NavFlexBoxAdapter(data:List<Article>):BaseAdapter<Article>(data){
    override fun getLayoutId(): Int = R.layout.wan_item_flex_box

    override fun createCurrentViewHolder(view: View, viewType: Int): BaseViewHolder<Article> {
        return NavFlexBoxHolder(view)
    }
}