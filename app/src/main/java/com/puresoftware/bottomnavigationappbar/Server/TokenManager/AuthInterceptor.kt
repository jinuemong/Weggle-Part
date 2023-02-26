package com.puresoftware.bottomnavigationappbar.Server.TokenManager

import android.content.Context
import android.util.Log
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import kotlinx.coroutines.runBlocking
import okhttp3.*


// 임시 코드 수정해야 함

class AuthInterceptor(
    private val context: Context,
    private val tokenApi: TokenRefreshApi,
    ) : Interceptor,BaseRepository(){
    override fun intercept(chain: Interceptor.Chain): Response{
        val request = chain.request()
        val response = chain.proceed(request)

        when( response.code){
            400 ->{

            }
            401 ->{
                // 토큰 재인증
                runBlocking {
                    when (val token = getUpdateToken()) {
                        is Resource.Success -> {
                            val sp  =context.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
                            val editor = sp.edit()
                            // 기존 토큰
                            Log.d("test code ists sssss 2",sp.getString("accessToken","no token").toString())
                            val tokenValue = token.value!!
                            // new 토큰
                            Log.d("test code ists sssss 3",token.value.accessToken)
                            val accessToken = tokenValue.accessToken
                            val refreshToken =tokenValue.refreshToken
                            editor.putString("accessToken", accessToken)
                            editor.putString("refreshToken", refreshToken)
                            editor.apply()
                            response.request.newBuilder()
                                .header("Authorization","Bearer $accessToken")
                                .build()
                        }
                        else ->{}
                    }
                    response
                }
            }
            403 ->{

            }
            404 ->{

            }
        }
        return response
    }

    private suspend fun getUpdateToken() : Resource<Token?> {
        val refreshToken = context.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
            .getString("refreshToken","").toString()

        // updateToken 확인
        Log.d("test code ists sssss 4 ",refreshToken.toString())

        //safeApiCall을 통한 api 요청
        // refresh token이 비었을 경우에는 null 전송을 통해서 에러 반환을 받음
        return safeApiCall {tokenApi.patchToken(refreshToken)}
    }


}