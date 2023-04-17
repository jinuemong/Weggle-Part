package com.puresoftware.bottomnavigationappbar.SideMenu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemProductSimpleAdapter
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.FragmentLikeProductBinding

// 상품 찜 목록
class LikeProductFragment : Fragment() {
    private var _binding : FragmentLikeProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var  callback : OnBackPressedCallback
    private lateinit var mainActivity: MainActivity
    private lateinit var adapter : ItemProductSimpleAdapter
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
    ): View{
        _binding = FragmentLikeProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }

    private fun initView(){
        adapter = ItemProductSimpleAdapter(mainActivity)
        binding.likeList.adapter = adapter
        val data = arrayListOf<Product>()
        //data : 불러온 데이터 ( 찜목록)
        if (data.size>0){
            setIsData(true)
        }else{
            setIsData(false)
        }
    }

    private fun setUpListener(){
        binding.backButton.setOnClickListener {
            mainActivity.setSubFragment()
        }
    }

    private fun setIsData(boolean: Boolean){
        if (boolean){
            binding.dataBox.visibility =View.VISIBLE
            binding.notData.visibility = View.GONE
        }else{
            binding.dataBox.visibility = View.GONE
            binding.notData.visibility = View.VISIBLE
        }
    }


}