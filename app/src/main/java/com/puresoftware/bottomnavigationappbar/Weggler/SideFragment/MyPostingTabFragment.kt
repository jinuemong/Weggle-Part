package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment

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
import com.puresoftware.bottomnavigationappbar.databinding.FragmentMyPostingTabBinding


class MyPostingTabFragment : Fragment() {
    private var _binding : FragmentMyPostingTabBinding? = null
    private val binding get() = _binding!!
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
    ): View{
        _binding = FragmentMyPostingTabBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabItemMargin(binding.tjfLayout.tjfTabLayout,)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // tab item에 margin을 주기 위함
    // child를 불러오고 해당 데이터의 layoutParams에서 margin을 설정
    private fun setTabItemMargin(tabLayout: TabLayout){
        val tabs = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabs.childCount){
            val tab = tabs.getChildAt(i)
            val lp = tab.layoutParams as LinearLayout.LayoutParams
            lp.marginEnd = 10
            tab.layoutParams = lp
            tabLayout.requestLayout()
        }
    }
}