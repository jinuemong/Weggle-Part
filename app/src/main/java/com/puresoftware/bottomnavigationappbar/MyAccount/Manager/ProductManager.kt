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

    fun getProductFromProductId(productId : Int, paramFun : (Product?, String?)->Unit){
        masterApp.service.getProductFromId(productId)
            .enqueue(object :  Callback<Product>{
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    if (response.isSuccessful){
                        paramFun(response.body(),null)
                    }else{
                        paramFun(null, response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    paramFun(null,"error")
                }

            })
    }

    fun getAdditionalProductList(dataList : ArrayList<Int>,paramFunc: (ArrayList<Product>?, String?) -> Unit){
        masterApp.service.getProductListFromIds(dataList)
            .enqueue(object : Callback<ArrayList<Product>>{
                override fun onResponse(
                    call: Call<ArrayList<Product>>,
                    response: Response<ArrayList<Product>>
                ) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }
}