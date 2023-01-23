package com.puresoftware.bottomnavigationappbar.Weggler.Server

import com.puresoftware.bottomnavigationappbar.Weggler.Model.Community
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
    ): Call<Community>

    //post community post data
    @POST("boards/community/posts/")
    @FormUrlEncoded
    fun addCommunityPost(
        @Field("body")body: MultiCommunityData
    ): Call<MultiCommunityData>
}