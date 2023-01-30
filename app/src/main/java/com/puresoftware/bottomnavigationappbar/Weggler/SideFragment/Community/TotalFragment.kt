package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.Community

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapter
import com.puresoftware.bottomnavigationappbar.databinding.FragmentTotalBinding


class TotalFragment() : Fragment() {
    private var _binding : FragmentTotalBinding? = null
    private val binding get()=_binding!!
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //데이터가 있다면 갱신
        mainActivity.communityViewModel.apply {
            if (this.communityLiveData.value != null && this.communityLiveData.value!!.size > 0) {
                val adapter = ItemCommunitySmallAdapter(mainActivity,this.communityLiveData.value!!)
                binding.totalRecycler.adapter = adapter
            }

        }
    }

}