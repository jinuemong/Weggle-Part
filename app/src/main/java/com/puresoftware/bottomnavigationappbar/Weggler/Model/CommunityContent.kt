package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

//데이터를 전달 받을 때

class CommunityContent (
    val postId : Int,
    val boardName : String,
    val userId : String,
    val thumbnail:String="", //자동
    var resource : String="", //자동
    var body : MultiCommunityData,
    var likeCount : Int = 0,
    val createTime : String,
    val updateTime : String,
):Serializable