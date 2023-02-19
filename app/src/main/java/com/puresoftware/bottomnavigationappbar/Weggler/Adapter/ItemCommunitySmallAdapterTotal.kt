package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.*
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallFreeBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallJointBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//프리 or 공구 or 통합
class ItemCommunitySmallAdapterTotal(
    private val mainActivity: MainActivity,
    dataList : ArrayList<CommunityContent>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var jointBinding: ItemCommunitySmallJointBinding
    private lateinit var freeBinding: ItemCommunitySmallFreeBinding
    private lateinit var trashBinding : ItemCommunitySmallJointBinding
    private var onItemClickListener : OnItemClickListener? = null

    var dataSet = dataList

    interface OnItemClickListener{
        fun onItemClick(item:CommunityContent)
    }
    fun setOnItemClickListener(listener : OnItemClickListener){
        this.onItemClickListener =listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            //type 공구해요
            type_joint -> {
                jointBinding = ItemCommunitySmallJointBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent, false
                )
                JointViewHolder(jointBinding)
            }
            type_free->{
                freeBinding = ItemCommunitySmallFreeBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent, false
                )
                FreeViewHolder(freeBinding)
            }
            //type 프리토크
            else ->{
                trashBinding = ItemCommunitySmallJointBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent,false
                )
                TrashViewHolder(trashBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = dataSet[position]

        Log.d(
            "lsjowgejgoiwejglew 1",
            currentItem.body.type.toString() + ": " + currentItem.body.text
        )
        when (currentItem.body.type) {
            type_trash -> {
                (holder as TrashViewHolder).bind()
            }
            type_joint -> {
                (holder as JointViewHolder).bind(currentItem)
            }
            type_free -> {
                (holder as FreeViewHolder).bind(currentItem)
            }
        }

    }

    override fun getItemCount() = dataSet.size

    // View Init
    inner class JointViewHolder(private val jointBinding: ItemCommunitySmallJointBinding) :
        RecyclerView.ViewHolder(jointBinding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(data:CommunityContent) {
            jointBinding.apply {
                timeText.text = getTimeText(data.createTime)
                sujectText.text = data.body.subject
                contentText.text = data.body.text
                Glide.with(mainActivity)
                    .load(data.thumbnail)
                    .into(mainImage)
                likeNum.text = data.likeCount.toString()
                //클릭 이벤트
                root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }
            }
        }
    }

    inner class FreeViewHolder(private val freeBinding: ItemCommunitySmallFreeBinding) :
        RecyclerView.ViewHolder(freeBinding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(data:CommunityContent) {
            freeBinding.apply {
                timeText.text = getTimeText(data.createTime)
                sujectText.text = data.body.subject
                contentText.text = data.body.text
                Glide.with(mainActivity)
                    .load(data.thumbnail)
                    .into(mainImage)
                likeNum.text = data.likeCount.toString()

                //클릭 이벤트
                root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }
            }
        }
    }

    inner class TrashViewHolder(private val trashBinding : ItemCommunitySmallJointBinding) :
        RecyclerView.ViewHolder(trashBinding.root) {
        fun bind(){
            trashBinding.root.layoutParams.height= 0
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList : ArrayList<CommunityContent>){
        dataSet = dataList
        notifyDataSetChanged()
    }



}