package com.puresoftware.bottomnavigationappbar.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.R

class HomeViewPager(var homelist: ArrayList<String>, var context: Context): RecyclerView.Adapter<HomeViewPager.ViewpagerViewHolder>() {
    inner class ViewpagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val pagerImage:ImageView = itemView.findViewById(R.id.home_viewpager_item)

        fun bind(url:String){
            Glide.with(context)
                .load(url)
                .into(pagerImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewpagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_viewpager_item,parent,false)
        return ViewpagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewpagerViewHolder, position: Int) {
        holder.bind(homelist[position])
    }

    override fun getItemCount(): Int {
        return homelist.size
    }
}