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
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallFreeBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallJointBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

//프리 or 공구 or 통합
class ItemCommunitySmallAdapter(
    private val mainActivity: MainActivity,
    private val dataSet : ArrayList<CommunityContent>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var jointBinding: ItemCommunitySmallJointBinding
    private lateinit var freeBinding: ItemCommunitySmallFreeBinding
    val dataType = 0 //0 : total , 1: joint , 2: Free

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when (viewType) {
            //type 공구해요
            type_joint -> {
                jointBinding = ItemCommunitySmallJointBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent, false
                )
                JointViewHolder(jointBinding)
            }
            //type 프리토크
            else -> {
                freeBinding = ItemCommunitySmallFreeBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent, false
                )
                FreeViewHolder(freeBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (dataSet[position].body.type) {
            type_free -> {
                val viewHolder =(holder as FreeViewHolder)
                viewHolder.bind()
            }
            else -> {
                val viewHolder = (holder as JointViewHolder)
                viewHolder.bind()
            }
        }
    }

    override fun getItemCount() = dataSet.size

    inner class JointViewHolder(private val jointBinding: ItemCommunitySmallJointBinding) :
        RecyclerView.ViewHolder(jointBinding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind() {
            val data = dataSet[absoluteAdapterPosition]

            jointBinding.timeText.text = getTimeText(data.createTime)
            jointBinding.sujectText.text = data.body.subject
            jointBinding.contentText.text = data.body.text
            Glide.with(mainActivity)
                .load(data.body.mainImage)
                .into(jointBinding.mainImage)

        }
    }

    inner class FreeViewHolder(private val freeBinding: ItemCommunitySmallFreeBinding) :
        RecyclerView.ViewHolder(freeBinding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind() {
            val data = dataSet[absoluteAdapterPosition]

            freeBinding.timeText.text = getTimeText(data.createTime)
            freeBinding.sujectText.text = data.body.subject
            freeBinding.contentText.text = data.body.text
            Glide.with(mainActivity)
                .load(data.body.mainImage)
                .into(freeBinding.mainImage)
        }
    }

    //Time Text 얻기
    @SuppressLint("SimpleDateFormat")
    private fun getTimeText(createTime: String): String {
        val uploadTimeStamp = createTime.split("-", "T", ":", ".")
        val timeData = uploadTimeStamp[0] + "-" + uploadTimeStamp[1] + "-" + uploadTimeStamp[2] +
                " " + uploadTimeStamp[3] + ":" + uploadTimeStamp[4] + ":" + uploadTimeStamp[5].chunked(2)[0]
        val timeString = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeData)?.time
        val today = Calendar.getInstance().time.time
        return if (timeString != null) {
            val calDate = (today - timeString)
            if ((calDate / (60 * 60 * 24 * 1000)) >= 1) { //1일 이상
                (calDate / (60 * 60 * 24 * 1000)).toInt().toString() + " 일 전"
            } else if ((calDate / (60 * 60 * 1000)) >= 1) { //1시간 이상
                (calDate / (60 * 60 * 1000)).toInt().toString() + " 시간 전"
            } else if ((calDate / (60 * 1000)) >= 1) { //분 단위
                (calDate / (60 * 1000)).toInt().toString() + " 분 전"
            } else {
                "몇초 전"
            }
        } else { "" }
    }

    fun setType() {

    }
}