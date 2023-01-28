package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

//수정해야함 ! Posting이  CommunityContent로 변경
class Posting (
    var profile : Profile,
    var videoUrl : String,
    var createTime : String,
    var totalLike: Int=0,
    var totalComment : Int=0,
    var commentList : ArrayList<Comment>?,
    var body : String,
):Serializable