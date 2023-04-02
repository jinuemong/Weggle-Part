package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Unit.tagList
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.ItemPictureBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemTagBinding

class KeywordAdapter(
    private val activity:Activity,
    dataList : List<String>,
    private val type : String
):RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {
    private lateinit var binding:ItemTagBinding
    var dataSet = tagList // 총 태그 리스트
    var selectTagList = dataList //내가 선택한 리스트

    init {
        if (type=="userProfile"){
            dataSet = selectTagList
        }
    }

    private var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun onSelected(item:String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    inner class ViewHolder(val binding: ItemTagBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(){
                val item = dataSet[absoluteAdapterPosition]
                binding.tagText.text = item

                if (type!="userProfile") {
                    binding.root.setOnClickListener {
                        if (selectTagList.contains(item)) {
                            selectTagList.plus(item)
                            binding.setCheck()
                        } else {
                            selectTagList.minus(item)
                            binding.unCheck()
                        }
                    }
                }
            }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordAdapter.ViewHolder {
        binding = ItemTagBinding.inflate(LayoutInflater.from(activity),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int  = dataSet.size

    @SuppressLint("ResourceAsColor")
    private fun ItemTagBinding.setCheck() {
        tagText.setBackgroundColor(R.color.my_selected_back)
        tagText.setTextColor(R.color.my_selected_text)
    }
    @SuppressLint("ResourceAsColor")
    private fun ItemTagBinding.unCheck() {
        tagText.setBackgroundColor(R.color.white)
        tagText.setTextColor(R.color.line_color)
    }
}