package com.puresoftware.bottomnavigationappbar.MyAccount.AboutRelation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.google.android.material.tabs.TabLayout
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFollowDataBinding

class FollowDataFragment : Fragment() {
    private var _binding : FragmentFollowDataBinding? = null
    private val binding get() = _binding!!
    private var userId = ""
    private var viewType = 0
    private var mainType = ""
    private lateinit var mainActivity: MainActivity
    private lateinit var callback: OnBackPressedCallback
    private lateinit var followerFragment : FollowListFragment
    private lateinit var followingFragment : FollowListFragment
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainActivity.goBackFragment(this@FollowDataFragment)
                if (mainType == "main") {
                    mainActivity.setMainViewVisibility(true)
                }
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString("userId").toString()
            viewType = it.getInt("viewType")
            mainType = it.getString("mainType").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowDataBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followerFragment = FollowListFragment.newInstance(userId,0)
        followingFragment = FollowListFragment.newInstance(userId,1)

        //초기 세팅
        if (viewType==0){
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
            // follower
            mainActivity.fragmentManager!!.beginTransaction()
                .replace(R.id.follow_container, followerFragment)
                .commit()
        }else if(viewType==1) {
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(1))
            // following
            mainActivity.fragmentManager!!.beginTransaction()
                .replace(R.id.follow_container, followingFragment)
                .commit()
        }


        //탭
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{
                        // follower
                        mainActivity.fragmentManager!!.beginTransaction()
                            .replace(R.id.follow_container, followerFragment)
                            .commit()
                    }
                    1->{
                        // following
                        mainActivity.fragmentManager!!.beginTransaction()
                            .replace(R.id.follow_container, followingFragment)
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.backButton.setOnClickListener {
            mainActivity.goBackFragment(this@FollowDataFragment)
            if (mainType == "main") {
                mainActivity.setMainViewVisibility(true)
            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(userId:String,viewType: Int,mainType:String) =
            FollowDataFragment().apply {
                arguments = Bundle().apply {
                    putString("userId",userId)
                    putInt("viewType", viewType)
                    putString("mainType",mainType)
                }
            }
    }
}