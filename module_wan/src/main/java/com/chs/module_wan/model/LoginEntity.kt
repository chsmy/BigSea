package com.chs.module_wan.model

/**
 * author：chs
 * date：2020/4/11
 * des：
 */ 

data class LoginEntity(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)