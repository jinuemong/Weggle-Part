package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeImageBinding

class AdditionalImageAdapter(
    val activity: Activity
) : RecyclerView.Adapter<AdditionalImageAdapter.ViewHolder>(){

    private lateinit var binding : ItemMiniProductTypeImageBinding
    private var dataSet = ArrayList<Product>()

    private var onItemClickListener : OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(data: Product)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    inner class ViewHolder(binding: ItemMiniProductTypeImageBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val item = dataSet[absoluteAdapterPosition]
                Glide.with(activity)
                    .load(item.subjectFiles[0])
                    .into(binding.productImage)

                binding.delImage.setOnClickListener {
                    delData(item)
                    onItemClickListener?.onItemClick(item)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeImageBinding.inflate(LayoutInflater.from(activity)
        ,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int  = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun delData(data: Product){
        dataSet.remove(data)
        notifyDataSetChanged()
    }

    fun addData(data:Product){
        dataSet.add(data)
        notifyItemInserted(dataSet.size-1)
    }

}