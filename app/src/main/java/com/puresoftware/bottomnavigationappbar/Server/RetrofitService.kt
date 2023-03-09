package com.puresoftware.bottomnavigationappbar.Server

import com.puresoftware.bottomnavigationappbar.Weggler.Model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

//레트로핏 연결 url 관리 (weggler)



interface RetrofitService {

    //Login User
    @POST("login")
    @FormUrlEncoded
    fun loginUser(
        @Field("name") name :String,
        @Field("password") password : String,
    ) : Call<Token>

    //2개의 데이터 (공구해요 , 프리토크)
    @GET("categories/{category}/products")
    fun  getCommunityProduct(
        @Path("category") category : String,
        @Query(value = "page", encoded = true) page: Int?,
        @Query(value = "size", encoded = true) size: Int?,
        @Query(value = "sort", encoded = true) sort: List<String>?,
    ): Call<ProductList>


    //커뮤니티 product 생성
    @POST("categories/{category}/products/{name}")
    fun addCommunityProduct(
        @Path("category") category : String,
        @Path("name") name : String,
        @Body body : BodyProductForPOST,
    ): Call<Product>

    //리뷰 얻기
    @GET("products/{productId}/reviewsByCreateTime")
    fun getReViews(
        @Path("productId") productId : Int,
    ): Call<ArrayList<ReviewInCommunity>>

    //리뷰 추가
    @Multipart
    @POST("products/{productId}/reviews")
    fun addReView(
        @Path("productId") productId: Int,
        @Part("param") param : BodyReviewForPOST, //body
        @Part multipartFile : MultipartBody.Part? //image
    ): Call<ReviewInCommunity>

    //리뷰 좋아요 순으로 얻기
    @GET("products/{productId}/reviewsByLike")
    fun getReviewsByLike(
        @Path("productId") productId: Int,
    ): Call<ArrayList<ReviewInCommunity>>

    //내 리뷰 조회
    @GET("reviewsByUser")
    fun getMyReviewList() : Call<ArrayList<ReviewInCommunity>>


    //내 댓글 조회
    @GET("commentByUser")
    fun getMyCommentList(
        @Query(value = "page", encoded = true) page: Int?,
        @Query(value = "size", encoded = true) size: Int?,
        @Query(value = "sort", encoded = true) sort: List<String>?,
    ) : Call<CommentList>

    //리뷰의 댓글 리스트 가져오기
    @GET("reviews/{reviewId}/comments")
    fun getReviewCommentList(
        @Path("reviewId")reviewId : Int,
        @Query(value = "page", encoded = true) page: Int?,
        @Query(value = "size", encoded = true) size: Int?,
        @Query(value = "sort", encoded = true) sort: List<String>?,
    ) : Call<CommentList>

    //댓글 추가
    @POST("reviews/{reviewId}/comments")
    fun addReviewComment(
        @Path("reviewId")reviewId: Int,
        @Body body : BodyCommentForPOST,
    ) : Call<Comment>

    @GET("reviews/{reviewId}")
    fun getCommunityReviewFromId(
        @Path("reviewId")reviewId: Int,
    ):Call<ReviewInCommunity>

    // like or unlike
    @PUT("reviews/{reviewId}")
    fun putLike(
        @Path("reviewId")reviewId: Int,
        @Query(value = "like") like : Boolean,
    ):Call<String>

}