package com.puresoftware.bottomnavigationappbar.Home.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.puresoftware.bottomnavigationappbar.Home.adapter.CategoryViewPagerAdapter
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.ActivityCategoryMainBinding

class CategoryMainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCategoryMainBinding
    private val tabList = listOf("전체","식품","반려동물")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.categoryTabViewpager.adapter = CategoryViewPagerAdapter(this)

        TabLayoutMediator(binding.categoryTab,binding.categoryTabViewpager){ tab, pos ->
            tab.text = tabList[pos]
        }.attach()

        binding.categoryTabViewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            var currentState = 0
            var currentPos = 0

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position){
                    if(currentPos == 0) binding.categoryTabViewpager.currentItem = 2
                    else if(currentPos == 2) binding.categoryTabViewpager.currentItem = 0
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                currentPos = position
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                currentState = state
                super.onPageScrollStateChanged(state)
            }
        } )
    }
}