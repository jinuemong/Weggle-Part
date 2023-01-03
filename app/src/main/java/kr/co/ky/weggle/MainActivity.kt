package kr.co.ky.weggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import kr.co.ky.weggle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding :ActivityMainBinding
    lateinit var frManger: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        frManger = this@MainActivity.supportFragmentManager
        setSupportActionBar(binding.mainView.mainToolbar.mainLayoutToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24) //홈 버튼 설정
        supportActionBar?.setDisplayShowTitleEnabled(false) //타이틀 제거
    }

    private fun initView(){

    }

    private fun setUpListener(){

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.
        }
        return super.onOptionsItemSelected(item)
    }

}