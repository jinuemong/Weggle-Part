package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import android.app.Activity
import android.net.Uri
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityList
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import com.puresoftware.bottomnavigationappbar.Weggler.ViewModel.MultiPartViewModel
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

    // view model -> community data 추가
    fun addCommunityData(multiCommunityData: MultiCommunityData, filePath: Uri?,
                         activity: Activity, paramFunc: (CommunityContent?) -> Unit){
        MultiPartViewModel().uploadCommunityPoster(multiCommunityData,filePath, activity,
            paramFunc = {
            paramFunc(it)
        })
    }

    // comment 가져오기
    fun getCommentList(postId: Int, paramFunc: (ArrayList<Comment>?) -> Unit){
        masterApp.service.getCommentList(postId)
            .enqueue(object : Callback<ArrayList<Comment>>{
                override fun onResponse(
                    call: Call<ArrayList<Comment>>, response: Response<ArrayList<Comment>>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body())
                    }else{
                        paramFunc(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Comment>>, t: Throwable) {
                    paramFunc(null)
                }

            })
    }


}