package com.puresoftware.bottomnavigationappbar.SideMenu.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemProductDetailAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeDetailBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeSelectBinding
import java.text.DecimalFormat


class ItemSelectProductAdapter(
    private val mainActivity: MainActivity,
    private var itemList : ArrayList<Product>
) : RecyclerView.Adapter<ItemSelectProductAdapter.ViewHolder>() {
    private lateinit var binding: ItemMiniProductTypeSelectBinding
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun click(id: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemMiniProductTypeSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val item = itemList[absoluteAdapterPosition]


            binding.name.text = item.name
            Glide.with(mainActivity)
                .load(item.subjectFiles[0])
                .into(binding.image)
            binding.company.text = item.body.company
            binding.salePer.text = "${item.body.discount}%"
            val decimal = DecimalFormat("#,###")
            binding.salePrice.text = "${decimal.format(item.body.price)}원"

            binding.root.setOnClickListener {
                onItemClickListener?.click(item.productId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeSelectBinding.inflate(
            LayoutInflater.from(mainActivity),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

}