package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

//데이터를 전달 받을 때

class CommunityContent (
    val postId : Int,
    val boardName : String,
    val userId : String,
    var body : MultiCommunityData,
    val resourcePath : String?,
    val createTime : String,
    val updateTime : String,
):Serializable