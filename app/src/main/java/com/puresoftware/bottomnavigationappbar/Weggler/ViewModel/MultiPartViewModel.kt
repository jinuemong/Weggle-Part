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
    fun uploadCommunityPoster(multiCommunityData: MultiCommunityData,filePath : Uri?,
                              activity: Activity,paramFunc:(ReviewInCommunity?)->Unit){
        viewModelScope.launch {
            try {
                // 방법 1
//                //데이터 변환
//                val typeRequestBody : RequestBody = multiCommunityData.type.toString()
//                    .toPlainRequestBody()
//                val subjectRequestBody : RequestBody = multiCommunityData.subject
//                    .toPlainRequestBody()
//                val textRequestBody : RequestBody = multiCommunityData.text
//                    .toPlainRequestBody()
//                val linkUrlRequestBody : RequestBody = multiCommunityData.linkUrl
//                    .toPlainRequestBody()
//                val jointProductRequestBody : RequestBody = multiCommunityData.jointProduct
//                    .toPlainRequestBody()

                // 방법 2
                // key -value 형식으로 묶음
//                val params = hashMapOf<String,String>()
//                params["type"] = multiCommunityData.type.toString()
//                params["subject"] = multiCommunityData.subject
//                params["text"] = multiCommunityData.text
//                params["linkUrl"] = multiCommunityData.linkUrl
//                params["jointProduct"] = multiCommunityData.jointProduct

                // 방법 3
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
                    .addCommunityPost(body,multipartFile)
                    .enqueue(object : Callback<ReviewInCommunity> {
                        override fun onResponse(
                            call: Call<ReviewInCommunity>, response: Response<ReviewInCommunity>) {
                            if (response.isSuccessful){
                                val data = response.body()!!
                                Log.d("selfjsefljselefj 1",data.toString())
                                paramFunc(data)
                            }else{
                                paramFunc(null)
                                Log.d("selfjsefljselefj 2",response.errorBody()!!.string())
                            }
                        }
                        override fun onFailure(call: Call<ReviewInCommunity>, t: Throwable) {
                            Log.d("selfjsefljselefj 3",t.message.toString())
                            paramFunc(null)
                        }
                    })
            }catch (e:Exception){
                Log.d("selfjsefljselefj 4", e.message.toString())
                paramFunc(null)
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