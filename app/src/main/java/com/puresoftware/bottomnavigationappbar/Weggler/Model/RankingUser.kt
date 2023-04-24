package com.puresoftware.bottomnavigationappbar.Weggler.Model

import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import java.io.Serializable

// 랭킹 유저 프로필 
class RankingUser(
    var userInfo : UserInfo,
    var totalLike : Int,
):Serializable