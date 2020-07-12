package com.chs.module_wan.model

/**
 * author：chs
 * date：2020/7/12
 * des：
 */
data class ToDoEntity(
    val completeDate: Any,
    val completeDateStr: String,
    val content: String,
    val date: Long,
    val dateStr: String,
    val id: Int,
    val priority: Int,
    val status: Int,
    val title: String,
    val type: Int,
    val userId: Int
)