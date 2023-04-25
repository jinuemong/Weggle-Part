package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemProductHoViewBinding
import java.text.DecimalFormat

class ItemProductHoAdapter(
    private val mainActivity: MainActivity,
    private val dataList : ArrayList<Product>
) :RecyclerView.Adapter<ItemProductHoAdapter.ViewHolder>(){
    private lateinit var binding: ItemProductHoViewBinding

    inner class ViewHolder(binding: ItemProductHoViewBinding)
        :RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun hold(){
            val data = dataList[absoluteAdapterPosition]
            binding.productCompany.text = data.body.company
            Glide.with(mainActivity)
                .load(data.subjectFiles[0])
                .into(binding.productImage)
            binding.productName.text = data.name
            binding.salePer.text = "${data.body.discount}%"
            val decimal = DecimalFormat("#,###")
            binding.salePrice.text = decimal.format(data.body.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemProductHoAdapter.ViewHolder {
        binding = ItemProductHoViewBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ItemProductHoAdapter.ViewHolder, position: Int) {
        holder.hold()
    }
}