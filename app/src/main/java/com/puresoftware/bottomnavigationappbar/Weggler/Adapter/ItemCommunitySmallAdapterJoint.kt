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
class ItemCommunitySmallAdapterJoint(
    private val mainActivity: MainActivity,
    private var dataSet: ArrayList<CommunityContent>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var jointBinding: ItemCommunitySmallJointBinding

    private var onItemClickListener : OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(item:CommunityContent)
    }
    fun setOnItemClickListener(listener : OnItemClickListener){
        this.onItemClickListener =listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        jointBinding = ItemCommunitySmallJointBinding.inflate(
            LayoutInflater.from(mainActivity),
            parent, false
        )
        return JointViewHolder(jointBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as JointViewHolder).bind()
    }

    override fun getItemCount() = dataSet.size

    inner class JointViewHolder(private val jointBinding: ItemCommunitySmallJointBinding) :
        RecyclerView.ViewHolder(jointBinding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind() {
            val data = dataSet[absoluteAdapterPosition]
            if (data.body.type== type_joint) {
                jointBinding.timeText.text = getTimeText(data.createTime)
                jointBinding.sujectText.text = data.body.subject
                jointBinding.contentText.text = data.body.text
                Glide.with(mainActivity)
                    .load(data.thumbnail)
                    .into(jointBinding.mainImage)

                jointBinding.likeNum.text = data.likeCount.toString()


                //클릭 이벤트
                jointBinding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }
            }else{
                jointBinding.root.layoutParams.height=0
            }
        }
    }

}