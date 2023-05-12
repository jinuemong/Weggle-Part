package com.puresoftware.bottomnavigationappbar.Home.category

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.puresoftware.bottomnavigationappbar.Home.adapter.CategoryViewPagerAdapter
import com.puresoftware.bottomnavigationappbar.Home.manager.GroupManager
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.databinding.FragmentCategoryBinding
import org.mozilla.javascript.tools.jsc.Main


class CategoryFragment : Fragment() {

    private lateinit var binding:FragmentCategoryBinding
    private val tabList = listOf("전체","식품","반려동물","패션/잡화","인테리어","뷰티/주얼리","생활용품")
    private lateinit var mainActivity: MainActivity
    private lateinit var wegglerApp : MasterApplication

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        wegglerApp = mainActivity.masterApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        binding.categoryTabViewpager.adapter = CategoryViewPagerAdapter(activity as MainActivity)

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
                    if(currentPos == 0) binding.categoryTabViewpager.currentItem = 7
                    else if(currentPos == 7) binding.categoryTabViewpager.currentItem = 0
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
        return binding.root
    }


}