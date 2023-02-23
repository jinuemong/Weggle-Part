package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import com.puresoftware.bottomnavigationappbar.Weggler.Model.ProductList
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManager(
   private val wApp : WegglerApplication
) {

    // Community Product 얻기
    fun getCommunityProduct(paramFunc:(ProductList?,message:String?)->Unit){
        wApp.service.getCommunityProduct(category = "community",null,null,null)
            .enqueue(object : Callback<ProductList>{
                override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ProductList>, t: Throwable) {
                    paramFunc(null,"err")
                }

            })
    }


    // Community Product 갱신
    // 데이터가 있다면 반환 없다면 추가후 반환
    fun initCommunityProduct()
}