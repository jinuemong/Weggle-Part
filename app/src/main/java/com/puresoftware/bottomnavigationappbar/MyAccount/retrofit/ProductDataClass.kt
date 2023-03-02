package com.puresoftware.bottomnavigationappbar.MyAccount.retrofit

import com.google.gson.annotations.SerializedName
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
