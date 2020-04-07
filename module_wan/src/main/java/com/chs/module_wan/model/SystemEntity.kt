package com.chs.module_wan.model

/**
 * author：chs
 * date：2020/4/5
 * des： 体系
 */
data class SystemEntity(
    val children: List<SystemEntity>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

