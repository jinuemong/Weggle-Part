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
    dataList: ArrayList<CommunityContent>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null

    var dataSet = dataList

    interface OnItemClickListener {
        fun onItemClick(item: CommunityContent)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // viewType은 getItemViewType의 데이터가 자동으로 들어감
        return when (viewType) {

            //type 공구해요
            type_joint -> {
                val binding = ItemCommunitySmallJointBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent, false
                )
                JointViewHolder(binding)
            }
            //type 프리토크
            type_free -> {
                val binding = ItemCommunitySmallFreeBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent, false
                )
                FreeViewHolder(binding)
            }
            //잘 못 들어온 타입
            else -> {
                val binding = ItemCommunitySmallJointBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent, false
                )
                TrashViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = dataSet[position]

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

    //아이템 타입 리턴
    override fun getItemViewType(position: Int): Int {
        return dataSet[position].body.type
    }
    // View Init
    inner class JointViewHolder(private val binding: ItemCommunitySmallJointBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommunityContent) {
            binding.apply {
                timeText.text = getTimeText(data.createTime)
                sujectText.text = data.body.subject
                contentText.text = data.body.text
                Glide.with(mainActivity)
                    .load(data.thumbnail)
                    .into(mainImage)
                likeNum.text = data.likeCount.toString()
                commendNum.text = data.commentCount.toString()
                //클릭 이벤트
                root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }
            }
        }
    }

    inner class FreeViewHolder(private val binding: ItemCommunitySmallFreeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CommunityContent) {
            binding.apply {
                timeText.text = getTimeText(data.createTime)
                sujectText.text = data.body.subject
                contentText.text = data.body.text
                Glide.with(mainActivity)
                    .load(data.thumbnail)
                    .into(mainImage)
                likeNum.text = data.likeCount.toString()
                commentNum.text = data.commentCount.toString()

                //클릭 이벤트
                root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }


            }
        }
    }

    inner class TrashViewHolder(private val trashBinding: ItemCommunitySmallJointBinding) :
        RecyclerView.ViewHolder(trashBinding.root) {
        fun bind() {
            trashBinding.root.layoutParams.height = 0
            trashBinding.root.visibility = View.GONE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: ArrayList<CommunityContent>) {
        dataSet = dataList
        notifyDataSetChanged()
    }


}