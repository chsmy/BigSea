package com.chs.module_wan.ui.todo

import android.view.View
import androidx.core.content.ContextCompat
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_common_ui.utils.DrawableUtil
import com.chs.module_wan.R
import com.chs.module_wan.model.TypeBean
import kotlinx.android.synthetic.main.wan_item_todo_type.*

/**
 * 事物的类型 工作  生活  娱乐
 */
class TypeAdapter(data:List<TypeBean>): BaseAdapter<TypeBean>(data){
    override fun getLayoutId(): Int = R.layout.wan_item_todo_type

    override fun createCurrentViewHolder(view: View, viewType: Int): BaseViewHolder<TypeBean> {
        return TypeViewHolder(view)
    }

}
class TypeViewHolder(itemView: View) :BaseViewHolder<TypeBean>(itemView) {
    override fun setContent(item: TypeBean, position:Int) {
        tv_name?.text = item.name
        if(item.isClicked){
            tv_name.background = DrawableUtil.getRoundDrawable(itemView.context,R.color.colorPrimary,20)
            tv_name.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
        }else{
            tv_name.background = DrawableUtil.getRoundDrawable(itemView.context,R.color.color_f5f5f5,20)
            tv_name.setTextColor(ContextCompat.getColor(itemView.context,R.color.text_gray))
        }
    }
}