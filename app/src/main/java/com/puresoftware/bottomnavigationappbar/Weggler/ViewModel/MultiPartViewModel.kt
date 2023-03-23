package com.puresoftware.bottomnavigationappbar.Weggler.ViewModel

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityDataBody
import com.puresoftware.bottomnavigationappbar.Weggler.Model.BodyReviewForCommunityPOST
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getImageFilePath
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception

//Multi Part 형식 (이미지 + body)으로 보낼 때 필요

class MultiPartViewModel: ViewModel(){
    fun uploadCommunityFreeTalk(productId:Int, multiCommunityData: MultiCommunityDataBody, filePath : Uri?,
                                activity: Activity, paramFunc:(ReviewInCommunity?,String?)->Unit){
        viewModelScope.launch {
            try {

                val body = BodyReviewForCommunityPOST(multiCommunityData)

                //이미지 처리
                val multipartFile : MultipartBody.Part? = if (filePath!=null) {
                    val path = getImageFilePath(activity, filePath)
                    val file = File(path)
                    val imageRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    //이미지 데이터 생성
                    MultipartBody.Part.createFormData(
                        "multipartFile",
                        file.name,
                        imageRequestBody
                    )

                }else{ null } //이미지가 없다면 null 처리

                //retrofit 연결
                ((activity as MainActivity).masterApp).service
                    .addReViewForCommunity(productId,body,multipartFile)
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
                            Log.d("error post",t.toString())
                        }
                    })
            }catch (e:Exception){
                paramFunc(null,"error")
                Log.d("error post ",e.toString())
            }
        }

    }


    fun uploadCommunityGroupBuy(productId:Int, multiCommunityData: MultiCommunityDataBody, bitmap : Bitmap?,
                                fileName:String,activity: Activity, paramFunc:(ReviewInCommunity?,String?)->Unit){
        viewModelScope.launch {
            try {

                val body = BodyReviewForCommunityPOST(multiCommunityData)

                //이미지 처리
                val multipartFile : MultipartBody.Part? = if (bitmap!=null) {
                    val bitmapRequestBody = BitmapRequestBody(bitmap)
                    //이미지 데이터 생성
                    MultipartBody.Part.createFormData(
                        "multipartFile",
                        fileName,
                        bitmapRequestBody
                    )

                }else{ null } //이미지가 없다면 null 처리

                //retrofit 연결
                ((activity as MainActivity).masterApp).service
                    .addReViewForCommunity(productId,body,multipartFile)
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
                            Log.d("error post",t.toString())
                        }
                    })
            }catch (e:Exception){
                paramFunc(null,"error")
                Log.d("error post ",e.toString())
            }
        }

    }

    private fun bitmapToString(bitmap: Bitmap) : String?{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,baos)
        return Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT)
    }
    //string ->Plain Text Request로 변환하는 확장 함수
    private fun String?.toPlainRequestBody() =
        requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

    //비트맵 -> requsetbody 변환
    inner class BitmapRequestBody(private  val bitmap : Bitmap) : RequestBody(){
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG,99,sink.outputStream())
        }
    }

}