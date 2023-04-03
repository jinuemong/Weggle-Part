package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.print.PrintAttributes.Margins
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.GridLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Unit.tagList
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.ItemPictureBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemTagBinding

class KeywordAdapter(
    private val activity:Activity,
    dataList : ArrayList<String>,
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

    inner class ViewHolder(val binding: ItemTagBinding)
        :RecyclerView.ViewHolder(binding.root){
            @RequiresApi(Build.VERSION_CODES.M)
            fun bind(){

                binding.root.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

                val item = dataSet[absoluteAdapterPosition]
                binding.tagText.text = item

                //이미지 색상 선택
                if (selectTagList.contains(item)){
                    binding.setCheck()
                }else{
                    binding.unCheck()
                }

                if (type!="userProfile") {
                    binding.root.setOnClickListener {
                        val index = selectTagList.indexOf(item)
                        if (index!=-1) {
                            selectTagList.removeAt(index)
                            binding.unCheck()
                        } else {
                            selectTagList.add(item)
                            binding.setCheck()
                        }
                    }
                }
            }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordAdapter.ViewHolder {
        binding = ItemTagBinding.inflate(LayoutInflater.from(activity),parent,false)

        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int  = dataSet.size

    fun getSelectedList() : ArrayList<String>{
        return this.selectTagList
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun ItemTagBinding.setCheck() {
        tagText.setTextAppearance(R.style.selectText)
        tagText.setBackgroundResource(R.drawable.round_border_2)
        tagText.setPadding(15)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun ItemTagBinding.unCheck() {
        tagText.setTextAppearance(R.style.unSelectText)
        tagText.setBackgroundResource(R.drawable.round_border)
        tagText.setPadding(15)
    }
}