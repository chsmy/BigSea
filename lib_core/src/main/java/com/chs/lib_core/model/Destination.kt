package com.chs.lib_core.model

/**
 * author：chs
 * date：2020/3/29
 * des： 一个fragment 或者 一个 activity
 */
class Destination {
    var id = 0
    var className: String? = null
    var pageUrl: String? = null
    var needLogin = false
    var asStarter = false
    var isFragment = false
}
