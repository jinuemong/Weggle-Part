package com.puresoftware.bottomnavigationappbar.MyAccount.Manager

import android.telecom.Call
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.FollowData
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import retrofit2.Callback
import retrofit2.Response

class RelationManager(
    private val masterApp: MasterApplication,
) {
    fun getMyFollowers(paramFunc :(ArrayList<FollowData>? ,String?)->Unit){
        masterApp.service.getMyFollowerList()
            .enqueue(object : Callback<ArrayList<FollowData>>{
                override fun onResponse(
                    call: retrofit2.Call<ArrayList<FollowData>>,
                    response: Response<ArrayList<FollowData>>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: retrofit2.Call<ArrayList<FollowData>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun getMyFollowings(paramFunc :(ArrayList<FollowData>? ,String?)->Unit){
        masterApp.service.getMyFollowingList()
            .enqueue(object : Callback<ArrayList<FollowData>>{
                override fun onResponse(
                    call: retrofit2.Call<ArrayList<FollowData>>,
                    response: Response<ArrayList<FollowData>>
                ) {
                    if(response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: retrofit2.Call<ArrayList<FollowData>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun getTargetUserFollowers(userId:String, paramFunc: (ArrayList<FollowData>?, String?) -> Unit){
        masterApp.service.getUserFollowerList(userId)
            .enqueue(object : Callback<ArrayList<FollowData>>{
                override fun onResponse(
                    call: retrofit2.Call<ArrayList<FollowData>>,
                    response: Response<ArrayList<FollowData>>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: retrofit2.Call<ArrayList<FollowData>>, t: Throwable) {
                    paramFunc(null, "error")
                }

            })
    }

    fun getTargetUserFollowings(userId: String, paramFunc: (ArrayList<FollowData>?, String?) -> Unit){
        masterApp.service.getUserFollowingList(userId)
            .enqueue(object : Callback<ArrayList<FollowData>>{
                override fun onResponse(
                    call: retrofit2.Call<ArrayList<FollowData>>,
                    response: Response<ArrayList<FollowData>>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string() )
                    }
                }

                override fun onFailure(call: retrofit2.Call<ArrayList<FollowData>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun addFollow(targetId:String,paramFunc: (String?) -> Unit){
        masterApp.service.postFollowingUser(targetId,null)
            .enqueue(object : Callback<String>{
                override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                    if (response.isSuccessful){
                        paramFunc(response.body())
                    }else{
                        paramFunc(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    paramFunc(null)
                }

            })
    }

    fun delFollow(targetId:String, paramFunc: (String?) -> Unit){
        masterApp.service.delFollowingUser(targetId)
            .enqueue(object : Callback<String>{
                override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                    if (response.isSuccessful){
                        paramFunc(response.body())
                    }else{
                        paramFunc(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                    paramFunc(null)
                }

            })
    }


}