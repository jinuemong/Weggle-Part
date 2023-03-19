package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.AdditionalImageAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemProductAdditionalAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel.AddReviewViewModel
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAdditionalProductBinding
// step 3 : product add
class AdditionalProductFragment : Fragment() {
    private lateinit var activity: AddReviewActivity
    private lateinit var masterApp : MasterApplication
    private var _binding : FragmentAdditionalProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var addReviewModel : AddReviewViewModel
    private lateinit var searchAdapter : ItemProductAdditionalAdapter
    private lateinit var imageAdapter: AdditionalImageAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AddReviewActivity
        addReviewModel = activity.addReviewModel
        masterApp = activity.masterApp
        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity.returnView(this@AdditionalProductFragment)
                resetData()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionalProductBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()

        binding.searchBar.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                setSearchData(p0.toString())
            }

        })
    }

    private fun initView(){
        //어댑터 연결
        searchAdapter = ItemProductAdditionalAdapter(activity)
        imageAdapter = AdditionalImageAdapter(activity)
        binding.searchRecycler.adapter = searchAdapter
        binding.imageRecycler.adapter = imageAdapter



    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpListener(){
        searchAdapter.apply {
            setOnItemClickListener(object:ItemProductAdditionalAdapter.OnItemClickListener{
                override fun delData(item:Product) {
                    Log.d("현재 데이터를 삭제합니다,.",item.name)
                    val index = addReviewModel.delSelectData(item)
                    setSelectedNum(addReviewModel.getSelectNum())
                    if (index!=-1) {
                        imageAdapter.delSelectedData(index)
                    }
                    renewData()
                }

                override fun addData(item:Product) {
                    Log.d("현재 데이터를 추가합니다,.,.",item.name)

                    addReviewModel.addSelectData(item)
                    setSelectedNum(addReviewModel.getSelectNum())
                    imageAdapter.addSelectedData(item)
                    renewData()
                }

            })
        }

        imageAdapter.apply {
            setOnItemClickListener(object :AdditionalImageAdapter.OnItemClickListener{
                override fun delData(index:Int) {
//                    Log.d("현재 데이터를 삭ㅈ제,.,.",item.name)
//                    for (i in addReviewModel.selectProductData.value!!){
//                        Log.d("(전) 담겨있는 데이터 : ",i.name)
//                    }
//                    addReviewModel.delSelectData(item)
//                    for (i in addReviewModel.selectProductData.value!!){
//                        Log.d("(후) 담겨있는 데이터 : ",i.name)
//                    }
                    setSelectedNum(addReviewModel.getSelectNum())
                    searchAdapter.setData()
                    delSelectedData(index)
                }

            })

        }

        binding.cancelButton.setOnClickListener {
            activity.returnView(this@AdditionalProductFragment)
            resetData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSearchData(p0:String?){
        if (p0!=null && p0!="") {
            val searchData = p0.toString()
            addReviewModel.searchText = searchData
            ProductManager(masterApp).searchProduct(searchData,
                paramFun = { dataList, _ ->
                    if (dataList!=null){
                        setSearchNum(dataList.size)
                        addReviewModel.setSearchData(dataList)
                    }else{
                        setSearchNum(0)
                        addReviewModel.resetSearchData()
                    }
                    searchAdapter.setData()
                })
        }else{
            setSearchNum(0)
            addReviewModel.resetSearchData()
            searchAdapter.setData()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSearchNum(num:Int){
        binding.searchNum.text = "검색결과 ($num)"
    }

    @SuppressLint("SetTextI18n")
    private fun setSelectedNum(num:Int){
        binding.uploadNum.text = "현재 업로드한 상품 ($num)"
    }

    private fun resetData(){
        addReviewModel.resetSearchData()
        addReviewModel.resetSelectData()
    }
    companion object {
        @JvmStatic
        fun newInstance(currentId : Int) =
            AdditionalProductFragment().apply {
                arguments = Bundle().apply {
                    putInt("productId",currentId)
                }
            }
    }
}
