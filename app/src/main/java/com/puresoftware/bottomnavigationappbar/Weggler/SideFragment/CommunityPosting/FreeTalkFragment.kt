package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapterFree
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapterTotal
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFreeTalkBinding

//프리 토크

class FreeTalkFragment(
    private val selectPosition: String,

    ) : Fragment() {
    private var _binding : FragmentFreeTalkBinding? = null
    private val binding get()=_binding!!
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding=FragmentFreeTalkBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.communityViewModel.apply {
            // 메인 포스팅
            if (selectPosition == "Main Posting") {
                if (this.communityLiveData.value != null) {
                    val data  =
                        if(communityLiveData.value==null) arrayListOf() else  communityLiveData.value!!
                    val adapter =
                        ItemCommunitySmallAdapterFree(mainActivity, data)
                    binding.totalRecycler.adapter = adapter
                }

            // 인기 게시물
            } else if (selectPosition == "Popular Posting") {
                if (this.popularPostingLiveData.value!=null){
                    val data  =
                        if(popularPostingLiveData.value==null) arrayListOf() else  popularPostingLiveData.value!!
                    val adapter =
                        ItemCommunitySmallAdapterFree(mainActivity,data)
                    binding.totalRecycler.adapter = adapter
                }

            // 내 게시물
            }else if (selectPosition == "My Posting"){
                if (this.myPostingLiveData.value != null) {
                    val data  =
                        if(myPostingLiveData.value==null) arrayListOf() else  myPostingLiveData.value!!
                    val adapter =
                        ItemCommunitySmallAdapterFree(mainActivity, data)
                    binding.totalRecycler.adapter = adapter
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}