package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

class Product (
    val productId : Int,
    val category : String,
    val name  : String,
    val userId : String,
    val body : BodyProduct, //원래는 string
    // admin에서 object로 등록되있어서 맞춤
    val subjectFiles : List<String>,
    val contentFiles : List<String>,
    val createTime : String,
    val updateTime : String,
): Serializable