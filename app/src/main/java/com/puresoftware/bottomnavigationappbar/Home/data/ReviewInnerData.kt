package com.puresoftware.bottomnavigationappbar.Home.data

import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityDataBody
import java.io.Serializable

data class ReviewInnerData(val reviewId:Int,
                            val productId:Int,
                            val userInfo:UserInfoData,
                            val thumbnail:String,
                            val resource:String,
                            val body:MultiCommunityDataBody,
                            val likeCount:Int,
                            val commentCount:Int,
                            val createTime:String,
                            val updateTime:String): Serializable
