package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.databinding.ItemFeedThumnailBinding

class MyFeedReviewAdapter (
    private val mainActivity: MainActivity,
    dataList : ArrayList<ReviewData>
,):RecyclerView.Adapter<MyFeedReviewAdapter.ViewHolder>(){
    private lateinit var binding: ItemFeedThumnailBinding
    private var onItemClickListener : OnItemClickListener? = null

    var dataSet = dataList

    interface OnItemClickListener{
        fun itemClick(review : ReviewData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    inner class ViewHolder(val binding:ItemFeedThumnailBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val item = dataSet[absoluteAdapterPosition]

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

                binding.root.setOnClickListener {
                    onItemClickListener?.itemClick(item)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFeedThumnailBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int  = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }
}