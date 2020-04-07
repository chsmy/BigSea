package com.chs.module_wan.model

/**
 * author：chs
 * date：2020/4/5
 * des：
 */
data class ProjectEntity(
    val children: List<ProjectEntity>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)
