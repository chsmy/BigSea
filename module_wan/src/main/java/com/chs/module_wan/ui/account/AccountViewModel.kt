package com.chs.module_wan.ui.account

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.chs.lib_common_ui.base.BaseListViewModel
import com.chs.module_wan.api.WanRetrofitClient
import com.chs.module_wan.model.*

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
class AccountViewModel : BaseListViewModel<AccountListData>(){
    var accountId:Int = 0
    val mAccountName: MutableLiveData<List<AccountNameData>> = MutableLiveData<List<AccountNameData>>()

    fun getAccountNameData(){
        launch {
            val accountNames = WanRetrofitClient.service.getAccountNameData()
            mAccountName.value = accountNames.data
        }
    }

    override fun createDataSource(): DataSource<Int, AccountListData> {
        return AccountDataSource(this,accountId)
    }
}

class AccountDataSource(private val viewModel:BaseListViewModel<AccountListData>,
                        private val accountId:Int) : PageKeyedDataSource<Int, AccountListData>(){

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, AccountListData>) {
        getProjectData(0,callback,null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AccountListData>) {
        getProjectData(params.key+1,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AccountListData>) {
    }

    private fun getProjectData(page:Int, iniCallback: LoadInitialCallback<Int, AccountListData>?,
                               callback: LoadCallback<Int, AccountListData>?){
        viewModel.launch {
            val accountData =
                WanRetrofitClient.service.getAccountListData(accountId,page)
            if(iniCallback!=null){
                iniCallback.onResult(accountData.data.datas,-1,0)
            }else{
                callback?.onResult(accountData.data.datas,page)
            }
        }
    }
}