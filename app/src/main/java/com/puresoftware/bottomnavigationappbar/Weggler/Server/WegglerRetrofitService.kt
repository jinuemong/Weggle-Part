package com.puresoftware.bottomnavigationappbar.Weggler.Server

import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityList
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

//레트로핏 연결 url 관리 (weggler)

interface WegglerRetrofitService {

    //get community post List
    @GET("boards/community/posts/")
    fun getCommunityPostList(
        @Query(value = "page",encoded=true)page:Int?,
        @Query(value = "size",encoded=true)size:Int?,
        @Query(value = "sort",encoded = true)sort:List<String>?,
    ): Call<CommunityList>

    //post community post data (type 1: joint , type 2 : free)
    @Multipart
    @POST("boards/community/posts/")
    fun addCommunityPost(
        @PartMap param: HashMap<String,RequestBody>, //Community
        @Part multipartFile: MultipartBody.Part? //image
    ): Call<CommunityContent>

    // CommunityContent 데이터 수정
    @PUT("boards/community/posts/{postId}")
    fun updateCommunityPost(
        @Path("postId")postId:Int,
        @PartMap param: HashMap<String,RequestBody>, //Community
        @Part multipartFile: MultipartBody.Part? //image
    ):Call<CommunityContent>

}