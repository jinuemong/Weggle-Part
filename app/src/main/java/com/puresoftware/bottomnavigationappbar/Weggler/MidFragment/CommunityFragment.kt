package com.puresoftware.bottomnavigationappbar.Weggler.MidFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityPostManager
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.ShellFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentCommunityBinding


class CommunityFragment : Fragment() {
    private var _binding : FragmentCommunityBinding?= null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var wegglerApp : WegglerApplication
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        wegglerApp = mainActivity.application as WegglerApplication
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }

    private fun initView() {

        //테스트 코드
        val community = CommunityPostManager(wegglerApp)
        community.getCommunityPostList(0,10, paramFunc = {
            if(it!=null){
                for ( item in it.content){
                    Log.d("test////////////////",item.body.toString())
                }
            }
        })
    }
    private fun setUpListener() {
        binding.commGoJointPurchaseList.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment("공구해요"))
        }
        binding.commGoFreeTalkList.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment("프리토크"))
        }
        binding.commGoMyCommunityTabList.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment("내가 쓴 글"))
        }
        binding.commGoPopularPostList.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment("인기 게시글"))
        }
    }
}