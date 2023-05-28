package com.puresoftware.bottomnavigationappbar.Weggler.AboutCommunity.CommunityFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.AboutCommunity.CommunityPostingDetail.FreeTalkFragment
import com.puresoftware.bottomnavigationappbar.Weggler.AboutCommunity.CommunityPostingDetail.JointPurchaseFragment
import com.puresoftware.bottomnavigationappbar.Weggler.AboutCommunity.CommunityPostingDetail.TotalFragment
import com.puresoftware.bottomnavigationappbar.databinding.SelectDetailsTabBinding

//selectPosition  : 포스팅 , 댓글
class SelectDetailsTabFragment() : Fragment() {
    private var selectPosition : String = ""
    private var _binding : SelectDetailsTabBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var fm: FragmentManager
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.supportFragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectPosition = it.getString("selectPosition","")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SelectDetailsTabBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //tab layout 설정
        setPostingTabContainer(TotalFragment.newInstance(selectPosition))
        binding.tjfTabLayout.apply {
            setTabItemMargin(this)
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab?.position){
                        0->{setPostingTabContainer(TotalFragment.newInstance(selectPosition))}
                        1->{setPostingTabContainer(JointPurchaseFragment.newInstance(selectPosition))}
                        2->{setPostingTabContainer(FreeTalkFragment.newInstance(selectPosition))}
                        else->{setPostingTabContainer(TotalFragment.newInstance(selectPosition))}
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
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

    private fun setPostingTabContainer(fragment:Fragment){
        fm.beginTransaction().replace(R.id.sdt_main_container,fragment)
            .commit()
    }

    companion object{
        fun newInstance(selectPosition:String) =
            SelectDetailsTabFragment().apply {
                arguments= Bundle().apply {
                    putString("selectPosition",selectPosition)
                }
            }

    }
}
