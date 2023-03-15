package com.puresoftware.bottomnavigationappbar.MyAccount.Manager

import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManager(
    private val masterApp : MasterApplication
) {

    fun searchProduct(name : String,paramFun : (ArrayList<Product>?,String?)->Unit){
        masterApp.service.getProductsByName(name)
            .enqueue(object : Callback<ArrayList<Product>>{
                override fun onResponse(
                    call: Call<ArrayList<Product>>,
                    response: Response<ArrayList<Product>>
                ) {
                    if (response.isSuccessful){
                        paramFun(response.body(),null)
                    }else{
                        paramFun(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                    paramFun(null,"error")
                }

            })
    }
}