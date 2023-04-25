package com.puresoftware.bottomnavigationappbar.Weggler.Model

import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import java.io.Serializable

// 리뷰에 커맨트 달기
class Comment(
    val commentId: Int,
    var reviewInfo: ReviewInComment,
    var userInfo: UserInfo?,
    var body: String,
    var likeCount : Int = 0,
    val parentCommentId : Int,
    var userLike : Boolean,
    var createTime: String,
    var updateTime: String,
//    var commentPost : ReviewInCommunity?,
    // 어떤 리뷰의 댓글인지 구분용
) : Serializable

