package com.puresoftware.bottomnavigationappbar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import com.puresoftware.bottomnavigationappbar.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var backPressTime:Long = 0
    private lateinit var callback:OnBackPressedCallback
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:Editor

    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 자동 로그인 구현 (암호화)

        val masterKeyAlias = MasterKey
            .Builder(applicationContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        sharedPreferences =
            EncryptedSharedPreferences.create(
                applicationContext,
                "encrypted_settings", //이름
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        editor = sharedPreferences.edit()

        // 로그아웃으로 넘어왔다면 자동 로그인 데이터 삭제
        val isLogout = intent.getBooleanExtra("logout",false)
        if (isLogout){deleteLoginData()}

        // 기존 로그인 데이터가 존재하는지 확인
        val name = sharedPreferences.getString("name","")
        if (name!=""){
            setUserName(sharedPreferences.getString("name",""))
            setUserPass(sharedPreferences.getString("pass",""))
            login()
        }

        //로그인 버튼 클릭 시 로그인
        binding.loginButton.setOnClickListener {
            login()
        }

        binding.insertButton.setOnClickListener {

        }

        //뒤로가기 버튼 조작 (2초 이내 연속 클릭 시 앱 종료 )
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(System.currentTimeMillis()>backPressTime+2000){
                    backPressTime = System.currentTimeMillis()
                    Toast.makeText(applicationContext,"'뒤로' 버틀을 한번 더 누르시면 앱이 종료됩니다."
                        ,Toast.LENGTH_SHORT).show()
                }else{
                    finishAffinity()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this,callback)
    }

    // user login
    private fun login(){
        val masterApp = (application as MasterApplication)
        masterApp.createRetrofit(this@LoginActivity)
        masterApp.service.loginUser(
            getUserName(),getUserPass()
        ).enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful && response.body()!=null){
                    //성공 시 토큰 저장
                    val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
                    val editor = sp.edit()
                    editor.putString("accessToken", response.body()?.accessToken)
                    editor.putString("refreshToken", response.body()?.refreshToken)
                    editor.apply()

                    //자동 로그인 추가
                    addLoginData()

                    //메인으로 이동
                    val intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this@LoginActivity,"계정 오류",Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Token>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"로그인 실패", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    // 데이터 삭제
    private fun deleteLoginData(){
        editor.remove("name")
        editor.remove("pass")
        editor.clear()
        editor.commit()
    }

    //자동 로그인 데이터 추가
    private fun addLoginData(){
        editor.putString("name",getUserName())
        editor.putString("pass",getUserPass())
        editor.commit()
    }

    private fun getUserName() : String{
        return binding.userNameInput.text.toString()
    }
    private fun getUserPass() : String{
        return binding.userPasswordInput.text.toString()
    }

    private fun setUserName(name : String?){
        if(name!="") {
            binding.userNameInput.setText(name)
        }
    }

    private fun setUserPass(pass : String?){
        if(pass!="") {
            binding.userPasswordInput.setText(pass)
        }
    }
}