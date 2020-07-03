package com.chs.lib_core.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chs.lib_core.utils.AppUtil

/**
 * author：chs
 * date：2020/4/11
 * des：
 */
@Database(entities = [Cache::class], version = 1, exportSchema = true)
abstract class CacheDatabase : RoomDatabase() {

    companion object{
        private var db:CacheDatabase? = null
        init {
             db = Room.databaseBuilder(AppUtil.getApp(), CacheDatabase::class.java, "bigsea_cache" )
                //是否允许在主线程进行查询
                .allowMainThreadQueries()
                .build()
        }

        fun get(): CacheDatabase? {
            return db
        }
    }

    abstract fun getCache():CacheDao

}