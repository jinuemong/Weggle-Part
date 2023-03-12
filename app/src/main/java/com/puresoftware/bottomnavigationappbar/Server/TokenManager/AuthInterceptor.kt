package com.puresoftware.bottomnavigationappbar.Server.TokenManager

import android.content.Context
import android.util.Log
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import kotlinx.coroutines.runBlocking
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback


//  오류가 발생했을 때 처리 코드

class AuthInterceptor(
    private val context: Context,
    private val tokenApi: TokenRefreshApi,
    ) : Interceptor,BaseRepository(){
    override fun intercept(chain: Interceptor.Chain): Response{
        val request = chain.request()
        val mainResponse = chain.proceed(request)

        when(mainResponse.code){
            401 ->{
                // 토큰 재인증
                runBlocking {
//                    val refreshToken = context.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
//                        .getString("refreshToken","").toString()
//                    Log.d("test refresh token 1",refreshToken)
//                    ((context as MainActivity).application as MasterApplication).service
//                        .patchToken(refreshToken).enqueue(object : Callback<Token>{
//                            override fun onResponse(
//                                call: Call<Token>,
//                                response: retrofit2.Response<Token>
//                            ) {
//                                if (response.isSuccessful){
//                                    val token = response.body()
//                                    val sp = context.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
//                                    val editor = sp.edit()
//                                    // 기존 토큰
//                                    Log.d(
//                                        "test code ists sssss 2",
//                                        sp.getString("accessToken", "no token").toString()
//                                    )
//                                    // new 토큰
//                                    Log.d("test code ists sssss 3", token!!.accessToken)
//                                    val newAccessToken = token.accessToken
//                                    val newRefreshToken = token.refreshToken
//                                    editor.putString("accessToken", newAccessToken)
//                                    editor.putString("refreshToken", newRefreshToken)
//                                    editor.apply()
//
//                                    //new Build
//                                    mainResponse.request.newBuilder()
//                                        .header("Authorization", "Bearer $newAccessToken")
//                                        .build()
//                                }else{
//                                    Log.d("test refresh token 2",response.errorBody()!!.string())
//                                }
//                            }
//
//                            override fun onFailure(call: Call<Token>, t: Throwable) {}
//
//                        })
                    when (val token = getUpdateToken()) {
                        is Resource.Success -> {
                            val sp  =context.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
                            val editor = sp.edit()
                            // 기존 토큰
                            Log.d("test code ists sssss 2",sp.getString("accessToken","no token").toString())
                            val tokenValue = token.value!!
                            // new 토큰
                            Log.d("test code ists sssss 3",tokenValue.accessToken)
                            val accessToken = tokenValue.accessToken
                            val refreshToken =tokenValue.refreshToken
                            editor.putString("accessToken", accessToken)
                            editor.putString("refreshToken", refreshToken)
                            editor.apply()
                            mainResponse.request.newBuilder()
                                .header("Authorization","Bearer $accessToken")
                                .build()
                        }
                        else ->{
                            Log.d("qazwsxedc in Auth",token.toString())
                        }
                    }
                }
            }
        }
        Log.d("qazwsxedc in Auth 2 ",mainResponse.request.toString())
        return mainResponse
    }

    private suspend fun getUpdateToken() : Resource<Token?> {
        val refreshToken = context.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
            .getString("refreshToken","").toString()

        // updateToken 확인
        Log.d("test code ists sssss 4 ",refreshToken.toString())

        //safeApiCall을 통한 api 요청
        // refresh token이 비었을 경우에는 null 전송을 통해서 에러 반환을 받음
        return safeApiCall { tokenApi.patchToken(refreshToken) }
    }


}