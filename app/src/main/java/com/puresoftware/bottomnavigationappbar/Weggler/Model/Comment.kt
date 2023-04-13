package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

// 리뷰에 커맨트 달기
class Comment(
    val commentId: Int,
    var reviewInfo: ReviewInComment,
    var userId: String,
    var body: String,
    var likeCount : Int = 0,
    var createTime: String,
    var updateTime: String,
//    var commentPost : ReviewInCommunity?,
    // 어떤 리뷰의 댓글인지 구분용
) : Serializable