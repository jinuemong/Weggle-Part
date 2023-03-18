package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAdditionalProductBinding

class AdditionalProductFragment : Fragment() {
    private lateinit var activity: AddReviewActivity
    private var _binding : FragmentAdditionalProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var reviewProductId : Int =0  //현재 등록 중인 리뷰 제외

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AddReviewActivity
        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity.returnView(this@AdditionalProductFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       arguments?.let {
           reviewProductId = it.getInt("productId")
       }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionalProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance(currentId : Int) =
            AdditionalProductFragment().apply {
                arguments = Bundle().apply {
                    putInt("productId",currentId)
                }
            }
    }
}
