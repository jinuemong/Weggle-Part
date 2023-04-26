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
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.FollowData
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
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{
                        // follower

                    }
                    1->{
                        // following
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        if (viewType==0){
            binding.tabLayout.setScrollPosition(0,0f,false)
        }else if(viewType==1){
            binding.tabLayout.setScrollPosition(1,1f,false)
        }
    }

    private fun setData(data:ArrayList<FollowData>){

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