package com.puresoftware.bottomnavigationappbar.Weggler.MidFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.ShellFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    private var _binding : FragmentFeedBinding? = null
    private val binding  get() = _binding!!
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpListener(){
        binding.feedGoRcwButton.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment("추천 위글러"))
        }
    }

}