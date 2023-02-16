package com.puresoftware.bottomnavigationappbar.Weggler.ViewModel

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import kotlinx.coroutines.launch

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception

//Multi Part 형식 (이미지 + body)으로 보낼 때 필요

class MultiPartViewModel: ViewModel(){
    fun uploadCommunityPoster(multiCommunityData: MultiCommunityData,filePath : String?,
                              activity: Activity,paramFunc:(CommunityContent?)->Unit){
        viewModelScope.launch {
            try {
                //데이터 변환
                val typeRequestBody : RequestBody = multiCommunityData.type.toString()
                    .toPlainRequestBody()
                val subjectRequestBody : RequestBody = multiCommunityData.subject
                    .toPlainRequestBody()
                val textRequestBody : RequestBody = multiCommunityData.text
                    .toPlainRequestBody()
                val linkUrlRequestBody : RequestBody = multiCommunityData.linkUrl
                    .toPlainRequestBody()
                val jointProductRequestBody : RequestBody = multiCommunityData.jointProduct
                    .toPlainRequestBody()

                // key -value 형식으로 묶음
                val params = hashMapOf<String,RequestBody>()
                params["type"] = typeRequestBody
                params["subject"] = subjectRequestBody
                params["text"] = textRequestBody
                params["linkUrl"] = linkUrlRequestBody
                params["jointProduct"] = jointProductRequestBody

                //이미지 처리
                val multipartFile : MultipartBody.Part? = if (filePath!=null) {
//                    val path = getImageFilePath(activity, filePath)
                    val file = File(filePath)
                    val imageRequestBody = file.asRequestBody()
                    //이미지 데이터 생성
                    MultipartBody.Part.createFormData(
                        "multipartFile",
                        file.name, imageRequestBody
                    )

                }else{ null } //이미지가 없다면 null 처리

                //retrofit 연결
                (activity.application as WegglerApplication).service
                    .addCommunityPost(params,multipartFile)
                    .enqueue(object : Callback<CommunityContent> {
                        override fun onResponse(
                            call: Call<CommunityContent>, response: Response<CommunityContent>) {
                            if (response.isSuccessful){
                                val data = response.body()!!
                                paramFunc(data)
                            }else{
                                paramFunc(null)
                            }
                        }
                        override fun onFailure(call: Call<CommunityContent>, t: Throwable) {
                            paramFunc(null)
                        }
                    })
            }catch (e:Exception){
                paramFunc(null)
            }
        }

    }

    //string ->Plain Text Request로 변환하는 확장 함수
    private fun String?.toPlainRequestBody() =
        requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

    //Uri를 String으로 전환
    private fun getImageFilePath(activity:Activity,contentUri:Uri):String{
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