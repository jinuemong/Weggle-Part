package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPostingDetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommunitySmallAdapterFree
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemMyCommentAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityManagerWithReview
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFreeTalkBinding

//프리 토크

class FreeTalkFragment(
    ) : Fragment() {
    private var selectPosition: String? =null
    private var _binding : FragmentFreeTalkBinding? = null
    private val binding get()=_binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var fm: FragmentManager
    private lateinit var postingAdapter : ItemCommunitySmallAdapterFree
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
    ): View{
        _binding=FragmentFreeTalkBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postingAdapter  =ItemCommunitySmallAdapterFree(mainActivity, arrayListOf())
        commentAdapter =ItemMyCommentAdapter(mainActivity, arrayListOf(),2)

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
            setOnItemClickListener(object : ItemCommunitySmallAdapterFree.OnItemClickListener{
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
        fun newInstance(selectPosition:String) =
            FreeTalkFragment().apply {
                arguments= Bundle().apply {
                    putString("selectPosition",selectPosition)
                }
            }

    }

}