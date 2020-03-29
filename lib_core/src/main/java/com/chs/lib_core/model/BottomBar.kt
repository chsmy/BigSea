package com.chs.lib_core.model

/**
 * author：chs
 * date：2020/3/29
 * des：
 */
data class BottomBar(
    val activeColor: String,
    val inActiveColor: String,
    val selectTab: Int,
    val tabs: List<Tab>
)

data class Tab(
    val enable: Boolean,
    val index: Int,
    val pageUrl: String,
    val size: Int,
    val title: String,
    val tintColor: String
)