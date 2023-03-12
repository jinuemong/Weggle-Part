package com.puresoftware.bottomnavigationappbar.Server.TokenManager

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("name") name :String,
        @Field("password") password : String,
    ): Token
}
