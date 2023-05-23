package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.SwipeCardView.SwipeController
import com.puresoftware.bottomnavigationappbar.Weggler.SwipeCardView.SwipeControllerActions
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.addChildComment

import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.makeChildComment
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommentBinding
import kotlin.math.roundToInt

class ItemCommentAdapter(
    private val mainActivity: MainActivity,
    itemList: ArrayList<Comment>
) : RecyclerView.Adapter<ItemCommentAdapter.ItemCommentViewHolder>() {
    private lateinit var binding: ItemCommentBinding
    var itemSet = makeChildComment(itemList)

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun userClick(userInfo: UserInfo)
        fun likeClick(commentId: Int, like: Boolean)

        //답글
        fun addSubComment(comment: Comment)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ItemCommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind() {
            val data = itemSet[absoluteAdapterPosition]
            val dataParent = data.parentCommentId?:-1
            if (dataParent != -1) { //자식 뷰
                val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParams.setMargins(100,0,0,0)
                binding.itemLinear.layoutParams = layoutParams
                binding.reComment.visibility = View.GONE
            }
            binding.text.text = data.body
            binding.timeText.text = getTimeText(data.createTime)
            binding.likeNum.text = "좋아요 ${data.likeCount}개"

            //좋아요 검사
            if (data.userLike) {
                binding.onLike()
            } else {
                binding.offLike()
            }

            data.userInfo?.let { user ->
                binding.userName.text = user.id
                if (user.profileFile != null) {
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
                if (data.userLike) {
                    binding.onLike()
                    data.likeCount += 1
                } else {
                    binding.offLike()
                    data.likeCount -= 1
                }
                notifyItemChanged(absoluteAdapterPosition)
                onItemClickListener?.likeClick(data.commentId, data.userLike)
            }

            binding.reComment.setOnClickListener {
                onItemClickListener?.addSubComment(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCommentViewHolder {
        binding = ItemCommentBinding.inflate(LayoutInflater.from(mainActivity), parent, false)
        return ItemCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemCommentViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = itemSet.size

    private fun ItemCommentBinding.onLike() =
        likeComment.setImageResource(R.drawable.ic_baseline_favorite_24_red)

    private fun ItemCommentBinding.offLike() =
        likeComment.setImageResource(R.drawable.ic_baseline_favorite_24)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: ArrayList<Comment>) {
        itemSet = makeChildComment(dataList)
        notifyDataSetChanged()
    }

    fun addData(comment: Comment): Int {
        itemSet.add(comment)
        notifyItemInserted(itemCount - 1)
        notifyItemRangeChanged(0, itemCount)
        return itemCount
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addChildData(comment: Comment): Int {
        itemSet = addChildComment(itemSet,comment)
        notifyDataSetChanged()
        notifyItemRangeChanged(0, itemCount)
        return itemCount
    }

    fun delData(position: Int, userId: String?) : Int? {
        // 내가 쓴 댓글만 삭제 가능
        if (itemSet[position].userInfo?.id == userId && userId != null) {
            val delId = itemSet[position].commentId
            itemSet.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            return delId
        }
        return null
    }

}