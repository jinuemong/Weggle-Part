package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommentBinding

class ItemCommentAdapter (
    private val mainActivity: MainActivity,
    itemList : ArrayList<Comment>
): RecyclerView.Adapter<ItemCommentAdapter.ItemCommentViewHolder>(){
    private lateinit var binding: ItemCommentBinding
    var itemSet = itemList

    private var onItemClickListener : OnItemClickListener?= null
    interface OnItemClickListener{
        fun userClick(userId : String)
        fun likeClick(commentId : Int, like:Boolean)
        //답글
        fun addSubComment(comment:Comment)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    inner class ItemCommentViewHolder(private val binding: ItemCommentBinding)
        :RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SetTextI18n")
            fun bind(){
                val data = itemSet[absoluteAdapterPosition]

                binding.text.text = data.body
                binding.timeText.text = getTimeText(data.createTime)
                binding.likeNum.text = "좋아요 ${data.likeCount}개"

                //좋아요 검사
                if (data.userLike){
                    binding.onLike()
                }else{
                    binding.offLike()
                }

                //유저 프로필 보기
                binding.userImage.setOnClickListener {
                    //User image click
                    onItemClickListener?.userClick(data.userId)
                }

                // 좋아요 기능 추가
                binding.likeComment.setOnClickListener {
                    //add like
                    data.userLike = !data.userLike
                    if (data.userLike){
                        binding.onLike()
                        data.likeCount+=1
                    } else{
                        binding.offLike()
                        data.likeCount-=1
                    }
                    notifyItemChanged(absoluteAdapterPosition)
                    onItemClickListener?.likeClick(data.commentId,data.userLike)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCommentViewHolder {
        binding = ItemCommentBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ItemCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemCommentViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount()= itemSet.size

    private fun ItemCommentBinding.onLike() =
        likeComment.setImageResource(R.drawable.ic_baseline_favorite_24_red)
    private fun ItemCommentBinding.offLike() =
        likeComment.setImageResource(R.drawable.ic_baseline_favorite_24)
    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList : ArrayList<Comment>){
        itemSet = dataList
        notifyDataSetChanged()
    }

    fun addData(comment:Comment) :Int{
        itemSet.add(comment)
        notifyItemInserted(itemCount-1)
        return itemCount
    }

    fun delData(){

    }
}