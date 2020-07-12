package com.chs.module_wan.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chs.lib_core.room.CacheManager
import com.chs.module_wan.model.LoginEntity

/**
 * author：chs
 * date：2020/4/11
 * des：
 */
class UserManager {
    private var mUser: LoginEntity? = null
    private val userLiveData = MutableLiveData<LoginEntity>()

    init {
        val user = CacheManager.getCache(KEY_USER_CACHE)
        if(user!=null){
            mUser = user as LoginEntity
        }
    }

    companion object {
        private const val KEY_USER_CACHE = "key_user_cache"
        private val sUserManager = UserManager()
        fun get() : UserManager{
            return sUserManager
        }
    }

    fun isLogin(): Boolean {
        return mUser != null
    }

    fun isNotLogin(): Boolean {
        return mUser != null
    }

    fun getUser():LoginEntity?{
        return mUser
    }

    fun save(user: LoginEntity) {
        mUser = user
        CacheManager.save(KEY_USER_CACHE, user)
        userLiveData.value = user
    }

    fun gotoLogin(context: Context): LiveData<LoginEntity> {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return userLiveData
    }

    fun doLogin(){

    }

    fun logout(){
        CacheManager.delete(KEY_USER_CACHE,mUser)
        mUser = null
    }

}