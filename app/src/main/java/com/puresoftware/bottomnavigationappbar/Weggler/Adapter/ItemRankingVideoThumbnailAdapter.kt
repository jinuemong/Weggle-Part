package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.databinding.ItemRankingVideoThumbnailBinding
import org.mozilla.javascript.ast.ReturnStatement

// 리뷰 데이터의 썸네일

class ItemRankingVideoThumbnailAdapter(
    private val mainActivity: MainActivity,
    private val dataList : ArrayList<ReviewData>,

):RecyclerView.Adapter<ItemRankingVideoThumbnailAdapter.ThumbnailViewHolder>() {
    private lateinit var binding: ItemRankingVideoThumbnailBinding
    private var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun itemClick(review : ReviewData)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    inner class ThumbnailViewHolder(private val binding: ItemRankingVideoThumbnailBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val item = dataList[absoluteAdapterPosition]

                try {
                    Glide.with(mainActivity)
                        .load(item.thumbnail)
                        .into(object : CustomTarget<Drawable>() {
                            override fun onResourceReady(
                                resource: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                val layout = binding.root
                                layout.background = resource
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {}
                        })
                }catch (e:Exception){
                    binding.root.visibility = View.GONE
                }
                binding.root.setOnClickListener {
                    onItemClickListener?.itemClick(item)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        binding = ItemRankingVideoThumbnailBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ThumbnailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = dataList.size
}