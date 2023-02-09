package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapterJoint
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapterTotal
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.databinding.FragmentJointPurchaseBinding

//공구해요

class JointPurchaseFragment(
    private val selectPosition: String,

    ) : Fragment() {
    private var _binding : FragmentJointPurchaseBinding? = null
    private val binding get()=_binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var fm: FragmentManager

    var data : ArrayList<CommunityContent> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.supportFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentJointPurchaseBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.communityViewModel.apply {
            // 메인 포스팅
            if (selectPosition == "Main Posting") {
                if (this.communityLiveData.value != null) {
                    data  = if(communityLiveData.value==null) arrayListOf() else  communityLiveData.value!!
                }

                // 인기 게시물
            } else if (selectPosition == "Popular Posting") {
                if (this.popularPostingLiveData.value!=null){
                    data  = if(popularPostingLiveData.value==null) arrayListOf() else  popularPostingLiveData.value!!
                }

                // 내 게시물
            }else if (selectPosition == "My Posting"){
                if (this.myPostingLiveData.value != null) {
                    data  = if(myPostingLiveData.value!=null) arrayListOf() else  myPostingLiveData.value!!
                }
            }
        }

        //어댑터 적용 + 클릭 이벤트
        val adapter = ItemCommunitySmallAdapterJoint(mainActivity, data)
        binding.totalRecycler.adapter = adapter.apply {
            setOnItemClickListener(object : ItemCommunitySmallAdapterJoint.OnItemClickListener{
                override fun onItemClick(item: CommunityContent) {
                    mainActivity.setMainViewVisibility(false)
                    mainActivity.changeFragment(DetailCommunityPostingFragment("sub",item))
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}