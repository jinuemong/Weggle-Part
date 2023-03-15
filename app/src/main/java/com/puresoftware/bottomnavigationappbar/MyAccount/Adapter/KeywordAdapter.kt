package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Unit.tagList
import com.puresoftware.bottomnavigationappbar.databinding.ItemTagBinding

class KeywordAdapter(
    private val activity:Activity,
    private val dataList : List<Int>
):RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {
    private lateinit var binding:ItemTagBinding

    inner class ViewHolder(val binding: ItemTagBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val item = dataList[absoluteAdapterPosition]
                binding.tagText.text = tagList[item]
            }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordAdapter.ViewHolder {
        binding = ItemTagBinding.inflate(LayoutInflater.from(activity),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int  = dataList.size

}