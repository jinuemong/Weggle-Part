package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

class Posting (
    var profile : Profile,
    var videoUrl : String,
    var createTime : String,
    var totalLike: Int=0,
    var totalComment : Int=0,
    var commentList : ArrayList<Comment>?,
    var body : String,
):Serializable