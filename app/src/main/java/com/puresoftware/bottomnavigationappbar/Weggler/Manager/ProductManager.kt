package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import com.puresoftware.bottomnavigationappbar.Weggler.Model.BodyProduct
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ProductList
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManager(
   private val wApp : MasterApplication
) {

    // Community Product 갱신
    // freeTalk , jointPurchase 탐색
    // 데이터가 있다면 반환 없다면 추가후 반환
    fun initCommunityProduct(paramFunc:(Product?,message:String?)->Unit){
        getCommunityProduct(paramFunc = { data,message->
            if (message!=null){ //오류 반환
                paramFunc(null,message)
            }else{ //정상 받음
                val productList = data!!
                val communityList = productList.content
                paramFunc(communityList[0],null)
            }
        })
    }

    // Community Product 얻기
    private fun getCommunityProduct(paramFunc:(ProductList?,message:String?)->Unit){
        wApp.service.getCommunityProduct(category = "admin",null,null,null)
            .enqueue(object : Callback<ProductList>{
                override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ProductList>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }


}