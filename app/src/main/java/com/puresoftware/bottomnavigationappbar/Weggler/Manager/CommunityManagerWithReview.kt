package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityDataBody
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.RankingUser
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewListInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.ViewModel.MultiPartViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityManagerWithReview(
    private val wApp : MasterApplication
){

    //리뷰 시간 순
    fun getCommunityReviewList(productId : Int,paramFunc : (ReviewListInCommunity?,String?) -> Unit){

        wApp.service.getCommunityReViews(productId, listOf("reviewId,DESC"))
            .enqueue(object : Callback<ReviewListInCommunity>{
                override fun onResponse(
                    call: Call<ReviewListInCommunity>,
                    response: Response<ReviewListInCommunity>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }

                }

                override fun onFailure(call: Call<ReviewListInCommunity>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    //MultiPart로 데이터 추가
    @RequiresApi(Build.VERSION_CODES.Q)
    fun addCommunityReviewTypeFree(productId: Int, multiCommunityData: MultiCommunityDataBody, file: Uri?,
                                   activity: Activity, paramFunc: (ReviewInCommunity?,String?) -> Unit){
        MultiPartViewModel().uploadCommunityFreeTalk(productId,multiCommunityData,
            file, activity, paramFunc = {  data,message->
                paramFunc(data,message)
        })
    }
    fun addCommunityReviewTypeGroup(productId: Int, multiCommunityData: MultiCommunityDataBody, file: Bitmap?,
                                    fileName:String,activity: Activity, paramFunc: (ReviewInCommunity?,String?) -> Unit){
        MultiPartViewModel().uploadCommunityGroupBuy(productId,multiCommunityData,
            file, fileName,activity, paramFunc = {  data,message->
                paramFunc(data,message)
            })
    }
    //좋아요 순으로 커뮤 데이터 얻기
    fun getCommunityReviewListByLike(productId: Int,paramFunc: (ArrayList<ReviewInCommunity>?, String?) -> Unit){
        wApp.service.getReviewsByLike(productId)
            .enqueue(object : Callback<ArrayList<ReviewInCommunity>>{
                override fun onResponse(
                    call: Call<ArrayList<ReviewInCommunity>>,
                    response: Response<ArrayList<ReviewInCommunity>>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ReviewInCommunity>>, t: Throwable) {
                    paramFunc(null,"error")
                }
            })
    }

    fun getMyReviewList(paramFunc: (ArrayList<ReviewInCommunity>?, String?) -> Unit){
        wApp.service.getMyCommunityReviewList()
            .enqueue(object : Callback<ArrayList<ReviewInCommunity>>{
                override fun onResponse(
                    call: Call<ArrayList<ReviewInCommunity>>,
                    response: Response<ArrayList<ReviewInCommunity>>
                ) {
                    if(response.isSuccessful){
                        if (response.body()?.size==0){
                            paramFunc(null,"No Posting")
                        }else {
                            paramFunc(response.body(), null)
                        }
                    }else{
                        paramFunc(null, response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ReviewInCommunity>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    fun getReviewFromId(reviewId : Int, paramFunc: (ReviewInCommunity?, String?) -> Unit){
        wApp.service.getCommunityReviewFromId(reviewId)
            .enqueue(object : Callback<ReviewInCommunity>{
                override fun onResponse(
                    call: Call<ReviewInCommunity>,
                    response: Response<ReviewInCommunity>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ReviewInCommunity>, t: Throwable) {
                    paramFunc(null, "error")
                }

            })
    }

    //좋아요 추가
    fun reviewLike(reviewId: Int,like:Boolean,paramFunc: (Boolean) -> Unit){
        wApp.service.putReviewLike(reviewId,like)
            .enqueue(object :Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful){
                        paramFunc(true)
                    }else{
                        paramFunc(false)
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    paramFunc(false)
                }

            })
    }

    //랭킹 리스트 가져오기
    fun getRankingUsers(paramFunc: (ArrayList<RankingUser>?, String?) -> Unit){
        wApp.service.getUserRanking()
            .enqueue(object : Callback<ArrayList<RankingUser>>{
                override fun onResponse(
                    call: Call<ArrayList<RankingUser>>,
                    response: Response<ArrayList<RankingUser>>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<RankingUser>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }
}