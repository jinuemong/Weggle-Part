package com.puresoftware.bottomnavigationappbar.Weggler.Server

import com.google.gson.annotations.SerializedName
import com.puresoftware.bottomnavigationappbar.Weggler.Model.*
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

    //get community post List By Like
    @GET("boards/community/postsByLike/")
    fun getCommunityPostByLike() :Call<ArrayList<CommunityContent>>

    //post community post data (type 1: joint , type 2 : free)
    @Multipart
    @POST("boards/community/posts/")
    fun addCommunityPost(
        @Part("param") param: BodyPost, //Community
        @Part multipartFile: MultipartBody.Part? //image
    ): Call<CommunityContent>

    // CommunityContent 데이터 수정
    @PUT("boards/community/posts/{postId}/")
    fun updateCommunityPost(
        @Path("postId")postId:Int,
        @Part("param") param: HashMap<String,RequestBody>, //Community
        @Part multipartFile: MultipartBody.Part? //image
    ):Call<CommunityContent>


    // Comment 데이터 가져오기
    @GET("posts/{postId}/comments/")
    fun getCommentList(
        @Path("postId")postId:Int,
    ): Call<ArrayList<Comment>>

    //Comment 추가
    @POST("posts/{postId}/comments/")
    fun addComment(
        @Path(value = "postId")postId:Int,
        @Body body : BodyComment
    ): Call<Comment>

    //Comment 제거
    @DELETE("posts/{postId}/comments/{commentId}/")
    fun delComment(
        @Path("postId")postId:Int,
        @Path("commentId")commentId:Int
    ): Call<Comment>


}