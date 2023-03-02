package com.puresoftware.bottomnavigationappbar.brands


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.BrandsFragmentBinding
import java.lang.Math.ceil


// overlapRecyclerView https://kimyunseok.tistory.com/130
// auto scroll https://bumjae.tistory.com/65
class BrandsFragment : Fragment() {

    private lateinit var binding: BrandsFragmentBinding
    private var bannerPosition = 0 // 무한 Scroll 기본값...
    var vpHandler: Handler? = null

    val TAG: String = BrandsFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
//        val view: View = inflater.inflate(R.layout.brands_fragment, container, false)
        binding = BrandsFragmentBinding.inflate(inflater, container, false)

        setUpMainViewPager() // MainViewPager
        setUpRecyclerView() // Recycler

        Log.i(TAG, "oncreateView launched")

        return binding.root
    }

    // main에 들어갈 ViewPager
    private fun setUpMainViewPager() {

        //임시 데이터 생성
        var list: ArrayList<MainViewPagerData> = ArrayList<MainViewPagerData>().let {
            it.apply {
                add(
                    MainViewPagerData(
                        "https://ilyo.co.kr/contents/article/images/2022/0602/1654098706414313.jpg",
                        "팔도"
                    )
                )
                add(
                    MainViewPagerData(
                        "https://image.ajunews.com/content/image/2022/06/10/20220610112054178403.jpg",
                        "카카오게임즈"
                    )
                )
                add(
                    MainViewPagerData(
                        "https://file2.nocutnews.co.kr/newsroom/image/2021/11/04/202111041423592284_0.jpg",
                        "제주도협회"
                    )
                )
            }
        }

        binding.txtCurrentBanner.text = "${bannerPosition}/${list.size}" // 초기값

        // 뭐 암튼 복잡한 숫자로 Scroll 숫자를 초기화 시켜줌. 머리가 아프다.
        bannerPosition = Int.MAX_VALUE / 2 - ceil(list.size.toDouble() / 2).toInt()
        binding.vpBrandsMainItem.setCurrentItem(bannerPosition, false)

//         Handler를 사용하여 간단하게 자동 Scroll하기(Deparated 되어서 나중에 구현하기 어려워 질 것.)
        // 최적화: 반복적인 method 호출을 확인하였으므로, back에서 1번만 구동하도록, 반복적 호출로 인해서 꼬이는 현상 발생
        if (vpHandler == null) {
            val mHandler: Handler = Handler()
            mHandler.postDelayed(object : Runnable {
                override fun run() {
                    if (true) { // 재귀함수를 통한 반복?
                        binding.vpBrandsMainItem.currentItem++ // currentItem의 숫자가 바뀔 수록 Animation과 함께 page가 넘어감.
                        Handler().postDelayed(this, 3000)
                    }
                }
            }, 3000)
            vpHandler = mHandler
        }

////         Handler를 사용하여 간단하게 자동 Scroll하기(Deparated 되어서 나중에 구현하기 어려워 질 것.)
//        Handler().postDelayed(object : Runnable {
//            override fun run() {
//                if (true) { // 재귀함수를 통한 반복?
//                    binding.vpBrandsMainItem.currentItem++ // currentItem의 숫자가 바뀔 수록 Animation과 함께 page가 넘어감.
//                    Handler().postDelayed(this, 3000)
//                }
//            }
//        }, 3000)

        // MainViewPager Adapter
        binding.vpBrandsMainItem.adapter = MainItemViewPagerAdapter(list)
        binding.vpBrandsMainItem.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.vpBrandsMainItem.registerOnPageChangeCallback(object : // 자동으로 Page 숫자를 받기 위해서 listener
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // Scroll 값 올려주기
                bannerPosition = position
                binding.txtCurrentBanner.text = "${(bannerPosition % list.size) + 1}/${list.size}"
            }
        })
    }

    private fun setUpRecyclerView() {
        var itemList = mutableListOf(

            RecyclerOutViewModel(
                "(주)동아오츠카",
                "https://www.tfmedia.co.kr/data/photos/20180206/art_1518167665291_023679.jpg",
                mutableListOf(
                    RecyclerInViewModel(
                        "https://file.pickydiet.co.kr/image/PIKI/PRODUCT/PRD001/20221011/e11f8455a533009eb142221d3fd33021_600_600.jpg",
                        "(주)동아오츠카",
                        "포카리스웨트",
                        "50%",
                        "2,500"
                    ),
                    RecyclerInViewModel(
                        "https://scontent-gmp1-1.xx.fbcdn.net/v/t1.6435-9/57410715_1522986887836272_8472824990050287616_n.jpg?stp=cp0_dst-jpg_e15_p320x320_q65&_nc_cat=102&ccb=1-7&_nc_sid=110474&_nc_ohc=SJ3W1Ctugp0AX-prAT3&_nc_ht=scontent-gmp1-1.xx&oh=00_AfBWZHAoFBl_wHufwgBg18HLDE49KikMpfrw3cuWGNvrLA&oe=63E63688",
                        "(주)동아오츠카",
                        "이온워터",
                        "25%",
                        "1,500"
                    ),
                    RecyclerInViewModel(
                        "https://st.kakaocdn.net/thumb/P750x750/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fshoppingstore%2Fproduct%2F20220712071655_f0d1d0536da24bed89a4eb65a7cc82e4.png",
                        "(주)동아오츠카",
                        "마신다",
                        "70%",
                        "350"
                    ),
                ),
            ),

            RecyclerOutViewModel(
                "농심",
                "https://thumb.mt.co.kr/06/2017/07/2017071314130890218_2.jpg/dims/optimize/",
                mutableListOf(
                    RecyclerInViewModel(
                        "https://sitem.ssgcdn.com/79/44/63/item/0000008634479_i2_550.jpg",
                        "농심",
                        "신라면 1BOX",
                        "50%",
                        "2,500"
                    ),
                    RecyclerInViewModel(
                        "https://static.megamart.com/product/image/1346/13464110/13464110_1_960.jpg",
                        "농심",
                        "새우깡",
                        "10%",
                        "1,500"
                    ),
                ),
            ),
        )
//        binding.outRecyclerview.adapter = OutRecyclerViewAdapter(requireContext(), itemList)
//        binding.outRecyclerview.layoutManager = LinearLayoutManager(requireContext())

    }
}

