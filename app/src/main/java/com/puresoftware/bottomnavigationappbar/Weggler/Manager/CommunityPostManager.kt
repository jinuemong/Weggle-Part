package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import android.util.Log
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Community
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityList
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityPostManager (
    masterApplication:WegglerApplication
){
    private val masterApp = masterApplication

    //전체 post list 얻기
    fun getCommunityPostList(page:Int,sort:List<String>,paramFunc:(CommunityList?)->Unit){
        masterApp.service.getCommunityPostList(page,null,sort)
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

    //인기 post list 얻기
    fun getPopularCommunityPostList(size: Int,sort:List<String>,paramFunc: (CommunityList?) -> Unit){
        masterApp.service.getCommunityPostList(null,size,sort)
            .enqueue(object :Callback<CommunityList>{
                override fun onResponse(
                    call: Call<CommunityList>,
                    response: Response<CommunityList>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body())
                    }else {
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
        masterApp.service.addCommunityPost(Community(multiCommunityData))
            .enqueue(object :Callback<CommunityContent>{
                override fun onResponse(
                    call: Call<CommunityContent>,
                    response: Response<CommunityContent>
                ) {
                    if(response.isSuccessful){
                        Log.d("body 0",response.body().toString())
                        paramFunc(true)
                    }else{
                        paramFunc(false)
                        Log.d("body 1",response.body().toString())
                        Log.d("body 2",response.message().toString())
                        Log.d("body 3",response.errorBody().toString())
                        Log.d("body 4",response.raw().toString())
                    }
                }
                override fun onFailure(call: Call<CommunityContent>, t: Throwable) {
                    paramFunc(null)
                }
            })
    }
}