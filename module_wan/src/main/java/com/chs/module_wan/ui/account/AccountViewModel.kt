package com.chs.module_wan.ui.account

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.AccountNameEntity
import com.chs.module_wan.model.Article
import com.kingja.loadsir.core.LoadService

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class AccountViewModel : BaseListViewModel<Article>(){
    var accountId:Int = 0
    val mAccountName: MutableLiveData<List<AccountNameEntity>> = MutableLiveData<List<AccountNameEntity>>()

    fun getAccountNameData(){
        launch {
            val accountNames = WanRetrofitClient.service.getAccountNameData()
            mAccountName.value = accountNames.data
        }
    }

    override fun createDataSource(): DataSource<Int, Article> {
        return AccountDataSource(this,accountId)
    }
}

class AccountDataSource(private val viewModel:BaseListViewModel<Article>,
                        private val accountId:Int) : PageKeyedDataSource<Int, Article>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        getProjectData(1,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        getProjectData(params.key,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    }

    private fun getProjectData(page:Int, iniCallback: LoadInitialCallback<Int, Article>?,
                               callback: LoadCallback<Int, Article>?){

        val accountData =
            WanRetrofitClient.service.getAccountListData(accountId,page).execute().body()
        if(iniCallback!=null){
            accountData?.data?.datas?.let { iniCallback.onResult(it,-1,2) }
        }else{
            accountData?.data?.datas?.let { callback?.onResult(it,page+1) }
        }

        viewModel.mLoadService?.showSuccess()
        viewModel.isShowLoading = false
    }
}