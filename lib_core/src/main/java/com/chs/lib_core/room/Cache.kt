package com.chs.lib_core.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * author：chs
 * date：2020/4/11
 * des：
 */
@Entity(tableName = "cache")
class Cache : Serializable{

    @PrimaryKey
    var key:String = "key"

    var data: ByteArray? = null
}