package com.puresoftware.bottomnavigationappbar.Server.TokenManager

import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//Refresh Api 호출을 위한 인터페이스
// 새로운  access , refresh token 반환
interface TokenRefreshApi {
    @FormUrlEncoded
    @POST("token")
    suspend fun patchToken(
        @Body refreshToken:String?
    ) : Token
}