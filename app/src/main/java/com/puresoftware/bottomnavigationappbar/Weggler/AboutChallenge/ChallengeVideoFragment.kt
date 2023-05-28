package com.puresoftware.bottomnavigationappbar.Weggler.AboutChallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentChallengeVideoBinding

class ChallengeVideoFragment : Fragment() {
    private var _binding : FragmentChallengeVideoBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengeVideoBinding.inflate(inflater,container,false)
        return binding.root
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String) =
//            ChallengeVideoFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                }
//            }
//    }
}