package com.puresoftware.bottomnavigationappbar.Home.manager

import android.util.Log
import com.puresoftware.bottomnavigationappbar.Home.GroupList
import com.puresoftware.bottomnavigationappbar.Home.data.*
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ProductList
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.brands.data.MetaData
import com.puresoftware.bottomnavigationappbar.brands.data.MetaInsideData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupManager(private val wApp: MasterApplication) {

    fun getGroup(name:String, paramFunc: (VideoData) -> Unit){
        wApp.service.addGroupProduct(name)
            .enqueue(object : Callback<VideoData> {
                override fun onResponse(call: Call<VideoData>, response: Response<VideoData>) {
                    if (response.isSuccessful) {
//                            var arrayProduct = ArrayList<String>()
//                            arrayProduct.add(response.body()!!.productList[].subjectFiles.first())
                        paramFunc(response.body()!!)
                        Log.d("온레스폰스", response.errorBody().toString())
                    } else {
//                        Toast.makeText(this,"광고를 가져오는데 실패하였습니다.",Toast.LENGTH_LONG).show()
                        Log.d("레스폰스 엘스", response.errorBody().toString())
                    }
                }
                override fun onFailure(call: Call<VideoData>, t: Throwable) {
                    Log.d("온페일", "하하하하")
                }

            })
    }

    //카테고리 product pagenation
    fun getCategoryProduct(category:String,paramFunc:(ArrayList<GroupBuyData>)->Unit){
        wApp.service.getCategoryProduct(category,0,10,listOf("id,ASC"))
            .enqueue(object : Callback<PagenationList>{
                override fun onResponse(call: Call<PagenationList>, response: Response<PagenationList>) {
                    if (response.isSuccessful){
                        response.body()?.let { paramFunc(it.content) }
                    }else{
                        Log.d("에러",response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<PagenationList>, t: Throwable) {
                    Log.d("실패",t.toString())
                }

            })
    }
    fun getMetaData(key:String,paramFunc: (String) -> Unit){
        wApp.service.getMeta(key)
            .enqueue(object : Callback<MetaData>{
                override fun onResponse(call: Call<MetaData>, response: Response<MetaData>) {
                    if (response.isSuccessful){
                        if(response.body() != null) {
                           paramFunc(response.body()!!.body.name)
                        }
                    }else{
                        Log.d("에러",response.errorBody()!!.string())
                    }
                }
                override fun onFailure(call: Call<MetaData>, t: Throwable) {
                    Log.d("실패",t.toString())
                }

            })
    }
    fun getReview(id:Int,paramFunc:(ArrayList<ReviewInnerData>) -> Unit){
        wApp.service.getReViewLee(id)
            .enqueue(object : Callback<ArrayList<ReviewInnerData>>{
                override fun onResponse(
                    call: Call<ArrayList<ReviewInnerData>>,
                    response: Response<ArrayList<ReviewInnerData>>,
                ) {
                    paramFunc(response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<ReviewInnerData>>, t: Throwable) {
                    Log.d("실패",t.toString())
                }

            })
    }

}

