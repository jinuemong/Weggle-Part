package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Posting
import com.puresoftware.bottomnavigationappbar.databinding.ItemFeedPostingBinding

class ItemFeedPostingAdapter(
    private val mainActivity: MainActivity,
    dataList : ArrayList<Posting>
): RecyclerView.Adapter<ItemFeedPostingAdapter.ItemFeedPostingViewHolder>(){
    private lateinit var binding : ItemFeedPostingBinding
    var dataSet = dataList
    inner class ItemFeedPostingViewHolder(private val binding : ItemFeedPostingBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val posting = dataSet[absoluteAdapterPosition]

            //뷰 세팅
            Glide.with(mainActivity)
                .load(posting.profile.userImage)
                .into(binding.userImage)
            binding.userName.text = posting.profile.username
            binding.createTime.text = posting.createTime //변환 필요

            binding.videoView.setVideoURI(Uri.parse(posting.videoUrl))

            binding.likeNum.text = posting.totalLike.toString()
            binding.commentNum.text = posting.totalComment.toString()
            binding.bodyText.text = posting.body

            //클릭 리스너

            //댓글 클릭
            binding.commentImage.setOnClickListener {

            }
            binding.commentNum.setOnClickListener {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFeedPostingViewHolder {
        binding = ItemFeedPostingBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ItemFeedPostingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemFeedPostingViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = dataSet.size
}