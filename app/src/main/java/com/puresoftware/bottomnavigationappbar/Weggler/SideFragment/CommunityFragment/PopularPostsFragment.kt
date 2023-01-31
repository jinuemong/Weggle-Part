package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentPopularPostsBinding

//인기 게시글 ( 세부 기능은 SelectDetailsTabFragment 에서)

class PopularPostsFragment : Fragment() {
    private var _binding : FragmentPopularPostsBinding? = null
    private val binding get() =_binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var fm : FragmentManager
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.supportFragmentManager

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularPostsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView(){
        fm.beginTransaction()
            .replace(R.id.details_container,
                SelectDetailsTabFragment("Popular Posting"))
            .commit()
    }

}