package com.puresoftware.bottomnavigationappbar.Weggler.TokenManager

import android.app.Activity
import android.content.Context
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import kotlinx.coroutines.runBlocking
import okhttp3.*

//요청을 가로채서 인증 관련 오류를 탐색

class TokenAuthenticator constructor(
    private val activity: Activity,
    private val tokenApi: TokenRefreshApi,
) : Authenticator, BaseRepository(tokenApi) {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when(val token =getUpdateToken()){
                is Resource.Success ->{
                    val sp  =activity.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
                    val editor = sp.edit()
                    val accessToken = token.value.accessToken
                    val refreshToken =token.value.refreshToken
                    editor.putString("accessToken", accessToken)
                    editor.putString("refreshToken", refreshToken)
                    editor.apply()
                    response.request.newBuilder()
                        .header("Authorization","Bearer $accessToken")
                        .build()
                }
                else ->null
            }


        }
    }


    //토큰 자동 업데이트
    private suspend fun getUpdateToken() : Resource<Token> {
        val refreshToken = activity.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
            .getString("refreshToken","").toString()
        return safeApiCall {tokenApi.patchToken(refreshToken)}
    }
}