package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Profile
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProfileBinding

class ItemMiniProfileAdapter(
    itemList : ArrayList<Profile>,
    private val mainActivity: MainActivity
) : RecyclerView.Adapter<ItemMiniProfileAdapter.ItemMiniProfileViewHolder>() {

    private lateinit var binding: ItemMiniProfileBinding
    var itemSet  = itemList

    inner class ItemMiniProfileViewHolder(private val binding:ItemMiniProfileBinding)
        :RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMiniProfileViewHolder {
        binding = ItemMiniProfileBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ItemMiniProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemMiniProfileViewHolder, position: Int) {
        holder.bind(itemSet[position])
    }

    override fun getItemCount()= itemSet.size
}