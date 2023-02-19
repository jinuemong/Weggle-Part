package com.puresoftware.bottomnavigationappbar.Weggler.Server

import android.app.Activity
import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.NullOnEmptyConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//http://dev-api.kooru.be/swagger-ui/index.html#/

class WegglerApplication : Application() {
    lateinit var service: WegglerRetrofitService
    private val baseUrl = "http://dev-api.kooru.be/api/v1"


    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        createRetrofit()
    }

    private fun createRetrofit(){
        //토큰을 위한 헤더
        val header = Interceptor{
            val original = it.request()
            if (checkIsLogin()){
                getUserToken().let { token->
                    val request = original.newBuilder()
                        .header("Authorization","Bearer $token")
                        .build()
                    it.proceed(request)
                }
            }else{
                it.proceed(original)
            }
        }

        //오류 관련 인터셉터
        val logInterceptor  = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //클라이언트에 빌더 추가
        val clientBuilder  = OkHttpClient.Builder()
        clientBuilder
            .addInterceptor(header)
            .interceptors().add(logInterceptor)


        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl/").addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()
        service = retrofit.create(WegglerRetrofitService::class.java)

    }

    //토큰이 있는지 체크
    private fun checkIsLogin(): Boolean{
        val sp = getSharedPreferences("accessToken",Context.MODE_PRIVATE)
        val token = sp.getString("accessToken","null")
        return token!="null"
    }

    //토큰이 있다면 토큰 반환
    private fun getUserToken() : String?{
        val sp = getSharedPreferences("accessToken",Context.MODE_PRIVATE)
        val token = sp.getString("accessToken","null")
        return if (token=="null") null
        else token
    }

}