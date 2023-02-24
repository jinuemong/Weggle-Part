package com.puresoftware.bottomnavigationappbar.Server.TokenManager

import okhttp3.ResponseBody
import retrofit2.http.POST

//로그 아웃 기능 요청
interface BaseApi {
    @POST("logout")
    suspend fun logout(): ResponseBody
}