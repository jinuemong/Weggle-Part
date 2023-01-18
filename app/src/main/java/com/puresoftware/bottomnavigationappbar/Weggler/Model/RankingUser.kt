package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

class RankingUser(
    var rankingNum : Int,
    var userImage:String,
    var username: String,
    var totalLike : Int,
    var totalPlay : Int,
    var thumbnailList : ArrayList<String>,
    //이미지 리스트
):Serializable