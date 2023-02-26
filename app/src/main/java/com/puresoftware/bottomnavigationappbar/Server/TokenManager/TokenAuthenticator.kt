package com.puresoftware.bottomnavigationappbar.Server.TokenManager

import android.app.Activity
import android.content.Context
import android.util.Log
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import kotlinx.coroutines.runBlocking
import okhttp3.*

//요청을 가로채서 인증 관련 오류를 탐색
//
//class TokenAuthenticator constructor(
//    private val context: Context,
//    private val tokenApi: TokenRefreshApi,
//) : Authenticator , BaseRepository(){
//    //BaseRepository : 인증 실패시 로그아웃 전송
//
//    // authenticate :  401 에러가 발생 될 때마다 호출
//    // 토큰이 유효하지 않음을 의미
//    override fun authenticate(route: Route?, response: Response): Request? {
//        Log.d("test code ists sssss 1 ","authenticate")
//        return runBlocking {
//            // 토큰 생성 후 값에 따른 처리
//            when(val token =getUpdateToken()){
//                is Resource.Success ->{
//                    val sp  =context.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
//                    val editor = sp.edit()
//                    // 기존 토큰
//                    Log.d("test code ists sssss 2",sp.getString("accessToken","no token").toString())
//                    val tokenValue = token.value!!
//                    // new 토큰
//                    Log.d("test code ists sssss 3",token.value.accessToken)
//                    val accessToken = tokenValue.accessToken
//                    val refreshToken =tokenValue.refreshToken
//                    editor.putString("accessToken", accessToken)
//                    editor.putString("refreshToken", refreshToken)
//                    editor.apply()
//                    response.request.newBuilder()
//                        .header("Authorization","Bearer $accessToken")
//                        .build()
//                }
//                else ->null
//                // 오류가 발생하면 null을 반환해서 api가 무한 새로고침에 빠지지 않도록 함
//            }
//
//
//        }
//    }
//
//
//    //토큰 업데이트
//    //새로 토큰을 가져와서 업데이트함
//    //토큰을 성공적으로 얻을 경우 업데이트
//    //오류 발생 시 null 반환
//    private suspend fun getUpdateToken() : Resource<Token?> {
//        val refreshToken = context.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
//            .getString("refreshToken","").toString()
//
//        // updateToken 확인
//        Log.d("test code ists sssss 4 ",refreshToken.toString())
//
//        //safeApiCall을 통한 api 요청
//        // refresh token이 비었을 경우에는 null 전송을 통해서 에러 반환을 받음
//        val data = safeApiCall {tokenApi.patchToken(refreshToken)}
//        Log.d("test code ists sssss 6",data.toString())
//        return data
//    }
//
//}