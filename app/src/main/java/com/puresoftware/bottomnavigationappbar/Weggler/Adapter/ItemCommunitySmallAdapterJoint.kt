package com.puresoftware.bottomnavigationappbar.Weggler.Adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.Model.type_joint
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallJointBinding
import java.util.*
import kotlin.collections.ArrayList

//공구
class ItemCommunitySmallAdapterJoint(
    private val mainActivity: MainActivity,
    dataSet: ArrayList<ReviewInCommunity>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var jointBinding: ItemCommunitySmallJointBinding
    private var dataList = dataSet
    private var onItemClickListener : OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(item:ReviewInCommunity)
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

    override fun getItemCount() = dataList.size

    inner class JointViewHolder(private val jointBinding: ItemCommunitySmallJointBinding) :
        RecyclerView.ViewHolder(jointBinding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind() {
            val data = dataList[absoluteAdapterPosition]
            if (data.body.type== type_joint) {
                jointBinding.timeText.text = getTimeText(data.createTime)
                jointBinding.sujectText.text = data.body.subject
                jointBinding.contentText.text = data.body.text
                Glide.with(mainActivity)
                    .load(data.thumbnail)
                    .into(jointBinding.mainImage)

                jointBinding.likeNum.text = data.likeCount.toString()
                jointBinding.commendNum.text = data.commentCount.toString()

                //클릭 이벤트
                jointBinding.root.setOnClickListener {
                    onItemClickListener?.onItemClick(data)
                }
            }else{
                jointBinding.root.layoutParams.height=0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data :ArrayList<ReviewInCommunity>){
        dataList = data
        notifyDataSetChanged()
    }
}