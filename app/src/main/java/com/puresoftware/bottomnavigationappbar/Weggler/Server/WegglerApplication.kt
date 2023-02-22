package com.puresoftware.bottomnavigationappbar.Weggler.Server

import android.app.Activity
import android.app.Application
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import com.puresoftware.bottomnavigationappbar.Weggler.TokenManager.TokenAuthenticator
import com.puresoftware.bottomnavigationappbar.Weggler.TokenManager.TokenRefreshApi
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//http://dev-api.kooru.be/swagger-ui/index.html#/

//refrofit client
class WegglerApplication constructor(
    private val activity: Activity,
) : Application() {
    lateinit var service: WegglerRetrofitService
    private val baseUrl = "http://dev-api.kooru.be/api/v1"
    private lateinit var header : Interceptor
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        createRetrofit()
    }

    private fun createRetrofit(){
        //토큰을 위한 인증
        //TokenAuthenticator으로 새 토큰 발행
        val authenticator = TokenAuthenticator(activity,buildTokenApi())

        //레트로핏 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl/")
            .client(getRetrofitClient(authenticator))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(WegglerRetrofitService::class.java)

    }

    //TokenRefreshApi를 빌드
    private fun buildTokenApi() : TokenRefreshApi {
        return Retrofit.Builder()
            .baseUrl("$baseUrl/")
            .client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenRefreshApi::class.java)
    }

    //레트로핏 클라이언트 생성 함수
    // 빌드 :  okhttp client
    // 인터셉터를 통한 request를 보냄

    private fun getRetrofitClient(authenticator: Authenticator? = null): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor { chain->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { clint->
                authenticator?.let { clint.authenticator(it) }
                if (BuildConfig.DEBUG){
                    //오류 관련 인터셉터도 등록 (오류 출력 기능 )
                    val logInterceptor  = HttpLoggingInterceptor()
                    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    clint.addInterceptor(logInterceptor)
                }
            }.build()
    }

    // 401 에러 발생 시 인증자가 토큰을 새로 갱신하려고 시도
    // 새로 고침에 성공하면 사용자가 로그아웃 되지 않음
}