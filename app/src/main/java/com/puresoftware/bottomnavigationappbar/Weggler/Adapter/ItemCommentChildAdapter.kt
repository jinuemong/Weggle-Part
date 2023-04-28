package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.databinding.ItemChildCommentBinding

class ItemCommentChildAdapter (
    private val mainActivity: MainActivity,
    var itemList : ArrayList<Comment>,
) : RecyclerView.Adapter<ItemCommentChildAdapter.ViewHolder>(){
    private lateinit var binding: ItemChildCommentBinding

    inner class ViewHolder(binding: ItemChildCommentBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun hold(){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemChildCommentBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int =itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hold()
    }
}