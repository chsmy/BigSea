package com.chs.bigsea.an

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chs.bigsea.R
import com.chs.lib_common_ui.AbsPageListAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home_list.*

/**
 * @author：chs
 * date：2020/2/4
 * des：
 */
class WanAdapter : AbsPageListAdapter<HomeData,WanViewHolder>{

    constructor():super(object : DiffUtil.ItemCallback<HomeData>(){
        override fun areItemsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            return oldItem.id == newItem.id
         }

        override fun areContentsTheSame(oldItem: HomeData, newItem: HomeData): Boolean {
            return oldItem == newItem
        }

    })

    override fun onCreateViewHolder2(parent: ViewGroup, viewType: Int): WanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_list,parent,false)
        return WanViewHolder(view)
    }

    override fun onBindViewHolder2(holder: WanViewHolder, contentPosition: Int) {
        getItem(holder.adapterPosition)?.let { holder.setContent(it) }
    }

}

class WanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , LayoutContainer {
    override val containerView: View?
        get() = itemView

    fun setContent(data:HomeData){
        tv_item.text = data.title
    }

}