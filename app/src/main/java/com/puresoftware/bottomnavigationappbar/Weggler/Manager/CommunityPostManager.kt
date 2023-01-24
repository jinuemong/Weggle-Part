package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import android.util.Log
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Community
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityPostManager (
    masterApplication:WegglerApplication
){
    private val masterApp = masterApplication

    //전체 post list 얻기
    fun getCommunityPostList(page:Int,size:Int,paramFunc:(Community?)->Unit){
        masterApp.service.getCommunityPostList(page,size)
            .enqueue(object : Callback<Community>{
                override fun onResponse(call: Call<Community>, response: Response<Community>) {
                    if(response.isSuccessful){
                        paramFunc(response.body())
                    }else{
                        paramFunc(null)
                    }
                }

                override fun onFailure(call: Call<Community>, t: Throwable) {
                    paramFunc(null)
                }

            })
    }

    //community 데이터 전송

}