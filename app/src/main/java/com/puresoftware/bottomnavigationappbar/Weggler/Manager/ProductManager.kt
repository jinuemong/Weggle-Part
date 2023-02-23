package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import com.puresoftware.bottomnavigationappbar.Weggler.Model.BodyProductForPOST
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ProductList
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductManager(
   private val wApp : WegglerApplication
) {

    // Community Product 갱신
    // freeTalk , jointPurchase 탐색
    // 데이터가 있다면 반환 없다면 추가후 반환
    fun initCommunityProduct(paramFunc:(HashMap<String,Product>?,message:String?)->Unit){
        getCommunityProduct(paramFunc = { data,message->
            if (message!=null){ //오류 반환
                paramFunc(null,message)
            }else{ //정상 받음
                val productHash = HashMap<String,Product>()
                val productList = data!!
                var freeTalk = productList.content.find { it.name == "freeTalk" }
                var jointPurchase = productList.content.find { it.name == "jointPurchase" }

                // 둘 중에 null 값이 있으면 새로 생성
                if (freeTalk==null || jointPurchase==null){
                    //프리토크 생성
                    if (freeTalk==null){
                        addProduct("freeTalk",paramFunc={ newFreeTalk, message2 ->
                            if (newFreeTalk!=null &&message2==null){
                                freeTalk = newFreeTalk
                            }else {paramFunc(null,message2)} //freeTalk 추가 실패
                        })
                    }
                    //공동구매 생성
                    if (jointPurchase==null) {
                        addProduct("jointPurchase", paramFunc = { newJointPurchase, message3 ->
                            if (newJointPurchase != null && message3 == null) {
                                jointPurchase = newJointPurchase
                            } else {
                                paramFunc(null, message3) //joint 추가 실패
                            }
                        })
                    }

                    //시간 경과후 반환
                    Thread.sleep(500)
                }
                productHash["freeTalk"] = freeTalk!!
                productHash["jointPurchase"] = jointPurchase!!
                paramFunc(productHash,null)
            }
        })
    }

    // Community Product 얻기
    private fun getCommunityProduct(paramFunc:(ProductList?,message:String?)->Unit){
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
                    paramFunc(null,"error")
                }

            })
    }

    //새로 프로덕트 추가
    private fun addProduct(productName : String, paramFunc : (Product?, message:String?)->Unit){
        wApp.service.addCommunityProduct("community",productName, BodyProductForPOST(productName))
            .enqueue(object : Callback<Product>{
                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    paramFunc(null,"errr")
                }

            })
    }
}