package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.type_joint
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniCommunityPopularBinding

class ItemPopularPostingTabAdapter (
    itemList : List<CommunityContent>,
    private val mainActivity: MainActivity,
): RecyclerView.Adapter<ItemPopularPostingTabAdapter.ItemMiniPopularViewHolder>() {

    private lateinit var binding: ItemMiniCommunityPopularBinding
    var itemSet = itemList

    private var onItemClickListener : OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(item:CommunityContent)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener =listener
    }
    inner class ItemMiniPopularViewHolder(private val binding: ItemMiniCommunityPopularBinding)
        :RecyclerView.ViewHolder(binding.root){
            @RequiresApi(Build.VERSION_CODES.M)
            fun bind(){
                val data = itemSet[absoluteAdapterPosition]

                binding.text.text = data.body.subject

                if (data.body.type== type_joint){
                    setType1()
                }else{
                    setType2()
                }

                //클릭 이벤트
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMiniPopularViewHolder {
        binding = ItemMiniCommunityPopularBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ItemMiniPopularViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemMiniPopularViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount()= itemSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemList : List<CommunityContent>){
        itemSet = itemList
        notifyDataSetChanged()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setType1() {
        binding.type1.visibility =View.VISIBLE
        binding.type2.visibility =View.GONE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setType2() {
        binding.type2.visibility =View.VISIBLE
        binding.type1.visibility =View.GONE

    }
}