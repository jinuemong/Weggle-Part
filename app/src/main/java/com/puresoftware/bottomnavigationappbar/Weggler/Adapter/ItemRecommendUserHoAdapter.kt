package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Profile
import com.puresoftware.bottomnavigationappbar.databinding.ItemRecommendUserBoxHoBinding

class ItemRecommendUserHoAdapter(
    private val mainActivity: MainActivity,
    itemList : ArrayList<Profile>
): RecyclerView.Adapter<ItemRecommendUserHoAdapter.ItemRecommendUserHoViewHolder>(){
    private lateinit var  binding:ItemRecommendUserBoxHoBinding
    var itemSet = itemList

    inner class ItemRecommendUserHoViewHolder(private val binding: ItemRecommendUserBoxHoBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val profile = itemSet[absoluteAdapterPosition]

                //뷰 세팅
                binding.ruBoxUsername.text = profile.username
                Glide.with(mainActivity)
                    .load(profile.userImage)
                    .into(binding.ruBoxImage)

                var hashText = ""
                if (profile.userTag!=null) {
                    for (hash in profile.userTag!!){
                        hashText += (" $hash")
                    }
                }
                binding.urBoxHashTag.text =hashText

                //클릭 리스너
                binding.followButton.setOnClickListener {

                }
                binding.delData.setOnClickListener {

                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecommendUserHoViewHolder {
        binding = ItemRecommendUserBoxHoBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ItemRecommendUserHoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemRecommendUserHoViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int =itemSet.size
}