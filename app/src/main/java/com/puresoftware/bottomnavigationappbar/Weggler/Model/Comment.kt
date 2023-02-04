package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

class Comment(
    val commentId: Int,
    var postId : Int,
    var userId : String,
    var body : String,
    var createTime : String,
    var updateTime: String
):Serializable