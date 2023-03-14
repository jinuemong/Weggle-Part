package com.puresoftware.bottomnavigationappbar.MyAccount.retrofit_not_use

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

public interface WegglerRetrofitService {
    @GET("boards/product/posts/{post}/")
    fun getPost(@Path("post") post: Int):Call<ProductDataClass>

    @Multipart
    @POST("boards/challenge/posts")
    fun challengePost(@PartMap param:HashMap<String,RequestBody>,
                        @Part multipartFile: MultipartBody.Part?) :Call<ProductDataClass>
}