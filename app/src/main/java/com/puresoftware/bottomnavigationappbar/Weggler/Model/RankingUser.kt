package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

// 랭킹 유저 프로필 
class RankingUser(
    var rankingNum : Int,
    var userImage:String,
    var username: String,
    var totalLike : Int,
    var totalPlay : Int,
    var thumbnailList : ArrayList<String>,
    //이미지 리스트
):Serializable