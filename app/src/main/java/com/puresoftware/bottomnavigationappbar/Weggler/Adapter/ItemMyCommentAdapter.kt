package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.*
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemMyTabCommentBinding

class ItemMyCommentAdapter (
    private val mainActivity: MainActivity,
    dataSet : ArrayList<Comment>,
    private val type : Int,
):RecyclerView.Adapter<ItemMyCommentAdapter.ViewHolder>(){
    lateinit var binding : ItemMyTabCommentBinding
    private var dataList = dataSet

    private var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun itemClick(reviewId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    inner class ViewHolder(binding: ItemMyTabCommentBinding)
        :RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SetTextI18n")
            fun hold(){

                val item = dataList[absoluteAdapterPosition]
                if (type==item.reviewInfo.body.type || type== type_all) {
                    val review = item.reviewInfo.body
                    if (review.type == 1) {
                        binding.type2.visibility = View.GONE
                    } else {
                        binding.type1.visibility = View.GONE
                    }
                    binding.contentText.text =
                        "'${review.subject}' 게시글에 댓글을 남겼습니다."
//                Glide.with(mainActivity)
//                    .load(review.thumbnail)
//                    .into(binding.mainImage)


                    binding.timeText.text = getTimeText(item.createTime)
                    binding.comment.text = item.body

                    binding.root.setOnClickListener {
                        onItemClickListener?.itemClick(item.reviewInfo.id)
                    }
                }else{
                    binding.root.visibility = View.GONE
                    binding.root.layoutParams.height = 0
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMyTabCommentBinding.inflate(LayoutInflater.from(mainActivity)
            ,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hold()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData : ArrayList<Comment>){
        dataList = newData
        notifyDataSetChanged()
    }
}