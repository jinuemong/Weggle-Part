package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeImageBinding

class AdditionalImageAdapter(
    val activity: Activity
) : RecyclerView.Adapter<AdditionalImageAdapter.ViewHolder>(){

    private lateinit var binding : ItemMiniProductTypeImageBinding
    private var dataSet = ArrayList<String>()

    inner class ViewHolder(binding: ItemMiniProductTypeImageBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val item = dataSet[absoluteAdapterPosition]
                Glide.with(activity)
                    .load(item)
                    .into(binding.productImage)

                binding.delImage.setOnClickListener {
                    delData(absoluteAdapterPosition)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeImageBinding.inflate(LayoutInflater.from(activity)
        ,parent,false)
        return ViewHolder(binding)
    }

    fun delData(currentIndex : Int){
        dataSet.removeAt(currentIndex)
        notifyItemRemoved(currentIndex)
    }
    override fun getItemCount(): Int  = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

}