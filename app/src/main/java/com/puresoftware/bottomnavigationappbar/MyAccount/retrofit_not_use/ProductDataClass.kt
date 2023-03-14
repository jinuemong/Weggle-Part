package com.puresoftware.bottomnavigationappbar.MyAccount.retrofit_not_use

import java.io.Serializable

data class ProductDataClass(
    val postId: Int,
    val boadName:String,
    val userId:String,
    val tunmbnail:String,
    val resource:String,
    val body: ProductBodyDataClass,
    val likeCount:Int,
    val conmmentCount:Int,
    val createTime : String,
    val updateTime : String,
): Serializable
