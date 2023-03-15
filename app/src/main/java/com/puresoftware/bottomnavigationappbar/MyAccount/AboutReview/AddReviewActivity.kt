package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ProductSearchTextAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.databinding.ActivityReviewBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class AddReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private lateinit var adapter : ProductSearchTextAdapter
    private lateinit var masterApp : MasterApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrofit
        masterApp = MasterApplication()
        masterApp.createRetrofit(this@AddReviewActivity)

        //어댑터 생성 + 연결
        adapter = ProductSearchTextAdapter(this@AddReviewActivity)
        binding.searchView.searchList.adapter = adapter

        binding.reviewEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.searchText.text = "작성 가능한 리뷰가 있어요!"
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (p0!=null) {
                    ProductManager(masterApp).searchProduct(p0.toString(),
                        paramFun = { dataList , _ ->
                            if (dataList!=null){
                                adapter.setData(dataList)
                                onSubFragment()
                            }else{
                                adapter.setData(ArrayList())
                                offSubFragment()
                            }
                        })
                }else{
                    adapter.setData(ArrayList())
                    offSubFragment()
                }
            }

        })

    }

    fun onSubFragment(){
        binding.mainFrame.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
    }

    fun offSubFragment(){
        binding.mainFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

    }
}