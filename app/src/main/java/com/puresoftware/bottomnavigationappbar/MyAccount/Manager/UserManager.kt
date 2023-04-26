package com.puresoftware.bottomnavigationappbar.MyAccount.Manager

import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.User
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserBody
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserPatch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserManager(
    private val masterApp: MasterApplication
) {
    fun userLogout(paramFun : (success :String?,error:String?)->Unit){
        masterApp.service.logoutUser()
            .enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful){
                        paramFun(response.body(),null)
                    }else{
                        paramFun(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    paramFun(null,"error")
                }

            })
    }

    fun userDelete(id : String,paramFun: (User?, error: String?) -> Unit){
        masterApp.service.deleteUser(id)
            .enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        paramFun(response.body(),null)
                    }else{
                        paramFun(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    paramFun(null,"error")
                }

            })
    }

    fun getUser(paramFun: (User?, error: String?) -> Unit){
        masterApp.service.getUserInfo()
            .enqueue(object  : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        paramFun(response.body(),null)
                    }else{
                        paramFun(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    paramFun(null,"error")
                }

            })
    }

    fun updateUserInfo(email:String?,PW:String?,newPW:String?,body:UserBody?
                       ,paramFun: (User?, error: String?) -> Unit){
        masterApp.service.updateUser(UserPatch(email,PW,newPW,body))
            .enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){

                        paramFun(response.body(),null)
                    }else{
                        paramFun(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    paramFun(null,"error")
                }

            })
    }

    fun searchUserFromUserId(userId:String,paramFun: (User?, error: String?) -> Unit){
        masterApp.service.searchUser(userId)
            .enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        paramFun(response.body(),null)
                    }else{
                        paramFun(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    paramFun(null,"error")
                }

            })
    }
}