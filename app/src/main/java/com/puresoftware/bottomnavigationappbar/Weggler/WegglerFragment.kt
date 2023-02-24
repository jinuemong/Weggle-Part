package com.puresoftware.bottomnavigationappbar.Weggler

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityCommentManager
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityManagerWithReview
import com.puresoftware.bottomnavigationappbar.Weggler.MidFragment.ChallengeFragment
import com.puresoftware.bottomnavigationappbar.Weggler.MidFragment.CommunityFragment
import com.puresoftware.bottomnavigationappbar.Weggler.MidFragment.FeedFragment
import com.puresoftware.bottomnavigationappbar.Weggler.MidFragment.RankingFragment
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Token
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.databinding.WegglerFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WegglerFragment : Fragment() {
    private var _binding : WegglerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity:MainActivity
    private lateinit var fm : FragmentManager

    private lateinit var productManager : ProductManager
    private lateinit var communityPostManager:  CommunityManagerWithReview
    private lateinit var communityCommentManager : CommunityCommentManager

    private var feedFragment: FeedFragment? = null
    private var challengeFragment: ChallengeFragment? = null
    private var communityFragment: CommunityFragment? = null
    private var rankingFragment: RankingFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity  = context as MainActivity
        fm = mainActivity.supportFragmentManager
        productManager = ProductManager(mainActivity.masterApp)
        communityPostManager = CommunityManagerWithReview(mainActivity.masterApp)
        communityCommentManager = CommunityCommentManager(mainActivity.masterApp)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WegglerFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //임의로 로그인 구현 // - main으로 이동
        (mainActivity.masterApp).service.loginJinwoo()
            .enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if (response.isSuccessful){
                        val sp = mainActivity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
                        val editor = sp.edit()
                        editor.putString("accessToken", response.body()?.accessToken)
                        editor.putString("refreshToken", response.body()?.refreshToken)
                        editor.apply()
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {}
            })
        /////////////////

        feedFragment = FeedFragment()
        challengeFragment = ChallengeFragment()
        communityFragment = CommunityFragment()
        rankingFragment = RankingFragment()

        midFragmentChange(feedFragment!!)
        binding.wegglerTab.addOnTabSelectedListener(object :  TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position){
                    0 ->{midFragmentChange(feedFragment!!)}
                    1 ->{midFragmentChange(challengeFragment!!)}
                    2 ->{midFragmentChange(communityFragment!!)}
                    3 ->{midFragmentChange(rankingFragment!!)}
                    else ->{midFragmentChange(feedFragment!!)}
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        feedFragment = null
        challengeFragment = null
        communityFragment = null
        rankingFragment = null
    }

    private fun midFragmentChange(goFragment:Fragment){
        fm.beginTransaction().replace(R.id.mid_container,goFragment)
            .commit()
    }


    private fun initServerData(){
        productManager.initCommunityProduct { communityList, message ->
            if (message==null){
                // 내 리뷰 리스트 불러오기 ( 공구해요 + 프리토크 내부에서 body -type으로 분류 )
                communityPostManager.getCommunityReviewList(communityList!!.productId, paramFunc = { data, message2->
                    if (message2==null){
                        if (data != null) {
                            mainActivity.communityViewModel.setCommunityData(data)
                        }
                    }else{
                        Toast.makeText(mainActivity, message2, Toast.LENGTH_SHORT).show()
                    }
                })

                // 내 포스팅 불러오기
                communityPostManager.getMyCommunityReviewList(paramFunc = { data, message2 ->
                    if (message2==null){
                        if (data != null) {
                            mainActivity.communityViewModel.setMyPostingData(data)
                        }
                    }else{
                        Toast.makeText(mainActivity, message2, Toast.LENGTH_SHORT).show()

                    }
                })

                // 인기 게시물 불러오기
                communityPostManager.getCommunityReviewListByLike(communityList.productId, paramFunc = { data,message2->
                    if (message2 == null) {
                        if (data != null) {
                            mainActivity.communityViewModel.setPopularPostingData(data)
                        }
                    } else {
                        Toast.makeText(mainActivity, message2, Toast.LENGTH_SHORT).show()

                    }
                })

                //내 댓글 불러오기
                communityCommentManager.getMyCommentList(paramFunc = { data,message2->
                    if (message2==null){
                        if( data!=null){
                            mainActivity.communityViewModel.setMyCommentData(data)
                        }
                    }else{
                        Toast.makeText(mainActivity, message2, Toast.LENGTH_SHORT).show()
                    }
                })
            }else{
                Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}