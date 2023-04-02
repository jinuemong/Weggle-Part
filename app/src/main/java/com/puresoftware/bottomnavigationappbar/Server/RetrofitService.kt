package com.puresoftware.bottomnavigationappbar.Server

import com.puresoftware.bottomnavigationappbar.MyAccount.Model.BodyReviewForPOST
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.User
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserBody
import com.puresoftware.bottomnavigationappbar.Server.TokenManager.Token
import com.puresoftware.bottomnavigationappbar.Weggler.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

//레트로핏 연결 url 관리 (weggler)



interface RetrofitService {
    // 유저 관련 ////////////////////
    //Login User
    @POST("login")
    @FormUrlEncoded
    fun loginUser(
        @Field("name") name :String,
        @Field("password") password : String,
    ) : Call<Token>

    @GET("logout")
    fun logoutUser():Call<String>

    //유저 정보 얻기
    @GET("users")
    fun getUserInfo() : Call<User>

    //유저 삭제
    @DELETE("users")
    fun deleteUser(
        @Query("id")id : String,
    ):Call<User>

    //유저 수정
    @PATCH("users")
    @FormUrlEncoded
    fun updateUser(
        @Field("email") email : String?,
        @Field("password") password: String?,
        @Field("newPassword") newPassword : String?,
        @Field("body") userBody :UserBody?,
    ):Call<User>

    //유저 이미지 수정
    @Multipart
    @PATCH("userProfile")
    fun updateUserImage(
        @Part profile : MultipartBody.Part?,
        @Part background : MultipartBody.Part?
    ):Call<User>
    ///////////////////////////////////////////

    // 프로덱트 관련 /////////////////////////////
    //2개의 데이터 (공구해요 , 프리토크)
    @GET("categories/{category}/products")
    fun  getCommunityProduct(
        @Path("category") category : String,
        @Query(value = "page", encoded = true) page: Int?,
        @Query(value = "size", encoded = true) size: Int?,
        @Query(value = "sort", encoded = true) sort: List<String>?,
    ): Call<ProductList>


//    //커뮤니티 product 생성
//    @POST("categories/{category}/products/{name}")
//    fun addCommunityProduct(
//        @Path("category") category : String,
//        @Path("name") name : String,
//        @Body body : BodyProduct,
//    ): Call<Product>

    //프로덕트 조회
    @GET("productsByName/{name}")
    fun getProductsByName(
        @Path("name") name:String,
    ):Call<ArrayList<Product>>

    //프로덕트 id로 조회
    @GET("products/{productId}")
    fun getProductFromId(
        @Path("productId") productId : Int,
    ): Call<Product>
    ///////////////////////////////////

    //리뷰 관련////////////////////////////

    //리뷰 얻기
    @GET("products/{productId}/reviewsByCreateTime")
    fun getCommunityReViews(
        @Path("productId") productId : Int,
    ): Call<ArrayList<ReviewInCommunity>>

    //리뷰 추가 -커뮤니티
    @Multipart
    @POST("products/{productId}/reviews")
    fun addReViewForCommunity(
        @Path("productId") productId: Int,
        @Part("param") param : BodyReviewForCommunityPOST, //body
        @Part multipartFile : MultipartBody.Part? //image
    ): Call<ReviewInCommunity>

    //리뷰 추가 - 일반
    @Multipart
    @POST("products/{productId}/reviews")
    fun addReView(
        @Path("productId") productId: Int,
        @Part("param") param : BodyReviewForPOST, //body
        @Part multipartFile : MultipartBody.Part? //video
    ): Call<ReviewData>

    //리뷰 좋아요 순으로 얻기
    @GET("products/{productId}/reviewsByLike")
    fun getReviewsByLike(
        @Path("productId") productId: Int,
    ): Call<ArrayList<ReviewInCommunity>>

    //내 리뷰 조회
    @GET("reviewsByUser")
    fun getMyReviewList() : Call<ArrayList<ReviewData>>

    @GET("products/5/reviewsByUser")
    fun getMyCommunityReviewList() : Call<ArrayList<ReviewInCommunity>>

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
        @Body body : BodyComment,
    ) : Call<Comment>

    // 리뷰 아이디로 커뮤니티 리뷰 얻기
    @GET("reviews/{reviewId}")
    fun getCommunityReviewFromId(
        @Path("reviewId")reviewId: Int,
    ):Call<ReviewInCommunity>

    // 리뷰 아이디로 어카운트 리뷰 얻기
    @GET("reviews/{reviewId}")
    fun getAccountReviewFromId(
        @Path("reviewId")reviewId : Int,
    ): Call<ReviewData>

    // like or unlike
    @PUT("reviews/{reviewId}")
    fun putLike(
        @Path("reviewId")reviewId: Int,
        @Query(value = "like") like : Boolean,
    ):Call<String>

    /////////////////////////////////////////////
}