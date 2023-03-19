package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.AddReviewActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeImageBinding

class AdditionalImageAdapter(
    val activity: Activity
) : RecyclerView.Adapter<AdditionalImageAdapter.ViewHolder>(){

    var selectData = ArrayList<Product>()

    private var onItemClickListener : OnItemClickListener?= null
    interface OnItemClickListener{
        fun delData(index: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    private lateinit var binding : ItemMiniProductTypeImageBinding

    inner class ViewHolder(binding: ItemMiniProductTypeImageBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(item : Product){

                Glide.with(activity)
                    .load(item.subjectFiles[0])
                    .into(binding.productImage)

                binding.delImage.setOnClickListener {
                    onItemClickListener?.delData(absoluteAdapterPosition)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeImageBinding.inflate(LayoutInflater.from(activity)
        ,parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSelectedData(data:Product){
        selectData.add(data)
        notifyItemInserted(selectData.size-1)
    }

    fun delSelectedData(index:Int){
        selectData.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int  = selectData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(selectData[position])
    }

}