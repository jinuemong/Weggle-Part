package com.puresoftware.bottomnavigationappbar.Home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.puresoftware.bottomnavigationappbar.Home.adapter.CategoryViewPagerAdapter
import com.puresoftware.bottomnavigationappbar.Home.adapter.DetailTabViewPagerAdapter
import com.puresoftware.bottomnavigationappbar.Home.data.BodyList
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentProductDetailBinding
import com.puresoftware.bottomnavigationappbar.databinding.HomeFragmentBinding


class ProductDetailFragment(val soonList:ArrayList<BodyList>,val position:Int) : Fragment() {
private lateinit var binding:FragmentProductDetailBinding
private val tabList = listOf("상품정보","리뷰","문의","주문정보")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        binding.detailTabViewpager.adapter = DetailTabViewPagerAdapter(activity as MainActivity,soonList,position)

        TabLayoutMediator(binding.detailTab,binding.detailTabViewpager){ tab, pos ->
            tab.text = tabList[pos]
        }.attach()

        binding.detailTabViewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            var currentState = 0
            var currentPos = 0

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position){
                    if(currentPos == 0) binding.detailTabViewpager.currentItem = 4
                    else if(currentPos == 4) binding.detailTabViewpager.currentItem = 0
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Glide.with(this)
//            .load(soonList[position].image)
//            .into(binding.detailOfficeImageView)
        binding.detailOfficeTextView.text = soonList[position].company
        binding.detailProductTextView.text = soonList[position].name
        binding.detailPriceTextView.text = soonList[position].price.toString()
        binding.ordinaryDelivery.text = soonList[position].charge
        binding.deliverPeriod.text = soonList[position].duration
        binding.saleRate.text = soonList[position].benefit


    }

}