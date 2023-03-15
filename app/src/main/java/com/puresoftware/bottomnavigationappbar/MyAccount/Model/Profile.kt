package com.puresoftware.bottomnavigationappbar.MyAccount.Model

import java.io.Serializable

// 유저의 프로필 (body)

class Profile (
    var name : String?,
    var userComment : String?,
    var userImage : String?,
    var backgroundPic : String?,
    var userKeyword : ArrayList<Int>?,
    var instagramUrl : String?, //instagram
    var blogUrl : String?, //blog
    var youtubeUrl : String? //youtube
) : Serializable