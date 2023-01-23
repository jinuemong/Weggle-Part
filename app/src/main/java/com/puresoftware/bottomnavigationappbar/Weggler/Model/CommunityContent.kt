package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

class CommunityContent (
    val postId : Int,
    val boardName : String,
    val userId : String,
    val body : MultiCommunityData,
    val createTime : String,
    val updateTime : String,
):Serializable