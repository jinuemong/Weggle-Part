package com.puresoftware.bottomnavigationappbar.SideMenu

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.UserManager
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.NavigationInnerBinding

class MainNavigationFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private var _binding  : NavigationInnerBinding?= null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = NavigationInnerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.myAccountViewModel.userProfile?.let { user->
            //유저 정보
            Log.d("user",user.name)
            user.profile?.let {
                Glide.with(mainActivity)
                    .load(user.profile)
                    .into(binding.userImage)
            }
            binding.userName.text = user.name
        }

        setUpListener()
    }

    private fun setUpListener(){
        binding.navSetting.setOnClickListener {
            mainActivity.setSubFragmentView(SettingFragment(),1)
        }

        binding.navClose.setOnClickListener {
            mainActivity.drawerLayout.close()
        }

        binding.recentlyView.setOnClickListener {
            mainActivity.setSubFragmentView(RecentlyViewProductFragment(),1)
        }

        binding.likeView.setOnClickListener {
            mainActivity.setSubFragmentView(LikeProductFragment(),1)
        }

        binding.rewardListView.setOnClickListener {
            mainActivity.setSubFragmentView(RewardListFragment(),1)
        }

        binding.rewardGuide.setOnClickListener {
            mainActivity.setSubFragmentView(RewardGuideFragment.newInstance(1),1)
        }

    }


    /*
    [필요 기능] : 상품 서비스
        리워드 : INT (상품 리뷰 작성 시 받는 포인트) - 환급 가능
        포인트 : INT (주 결제 머니로 사용)
        주문 내역 : List<product> (내 프로덕트 아이디로 구매 상품 구분)-구매내역
        찜 목록 : List<product> (내 좋아요 상품 목록  -  프로덕트 아이디로 구분 )
        리워드 목록 : List<product> (내가 리뷰 남긴 상품 리스트)
        작성하지 않은 리뷰 : List<product>  (내 주문 내역 중, 리워드 목록에 포함되지 않은 상품)
        선물함 : List<product> (내가 받은 선물, 보낸 선물로 구분) 상품을 유저에게 선물하는 서비스
        취소,반품 목록 :List<product> : (내 취소, 반픔 묵록 프로덕트 리스트)

     */
}