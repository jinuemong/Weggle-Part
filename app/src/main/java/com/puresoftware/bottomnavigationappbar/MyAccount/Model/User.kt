package com.puresoftware.bottomnavigationappbar.MyAccount.Model

class User (
    val email : String,
    var name : String,
    val body : UserBody,
    val profile : String, //프로필 이미지
    val background : String, // 배경 이미지
):java.io.Serializable