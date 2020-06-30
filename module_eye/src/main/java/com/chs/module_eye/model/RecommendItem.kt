package com.chs.module_eye.model

/**
 * author：chs
 * date：2020/6/25
 * des：
 */

data class RecommendItem(
    val adIndex: Int,
    val `data`: Data,
    val id: Int,
    val tag: Any,
    val type: String
)

data class Data(
    val adTrack: Any,
    val content: Content,
    val count: Int,
    val dataType: String,
    val footer: Any,
    val header: Header,
    val itemList: List<ItemX>
)

data class Content(
    val adIndex: Int,
    val `data`: DataX,
    val id: Long,
    val tag: Any,
    val type: String
)

data class ItemX(
    val adIndex: Int,
    val `data`: DataXX,
    val id: Int,
    val tag: Any,
    val type: String
)

data class DataX(
    val addWatermark: Boolean,
    val area: String,
    val checkStatus: String,
    val city: String,
    val collected: Boolean,
    val consumption: Consumption,
    val cover: Cover,
    val createTime: Long,
    val dataType: String,
    val description: String,
    val duration: Int,
    val height: Int,
    val id: Long,
    val ifMock: Boolean,
    val latitude: Double,
    val library: String,
    val longitude: Double,
    val owner: Owner,
    val playUrl: String,
    val playUrlWatermark: String,
    val privateMessageActionUrl: Any,
    val reallyCollected: Boolean,
    val recentOnceReply: RecentOnceReply,
    val releaseTime: Long,
    val resourceType: String,
    val selectedTime: Long,
    val status: Any,
    val tags: List<Tag>,
    val title: String,
    val transId: Any,
    val type: String,
    val uid: Int,
    val updateTime: Long,
    val url: String,
    val urls: List<String>,
    val urlsWithWatermark: List<String>,
    val validateResult: String,
    val validateStatus: String,
    val validateTaskId: String,
    val width: Int
)

data class Consumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class Cover(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class Owner(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Any,
    val city: Any,
    val country: Any,
    val cover: Any,
    val description: Any,
    val expert: Boolean,
    val followed: Boolean,
    val gender: Any,
    val ifPgc: Boolean,
    val job: Any,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Long,
    val uid: Int,
    val university: Any,
    val userType: String
)

data class RecentOnceReply(
    val actionUrl: String,
    val contentType: Any,
    val dataType: String,
    val message: String,
    val nickname: String
)

data class Tag(
    val actionUrl: String,
    val adTrack: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: String,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Long,
    val ifNewest: Boolean,
    val name: String,
    val newestEndTime: Long,
    val tagRecType: String
)

data class DataXX(
    val actionUrl: String,
    val adTrack: List<Any>,
    val autoPlay: Boolean,
    val bgPicture: String,
    val dataType: String,
    val description: String,
    val header: Header,
    val id: Long,
    val image: String,
    val label: Label,
    val labelList: List<Any>,
    val shade: Boolean,
    val subTitle: String,
    val title: String
)

data class Header(
    val actionUrl: String,
    val cover: Any,
    val description: Any,
    val font: Any,
    val icon: String,
    val id: Long,
    val label: String,
    val labelList: Any,
    val rightText: Any,
    val subTitle: Any,
    val subTitleFont: Any,
    val textAlign: String,
    val title: String,
    val issuerName: String
)

data class Label(
    val card: String,
    val detail: Any,
    val text: String
)