package com.puresoftware.bottomnavigationappbar.SideMenu.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.databinding.ItemPicBinding

class RewardGuideImageAdapter(
    private val mainActivity: MainActivity,
    private val itemSet : List<String>
):RecyclerView.Adapter<RewardGuideImageAdapter.ViewHolder>() {
    private lateinit var binding : ItemPicBinding

    inner class ViewHolder(binding: ItemPicBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun hold(){
                Glide.with(mainActivity)
                    .load(itemSet[absoluteAdapterPosition])
                    .into(binding.image)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPicBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int =itemSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hold()
    }
}