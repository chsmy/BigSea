package com.chs.module_eye.ui.recommend

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chs.lib_common_ui.base.AbsPageListAdapter
import com.chs.lib_common_ui.base.BaseViewHolder
import com.chs.lib_common_ui.base.OnItemChildClickListener
import com.chs.lib_core.imageloader.ImageLoader
import com.chs.module_eye.R
import com.chs.module_eye.model.ItemX
import com.chs.module_eye.model.RecommendItem
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.eye_item_recommend_collectiion_item.*
import kotlinx.android.synthetic.main.eye_item_recommend_collection.*
import kotlinx.android.synthetic.main.eye_item_recommend_custom.*

/**
 * author：chs
 * date：2020/6/25
 * des： 推荐页面的adapter
 */
class RecommendAdapter : AbsPageListAdapter<RecommendItem, BaseViewHolder<RecommendItem>> {

    companion object {
        const val HORIZONTAL_SCROLL_CARD = "horizontalScrollCard"
        const val COMMUNITY_COLUMNS_CARD = "communityColumnsCard"
        const val ITEM_COLLECTION = "ItemCollection"
        const val BANNER = "HorizontalScrollCard"
    }

    constructor() : super(object : DiffUtil.ItemCallback<RecommendItem>() {
        override fun areItemsTheSame(oldItem: RecommendItem, newItem: RecommendItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecommendItem, newItem: RecommendItem): Boolean {
            return oldItem == newItem
        }
    })

    override fun getItemViewType2(position: Int): Int {
        val item = getItem(position)
        return when (item?.type) {
            HORIZONTAL_SCROLL_CARD -> {
                when (item.data.dataType) {
                    ITEM_COLLECTION -> R.layout.eye_item_recommend_collection
                    BANNER -> R.layout.eye_item_recommend_banner
                    else -> TYPE_UNKNOWN
                }
            }
            COMMUNITY_COLUMNS_CARD -> R.layout.eye_item_recommend_custom
            else -> TYPE_UNKNOWN
        }
    }

    override fun createCurrentViewHolder(view: View, viewType: Int): BaseViewHolder<RecommendItem> {
        return when(viewType){
            R.layout.eye_item_recommend_collection -> RecommendCollViewHolder(view,onItemChildClickListener)
            R.layout.eye_item_recommend_banner -> RecommendBannerViewHolder(view,onItemChildClickListener)
            R.layout.eye_item_recommend_custom -> RecommendCustomViewHolder(view,onItemChildClickListener)
            else -> RecommendCustomViewHolder(view,onItemChildClickListener)
        }
    }

}

class RecommendCollViewHolder(
    itemView: View,
    private val onItemChildClickListener: OnItemChildClickListener?
) : BaseViewHolder<RecommendItem>(itemView) {
    override fun setContent(item: RecommendItem, position: Int) {
        (itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
       recyclerView.layoutManager = LinearLayoutManager(itemView.context)
           .apply { orientation = LinearLayoutManager.HORIZONTAL }
        recyclerView.addItemDecoration(object :RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val pos = parent.getChildAdapterPosition(view)
                val count = parent.adapter?.itemCount?.minus(1)
                if(pos!=0){
                    outRect.left = SizeUtils.dp2px(5f)
                }
                if(pos == count){
                    outRect.right = SizeUtils.dp2px(10f)
                }
            }
        })
        recyclerView.adapter = HeadAdapter(item.data.itemList)
    }

    inner class HeadAdapter(private val list:List<ItemX>) : RecyclerView.Adapter<HeadViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadViewHolder {
            return HeadViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.eye_item_recommend_collectiion_item,parent,false))
        }

        override fun getItemCount(): Int  = list.size

        override fun onBindViewHolder(holder: HeadViewHolder, position: Int) {
             holder.setContent(list[position],position)
        }

    }
    inner class HeadViewHolder(itemView: View) : BaseViewHolder<ItemX>(itemView){
        override fun setContent(item: ItemX, position: Int) {
            itemView.layoutParams.width = (ScreenUtils.getScreenWidth()-SizeUtils.dp2px(25f))/2
           ImageLoader.url(item.data.bgPicture).into(iv_pic)
            tv_pic_des.text = item.data.title
        }

    }
}
class RecommendBannerViewHolder(
    itemView: View,
    private val onItemChildClickListener: OnItemChildClickListener?
) : BaseViewHolder<RecommendItem>(itemView) {
    override fun setContent(item: RecommendItem, position: Int) {
        (itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
        val b = itemView.findViewById<BannerViewPager<ItemX,BannerAdapter.ViewHolder>>(R.id.banner)
        b.apply {
            adapter = BannerAdapter()
            setAutoPlay(true)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
        }.create(item.data.itemList)
    }

    inner class BannerAdapter : BaseBannerAdapter<ItemX, BannerAdapter.ViewHolder>() {

        inner class ViewHolder(val view: View) : com.zhpan.bannerview.BaseViewHolder<ItemX>(view) {

            override fun bindData(item: ItemX, position: Int, pageSize: Int) {
                val ivPicture = view.findViewById<ImageView>(R.id.banner_image)
//                if (item.data.label?.text.isNullOrEmpty()) tvLabel.invisible() else tvLabel.visible()
//                tvLabel.text = item.data.label?.text ?: ""
                ImageLoader.url(item.data.image).roundRadius(10).roundInto(ivPicture)
            }
        }

        override fun getLayoutId(viewType: Int): Int {
            return R.layout.item_net
        }

        override fun createViewHolder(view: View, viewType: Int): ViewHolder {
            return ViewHolder(view)
        }

        override fun onBind(holder: ViewHolder, data: ItemX, position: Int, pageSize: Int) {
            holder.bindData(data, position, pageSize)
        }
    }

}
class RecommendCustomViewHolder(
    itemView: View,
    private val onItemChildClickListener: OnItemChildClickListener?
) : BaseViewHolder<RecommendItem>(itemView) {
    override fun setContent(item: RecommendItem, position: Int) {
      val picHeight = calculateImageHeight(item.data.content.data.width,item.data.content.data.height)
        tvPic.layoutParams.height = picHeight
      ImageLoader.url(item.data.content.data.cover.feed).roundRadius(10)
          .roundInto(tvPic)
    }

    /**
     * 根据屏幕比例计算图片高
     *
     * @param originalWidth   服务器图片原始尺寸：宽
     * @param originalHeight  服务器图片原始尺寸：高
     * @return 根据比例缩放后的图片高
     */
    private fun calculateImageHeight(originalWidth: Int, originalHeight: Int): Int {
        //服务器数据异常处理
        if (originalWidth == 0 || originalHeight == 0) {
            return ScreenUtils.getScreenWidth()/2
        }
        return ScreenUtils.getScreenWidth()/2 * originalHeight / originalWidth
    }
}
class RecommendItemDecoration:RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutParams = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams)
        val spanIndex = layoutParams.spanIndex
        outRect.top = SizeUtils.dp2px(10f)

        when (spanIndex) {
            0 -> {
                outRect.left = SizeUtils.dp2px(10f)
                outRect.right = SizeUtils.dp2px(if(layoutParams.isFullSpan) 10f else 3f)
            }
            else -> {
                outRect.left = SizeUtils.dp2px(3f)
                outRect.right = SizeUtils.dp2px(10f)
            }
        }
    }
}
