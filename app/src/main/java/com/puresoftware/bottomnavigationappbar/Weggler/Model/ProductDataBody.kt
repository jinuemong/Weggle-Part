package com.puresoftware.bottomnavigationappbar.Weggler.Model
import java.io.Serializable
class ProductDataBody(
    val groupNum : Int, // 공동구매 인원
    val company : String,
    val charge : String,
    val discount : Int,
    val orginal : Int,
    val price : Int,
    val duration : String,
    val benefit : String,
):Serializable

