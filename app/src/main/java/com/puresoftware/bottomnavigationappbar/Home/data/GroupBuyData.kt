package com.puresoftware.bottomnavigationappbar.Home.data


import java.io.Serializable

data class GroupBuyData(val productId : Int,
                        val category : String,
                        val name  : String,
                        val userId : String,
                        val body : OriginalBodyList, //원래는 string
    // admin에서 object로 등록되있어서 맞춤
                        val subjectFiles : List<String>,
                        val contentFiles : List<String>,
                        val createTime : String,
                        val updateTime : String,
): Serializable
