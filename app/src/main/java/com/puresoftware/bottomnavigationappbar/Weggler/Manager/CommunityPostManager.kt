package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import android.util.Log
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityList
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityPostManager (
    masterApplication:WegglerApplication
){
    private val masterApp = masterApplication

    //전체 post list 얻기
    fun getCommunityPostList(page:Int,size:Int,paramFunc:(CommunityList?)->Unit){
        masterApp.service.getCommunityPostList(page,size)
            .enqueue(object : Callback<CommunityList>{
                override fun onResponse(call: Call<CommunityList>, response: Response<CommunityList>) {
                    if(response.isSuccessful){
                        paramFunc(response.body())
                    }else{
                        paramFunc(null)
                    }
                }

                override fun onFailure(call: Call<CommunityList>, t: Throwable) {
                    paramFunc(null)
                }

            })
    }

    //community  type2 : Free 데이터 전송
    fun addCommunityFreeTalk(multiCommunityData: MultiCommunityData,paramFunc: (Boolean?) -> Unit){
        masterApp.service.addCommunityPost(body = multiCommunityData)
            .enqueue(object :Callback<CommunityContent>{
                override fun onResponse(
                    call: Call<CommunityContent>,
                    response: Response<CommunityContent>
                ) {
                    if(response.isSuccessful){
                        paramFunc(true)
                        Log.d("111231231231213 1",response.body()?.postId.toString())
                    }else{
                        paramFunc(false)
                        Log.d("111231231231213 2",response.errorBody().toString())
                        Log.d("111231231231213 2",response.body().toString())
                    }
                }
                override fun onFailure(call: Call<CommunityContent>, t: Throwable) {
                    paramFunc(null)
                }
            })
    }
}