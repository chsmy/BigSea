package com.chs.module_wan.model

import com.chs.module_wan.ui.todo.TodoDataSource

/**
 * author：chs
 * date：2020/7/12
 * des：
 */

data class ToDoList(
    val curPage: Int,
    val datas: List<ToDoEntity>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
