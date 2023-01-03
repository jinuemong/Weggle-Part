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
    }

    private fun initView(){

    }

    private fun setUpListener(){

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}