package com.puresoftware.bottomnavigationappbar.Weggler.Model

data class MultiCommunityData (
    val type : Int, //type 1: joint , type 2 : free
    val mainImage : String,
    val subject:String,
    val text : String,
    val totalLike : Int,
    val totalComment : Int,
)