package com.chs.module_eye.model

/**
 * author：chs
 * date：2020/6/30
 * des： 详情页面的所有数据
 */
class DetailPageData(val detail:Detail,var detailRecommend:List<DetailRecomm>){
    init {
        if(detailRecommend.isNotEmpty()){
           detailRecommend = detailRecommend.subList(1,detailRecommend.size)
        }
    }
}