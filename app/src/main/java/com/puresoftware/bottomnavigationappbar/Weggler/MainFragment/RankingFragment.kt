package com.puresoftware.bottomnavigationappbar.Weggler.MainFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.DetailReviewFragment
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ReviewManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutRelation.UserProfileFragment
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemRankingAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityManagerWithReview
import com.puresoftware.bottomnavigationappbar.databinding.FragmentRankingBinding

//랭킹 사용자 리스트 보기

class RankingFragment : Fragment() {
    private var _binding : FragmentRankingBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var adapter : ItemRankingAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CommunityManagerWithReview(mainActivity.masterApp)
            .getRankingUsers { rankingUsers, _ ->
                if (rankingUsers!=null){
                    adapter = ItemRankingAdapter(mainActivity,rankingUsers)
                    binding.rankingList.adapter = adapter
                        .apply {
                            setOnItemClickListener(object : ItemRankingAdapter.OnItemClickListener{

                                // 리뷰 리스트 보기
                                override fun getList(
                                    user: UserInfo,
                                    paramFunc: (ArrayList<ReviewData>) -> Unit
                                ) {
                                    // 비동기 처리
                                    // 버튼 클릭 시 유저 조회로 리뷰 리스트 얻기
                                    ReviewManager(mainActivity.masterApp,null)
                                        .getUserReviewData(user.id, paramFunc = {dataList,_->
                                            if (dataList!=null){
                                                paramFunc(dataList)
                                            }else{
                                                paramFunc(arrayListOf())
                                            }
                                        })
                                }

                                // 리뷰 상세 보기
                                override fun viewReview(reviewData: ReviewData) {
                                    mainActivity.setMainViewVisibility(false)
                                    mainActivity.changeFragment(
                                        DetailReviewFragment
                                        .newInstance(reviewData.reviewId)
                                    )
                                }

                                //유저 프로필 보기
                                override fun getUserProfile(user: UserInfo) {
                                    mainActivity.changeFragment(UserProfileFragment.newInstance(user.id,"main"))
                                }
                            })
                        }
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}