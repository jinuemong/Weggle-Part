package kr.co.ky.weggle

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import kr.co.ky.weggle.databinding.ActivityMainBinding
import kr.co.ky.weggle.mainFragment.*

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding : ActivityMainBinding
    lateinit var frManger: FragmentManager
    private lateinit var fragmentPageAdapter: FragmentAdapter

    //click tab
    private val tintColor = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_selected),
            intArrayOf(-android.R.attr.state_selected)
        ),
        intArrayOf(Color.parseColor("#629CD1"), Color.parseColor("#2C608F"))
    )

    //tab Icon
    private val iconView = arrayOf(
        R.drawable.ic_baseline_home_24,
        R.drawable.ic_baseline_business_center_24,
        R.drawable.circle,
        R.drawable.ic_baseline_podcasts_24,
        R.drawable.ic_iconmonstr_user_circle_thin
    )

    //tab name
    private val iconText = arrayOf(
        "홈","브랜드관","","위글러","마이"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        frManger = this@MainActivity.supportFragmentManager
        setSupportActionBar(binding.mainView.mainToolbar.mainLayoutToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24) //홈 버튼 설정
        supportActionBar?.setDisplayShowTitleEnabled(false) //타이틀 제거

        initFragmentAdapter()
        initViewPager()
        initTabLayout()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ //menu selected
                binding.drawLayout.openDrawer(GravityCompat.START) //left

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initFragmentAdapter(){
        fragmentPageAdapter = FragmentAdapter(this@MainActivity)
        fragmentPageAdapter.apply {
            addFragment(HomeFragment())
            addFragment(BrandFragment())
            addFragment(WeggleFragment())
            addFragment(WegglerFragment())
            addFragment(MyFragment())
        }
    }

    private fun initViewPager(){
        binding.mainView.mainViewPager.apply {
            adapter = fragmentPageAdapter
            registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
            isUserInputEnabled = false //스크롤 막기
        }
    }
    private fun initTabLayout() {
        binding.mainView.mainBottomTabLayout.tabIconTint = tintColor
        TabLayoutMediator(binding.mainView.mainBottomTabLayout, binding.mainView.mainViewPager)
        {tab,position->
            tab.setIcon(iconView[position])
            tab.text = iconText[position]
        }.attach()
    }
}