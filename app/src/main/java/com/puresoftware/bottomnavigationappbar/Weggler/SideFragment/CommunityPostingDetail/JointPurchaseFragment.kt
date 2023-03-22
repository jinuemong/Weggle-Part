package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPostingDetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapterFree
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapterJoint
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemMyCommentAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.databinding.FragmentJointPurchaseBinding

//공구해요

class JointPurchaseFragment() : Fragment() {
    private var _binding : FragmentJointPurchaseBinding? = null
    private val binding get()=_binding!!
    private var selectPosition: String? = null
    private lateinit var mainActivity: MainActivity
    private lateinit var fm: FragmentManager

    private lateinit var postingAdapter : ItemCommunitySmallAdapterJoint
    private lateinit var commentAdapter : ItemMyCommentAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.supportFragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectPosition = it.getString("selectPosition",null)
        }
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
        postingAdapter  = ItemCommunitySmallAdapterJoint(mainActivity, arrayListOf())
        commentAdapter =ItemMyCommentAdapter(mainActivity, arrayListOf())


        // 게시물 데이터 설정 (옵저버로 관찰)
        mainActivity.communityViewModel.apply {
            // 메인 포스팅
            when (selectPosition) {
                "Main Posting" -> {
                    binding.totalRecycler.adapter = postingAdapter
                    setPostingAdapterListener()
                    communityLiveData.observe(mainActivity, Observer {
                        postingAdapter.setData(it)
                    })

                    // 인기 게시물
                }
                "Popular Posting" -> {
                    binding.totalRecycler.adapter = postingAdapter
                    setPostingAdapterListener()
                    popularPostingLiveData.observe(mainActivity, Observer {
                        postingAdapter.setData(it)
                    })

                    // 내 게시물
                }
                "My Posting" -> {
                    binding.totalRecycler.adapter = postingAdapter
                    setPostingAdapterListener()
                    myPostingLiveData.observe(mainActivity, Observer {
                        postingAdapter.setData(it)
                    })

                    //내 댓글
                }
                "My Comment" -> {
                    binding.totalRecycler.adapter = commentAdapter
                    setCommentAdapterListener()
                    myCommentLiveData.observe(mainActivity, Observer {
                        commentAdapter.setData(it)
                    })
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setPostingAdapterListener(){
        postingAdapter.apply {
            setOnItemClickListener(object : ItemCommunitySmallAdapterJoint.OnItemClickListener{
                override fun onItemClick(item: ReviewInCommunity) {
                    mainActivity.changeFragment(DetailCommunityPostingFragment.newInstance(item.reviewId,"sub"))
                }

            })
        }
    }
    private fun setCommentAdapterListener(){
        commentAdapter.apply {
            setOnItemClickListener(object :ItemMyCommentAdapter.OnItemClickListener{
                override fun itemClick(reviewId :Int) { //클릭 시 동작
                    mainActivity.changeFragment(DetailCommunityPostingFragment.newInstance(reviewId,"sub"))
                }

            })
        }
    }

    companion object{
        fun newInstance(selectPosition: String)=
            JointPurchaseFragment().apply {
                arguments= Bundle().apply {
                    putString("selectPosition",selectPosition)
                }
            }
    }

}