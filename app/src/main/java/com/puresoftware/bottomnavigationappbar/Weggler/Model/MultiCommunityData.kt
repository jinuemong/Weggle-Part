package com.puresoftware.bottomnavigationappbar.Weggler.Model

// 리싸이클러에 2가지 종류 데이터 담기 위해서  투 타입 선언
// 리뷰 내부에 넣어야함 리뷰 = 포스팅
class MultiCommunityData (
    val type : Int=0, //type 1: joint , type 2 : free
    val subject:String,
    val text : String,
    var linkUrl: String ="", //free
    val jointProduct : String="", //joint
)