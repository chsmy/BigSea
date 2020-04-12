package com.chs.lib_core.room

import retrofit2.http.Body
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * author：chs
 * date：2020/4/12
 * des：
 */
class CacheManager {

    companion object {

        /**
         * 对象转换为byte数组
         */
        fun <T> toByteArray(body: T): ByteArray {
            val baos = ByteArrayOutputStream()
            val oos = ObjectOutputStream(baos)
            oos.use {
                it.writeObject(body)
            }
            return baos.toByteArray()
        }

        /**
         * byte数组还原为对象
         */
        fun toObject(bytes:ByteArray?) : Any{
            val bais = ByteArrayInputStream(bytes)
            val ois = ObjectInputStream(bais)
            ois.use {
                return it.readObject()
            }
        }

        /**
         * 保存一个对象到数据库
         */
        fun <T> save(key:String, body: T){
            val cache = Cache()
            cache.key = key
            cache.data = toByteArray(body)
            CacheDatabase.get()?.getCache()?.save(cache)
        }

        fun <T> delete(key:String, body: T){
            val cache = Cache()
            cache.key = key
            cache.data = toByteArray(body)
            CacheDatabase.get()?.getCache()?.delete(cache)
        }

        fun getCache(key:String) : Any?{
            val cache = CacheDatabase.get()?.getCache()?.getCache(key)
            if(cache?.data != null){
                return toObject(cache.data)
            }
            return null
        }

    }

}