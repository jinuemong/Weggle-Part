package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemProductSimpleAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ProductSearchTextAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.MyAccount.UpdateProfileFragment
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.MainFragment.CommunityFragment
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.ActivityReviewBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class AddReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private lateinit var searchAdapter : ProductSearchTextAdapter
    private lateinit var productAdapter :ItemProductSimpleAdapter
    lateinit var masterApp : MasterApplication
    private lateinit var fragmentManager:FragmentManager
//    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentManager = this@AddReviewActivity.supportFragmentManager

//        // 뒤로가기
//        onBackPressedCallback = object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                finish()
//                Log.d("뒤로가기 동작","")
//            }
//        }
//        this.onBackPressedDispatcher.addCallback(this,onBackPressedCallback)
        //retrofit
        masterApp = MasterApplication()
        masterApp.createRetrofit(this@AddReviewActivity)

        //검색 완료 어댑터(프로덕트 리스트 ) 생성 + 연결
        productAdapter = ItemProductSimpleAdapter(this@AddReviewActivity)
        binding.reviewRecyclerview.adapter = productAdapter.apply {
            setOnItemClickListener(object:ItemProductSimpleAdapter.OnItemClickListener{
                override fun itemClick(id: Int) {
                    // 가이드에 연결
                    getUploadGuide(id)
                }

            })
        }

        //어댑터 생성 + 연결 (검색 어댑터)
        searchAdapter = ProductSearchTextAdapter(this@AddReviewActivity)
        binding.searchView.searchList.adapter = searchAdapter.apply {
            setOnItemClickListener(object :ProductSearchTextAdapter.OnItemClickListener{
                override fun onItemClick(item : Product) {
                    binding.reviewText.text = "작성 가능한 리뷰가 있어요!"
                    binding.searchText.visibility = View.VISIBLE
                    //RecyclerView 데이터 변환
                    val data = ArrayList<Product>()
                    data.add(item)
                    productAdapter.setData(data)
                    //텍스트 제거 + 검색 뷰 제거
                    binding.reviewEdit.setText("")
                    offSubFragment()
                }
            })
        }

        binding.reviewEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                setSearchTextData(p0.toString())
            }

        })

    }

    fun onSubFragment(){
        binding.mainFrame.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
    }

    fun offSubFragment(){
        binding.mainFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

    }

    fun setSearchTextData(p0:String?){
        if (p0!=null && p0!="") {
            val searchData = p0.toString()
            ProductManager(masterApp).searchProduct(searchData,
                paramFun = { dataList , _ ->
                    if (dataList!=null){
                        searchAdapter.setData(dataList,searchData)
                        onSubFragment()
                    }else{
                        searchAdapter.setData(ArrayList(),"changeText")
                        offSubFragment()
                    }
                })
        }else{
            searchAdapter.setData(ArrayList(),"changeText")
            offSubFragment()
        }
    }

    //영상 업로드 가이드 생성 + 확인 시 프래그먼트 전환
    private fun getUploadGuide(productId : Int){
        val guide  = UploadVideoGuideFragment()
        guide.show(this@AddReviewActivity.supportFragmentManager,null)
        guide.apply {
            setOnClickListener(object : UploadVideoGuideFragment.OnItemClickListener{
                override fun itemClick() {
                    guide.dismissNow()
                    changeView(VideoReviewFragment.newInstance(productId))
                }

            })
        }
    }


    fun changeView(fragment: Fragment){
        fragmentManager.beginTransaction()
            .add(R.id.main_view_in_add_review,fragment)
            .addToBackStack(null)
            .commit()
    }

    fun returnView(fragment: Fragment){
        fragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
        fragmentManager.popBackStack()
    }
}