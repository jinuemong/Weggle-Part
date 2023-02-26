package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

//community 데이터

class ReviewInCommunity (
    val reviewId : Int,
    val productId : Int,
    val userId : String,
    val thumbnail:String="", //자동
    var resource : String="", //자동
    var body : MultiCommunityData,
    var likeCount : Int = 0,
    val commentCount : Int = 0,
    val createTime : String,
    val updateTime : String,
):Serializable