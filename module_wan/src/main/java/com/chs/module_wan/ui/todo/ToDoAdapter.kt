package com.chs.module_wan.ui.todo

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_common_ui.utils.DrawableUtil
import com.chs.module_wan.R
import com.chs.module_wan.model.ToDoEntity
import kotlinx.android.synthetic.main.wan_item_todo.*

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class ToDoAdapter :
    AbsPageListAdapter<ToDoEntity, ToDoHolder>(object : DiffUtil.ItemCallback<ToDoEntity>() {
        override fun areItemsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun getItemViewType2(position: Int): Int  = R.layout.wan_item_todo

    override fun createCurrentViewHolder(view: View, viewType: Int): ToDoHolder {
        return ToDoHolder(view)
    }

}

class ToDoHolder(itemView: View) : BaseViewHolder<ToDoEntity>(itemView) {
    override fun setContent(item: ToDoEntity, position: Int) {
        itemView.background = DrawableUtil.getRoundDrawable(itemView.context,R.color.white,6)
        tv_title.text = item.title
        tv_time.text = item.dateStr
        tv_content.text = item.content
    }
}