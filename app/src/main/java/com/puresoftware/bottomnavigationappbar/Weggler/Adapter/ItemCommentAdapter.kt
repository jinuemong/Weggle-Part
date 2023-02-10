package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityPostManager
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommentBinding

class ItemCommentAdapter (
    private val mainActivity: MainActivity,
    private val communityPostManager: CommunityPostManager,
    itemList : ArrayList<Comment>
): RecyclerView.Adapter<ItemCommentAdapter.ItemCommentViewHolder>(){
    private lateinit var binding: ItemCommentBinding
    var itemSet = itemList
    inner class ItemCommentViewHolder(private val binding: ItemCommentBinding)
        :RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SetTextI18n")
            fun bind(){
                val data = itemSet[absoluteAdapterPosition]

                binding.text.text = data.body
                binding.timeText.text = getTimeText(data.createTime)
                binding.likeNum.text = "좋아요 ${data.likeCount}개"

                //유저 정보 갱신해야 함
                binding.userImage.setOnClickListener {
                    //User image click
                }
                binding.likeComment.setOnClickListener {
                    //add like
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

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList : ArrayList<Comment>){
        itemSet = dataList
        notifyDataSetChanged()
    }

    fun addData(comment:Comment) :String{
        Log.d("cccc",comment.body.toString())
        itemSet.add(comment)
        notifyItemInserted(itemCount-1)
        return itemCount.toString()
    }

    fun delData(){

    }
}