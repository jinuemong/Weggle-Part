package com.puresoftware.bottomnavigationappbar.Weggler.Server

import android.app.Application
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
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
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("$baseUrl/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create(WegglerRetrofitService::class.java)

    }
}