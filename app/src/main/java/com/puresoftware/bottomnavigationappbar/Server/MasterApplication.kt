package com.puresoftware.bottomnavigationappbar.Server

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.puresoftware.bottomnavigationappbar.Server.TokenManager.AuthInterceptor
import com.puresoftware.bottomnavigationappbar.Server.TokenManager.TokenAuthenticator
import com.puresoftware.bottomnavigationappbar.Server.TokenManager.TokenRefreshApi
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import kotlin.math.log

//http://dev-api.kooru.be/swagger-ui/index.html#/

//refrofit client
class MasterApplication : Application() {
    lateinit var service: RetrofitService
    private val baseUrl = "http://dev-api.kooru.be/api/v1"
    private lateinit var activity: Activity
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

    fun createRetrofit(currentActivity: Activity){
        activity = currentActivity

        val header = Interceptor{
            val original = it.request()
            if (checkIsLogin()){
                getUserToken().let { token->
                    Log.d("token test 1",token.toString())
                    val request = original.newBuilder()
                        .header("Authorization","Bearer $token")
                        .build()
                    it.proceed(request)
                }
            }else{
                it.proceed(original)
            }
        }

//        val normalClient = OkHttpClient.Builder()
//            .addInterceptor(header)
//            .addInterceptor(logInterceptor)
//            .addNetworkInterceptor(StethoInterceptor())
//            .build()
//
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(header)
//            .addInterceptor(AuthInterceptor(activity,buildTokenApi(normalClient)))
//            .build()
//
        // 레트로핏 생성 1
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getRetrofitClient(header))
            .build()

        service = retrofit.create(RetrofitService::class.java)

        //레트로핏 생성 2

        //토큰을 위한 인증
        //TokenAuthenticator으로 새 토큰 발행
//        val authenticator = TokenAuthenticator(activity,buildTokenApi())

//        val retrofit = Retrofit.Builder()
//            .baseUrl("$baseUrl/")
//            .client(getRetrofitClient(authenticator))
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(RetrofitService::class.java)
//        service = retrofit

    }

    //TokenRefreshApi를 빌드 1
    private fun buildTokenApi() : TokenRefreshApi {
        //임시 클라이언트
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl("$baseUrl/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenRefreshApi::class.java)
    }
    //TokenRefreshApi를 빌드 2
//    private fun buildTokenApi() : TokenRefreshApi {
//
//        return Retrofit.Builder()
//            .baseUrl("$baseUrl/")
//            .client(getRetrofitClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(TokenRefreshApi::class.java)
//    }

    //레트로핏 클라이언트 생성 함수 1
    // 빌드 :  okhttp client
    // 인터셉터를 통한 request를 보냄

    private fun getRetrofitClient(header:Interceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor {chain->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client->
                client.addInterceptor(header)
                client.addInterceptor(AuthInterceptor(activity,buildTokenApi()))
                //오류 관련 인터셉터도 등록 (오류 출력 기능 )
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(logInterceptor)
            }.build()
    }

    // 레트로핏 생성 2
//    private fun getRetrofitClient(authenticator: Authenticator? = null): OkHttpClient{
//        return OkHttpClient.Builder()
//            .connectTimeout(1,TimeUnit.HOURS)
//            .readTimeout(1,TimeUnit.HOURS)
//            .writeTimeout(1,TimeUnit.HOURS)
//            .addInterceptor { chain->
//                chain.proceed(chain.request().newBuilder().also {
//                    it.addHeader("Accept", "application/json")
//                }.build())
//            }.also { clint->
//                // 401 에러 시 새로 인증 요청
//                authenticator?.let { clint.authenticator(it) }
//                if (BuildConfig.DEBUG){
//                    //오류 관련 인터셉터도 등록 (오류 출력 기능 )
//                    val logInterceptor  = HttpLoggingInterceptor()
//                    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
//                    clint.addInterceptor(logInterceptor)
//                }
//            }.build()
//    }

    // 401 에러 발생 시 인증자가 토큰을 새로 갱신하려고 시도
    // 새로 고침에 성공하면 사용자가 로그아웃 되지 않음


//    //토큰 반환 함수
//    private fun getUserToken(): String {
//        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
//        return sp.getString("accessToken", "no token").toString()
//    }

    private fun checkIsLogin() : Boolean{
        val sp = activity.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
        val token = sp.getString("accessToken","null")
        return token!="null"
        //is not default
    }
    private fun getUserToken(): String?{
        val sp = activity.getSharedPreferences("login_sp",Context.MODE_PRIVATE)
        val token = sp.getString("accessToken","null")
        return if (token=="null") null
        else token
    }
}