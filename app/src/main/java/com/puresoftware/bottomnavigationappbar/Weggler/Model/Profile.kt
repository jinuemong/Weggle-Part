package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

// 유저의 프로필

class Profile (
    var name : String,
    var userComment : String,
    var userImage : String,
    var backgroundPic : String,
    var userKeyword : ArrayList<Int>?
) : Serializable