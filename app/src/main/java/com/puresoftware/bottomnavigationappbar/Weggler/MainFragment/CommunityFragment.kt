package com.puresoftware.bottomnavigationappbar.Weggler.MainFragment

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
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting.TotalFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment.ShellFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting.DetailCommunityPostingFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentCommunityBinding

//커뮤니티 게시판 : 공동 구매 , 프리 토크 구현
class CommunityFragment : Fragment() {
    private var _binding : FragmentCommunityBinding?= null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var wegglerApp : MasterApplication
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        wegglerApp = mainActivity.masterApp
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

        // 버튼 위치 조정 -> 수정
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
        //어댑터 생성
        val popularData = if (mainActivity.communityViewModel.popularPostingLiveData.value==null) arrayListOf()
        else mainActivity.communityViewModel.popularPostingLiveData.value!!
        val popularAdapter = ItemPopularPostingTabAdapter(popularData,mainActivity)
        binding.popList.adapter = popularAdapter.apply{
            setOnItemClickListener(object : ItemPopularPostingTabAdapter.OnItemClickListener{
                override fun onItemClick(item: ReviewInCommunity) {
                    mainActivity.changeFragment(DetailCommunityPostingFragment("main", item))
                    mainActivity.setMainViewVisibility(false)
                }

            })
        }

        //인기 포스팅 변화 시 갱신
        mainActivity.communityViewModel.popularPostingLiveData.observe(mainActivity, Observer {
            val data = mainActivity.communityViewModel.popularPostingLiveData.value
            val dataList = if (data!!.size >= 4) data.subList(0, 4) else data
            popularAdapter.setData(dataList)
        })
    }
}