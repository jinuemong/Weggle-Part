package com.puresoftware.bottomnavigationappbar.Weggler.TokenManager

import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRefreshApi {
    @FormUrlEncoded
    @POST("token")
    suspend fun patchToken(
        @Body refreshToken:String?
    ) : Token
}