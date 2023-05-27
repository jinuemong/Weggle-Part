package com.puresoftware.bottomnavigationappbar.Home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.Home.GroupList
import com.puresoftware.bottomnavigationappbar.Home.category.CategoryFragment
import com.puresoftware.bottomnavigationappbar.Home.data.BodyList
import com.puresoftware.bottomnavigationappbar.Home.data.GroupBuyData
import com.puresoftware.bottomnavigationappbar.Home.detail.ProductDetailFragment
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product

class SoonGroupbuyAdapter(val soonList:ArrayList<BodyList>,val context: Context): RecyclerView.Adapter<SoonGroupbuyAdapter.SoonGroupBuyViewHolder>() {
    inner class SoonGroupBuyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val soonImage = itemView.findViewById<ImageView>(R.id.include_image)
        val soonOfficeText = itemView.findViewById<TextView>(R.id.include_office_textview)
        val soonProductText = itemView.findViewById<TextView>(R.id.include_product_textview)
        val soonPercentageText = itemView.findViewById<TextView>(R.id.include_percentage)
        val soonPriceText = itemView.findViewById<TextView>(R.id.include_price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoonGroupBuyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_include_recyclerview_item,parent,false)
        return SoonGroupBuyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoonGroupBuyViewHolder, position: Int) {
        Glide.with(context)
            .load(soonList[position].image.first())
            .into(holder.soonImage)

        holder.soonOfficeText.text = soonList[position].company
        holder.soonProductText.text = soonList[position].name
        holder.soonPercentageText.text = soonList[position].discount.toString()
        holder.soonPriceText.text = soonList[position].price.toString()

        holder.itemView.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, ProductDetailFragment(soonList,position))
                .addToBackStack(null)
                .commit()

        }
    }

    override fun getItemCount(): Int {
       return soonList.size
    }
}