package com.chs.lib_common_ui.base

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @author：chs
 * date：2020/2/4
 * des：可以添加header footer 并且可以分页加载的adapter
 */
abstract class AbsPageListAdapter<T,VH: BaseViewHolder<T>>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, VH>(diffCallback) {

    var onItemClickListener: OnItemClickListener? = null
    private var BASE_ITEM_HEADER_TYPE = 10000
    private var BASE_ITEM_FOOTER_TYPE = 20000

    private val mHeaders = SparseArray<View>()
    private val mFooters = SparseArray<View>()


    fun addHeaderView(view:View){
        if(mHeaders.indexOfValue(view)<0){
            mHeaders.append(BASE_ITEM_HEADER_TYPE++,view)
            notifyDataSetChanged()
        }
    }

    fun addFooterView(view:View){
        if(mFooters.indexOfValue(view)<0){
            mFooters.append(BASE_ITEM_FOOTER_TYPE++,view)
        }
    }

    fun removeHeader(view:View){
        val index = mHeaders.indexOfValue(view)
        if(index<0){
            return
        }
        mHeaders.remove(index)
        notifyDataSetChanged()
    }
    fun removeFooter(view:View){
        val index = mFooters.indexOfValue(view)
        if(index<0){
            return
        }
        mFooters.remove(index)
        notifyDataSetChanged()
    }

    fun getHeaderCount():Int{return mHeaders.size()}

    fun getFooterCount():Int{return mFooters.size()}

    override fun getItemCount(): Int {
        var itemCount = super.getItemCount()
        return itemCount + mHeaders.size() + mFooters.size()
    }

    fun getOrginalItemCount():Int{
        return itemCount - mFooters.size() - mFooters.size()
    }

    override fun getItemViewType(position: Int): Int {
       if(isHeaderPosition(position)){
           return mHeaders.keyAt(position)
       }
        val footerPos = isFooterPosition(position)
        if(footerPos>=0){
           return mFooters.keyAt(footerPos)
        }
        return getItemViewType2(position - mHeaders.size())
    }

    protected fun getItemViewType2(i: Int): Int {
        return 0
    }

    private fun isFooterPosition(position: Int): Int {
        return position - getOrginalItemCount() - mHeaders.size()
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position<mHeaders.size()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if(mHeaders.indexOfKey(viewType)>=0){
            val view = mHeaders.get(viewType)
            return object : BaseViewHolder<T>(view){
                override fun setContent(item: T) {}
            } as VH
        }
        if(mFooters.indexOfKey(viewType)>=0){
            val view = mFooters.get(viewType)
            return object : BaseViewHolder<T>(view){
                override fun setContent(item: T) {}
            } as VH
        }
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(),parent,false)
        val createCurrentViewHolder = createCurrentViewHolder(view, viewType)
        view.setOnClickListener {
            onItemClickListener?.onItemClick(view,createCurrentViewHolder.adapterPosition)
        }
        return createCurrentViewHolder
    }

    abstract fun createCurrentViewHolder(view: View, viewType: Int): VH

    abstract fun getLayoutId(): Int

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)>=0) return
//        //列表中正常的item的位置 需要减去头部的数量
        var contentPosition = position - mHeaders.size()
        getItem(contentPosition)?.let {
            holder.setContent(it)
        }
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(
            AdapterDataObserverProxy(
                observer,
                this
            )
        )
    }

    override fun onViewAttachedToWindow(holder: VH) {
        if (!isHeaderPosition(holder.adapterPosition) && isFooterPosition(holder.adapterPosition) < 0) {
            onViewAttachedToWindow2(holder)
        }
    }
    open fun onViewAttachedToWindow2(holder: VH) {}

    override fun onViewDetachedFromWindow(holder: VH) {
        if (!isHeaderPosition(holder.adapterPosition) && isFooterPosition(holder.adapterPosition)<0) {
            onViewDetachedFromWindow2(holder)
        }
    }
    open fun onViewDetachedFromWindow2(holder: VH) {}
}

/**
 * 如果我们先添加了header  然后请求网络，请求回来的数据是不包含我们的header的个数的
 * 所以只会定位到数据的第一位 不会定位到header的第一位
 * 解决办法 重写registerAdapterDataObserver 传入一个包装好的RecyclerView.AdapterDataObserver，在其内部加上header的数量
 */
class AdapterDataObserverProxy<T,VH: BaseViewHolder<T>>(private val observer: RecyclerView.AdapterDataObserver,
                               private val adapter: AbsPageListAdapter<T, VH>
)
    : RecyclerView.AdapterDataObserver(){
    override fun onChanged() {
        observer.onChanged()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        observer.onItemRangeRemoved(positionStart + adapter.getHeaderCount(),itemCount)
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
        observer.onItemRangeMoved(fromPosition +  adapter.getHeaderCount(), toPosition +  adapter.getHeaderCount(), itemCount)
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        observer.onItemRangeInserted(positionStart +  adapter.getHeaderCount(), itemCount)
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        observer.onItemRangeChanged(positionStart +  adapter.getHeaderCount(), itemCount)
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
        observer.onItemRangeChanged(positionStart +  adapter.getHeaderCount(), itemCount, payload)
    }
}