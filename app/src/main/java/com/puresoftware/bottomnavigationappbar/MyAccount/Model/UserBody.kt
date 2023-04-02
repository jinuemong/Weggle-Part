package com.puresoftware.bottomnavigationappbar.MyAccount.Model

import java.io.Serializable

// 유저의 프로필 (body)

class UserBody (
    var userComment : String?,
    var userKeyword : ArrayList<String>?,
    var instagramUrl : String?, //instagram
    var blogUrl : String?, //blog
    var youtubeUrl : String? //youtube
) : Serializable