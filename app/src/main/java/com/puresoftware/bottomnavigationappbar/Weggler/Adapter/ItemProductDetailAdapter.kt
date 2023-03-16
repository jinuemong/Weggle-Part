package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeDetailBinding

class ItemProductDetailAdapter(
    private val mainActivity: MainActivity,
    itemSet : ArrayList<Product>,
) : RecyclerView.Adapter<ItemProductDetailAdapter.ViewHolder>(){
    private var itemList = itemSet
    private lateinit var binding : ItemMiniProductTypeDetailBinding

    inner class ViewHolder(val binding: ItemMiniProductTypeDetailBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeDetailBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : ArrayList<Product>){
        itemList = data
        notifyDataSetChanged()
    }
}