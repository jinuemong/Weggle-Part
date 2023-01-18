package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

class Comment(
    var profile: Profile,
    var body : String,
    var createTime : String,
    var likeCount : Int=0,
    var replyCommentList: ArrayList<Comment>?
):Serializable