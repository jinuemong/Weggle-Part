package com.puresoftware.bottomnavigationappbar.Server

import com.puresoftware.bottomnavigationappbar.Weggler.Model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

//레트로핏 연결 url 관리 (weggler)



interface RetrofitService {

    //Login jinwoo User
    @POST("login")
    @FormUrlEncoded
    fun loginJinwoo(
        @Field("name") name :String = "jinwoo",
        @Field("password") password : String = "jinwoo!@#123",
    ) : Call<Token>

    //2개의 데이터 (공구해요 , 프리토크)
    @GET("categories/{category}/products")
    fun getCommunityProduct(
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
    @GET("productes/{productId}/reviewsByCreateTime")
    fun getReViews(
        @Path("productId") productId : Int,
    ): Call<ReviewListInCommunity>

    //리뷰 추가
    @GET("productes/{productId}/reviews")
    fun addReView(
        @Path("productId") productId: Int,
        @Part("param") param : BodyReviewForPOST, //body
        @Part multipartFile : MultipartBody.Part? //image
    ): Call<ReviewInCommunity>

    //리뷰 좋아요 순으로 얻기
    @GET("productes/{productId}/reviewsByLike")
    fun getReviewsByLike(
        @Path("productId") productId: Int,
    ): Call<ReviewListInCommunity>

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

//    //get community post List
//    @GET("categories/community/products/")
//    fun getCommunityPostList(
//        @Query(value = "page",encoded=true)page:Int?,
//        @Query(value = "size",encoded=true)size:Int?,
//        @Query(value = "sort",encoded = true)sort:List<String>?,
//    ): Call<ReviewList>
//
//    //get community post List By Like
//    @GET("boards/community/postsByLike/")
//    fun getCommunityPostByLike() :Call<ArrayList<Review>>
//
//    //post community post data (type 1: joint , type 2 : free)
//    @Multipart
//    @POST("boards/community/posts/")
//    fun addCommunityPost(
//        @Part("param") param: BodyReviewForPOST, //Community
//        @Part multipartFile: MultipartBody.Part? //image
//    ): Call<Review>
//
//    // CommunityContent 데이터 수정
//    @PUT("boards/community/posts/{postId}/")
//    fun updateCommunityPost(
//        @Path("postId")postId:Int,
//        @Part("param") param: HashMap<String,RequestBody>, //Community
//        @Part multipartFile: MultipartBody.Part? //image
//    ):Call<Review>
//
//
//    // Comment 데이터 가져오기
//    @GET("posts/{postId}/comments/")
//    fun getCommentList(
//        @Path("postId")postId:Int,
//    ): Call<ArrayList<Comment>>
//
//    //Comment 추가
//    @POST("posts/{postId}/comments/")
//    fun addComment(
//        @Path(value = "postId")postId:Int,
//        @Body body : BodyCommentForPOST
//    ): Call<Comment>
//
//    //Comment 제거
//    @DELETE("posts/{postId}/comments/{commentId}/")
//    fun delComment(
//        @Path("postId")postId:Int,
//        @Path("commentId")commentId:Int
//    ): Call<Comment>


}