package com.puresoftware.bottomnavigationappbar.Weggler.MidFragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemPopularPostingTabAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityPostManager
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityList
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting.TotalFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment.ShellFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting.DetailCommunityPostingFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentCommunityBinding

//커뮤니티 게시판 : 공동 구매 , 프리 토크 구현
class CommunityFragment : Fragment() {
    private var _binding : FragmentCommunityBinding?= null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var wegglerApp : WegglerApplication
    private var popularAdapter: ItemPopularPostingTabAdapter?= null
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

        binding.addButton.setOnClickListener(object :View.OnClickListener{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onClick(v: View?) {
                if (v != null) {
                    registerForContextMenu(v)
                    v.showContextMenu(((v.x-v.x/2)),((v.y-v.y*2)) )
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

        //커뮤니티 데이터 불러오기
        val community = CommunityPostManager(wegglerApp)
        community.getCommunityPostList(0, listOf("postId,DESC"), paramFunc = {
            if(it!=null){

                mainActivity.communityViewModel.communityLiveData.value = it.content
                //아래는 나중에 수정
                mainActivity.communityViewModel.myPostingLiveData.value = it.content
            }
        })


        //인기 게시물 불러오기
        community.getPopularCommunityPostList( paramFunc = {
            if(it!=null){
                mainActivity.communityViewModel.popularPostingLiveData.value = it
                //인기 게시물 설정 (main 4개)
                popularAdapter = ItemPopularPostingTabAdapter(it,mainActivity).apply {
                    setOnItemClickListener(object : ItemPopularPostingTabAdapter.OnItemClickListener{
                        override fun onItemClick(item: CommunityContent) {
                            mainActivity.changeFragment(DetailCommunityPostingFragment("main",item))
                            mainActivity.setMainViewVisibility(false)
                        }
                    })
                }
                binding.popList.adapter = popularAdapter
            }
        })


        //하단 뷰 설정
        mainActivity.fragmentManager!!.beginTransaction()
            .replace(R.id.total_com_list_container,TotalFragment("Main Posting"))
            .commit()

        //클릭 리스너 (뷰가 그려진 후에 호출)
        setUpListener()

        //observer
        initData()
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

    private fun initData(){
        //인기 포스팅 변화 시 갱신
        mainActivity.communityViewModel.popularPostingLiveData.observe(mainActivity, Observer {
            if (it!=null && it.size>0 && popularAdapter!=null){
                val data = mainActivity.communityViewModel.popularPostingLiveData.value
                val dataList =if (data!!.size>=4)  data.subList(0,4).toList() else data.toList()
                popularAdapter?.setData(dataList)
            }
        })
    }
}