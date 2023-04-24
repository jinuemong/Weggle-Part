package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import com.puresoftware.bottomnavigationappbar.Weggler.Model.RankingUser
import com.puresoftware.bottomnavigationappbar.databinding.ItemRankingBinding

//랭킹 데이터 - 밑에 리뷰 썸네일  연결 (ItemRankingVideoThumbnailAdapter)

class ItemRankingAdapter(
    private val mainActivity: MainActivity,
    itemList:ArrayList<RankingUser>
): RecyclerView.Adapter<ItemRankingAdapter.ItemRankingViewHolder>() {
    private lateinit var binding:ItemRankingBinding
    private var itemSet = itemList

    private var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun getList(user : UserInfo,paramFunc : (ArrayList<ReviewData>)->Unit)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    inner class ItemRankingViewHolder(val binding:ItemRankingBinding)
        : RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SetTextI18n")
            fun bind() {
                val rankingUser = itemSet[absoluteAdapterPosition]
                //썸네일 리스트 숨기기
                binding.thumbnailRecycler.visibility = View.GONE

                //이미지 + 텍스트 세팅
                binding.rankingNum.text = (absoluteAdapterPosition+1).toString() // 랭킹
                Glide.with(mainActivity)
                    .load(rankingUser.userInfo.profileFile)
                    .into(binding.rankingUserImage)
                binding.rankingUserName.text = rankingUser.userInfo.id
                binding.rankingLikeNum.text = "${rankingUser.totalLike}개"
//                binding.rankingPlayNum.text = "{${rankingUser.totalPlay}}회"

                //썸네일 리스트 보기 (클릭) -> adapter에서 불러오기
                binding.popRankingThumbnails.setOnClickListener {
                    binding.thumbnailRecycler.apply {
                        //on off 코드
                        if (visibility == View.VISIBLE){
                            visibility = View.GONE
                        }else {
                            //on일 경우 서버에 전송
                            visibility = View.VISIBLE
                            // data를 받았을 경우 어댑터 연결 함수 실행
                            onItemClickListener?.getList(rankingUser.userInfo, paramFunc = {
                                binding.setThumbnailData(it)
                            })
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

    fun ItemRankingBinding.setThumbnailData(dataList: ArrayList<ReviewData>){
        thumbnailRecycler.adapter = ItemRankingVideoThumbnailAdapter(mainActivity,dataList)
    }

}