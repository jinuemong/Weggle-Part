package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import android.app.Activity
import android.net.Uri
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityDataBody
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.ViewModel.MultiPartViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityManagerWithReview(
    private val wApp : MasterApplication
){

    fun getCommunityReviewList(productId : Int,paramFunc : (ArrayList<ReviewInCommunity>?,String?) -> Unit){
        wApp.service.getReViews(productId)
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

    //MultiPart로 데이터 추가
    fun addCommunityReview(productId: Int, multiCommunityData: MultiCommunityDataBody, file: Uri?,
                           activity: Activity, paramFunc: (ReviewInCommunity?,String?) -> Unit){
        MultiPartViewModel().uploadCommunityPoster(productId,multiCommunityData,
            file, activity, paramFunc = {  data,message->
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

    fun getMyCommunityReviewList(paramFunc: (ArrayList<ReviewInCommunity>?, String?) -> Unit){
        wApp.service.getMyReviewList()
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
}