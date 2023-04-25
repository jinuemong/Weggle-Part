package com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.User
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserBody
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserPatch
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getFilePath
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getImageFilePath
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.mozilla.javascript.tools.jsc.Main
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.reflect.Field

class MyAccountViewModel : ViewModel(){
    var userProfile : User? = null

    var newBackgroundImage : Uri? = null
    var newProfileImage : Uri? = null
    var exploreProfile : User? = null

//    fun updateUserProfile(activity: Activity,email:String?,PW:String?,newPW:String?,body: UserBody?
//                          ,paramFun: (User?, error: String?) -> Unit){
//        viewModelScope.launch {
//            try {
////                val emailRequestBody :RequestBody = email.toString().toPlainRequestBody()
////                val pwRequestBody : RequestBody = PW.toString().toPlainRequestBody()
////                val newPwRequestBody : RequestBody = newPW.toString().toPlainRequestBody()
////                val bodyRequestBody : RequestBody = body.toString().toPlainRequestBody()
////
////                val textHashMap = hashMapOf<String,RequestBody>()
////                textHashMap["email"] = emailRequestBody
////                textHashMap["password"] = pwRequestBody
////                textHashMap["newPassword"] = newPwRequestBody
////                textHashMap["body"] = bodyRequestBody
//                val userPath = UserPatch(email,PW,newPW,body)
//                val requestBody : RequestBody = userPath.toString().toPlainRequestBody()
//                (activity as MainActivity).masterApp.service
//                    .updateUser(requestBody)
//                    .enqueue(object : Callback<User>{
//                        override fun onResponse(
//                            call: retrofit2.Call<User>,
//                            response: Response<User>
//                        ) {
//                            if (response.isSuccessful){
//                                paramFun(response.body(),null)
//                            }else{
//                                paramFun(null,response.errorBody()!!.string())
//                            }
//                        }
//
//                        override fun onFailure(call: retrofit2.Call<User>, t: Throwable) {
//                            paramFun(null,"error : $t")
//                        }
//
//                    })
//            }catch (e:Exception){
//                paramFun(null,"error:$e")
//            }
//        }
//    }
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

    //string 을 plain Text Request로 바꿔주는 확장 함수  : l2hyunwoo(저작권)
    private fun String?.toPlainRequestBody () =
        requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

}