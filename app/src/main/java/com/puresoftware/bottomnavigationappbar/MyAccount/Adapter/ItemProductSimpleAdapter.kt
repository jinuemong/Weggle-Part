package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeSimpleBinding
import java.text.DecimalFormat

class ItemProductSimpleAdapter(
    private val activity: Activity,
) : RecyclerView.Adapter<ItemProductSimpleAdapter.ViewHolder>(){
    private var itemList = ArrayList<Product>()
    private lateinit var binding : ItemMiniProductTypeSimpleBinding

    private var onItemClickListener : OnItemClickListener?= null
    interface OnItemClickListener{
        fun itemClick(id : Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }

    inner class ViewHolder(val binding: ItemMiniProductTypeSimpleBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val item = itemList[absoluteAdapterPosition]
                //가격 변환 (컴마찍기) 등록
                val decimal = DecimalFormat("#,###")
                binding.priceText.text = decimal.format(item.body.price)
                binding.nameText.text = item.name
                Glide.with(activity)
                    .load(item.subjectFiles[0])
                    .into(binding.image)

                binding.root.setOnClickListener {
                    if (onItemClickListener!=null){
                        onItemClickListener?.itemClick(item.productId)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMiniProductTypeSimpleBinding.inflate(LayoutInflater.from(activity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : ArrayList<Product>){
        itemList = data
        notifyDataSetChanged()
    }
}