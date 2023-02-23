package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

// 리뷰에 커맨트 달기
class Comment(
    val commentId: Int,
    var reviewId: Int,
    var userId: String,
    var body: String,
    var likeCount : Int = 0,
    var createTime: String,
    var updateTime: String
) : Serializable