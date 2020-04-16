package com.chs.module_wan.ui.rank

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.lib_core.http.WanBaseResponse
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.Rank
import com.chs.module_wan.model.RankList

/**
 *  @author chs
 *  date: 2020-04-14 15:08
 *  des:
 */
class RankViewModel: BaseListViewModel<RankList>() {

    override fun createDataSource(): DataSource<Int, RankList> {
        return RankDataSource(this)
    }

}

class RankDataSource(private val viewModel:BaseListViewModel<RankList>) : PageKeyedDataSource<Int, RankList>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, RankList>) {
        getProjectData(1,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RankList>) {
        getProjectData(params.key,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RankList>) {
    }

    private fun getProjectData(page:Int, iniCallback: LoadInitialCallback<Int, RankList>?,
                               callback: LoadCallback<Int, RankList>?){

        val rankData: WanBaseResponse<Rank>? = viewModel.execute {
            WanRetrofitClient.service.getRank(page)
        }
        if(iniCallback!=null){
            rankData?.data?.datas?.let { iniCallback.onResult(it,-1,2) }
        }else{
            rankData?.data?.datas?.let { callback?.onResult(it,page+1) }
        }
        viewModel.mLoadService?.showSuccess()
        viewModel.isShowLoading = false
    }
}