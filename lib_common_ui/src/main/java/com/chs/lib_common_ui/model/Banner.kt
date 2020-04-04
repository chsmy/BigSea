package com.chs.lib_common_ui.model
import com.google.gson.annotations.SerializedName


/**
 *  @author chs
 *  date: 2020-01-04 17:25
 *  des:
 */
data class Banner(
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("imagePath")
    val imagePath: String = "",
    @SerializedName("isVisible")
    val isVisible: Int = 0,
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("url")
    val url: String = ""
)