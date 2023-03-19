package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemSearchTextBinding


// 프로덕트 검색 시 텍스트 리스트

class ProductSearchTextAdapter(
    private val activity: Activity,
) : RecyclerView.Adapter<ProductSearchTextAdapter.ViewHolder>() {
    private lateinit var binding: ItemSearchTextBinding
    var dataSet = ArrayList<Product>()
    var changeText = "changeText"
    private var onClickListener: OnItemClickListener? = null //리스너

    interface OnItemClickListener { //클릭 동작
        fun onItemClick(item: Product)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) { //외부 동작 함수
        this.onClickListener = listener
    }

    inner class ViewHolder(val binding: ItemSearchTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind() {
            val item = dataSet[absoluteAdapterPosition]

            if (item.name == "communityList") {
                binding.root.layoutParams.height = 0
            } else {

                //검색 글자 변환
                val spannableString = SpannableString(item.name)
                val startIndex = spannableString.indexOf(changeText)

                if (startIndex >= 0) {
                    spannableString.setSpan(
                        ForegroundColorSpan(Color.parseColor("#E60FAB")),
                        startIndex, (startIndex + changeText.length),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    spannableString.setSpan(
                        StyleSpan(Typeface.BOLD),
                        startIndex, (startIndex + changeText.length),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.name.text = spannableString
                }

                binding.root.setOnClickListener {
                    if (onClickListener != null) {
                        onClickListener?.onItemClick(item)
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSearchTextBinding.inflate(LayoutInflater.from(activity), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<Product>, text: String) {
        dataSet = data
        changeText = text
        notifyDataSetChanged()
    }

}