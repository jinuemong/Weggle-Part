package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import android.app.Activity
import android.net.Uri
import com.puresoftware.bottomnavigationappbar.Weggler.Model.*
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import com.puresoftware.bottomnavigationappbar.Weggler.ViewModel.MultiPartViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


////삭제해야 함
//class CommunityPostManager (
//    masterApplication:WegglerApplication
//){
//    private val masterApp = masterApplication
//
//    //전체 post list 얻기
//    fun getCommunityPostList(page:Int,sort:List<String>,paramFunc:(ReviewListInCommunity?)->Unit){
//        masterApp.service.getCommunityPostList(page,null,sort)
//            .enqueue(object : Callback<ReviewListInCommunity>{
//                override fun onResponse(call: Call<ReviewListInCommunity>, response: Response<ReviewListInCommunity>) {
//                    if(response.isSuccessful){
//                        paramFunc(response.body())
//                    }else{
//                        paramFunc(null)
//                    }
//                }
//
//                override fun onFailure(call: Call<ReviewListInCommunity>, t: Throwable) {
//                    paramFunc(null)
//                }
//
//            })
//    }
//
//    //인기 post list 얻기
//    fun getPopularCommunityPostList(paramFunc: (ArrayList<ReviewInCommunity>?) -> Unit){
//        masterApp.service.getCommunityPostByLike()
//            .enqueue(object :Callback<ArrayList<ReviewInCommunity>>{
//                override fun onResponse(
//                    call: Call<ArrayList<ReviewInCommunity>>,
//                    response: Response<ArrayList<ReviewInCommunity>>
//                ) {
//                    if (response.isSuccessful){
//                        paramFunc(response.body())
//                    }else {
//                        paramFunc(null)
//                    }
//                }
//
//                override fun onFailure(call: Call<ArrayList<ReviewInCommunity>>, t: Throwable) {
//                    paramFunc(null)
//                }
//
//            })
//    }
//
//    // view model -> community data 추가
//    fun addCommunityData(multiCommunityData: MultiCommunityData, filePath: Uri?,
//                         activity: Activity, paramFunc: (ReviewInCommunity?) -> Unit){
//        MultiPartViewModel().uploadCommunityPoster(multiCommunityData,filePath, activity,
//            paramFunc = {
//            paramFunc(it)
//        })
//    }
//
//    // comment 가져오기
//    fun getCommentList(postId: Int, paramFunc: (ArrayList<Comment>?) -> Unit){
//        masterApp.service.getCommentList(postId)
//            .enqueue(object : Callback<ArrayList<Comment>>{
//                override fun onResponse(
//                    call: Call<ArrayList<Comment>>, response: Response<ArrayList<Comment>>
//                ) {
//                    if (response.isSuccessful){
//                        paramFunc(response.body())
//                    }else{
//                        paramFunc(null)
//                    }
//                }
//
//                override fun onFailure(call: Call<ArrayList<Comment>>, t: Throwable) {
//                    paramFunc(null)
//                }
//
//            })
//    }
//
//    // comment 추가
//    fun addComment(postId : Int, body:String,paramFunc: (Comment?) -> Unit){
//        masterApp.service.addComment(postId, BodyCommentForPOST(body))
//            .enqueue(object : Callback<Comment>{
//                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
//                    if (response.isSuccessful) {
//                        response.body()?.body = body
//                        paramFunc(response.body())
//                    } else {
//                        paramFunc(null)
//                    }
//                }
//
//                override fun onFailure(call: Call<Comment>, t: Throwable) {
//                    paramFunc(null)
//                }
//
//            })
//    }
//
//    // comment 제거
//    fun delComment(postId: Int,commentId : Int, paramFunc: (Comment?) -> Unit){
//        masterApp.service.delComment(postId,commentId)
//            .enqueue(object : Callback<Comment>{
//                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
//                    if(response.isSuccessful){
//                        paramFunc(response.body())
//                    }else{
//                        paramFunc(null)
//                    }
//                }
//
//                override fun onFailure(call: Call<Comment>, t: Throwable) {
//                    paramFunc(null)
//                }
//
//            })
//    }
//
//
//}