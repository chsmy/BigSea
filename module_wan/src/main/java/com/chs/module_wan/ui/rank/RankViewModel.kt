package com.chs.module_wan.ui.rank

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.module_wan.api.WanRetrofitClient
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
        getProjectData(params.key+1,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RankList>) {
    }

    private fun getProjectData(page:Int, iniCallback: LoadInitialCallback<Int, RankList>?,
                               callback: LoadCallback<Int, RankList>?){
        viewModel.isShowLoading = page == 1
        viewModel.launch {
            val rankData =
                WanRetrofitClient.service.getRank(page)
            if(iniCallback!=null){
                iniCallback.onResult(rankData.data.datas,-1,0)
            }else{
                callback?.onResult(rankData.data.datas,page)
            }
        }
    }
}