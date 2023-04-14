package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

//community 데이터

class ReviewInCommunity (
    val reviewId : Int,
    val productId : Int,
    val userId : String,
    val thumbnail:String="", //자동
    var resource : String="", //자동
    var body : MultiCommunityDataBody,
    var likeCount : Int = 0,
    var userLike : Boolean,
    var commentCount : Int = 0,
    val createTime : String,
    val updateTime : String,
):Serializable


class ReviewInComment(
    val id :Int,
    val body : MultiCommunityDataBody,
)