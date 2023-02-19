package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Model.type_free
import com.puresoftware.bottomnavigationappbar.Weggler.Model.type_joint
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallFreeBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallJointBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//프리 or 공구 or 통합
class ItemCommunitySmallAdapterFree(
    private val mainActivity: MainActivity,
    private var dataSet : ArrayList<CommunityContent>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var freeBinding: ItemCommunitySmallFreeBinding

    private var onItemClickListener : OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(item:CommunityContent)
    }
    fun setOnItemClickListener(listener : OnItemClickListener){
        this.onItemClickListener =listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        freeBinding = ItemCommunitySmallFreeBinding.inflate(
            LayoutInflater.from(mainActivity),
            parent, false
        )
        return FreeViewHolder(freeBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FreeViewHolder).bind()
    }

    override fun getItemCount() = dataSet.size


    inner class FreeViewHolder(private val freeBinding: ItemCommunitySmallFreeBinding) :
        RecyclerView.ViewHolder(freeBinding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind() {
            val data = dataSet[absoluteAdapterPosition]
            if(data.body.type== type_free) {
                freeBinding.timeText.text = getTimeText(data.createTime)
                freeBinding.sujectText.text = data.body.subject
                freeBinding.contentText.text = data.body.text
                Glide.with(mainActivity)
                    .load(data.thumbnail)
                    .into(freeBinding.mainImage)

                freeBinding.likeNum.text = data.likeCount.toString()
                freeBinding.commentNum.text = data.commentCount.toString()


                //클릭 이벤트
                freeBinding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }
            }else{
                freeBinding.root.layoutParams.height=0
            }
        }
    }


}