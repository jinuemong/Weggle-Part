package com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.User
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getFilePath
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getImageFilePath
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.mozilla.javascript.tools.jsc.Main
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.reflect.Field

class MyAccountViewModel : ViewModel(){
    var userProfile : User? = null
    var newBackgroundImage : Uri? = null
    var newProfileImage : Uri? = null

    fun updateUserImages(activity: Activity,paramFunc : (User?,String?)->Unit){
        viewModelScope.launch {
            try {

                val profile : MultipartBody.Part? = if(newProfileImage!=null){
                    val path = getImageFilePath(activity,newProfileImage!!)
                    val file = File(path)
                    val profileRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData(
                        "profile",
                        file.name,
                        profileRequestBody
                    )
                }else{ null }

                val background : MultipartBody.Part? = if(newBackgroundImage!=null){
                    val path = getImageFilePath(activity,newBackgroundImage!!)
                    val file = File(path)
                    val backgroundRequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData(
                        "background",
                        file.name,
                        backgroundRequestBody
                    )
                }else{null}

                (activity as MainActivity).masterApp.service
                    .updateUserImage(profile,background)
                    .enqueue(object : Callback<User> {
                        override fun onResponse(
                            call: retrofit2.Call<User>,
                            response: Response<User>
                        ) {
                            if (response.isSuccessful){
                                paramFunc(response.body(),null)
                                // 유저 프로필 초기화
                                userProfile = response.body()
                            }else{
                                paramFunc(null,response.errorBody()!!.string())
                            }
                        }

                        override fun onFailure(call: retrofit2.Call<User>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })

            }catch (e:Exception){
                paramFunc(null,"error")
            }
        }
    }
}