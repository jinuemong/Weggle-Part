package com.puresoftware.bottomnavigationappbar.Weggler.Server

import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityList
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import retrofit2.Call
import retrofit2.http.*

//레트로핏 연결 url 관리 (weggler)

interface WegglerRetrofitService {

    //get community post List
    @GET("boards/community/posts/")
    fun getCommunityPostList(
        @Query(value = "page",encoded=true)page:Int,
        @Query(value = "size",encoded=true)size:Int,
    ): Call<CommunityList>

    //post community post data (type 1: joint , type 2 : free)
    @POST("boards/community/posts/")
    @FormUrlEncoded
    fun addCommunityPost(
        @Field("body")body: MultiCommunityData,
    ): Call<CommunityContent>


}