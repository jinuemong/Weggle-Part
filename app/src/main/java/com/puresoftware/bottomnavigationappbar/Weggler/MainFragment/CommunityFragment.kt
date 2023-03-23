package com.puresoftware.bottomnavigationappbar.Weggler.MainFragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemPopularPostingTabAdapter
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPostingDetail.TotalFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment.ShellFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPostingDetail.DetailCommunityPostingFragment
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

        // 커뮤니티 추가 기능
        binding.addButton.setOnClickListener {

            val popupMenu = PopupMenu(context,it)

            mainActivity.menuInflater.inflate(R.menu.pop_up_in_community,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.add_free_talk->{
                        mainActivity.setMainViewVisibility(false)
                        mainActivity.fragmentManager!!.beginTransaction()
                            .add(R.id.main_frame,
                                ShellFragment.newInstance("프리토크 글쓰기")
                                ,"add free talk")
                            .addToBackStack(null)
                            .commit()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.add_joint->{
                        mainActivity.setMainViewVisibility(false)
                        mainActivity.changeFragment(ShellFragment.newInstance("공구해요 글쓰기"))
                        return@setOnMenuItemClickListener true
                    }
                    else->{
                        return@setOnMenuItemClickListener  false
                    }
                }
            }
        }

    }


    private fun initView() {

        //하단 뷰 설정
        mainActivity.fragmentManager!!.beginTransaction()
            .replace(R.id.total_com_list_container,TotalFragment.newInstance("Main Posting"))
            .commit()

        //클릭 리스너 (뷰가 그려진 후에 호출)
        setUpListener()

        //observer
        initData()
    }
    private fun setUpListener() {
        binding.commGoJointPurchaseList.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment.newInstance("공구해요"))
        }
        binding.commGoFreeTalkList.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment.newInstance("프리토크"))
        }
        binding.commGoMyCommunityTabList.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment.newInstance("내가 쓴 글"))
        }
        binding.commGoPopularPostList.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment.newInstance("인기 게시글"))
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
                    mainActivity.changeFragment(DetailCommunityPostingFragment.newInstance(item.reviewId,"main"))

                }

            })
        }

        //인기 포스팅 변화 시 갱신
        mainActivity.communityViewModel.popularPostingLiveData.observe(mainActivity, Observer {
            val data = mainActivity.communityViewModel.popularPostingLiveData.value!!
            val freeData = ArrayList<ReviewInCommunity>()
            val joinData = ArrayList<ReviewInCommunity>()
            for (i in data){
                if(i.body.type==1 && joinData.size<2){
                    joinData.add(i)
                }
                if (i.body.type==2 && freeData.size<2){
                    freeData.add(i)
                }
                if (freeData.size+joinData.size==4){
                    break
                }
            }
            popularAdapter.setData(joinData.plus(freeData))
        })
    }
}