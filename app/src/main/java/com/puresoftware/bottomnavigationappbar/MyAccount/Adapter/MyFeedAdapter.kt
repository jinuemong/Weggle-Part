package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.databinding.ItemFeedThumnailBinding

class MyFeedAdapter (
    private val mainActivity: MainActivity,
    dataList : ArrayList<ReviewData>
,):RecyclerView.Adapter<MyFeedAdapter.ViewHolder>(){
    private lateinit var binding: ItemFeedThumnailBinding
    private var onItemClickListener : OnItemClickListener? = null

    var dataSet = dataList

    interface OnItemClickListener{
        fun itemClick(review : ReviewData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    inner class ViewHolder(val binding:ItemFeedThumnailBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val item = dataSet[absoluteAdapterPosition]
                Glide.with(mainActivity)
                    .load(item.thumbnail)
                    .into(binding.thumnailImage)

                binding.root.setOnClickListener {
                    onItemClickListener?.itemClick(item)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFeedThumnailBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int  = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }
}