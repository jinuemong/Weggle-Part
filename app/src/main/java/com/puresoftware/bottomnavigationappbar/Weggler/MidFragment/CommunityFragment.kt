package com.puresoftware.bottomnavigationappbar.Weggler.MidFragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityPostManager
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity.AddFreeTalkFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity.AddJointPurchaseFragment
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

        binding.addButton.setOnClickListener(object :View.OnClickListener{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(v: View?) {
                if (v != null) {
                    registerForContextMenu(v)

                    v.showContextMenu(((v.x-v.x/2)),((v.y-v.y*2.5).toFloat()) )

                }
            }

        })

    }

    // 메뉴 생성
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        when (v.id){
            R.id.add_button->{
                mainActivity.menuInflater.inflate(R.menu.pop_up_in_community,menu)

            }
        }
    }
    //메뉴 클릭 add 버튼
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_free_talk->{
                mainActivity.setMainViewVisibility(false)
                mainActivity.changeFragment(ShellFragment("프리토크 글쓰기"))
            }
            R.id.add_joint->{
                mainActivity.setMainViewVisibility(false)
                mainActivity.changeFragment(ShellFragment("공구해요 글쓰기"))
            }
        }
        return false
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