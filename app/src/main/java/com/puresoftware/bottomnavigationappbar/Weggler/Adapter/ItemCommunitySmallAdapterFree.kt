package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.type_free
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommentBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallFreeBinding
import java.util.*
import kotlin.collections.ArrayList

//프리
class ItemCommunitySmallAdapterFree(
    private val mainActivity: MainActivity,
    dataSet : ArrayList<ReviewInCommunity>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var freeBinding: ItemCommunitySmallFreeBinding
    private var dataList = dataSet
    private var onItemClickListener : OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(item:ReviewInCommunity)
    }
    fun setOnItemClickListener(listener : OnItemClickListener){
        this.onItemClickListener =listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        freeBinding = ItemCommunitySmallFreeBinding.inflate(
            LayoutInflater.from(mainActivity),
            parent, false
        )
        return FreeViewHolder(freeBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FreeViewHolder).bind()
    }

    override fun getItemCount() = dataList.size


    inner class FreeViewHolder(private val freeBinding: ItemCommunitySmallFreeBinding) :
        RecyclerView.ViewHolder(freeBinding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind() {
            val data = dataList[absoluteAdapterPosition]
            if(data.body.type== type_free) {
                freeBinding.timeText.text = getTimeText(data.createTime)
                freeBinding.sujectText.text = data.body.subject
                freeBinding.contentText.text = data.body.text
                Glide.with(mainActivity)
                    .load(data.thumbnail)
                    .into(freeBinding.mainImage)

                freeBinding.likeNum.text = data.likeCount.toString()
                freeBinding.commentNum.text = data.commentCount.toString()

                //좋아요 표시
                if (data.userLike){
                    freeBinding.onLike()
                }else{
                    freeBinding.offLike()
                }

                //클릭 이벤트
                freeBinding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }
            }else{
                freeBinding.root.layoutParams.height=0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data :ArrayList<ReviewInCommunity>){
        dataList = data
        notifyDataSetChanged()
    }

    private fun ItemCommunitySmallFreeBinding.onLike() =
        likeImage.setImageResource(R.drawable.ic_baseline_favorite_24_red)
    private fun ItemCommunitySmallFreeBinding.offLike() =
        likeImage.setImageResource(R.drawable.ic_baseline_favorite_24)

}