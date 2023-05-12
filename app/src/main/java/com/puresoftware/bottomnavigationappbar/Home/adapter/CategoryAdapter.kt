package com.puresoftware.bottomnavigationappbar.Home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.Home.HomeFragment
import com.puresoftware.bottomnavigationappbar.Home.category.AllFragment
import com.puresoftware.bottomnavigationappbar.Home.category.CategoryFragment
import com.puresoftware.bottomnavigationappbar.Home.category.CategoryMainActivity
import com.puresoftware.bottomnavigationappbar.Home.data.CategoryData
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R

class CategoryAdapter(val categoryList:ArrayList<CategoryData>, val context: Context):RecyclerView.Adapter<CategoryAdapter.CategoryViewHoler>() {
    inner class CategoryViewHoler(itemView: View):RecyclerView.ViewHolder(itemView) {
        val categoryImage = itemView.findViewById<ImageButton>(R.id.category_food_image_button)
        val categoryText = itemView.findViewById<TextView>(R.id.category_food_text)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHoler {
     val view = LayoutInflater.from(parent.context).inflate(R.layout.home_category_recycler_item,parent,false)
        return CategoryViewHoler(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHoler, position: Int) {
//       holder.categoryImage = categoryList[position].imageView
        holder.categoryText.text = categoryList[position].category
        Glide.with(context)
            .load(categoryList[position].imageView)
            .override(100,100)
            .into(holder.categoryImage)

        holder.categoryImage.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame,CategoryFragment())
                .addToBackStack(null)
                .commit()

            context.setMainViewVisibility(false)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}