package com.puresoftware.bottomnavigationappbar.Home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.brands.BrandsFragment
import com.puresoftware.bottomnavigationappbar.brands.MainViewPagerData
import com.puresoftware.bottomnavigationappbar.brands.ViewHolderPage
import com.puresoftware.bottomnavigationappbar.databinding.BrandIncludeBinding
import com.puresoftware.bottomnavigationappbar.databinding.HomeCategoryRecyclerItemBinding
import com.puresoftware.bottomnavigationappbar.databinding.HomeIncludeRecyclerviewItemBinding
import com.puresoftware.bottomnavigationappbar.databinding.MainItemViewpagerBinding

//class ViewpagerBrandAdapter(private val listData: ArrayList<MainViewPagerData>) :
//        RecyclerView.Adapter<Holder>() {
//
//        var context: Context? = null
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//            context = parent.context // context 가져오기
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.home_include_recyclerview_item,parent,false)
//            return Holder(view)
//        }
//
//        override fun onBindViewHolder(holder:Holder, position: Int) {
//            val viewHolder: Holder = holder
//            viewHolder.onBind(listData[position % listData.size])
//
//            Log.i("asdf","position ${position % listData.size}")
//        }
//
//        override fun getItemCount(): Int = listData.size
//    }
//
//    class Holder(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//
//
//    }