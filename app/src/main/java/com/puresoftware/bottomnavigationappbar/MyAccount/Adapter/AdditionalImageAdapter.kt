package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.AddReviewActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeImageBinding
import okhttp3.internal.notifyAll

class AdditionalImageAdapter(
    val activity: Activity,
    val type : String,
) : RecyclerView.Adapter<AdditionalImageAdapter.ViewHolder>() {
    private val addViewModel = (activity as AddReviewActivity).addReviewModel
    var selectData =addViewModel.selectProductData.value!!

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun delData(item: Product)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    lateinit var binding: ItemMiniProductTypeImageBinding

    inner class ViewHolder(binding: ItemMiniProductTypeImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = selectData[absoluteAdapterPosition]
            Glide.with(activity)
                .load(item.subjectFiles[0])
                .into(binding.productImage)

            // addView type인 경우만 데이터 삭제 가능
            if (type=="addView") {
                binding.delImage.setOnClickListener {
                    onItemClickListener?.delData(item)
                }
            }else {
                binding.delImage.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeImageBinding.inflate(
            LayoutInflater.from(activity), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = selectData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

}