package com.puresoftware.bottomnavigationappbar.MyAccount.Model

import java.io.Serializable

class ExploreProfile(
    val user : User?,
    val followers : ArrayList<FollowData>,
    val followings : ArrayList<FollowData>,
): Serializable