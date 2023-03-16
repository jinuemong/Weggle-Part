package com.puresoftware.bottomnavigationappbar.Weggler.Model

import java.io.Serializable

class Product (
    val productId : Int,
    val category : String,
    val name  : String,
    val userId : String,
    val body : ProductDataBody, // Body
    // admin에서 object
    val subjectFiles : List<String>,
    val contentFiles : List<String>,
    val createTime : String,
    val updateTime : String,
): Serializable