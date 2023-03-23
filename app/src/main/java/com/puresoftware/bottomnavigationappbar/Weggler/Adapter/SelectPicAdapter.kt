package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Unit.getVideoTime
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.ItemPictureBinding

//사진 선택
class SelectPicAdapter(
    private val mainActivity: MainActivity,
    private val itemList:List<Uri>,
) : RecyclerView.Adapter<SelectPicAdapter.SelectPicViewHolder>() {

    private lateinit var binding: ItemPictureBinding
    private var onItemClickListener :OnItemClickListener?=null
    private var selectedPicNum = -1
    interface OnItemClickListener{
        fun onItemClick(imageUri: Uri?)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    inner class SelectPicViewHolder(private val binding: ItemPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
            fun bind(){
                val item = itemList[absoluteAdapterPosition]
                if (item.toString().contains("video")){
                    binding.videoTime.apply {
                        visibility = View.VISIBLE
                        text = "00:${getVideoTime(mainActivity,item)}"
                    }
                }else{
                    binding.videoTime.visibility = View.GONE
                }
                binding.checkImage.layoutParams = binding.root.layoutParams //크기 변환

                Glide.with(mainActivity)
                    .load(item)
                    .into(binding.checkImage)
                if (selectedPicNum==absoluteAdapterPosition){
                    binding.setCheck()
                }else{
                    binding.setUnCheck()
                }
                binding.root.setOnClickListener {
                    //재 클릭 시 선택 종료
                    if (absoluteAdapterPosition==selectedPicNum){
                        onItemClickListener?.onItemClick(null) //선택 uri 전달
                        selectedPicNum = -1
                    //이미지 클릭 시 uri 전달
                    }else{
                        onItemClickListener?.onItemClick(item) //선택 uri 전달
                        selectedPicNum = absoluteAdapterPosition
                    }
                    notifyDataSetChanged()
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPicViewHolder {
        binding = ItemPictureBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return SelectPicViewHolder(binding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: SelectPicViewHolder, position: Int) {
        holder.bind()
    }

    private fun ItemPictureBinding.setCheck() =
        checkBox.setBackgroundResource(R.drawable.baseline_check_circle_24)

    private fun ItemPictureBinding.setUnCheck() =
        checkBox.setBackgroundResource(R.drawable.circle)

}