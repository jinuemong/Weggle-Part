package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemChildCommentBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommentBinding

class ItemCommentChildAdapter (
    private val mainActivity: MainActivity,
    var itemList : List<Comment>,
) : RecyclerView.Adapter<ItemCommentChildAdapter.ViewHolder>(){
    private lateinit var binding: ItemChildCommentBinding
    private var onItemClickListener : OnItemClickListener?= null
    interface OnItemClickListener{
        fun userClick(userInfo : UserInfo)
        fun likeClick(commentId : Int, like:Boolean)

    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    inner class ViewHolder(binding: ItemChildCommentBinding)
        : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun hold(){
            val data = itemList[absoluteAdapterPosition]

            binding.text.text = data.body
            binding.timeText.text = getTimeText(data.createTime)
            binding.likeNum.text = "좋아요 ${data.likeCount}개"

            //좋아요 검사
            if (data.userLike){
                binding.onLike()
            }else{
                binding.offLike()
            }

            data.userInfo?.let{user->
                binding.userName.text = user.id
                if (user.profileFile!=null) {
                    Glide.with(mainActivity)
                        .load(user.profileFile)
                        .into(binding.userImage)
                }
                //유저 프로필 보기
                binding.userImage.setOnClickListener {
                    //User image click
                    onItemClickListener?.userClick(user)
                }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemChildCommentBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int =itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hold()
    }
    private fun ItemChildCommentBinding.onLike() =
        likeComment.setImageResource(R.drawable.ic_baseline_favorite_24_red)
    private fun ItemChildCommentBinding.offLike() =
        likeComment.setImageResource(R.drawable.ic_baseline_favorite_24)
}