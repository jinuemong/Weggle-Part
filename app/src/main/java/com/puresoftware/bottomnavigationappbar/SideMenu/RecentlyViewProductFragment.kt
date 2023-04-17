package com.puresoftware.bottomnavigationappbar.SideMenu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.airbnb.lottie.parser.moshi.JsonReader.Token
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemProductDetailAdapter
import com.puresoftware.bottomnavigationappbar.databinding.FragmentRecentlyViewProductBinding
import com.puresoftware.bottomnavigationappbar.databinding.ItemMiniProductTypeDetailBinding

class RecentlyViewProductFragment : Fragment() {

    private var _binding : FragmentRecentlyViewProductBinding?= null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemProductDetailAdapter

    private lateinit var  callback : OnBackPressedCallback
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        callback =object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                mainActivity.setSubFragment()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentRecentlyViewProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }


    @SuppressLint("CommitPrefEdits")
    private fun initView(){

        // 전부 불러오기
        val currentView = mainActivity.getSharedPreferences("viewProducts",MODE_PRIVATE)

        val array : List<Int> = currentView.all.map { it.key.toInt() } //key 값을 int로 받음

        if (array.isNotEmpty()){
            // id 리스트로 데이터 얻기
            // itemSeletProductAdapter에 연결
            setIsDataMenu(true)
        }else{
            setIsDataMenu(false)
        }
        adapter = ItemProductDetailAdapter(mainActivity,"no category")

        /* 데이터 추가- view 볼 때
        currentView.edit().putString("productId?////","id값 : Int").apply()
         */

    }
    @SuppressLint("ResourceAsColor")
    private fun setUpListener(){
        binding.backButton.setOnClickListener {
            mainActivity.setSubFragment()
        }

        binding.editMenu.setOnClickListener {
            if(setSelectMode()){
                binding.editMenu.apply {
                    text = "완료"
                    setTextColor(Color.parseColor("#E60FAB"))
                }
            }else{
                binding.editMenu.apply {
                    text = "편집"
                    setTextColor(Color.parseColor("#D3CDCD"))
                }
            }
        }
    }

    // 데이터 존재 여부 표시
    private fun setIsDataMenu(boolean: Boolean){
        if (boolean){
            binding.notData.visibility = View.VISIBLE
            binding.productList.visibility = View.GONE
        }else{
            binding.notData.visibility = View.GONE
            binding.productList.visibility = View.VISIBLE
        }
    }

    // 삭제 상품 선택
    private fun setSelectMode():Boolean{
        return if (binding.selceBox.visibility==View.GONE){
            binding.selceBox.visibility =View.VISIBLE
            true
        }else{
            binding.selceBox.visibility= View.GONE
            false
        }
    }


}