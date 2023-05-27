package com.puresoftware.bottomnavigationappbar.Home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.puresoftware.bottomnavigationappbar.Home.category.*

class CategoryViewPagerAdapter(fragmentActivity:FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> AllFragment()
           1 -> FoodFragment()
           2 -> PetFragment()
           3 -> FashionFragment()
           4 -> InteriorFragment()
           5 -> BeautyFragment()
           else -> LivingFragment()
       }
    }
}