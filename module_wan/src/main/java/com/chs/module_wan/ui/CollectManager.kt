package com.chs.module_wan.ui

import android.content.Context
import androidx.lifecycle.*
import com.chs.module_wan.model.Article
import com.chs.module_wan.ui.login.UserManager

/**
 *  @author chs
 *  date: 2020-04-13 15:37
 *  des:
 */
class CollectManager {

    companion object{

        fun goToLogin(article: Article, owner: LifecycleOwner, context:Context,viewmodel:CollectViewModel) {
            UserManager.get().gotoLogin(context).observe(owner, Observer {
                //登录成功去执行原来要执行的操作
                toggleCollect(article,viewmodel)
            })
        }

        fun toggleCollect(article: Article,viewmodel:CollectViewModel) {
            if(!article.collect){
                viewmodel.collectArticle(article.id)
            }else{
                viewmodel.unCollectArticle(article.id)
            }
        }

    }

}