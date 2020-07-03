package com.chs.module_eye.ui.detail

import android.view.View
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_core.extension.loadRound
import com.chs.module_eye.R
import com.chs.module_eye.model.DetailRecomm
import kotlinx.android.synthetic.main.eye_item_video_detail_comm.*

/**
 * author：chs
 * date：2020/7/1
 * des：
 */
class DetailCommAdapter(data:List<DetailRecomm>):BaseAdapter<DetailRecomm>(data) {

    override fun getLayoutId(): Int = R.layout.eye_item_video_detail_comm

    override fun createCurrentViewHolder(
        view: View,
        viewType: Int
    ): BaseViewHolder<DetailRecomm> {
       return DetailCommViewHolder(view)
    }
}
class DetailCommViewHolder(itemView: View):BaseViewHolder<DetailRecomm>(itemView){
    override fun setContent(item: DetailRecomm, position: Int) {
        if(item.data.cover!=null){
            iv_head.loadRound(item.data.cover.feed,6)
        }
        tv_name.text = item.data.title
        tv_des.text = item.data.description
    }

}