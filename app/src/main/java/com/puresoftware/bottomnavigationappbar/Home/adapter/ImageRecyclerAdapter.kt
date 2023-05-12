package com.puresoftware.bottomnavigationappbar.Home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.R

class ImageRecyclerAdapter(val context: Context, private val imageList:List<String>):
    RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.detail_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.detail_recyclerview_item,parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position])
            .into(holder.image)
    }

    override fun getItemCount(): Int {
       return imageList.size
    }

}