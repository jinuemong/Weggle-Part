package com.puresoftware.bottomnavigationappbar.MyAccount.Model


import java.io.Serializable

data class User (
    val email : String,
    var name : String,
    val body : UserBody?,
    val profile : String?, //프로필 이미지
    val background : String?, // 배경 이미지
):Serializable


class UserPatch(
    val email : String?,
    val password : String?,
    val newPassword : String?,
    val body : UserBody?,
):Serializable