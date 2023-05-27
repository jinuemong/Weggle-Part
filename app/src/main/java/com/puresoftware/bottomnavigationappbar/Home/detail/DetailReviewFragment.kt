package com.puresoftware.bottomnavigationappbar.Home.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.puresoftware.bottomnavigationappbar.Home.adapter.DetailReviewAdapter
import com.puresoftware.bottomnavigationappbar.Home.manager.GroupManager
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication

import com.puresoftware.bottomnavigationappbar.databinding.FragmentDetailReviewListBinding
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFashionBinding


class DetailReviewFragment(private val postId:Int) : Fragment() {

    private lateinit var binding:FragmentDetailReviewListBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var groupManager: GroupManager
    private lateinit var wegglerApp : MasterApplication

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        wegglerApp = mainActivity.masterApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailReviewListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupManager = GroupManager(wegglerApp)

        groupManager.getReview(postId){
            binding.detailReviewRecyclerView.adapter = DetailReviewAdapter(requireContext(),it)
            binding.detailReviewRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }

    }
}