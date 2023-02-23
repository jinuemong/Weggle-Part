package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import android.app.Activity
import android.net.Uri
import com.puresoftware.bottomnavigationappbar.Weggler.Model.BodyReviewForPOST
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewListInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import com.puresoftware.bottomnavigationappbar.Weggler.ViewModel.MultiPartViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityManagerWithReview(
    private val wApp : WegglerApplication
){

    fun getCommunityReviewList(productId : Int,paramFunc : (ArrayList<ReviewInCommunity>?,String?) -> Unit){
        wApp.service.getReViews(productId,null,null,null)
            .enqueue(object : Callback<ReviewListInCommunity>{
                override fun onResponse(
                    call: Call<ReviewListInCommunity>,
                    response: Response<ReviewListInCommunity>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body()?.content,null)
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
    fun addCommunityReview(productId: Int, multiCommunityData: MultiCommunityData, file: Uri?,
    activity: Activity, paramFunc: (ReviewInCommunity?,String?) -> Unit){
        MultiPartViewModel().uploadCommunityPoster(productId,multiCommunityData,
            file, activity, paramFunc = {  data,message->
                paramFunc(data,message)
        })
    }

    //좋아요 순으로 커뮤 데이터 얻기
    fun getCommunityReviewListByLike(productId: Int,paramFunc: (ArrayList<ReviewInCommunity>?, String?) -> Unit){
        wApp.service.getReviewsByLike(productId)
            .enqueue(object : Callback<ReviewListInCommunity>{
                override fun onResponse(
                    call: Call<ReviewListInCommunity>,
                    response: Response<ReviewListInCommunity>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body()!!.content,null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ReviewListInCommunity>, t: Throwable) {
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
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null, response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ReviewInCommunity>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }
}