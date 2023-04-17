package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeDetailBinding
import java.text.DecimalFormat

//공동 구매 검색
class ItemProductDetailAdapter(
    private val mainActivity: MainActivity,
    private val type : String,
) : RecyclerView.Adapter<ItemProductDetailAdapter.ViewHolder>(){
    private var itemList = ArrayList<Product>()
    private lateinit var binding : ItemMiniProductTypeDetailBinding
    private var changeText ="text"
    private var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun click(id: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    inner class ViewHolder(val binding: ItemMiniProductTypeDetailBinding)
        :RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SetTextI18n")
            fun bind(){
                val item = itemList[absoluteAdapterPosition]

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
                }else{
                    binding.name.text = item.name
                }

                if (type=="no category"){binding.category.visibility = View.GONE}
                Glide.with(mainActivity)
                    .load(item.subjectFiles[0])
                    .into(binding.image)
                binding.company.text = item.body.company
                binding.salePer.text = "${item.body.discount}%"
                val decimal = DecimalFormat("#,###")
                binding.salePrice.text = "${decimal.format(item.body.price)}원"

                binding.root.setOnClickListener {
                    onItemClickListener?.click(item.productId)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeDetailBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchData(data : ArrayList<Product>, typeText:String){
        changeText = typeText
        itemList = data
        notifyDataSetChanged()
    }

}