package com.puresoftware.bottomnavigationappbar.Weggler.Model

class Product (
    val productId : Int,
    val category : String,
    val name  : String,
    val userId : String,
    val body : String,
    val subjectFiles : List<String>,
    val contentFiles : List<String>,
    val createTime : String,
    val updateTime : String,
):java.io.Serializable