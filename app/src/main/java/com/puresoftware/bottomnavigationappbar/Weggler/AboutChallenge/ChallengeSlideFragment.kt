package com.puresoftware.bottomnavigationappbar.Weggler.AboutChallenge

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentChallengeSlideBinding

// main 슬라이드 뷰
class ChallengeSlideFragment : Fragment() {
    private var challengeType = -1
    private lateinit var mainActivity: MainActivity

    private var _binding : FragmentChallengeSlideBinding? = null
    private val binding get()  = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            challengeType = it.getInt("type", -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChallengeSlideBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // tab layout
        binding.challengeTab.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab?.position){
                        0->{ setPostingTabContainer(ChallengeVideoFragment()) }
                        1->{ setPostingTabContainer(ChallengeGuideFragment()) }
                        else->{ setPostingTabContainer(ChallengeVideoFragment()) }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })
        }
    }

    private fun setPostingTabContainer(fragment:Fragment){
        mainActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.challenge_container,fragment)
            .commit()
    }



    companion object {
        @JvmStatic
        fun newInstance(type: Int) =
            ChallengeSlideFragment().apply {
                arguments = Bundle().apply {
                    putInt("type", type)
                }
            }
    }
}