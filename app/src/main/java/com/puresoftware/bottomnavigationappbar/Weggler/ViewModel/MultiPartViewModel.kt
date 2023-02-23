package com.puresoftware.bottomnavigationappbar.Weggler.ViewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Model.BodyReviewForPOST
import kotlinx.coroutines.launch

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception

//Multi Part 형식 (이미지 + body)으로 보낼 때 필요

class MultiPartViewModel: ViewModel(){
    fun uploadCommunityPoster(productId:Int,multiCommunityData: MultiCommunityData,filePath : Uri?,
                              activity: Activity,paramFunc:(ReviewInCommunity?,String?)->Unit){
        viewModelScope.launch {
            try {

                val body = BodyReviewForPOST(multiCommunityData)

                //이미지 처리
                val multipartFile : MultipartBody.Part? = if (filePath!=null) {
                    val path = getImageFilePath(activity, filePath)
                    val file = File(path)
                    val imageRequestBody = file.asRequestBody()
                    //이미지 데이터 생성
                    MultipartBody.Part.createFormData(
                        "multipartFile",
                        file.name, imageRequestBody
                    )

                }else{ null } //이미지가 없다면 null 처리

                //retrofit 연결
                ((activity as MainActivity).wApp).service
                    .addReView(productId,body,multipartFile)
                    .enqueue(object : Callback<ReviewInCommunity> {
                        override fun onResponse(
                            call: Call<ReviewInCommunity>, response: Response<ReviewInCommunity>) {
                            if (response.isSuccessful){
                                val data = response.body()!!
                                paramFunc(data,null)
                            }else{
                                paramFunc(null,response.errorBody()!!.string())
                            }
                        }
                        override fun onFailure(call: Call<ReviewInCommunity>, t: Throwable) {
                            paramFunc(null,"error")
                        }
                    })
            }catch (e:Exception){
                paramFunc(null,"error")
            }
        }

    }

    //string ->Plain Text Request로 변환하는 확장 함수
    private fun String?.toPlainRequestBody() =
        requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

    //Uri를 String으로 전환
    @SuppressLint("Recycle")
    private fun getImageFilePath(activity:Activity, contentUri:Uri):String{
        var columnIndex = 0
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity.contentResolver.query(contentUri,projection,
        null,null,null)
        if (cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }
}