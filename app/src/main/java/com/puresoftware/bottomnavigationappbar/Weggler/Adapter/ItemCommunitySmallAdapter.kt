package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Model.type_free
import com.puresoftware.bottomnavigationappbar.Weggler.Model.type_joint
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallFreeBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemCommunitySmallJointBinding

//프리 or 공구 or 통합
class ItemCommunitySmallAdapter(
    private val mainActivity: MainActivity,
) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var jointBinding:ItemCommunitySmallJointBinding
    private lateinit var freeBinding:ItemCommunitySmallFreeBinding

    var dataSet = mutableListOf<MultiCommunityData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View?
        return when(viewType){
            type_joint->{
                jointBinding =ItemCommunitySmallJointBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent,false
                )
                JointViewHolder(jointBinding)
            }
            //type_free
            else->{
                freeBinding =ItemCommunitySmallFreeBinding.inflate(
                    LayoutInflater.from(mainActivity),
                    parent,false
                )
                FreeViewHolder(freeBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(dataSet[position].type){
            type_free->{
                (holder as FreeViewHolder).bind()
            }else->{
            (holder as JointViewHolder).bind()
            }
        }
    }

    override fun getItemCount()= dataSet.size

    inner class JointViewHolder(private val jointBinding:ItemCommunitySmallJointBinding)
        :RecyclerView.ViewHolder(jointBinding.root){
            fun bind(){

            }
    }
    inner class FreeViewHolder(private val freeBinding:ItemCommunitySmallFreeBinding)
        :RecyclerView.ViewHolder(freeBinding.root){
            fun bind(){

            }
    }
}