package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.databinding.ItemRankingVideoThumbnailBinding
import org.mozilla.javascript.ast.ReturnStatement

class ItemRankingVideoThumbnailAdapter(
    private val mainActivity: MainActivity,
    private val dataList : ArrayList<String>,

):RecyclerView.Adapter<ItemRankingVideoThumbnailAdapter.ThumbnailViewHolder>() {
    private lateinit var binding: ItemRankingVideoThumbnailBinding

    inner class ThumbnailViewHolder(private val binding: ItemRankingVideoThumbnailBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val image  = dataList[absoluteAdapterPosition]
                Glide.with(mainActivity)
                    .load(image)
                    .into(binding.thumbnailImage)
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