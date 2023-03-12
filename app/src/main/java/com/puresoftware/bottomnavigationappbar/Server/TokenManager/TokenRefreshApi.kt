package com.puresoftware.bottomnavigationappbar.Server.TokenManager

import retrofit2.http.PATCH
import retrofit2.http.Query

//Refresh Api 호출을 위한 인터페이스
// 새로운  access , refresh token 반환
interface TokenRefreshApi {
    @PATCH("token")
    suspend fun patchToken(
        @Query("refreshToken") refreshToken:String
    ) : Token
}