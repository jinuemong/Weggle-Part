package com.puresoftware.bottomnavigationappbar.Weggler.MainFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.DetailReviewFragment
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemProductHoAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemFeedPostingAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityManagerWithReview
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment.ShellFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFeedBinding

// 리뷰 데이터들 불러오기
// 내가 팔로우 하는 사용자의 리뷰 모아보기

class FeedFragment : Fragment() {
    private var _binding : FragmentFeedBinding? = null
    private val binding  get() = _binding!!
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        CommunityManagerWithReview(mainActivity.masterApp)
            .getFollowingReviewList(paramFunc = {dataList,_->
                if (dataList==null) {
                    // 모든 리뷰 데이터 가져오기 -> 팔로우 중인 데이터가 없을 때
                }else{
                    // 팔로잉 중인 리뷰 데이터

                    //데이터 하나만 우선 추출 -> 상단 프레임 변경
                    if(dataList.size>0){
                        mainActivity.fragmentManager!!.beginTransaction()
                            .replace(R.id.recommend_posting,
                            DetailReviewFragment.newInstance(dataList[0].reviewId,"feed"))
                            .commit()
                        dataList.removeAt(0)
                    }

                    binding.feedReviewList.adapter= ItemFeedPostingAdapter(mainActivity,dataList)
                        .apply {
                            setOnItemClickListener(object : ItemFeedPostingAdapter.OnItemClickListener{

                                // 프로덕트 리스트 불러오기 - > setOnItemClickListener에 등록해서 간편하게 사용
                                override fun setProductData(
                                    productList: ArrayList<Int>,
                                    paramFunc: (ArrayList<Product>) -> Unit
                                ) {
                                    ProductManager(mainActivity.masterApp)
                                        .getAdditionalProductList(productList, paramFunc = { dataSet,_->
                                            if(dataSet!=null){
                                                paramFunc(dataSet)
                                            }else{
                                                paramFunc(arrayListOf())
                                            }
                                        })
                                }

                            })
                        }

                }
            })
    }

    private fun setUpListener(){

        // 추천 위글러
        binding.feedGoRcwButton.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(ShellFragment.newInstance("추천 위글러"))
        }
    }
}