package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentManager
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentShellBinding


// 원하는 Fragment를 부착하는 곳 (중복 코드 줄이기)
// adapter에 따라서 다른 뷰 보여주기
// 공구해요, 프리토크 , 인기 게시글(view연결),내가쓴글(view연결) , 추천 위글러 topText 로 받아옴
// JointPurchase, FreeTalk, PopularPost, MyCommunityTab ,RecommendWeggler
// 에 따른 다른 어댑터 연결

class ShellFragment(
    private val topText : String,
) : Fragment() {
    private var _binding: FragmentShellBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var fm: FragmentManager
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.supportFragmentManager
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
                setShellContainer(JointPurchaseFragment())
            }
            "프리토크"->{ //FreeTalkFragment
                setShellContainer(FreeTalkFragment())
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
}