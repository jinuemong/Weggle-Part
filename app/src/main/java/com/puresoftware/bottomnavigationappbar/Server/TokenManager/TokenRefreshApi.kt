package com.puresoftware.bottomnavigationappbar.Server.TokenManager

import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

//Refresh Api 호출을 위한 인터페이스
// 새로운  access , refresh token 반환
interface TokenRefreshApi {
    @PATCH("token")
    suspend fun patchToken(
        @Query("refreshToken") refreshToken:String
    ) : Token
}