package com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.AddReviewActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.BodyReviewForPOST
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewBody
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getImageFilePath
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddReviewViewModel : ViewModel(){

    var reviewProduct : Product?=null
    var searchText  = "changeText"
    var searchData  = MutableLiveData<ArrayList<Product>>()
    var selectProductData = MutableLiveData<ArrayList<Product>>()
    init {
        searchData.value = ArrayList()
        selectProductData.value = ArrayList()
    }

    fun setSearchData(data: ArrayList<Product>){
        searchData.value = data
    }

    fun resetSearchData(){
        searchData.value?.clear()
    }

    //데이터 추가 시 유무 확인
    fun findIsData(data: Product): Product? {
        return selectProductData.value!!.find { it.productId == data.productId }
    }
    fun addSelectData(data:Product){
        //같은 값이 없을 경우 추가
        val pr = findIsData(data)
        if (pr==null) {
            selectProductData.value!!.add(data)
        }
    }

    fun delSelectData(data:Product){
        //값이 있을 경우 삭제
        val pr = findIsData(data)
        if (pr !=null) {
            selectProductData.value!!.remove(pr)
        }
    }

    fun getSelectNum():Int{
        return selectProductData.value!!.size
    }
    fun resetSelectData(){
        selectProductData.value?.clear()
    }

    fun uploadReviewPoster(productId:Int, reviewBody: ReviewBody, filePath: Uri?,
                           activity: Activity,paramFunc:(ReviewData?,String?)->Unit){
        viewModelScope.launch {
            try {

                val body = BodyReviewForPOST(reviewBody)

                val multipartFile : MultipartBody.Part? = if (filePath!=null){
                    val path = getImageFilePath(activity,filePath)
                    val file = File(path)
                    val imageRequestBody = file.asRequestBody("video".toMediaTypeOrNull())

                    MultipartBody.Part.createFormData(
                        "multipartFile",
                        file.name,
                        imageRequestBody
                    )
                }else{null}

                ((activity as AddReviewActivity).masterApp).service
                    .addReView(productId,body,multipartFile)
                    .enqueue(object : Callback<ReviewData>{
                        override fun onResponse(
                            call: Call<ReviewData>, response: Response<ReviewData>
                        ) {
                            if (response.isSuccessful){
                                paramFunc(response.body(),null)
                            }else{
                                paramFunc(null,response.errorBody()!!.string())
                            }
                        }

                        override fun onFailure(call: Call<ReviewData>, t: Throwable) {
                            paramFunc(null,"error")
                            Log.d("error post",t.toString())
                        }

                    })
            }catch (e:java.lang.Exception){
                paramFunc(null,"error")
                Log.d("error post ",e.toString())
            }
        }
    }

}