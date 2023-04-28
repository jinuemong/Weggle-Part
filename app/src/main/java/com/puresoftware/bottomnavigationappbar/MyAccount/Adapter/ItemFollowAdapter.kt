package com.puresoftware.bottomnavigationappbar.MyAccount.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.FollowData
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import com.puresoftware.bottomnavigationappbar.databinding.ItemFollowUserDataBinding

class ItemFollowAdapter(
    private val mainActivity: MainActivity,
    private var itemList : ArrayList<FollowData>
):RecyclerView.Adapter<ItemFollowAdapter.ViewHolder>(){
    private lateinit var binding : ItemFollowUserDataBinding

    private var onItemClickListener : OnItemClickListener? = null
    interface OnItemClickListener{
        fun clickUser(userInfo: UserInfo)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
    inner class ViewHolder(binding: ItemFollowUserDataBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(){
            val item = itemList[absoluteAdapterPosition]
            item.userInfo?.let { user->
                binding.userName.text = user.id
                Glide.with(mainActivity)
                    .load(user.profileFile)
                    .into(binding.userImage)

                binding.root.setOnClickListener {
                    onItemClickListener?.clickUser(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFollowUserDataBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemSet:ArrayList<FollowData>){
        itemList = itemSet
        notifyDataSetChanged()
    }
}