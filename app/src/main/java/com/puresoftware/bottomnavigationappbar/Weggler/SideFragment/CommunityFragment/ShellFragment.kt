package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity.AddFreeTalkFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity.AddJointPurchaseFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting.DetailCommunityPostingFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting.FreeTalkFragment
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting.JointPurchaseFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentShellBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout


// 원하는 Fragment를 부착하는 곳 (중복 코드 줄이기)
// adapter에 따라서 다른 뷰 보여주기
// 공구해요, 프리토크 , 인기 게시글(view연결),내가쓴글(view연결) , 추천 위글러 topText 로 받아옴
// JointPurchase, FreeTalk, PopularPost, MyCommunityTab ,RecommendWeggler
// 에 따른 다른 어댑터 연결

class ShellFragment() : Fragment() {
    private var topText : String? = null
    private var _binding: FragmentShellBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var fm: FragmentManager
    private lateinit var callback:OnBackPressedCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.supportFragmentManager
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                mainActivity.setMainViewVisibility(true)
                mainActivity.goBackFragment(this@ShellFragment)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topText = it.getString("topText",null)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShellBinding.inflate(inflater,container,false)
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
        binding.topText.text = topText
        //다른 adapter 연결 혹은 view 연결
        when (topText){
            "공구해요"->{ //JointPurchaseFragment
                setShellContainer(JointPurchaseFragment.newInstance("Main Posting"))
            }
            "프리토크"->{ //FreeTalkFragment
                setShellContainer(FreeTalkFragment.newInstance("Main Posting"))
            }
            "추천 위글러"->{ //RecommendedUsersFragment
                setShellContainer(RecommendedUsersFragment())
            }
            "인기 게시글"->{ //PopularPostsFragment
                setShellContainer(PopularPostsFragment())
            }
            "내가 쓴 글"->{ //MyPostingTabFragment
                setShellContainer(MyPostingTabFragment())
            }
            "프리토크 글쓰기"->{
                setShellContainer(AddFreeTalkFragment())
            }
            "공구해요 글쓰기"->{
                setShellContainer(AddJointPurchaseFragment())
            }
        }
    }

    private fun setUpListener() {
        binding.backButtonRwf.setOnClickListener {
            mainActivity.setMainViewVisibility(true)
            mainActivity.goBackFragment(this@ShellFragment)
        }
    }

    private fun setShellContainer(fragment:Fragment){
        fm.beginTransaction().replace(R.id.shell_container,fragment)
            .commit()
    }

    fun setSlide(){
        val state = binding.slideFrameInShell.panelState
        // 닫힌 상태일 경우 열기
        if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            binding.slideFrameInShell.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }
        // 열린 상태일 경우 닫기
        else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
            binding.slideFrameInShell.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }
    companion object{
        fun newInstance(topText : String) =
            ShellFragment().apply {
                arguments= Bundle().apply {
                    putString("topText",topText)
                }
            }

    }
}