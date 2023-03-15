package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.Profile
import com.puresoftware.bottomnavigationappbar.databinding.ItemRecommendUserBoxVerBinding

//추천 유저 어댑터 (세로 적립)
//
//class ItemRecommendUserVerAdapter(
//    private val mainActivity: MainActivity,
//    itemList : ArrayList<Profile>
//):RecyclerView.Adapter<ItemRecommendUserVerAdapter.ItemRecommendUserVerViewHolder>(){
//
//    private lateinit var binding:ItemRecommendUserBoxVerBinding
//    var itemSet = itemList
//
//    inner class ItemRecommendUserVerViewHolder(private val binding:ItemRecommendUserBoxVerBinding)
//        :RecyclerView.ViewHolder(binding.root){
//        fun bind() {
//            val profile = itemSet[absoluteAdapterPosition]
//
//            Glide.with(mainActivity)
//                .load(profile.userImage)
//                .into(binding.ruBoxImage)
//            binding.ruBoxUsername.text = profile.username
//
//            var hashText = ""
//            if (profile.userTag!=null) {
//                for (hash in profile.userTag!!){
//                    hashText += (" $hash")
//                }
//            }
//            binding.urBoxHashTag.text =hashText
//
//            //클릭 리스너
//            binding.postFollow.setOnClickListener {
//
//            }
//            binding.delData.setOnClickListener {
//
//            }
//        }
//
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ItemRecommendUserVerViewHolder {
//        binding = ItemRecommendUserBoxVerBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
//        return ItemRecommendUserVerViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ItemRecommendUserVerViewHolder, position: Int) {
//        holder.bind()
//    }
//
//    override fun getItemCount(): Int  = itemSet.size
//
//
//}