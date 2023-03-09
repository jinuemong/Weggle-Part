package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentMyPostingTabBinding


//내가 쓴 글

class MyPostingTabFragment : Fragment() {
    private var _binding : FragmentMyPostingTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var fm : FragmentManager
    private lateinit var postingFrag : SelectDetailsTabFragment
    private lateinit var commendFrag : SelectDetailsTabFragment
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.supportFragmentManager
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentMyPostingTabBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postingFrag = SelectDetailsTabFragment.newInstance("My Posting")
        commendFrag = SelectDetailsTabFragment.newInstance("My Comment")

        //tab 설정
        setDetailsContainer(postingFrag)
        binding.postingCommendTab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0->{setDetailsContainer(postingFrag)}
                    1->{setDetailsContainer(commendFrag)}
                    else->{setDetailsContainer(postingFrag)}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setDetailsContainer(fragment: Fragment){
        fm.beginTransaction().replace(R.id.details_container,fragment)
            .commit()
    }

}
