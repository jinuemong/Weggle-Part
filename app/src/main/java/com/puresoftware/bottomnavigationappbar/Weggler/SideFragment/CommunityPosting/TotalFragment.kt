package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapterTotal
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment.ShellFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentTotalBinding


class TotalFragment(
    private val selectPosition: String,
) : Fragment() {
    private var _binding: FragmentTotalBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var fm: FragmentManager

    var data :ArrayList<CommunityContent> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.supportFragmentManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //어댑터 적용, 클릭 이벤트 구현
        val adapter = ItemCommunitySmallAdapterTotal(mainActivity, data)
        binding.totalRecycler.adapter = adapter.apply {
            setOnItemClickListener(object : ItemCommunitySmallAdapterTotal.OnItemClickListener{
                override fun onItemClick(item: CommunityContent) {
                    mainActivity.setMainViewVisibility(false)
                    if (selectPosition =="Main Posting"){
                        mainActivity.changeFragment(DetailCommunityPostingFragment("main",item))
                    }else {
                        mainActivity.changeFragment(DetailCommunityPostingFragment("sub",item))
                    }
                }

            })
        }

        // 게시물 데이터 설정
        mainActivity.communityViewModel.apply {
            // 메인 포스팅
            if (selectPosition == "Main Posting") {
                data  = if(communityLiveData.value==null) arrayListOf() else  communityLiveData.value!!
                communityLiveData.observe(mainActivity, Observer {
                    adapter.setData(it)
                })

            // 인기 게시물
            } else if (selectPosition == "Popular Posting") {
                data  = if(popularPostingLiveData.value==null) arrayListOf() else  popularPostingLiveData.value!!
                communityLiveData.observe(mainActivity, Observer {
                    adapter.setData(it)
                })

            // 내 게시물
            } else if (selectPosition == "My Posting"){
                data  = if(myPostingLiveData.value==null) arrayListOf() else  myPostingLiveData.value!!
                communityLiveData.observe(mainActivity, Observer {
                    adapter.setData(it)
                })
            }
        }

    }

}