package com.puresoftware.bottomnavigationappbar.MyAccount.Model

import java.io.Serializable


class ReviewData (
    val reviewId : Int,
    val productId : Int,
    val userInfo : UserInfo,
    val thumbnail:String="", //자동
    var resource : String="", //자동
    var body  :ReviewBody,
    var likeCount : Int = 0,
    var commentCount : Int = 0,
    val createTime : String,
    val updateTime : String,
): Serializable