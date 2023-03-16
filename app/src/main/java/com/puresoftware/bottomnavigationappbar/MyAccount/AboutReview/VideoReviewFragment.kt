package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentVideoReviewBinding

// 영상을 등록하고 리뷰 작성

class VideoReviewFragment : Fragment() {
    private var _binding : FragmentVideoReviewBinding? = null
    private val binding get() = _binding!!
    private var productId : Int = -1 //초기화
    private lateinit var activity: AddReviewActivity
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AddReviewActivity
        onBackPressedCallback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity.returnView(this@VideoReviewFragment)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt("productId")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoReviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        onBackPressedCallback.remove()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("VideoReviewFragment:Id ",productId.toString())
    }
    companion object {

        @JvmStatic
        fun newInstance(id : Int) =
            VideoReviewFragment().apply {
                arguments = Bundle().apply {
                    putInt("productId",id)
                }
            }
    }
}