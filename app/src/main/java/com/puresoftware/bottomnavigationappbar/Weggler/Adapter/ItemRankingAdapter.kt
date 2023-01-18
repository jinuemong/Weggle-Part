package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.RankingUser
import com.puresoftware.bottomnavigationappbar.databinding.ItemRankingBinding

class ItemRankingAdapter(
    private val mainActivity: MainActivity,
    itemList:ArrayList<RankingUser>
): RecyclerView.Adapter<ItemRankingAdapter.ItemRankingViewHolder>() {
    private lateinit var binding:ItemRankingBinding
    private var itemSet = itemList
    inner class ItemRankingViewHolder(val binding:ItemRankingBinding)
        : RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SetTextI18n")
            fun bind() {
                val rankingUser = itemSet[absoluteAdapterPosition]
                //썸네일 리스트 숨기기
                binding.thumbnailRecycler.visibility = View.GONE

                //이미지 + 텍스트 세팅
                binding.rankingNum.text = rankingUser.rankingNum.toString()
                Glide.with(mainActivity)
                    .load(rankingUser.userImage)
                    .into(binding.rankingUserImage)
                binding.rankingUserName.text = rankingUser.username
                binding.rankingLikeNum.text = "{${rankingUser.totalLike}}개"
                binding.rankingPlayNum.text = "{${rankingUser.totalPlay}}회"

                //썸네일 리스트 보기 (클릭)
                binding.popRankingThumbnails.setOnClickListener {
                    binding.thumbnailRecycler.apply {
                        //on off 코드
                        if (visibility == View.VISIBLE){
                            visibility = View.GONE
                        }else {
                            //on일 경우 recyclerView 적용
                            visibility = View.VISIBLE
                            adapter = ItemRankingVideoThumbnailAdapter(
                                mainActivity,
                                rankingUser.thumbnailList)
                        }
                    }

                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRankingViewHolder {
        binding = ItemRankingBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ItemRankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemRankingViewHolder, position: Int) {
        holder.bind()

    }

    override fun getItemCount(): Int = itemSet.size


}