package com.chs.module_wan.model

/**
 * author：chs
 * date：2020/4/6
 * des：
 */

data class NavigationEntity(
    val articles: List<Article>,
    val cid: Int,
    val name: String
)