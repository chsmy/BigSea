package com.chs.module_eye.ui.detail

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_common_ui.base.OnItemChildClickListener
import com.chs.lib_common_ui.exoplayer.PagePlayerDetector
import com.chs.lib_core.imageloader.ImageLoader
import com.chs.module_eye.R
import com.chs.module_eye.model.DetailCommItem
import com.chs.module_eye.model.DetailRecomm
import com.chs.module_eye.model.Item
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
            ImageLoader.url(item.data.cover.feed).roundRadius(6).roundInto(iv_head)
        }
        tv_name.text = item.data.title
        tv_name.text = item.data.description
    }

}