package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

// 유저의 프로필

class Profile (
    var username : String,
    var userImage : String,
    var userTag : ArrayList<String>?
) : Serializable