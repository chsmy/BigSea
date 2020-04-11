package com.chs.lib_core.room

import androidx.room.*

/**
 * author：chs
 * date：2020/4/11
 * des：
 */
@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cache:Cache)

    @Query("select * from cache where `key`=:key")
    fun getCache(key:String)

    @Delete
    fun delete(cache:Cache)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache:Cache)
}