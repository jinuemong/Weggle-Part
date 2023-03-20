package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.AdditionalImageAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemProductAdditionalAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel.AddReviewViewModel
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAdditionalProductBinding

// step 3 : product add
class AdditionalProductFragment : Fragment() {
    private lateinit var activity: AddReviewActivity
    private lateinit var masterApp: MasterApplication
    private var _binding: FragmentAdditionalProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var addReviewModel: AddReviewViewModel
    private lateinit var searchAdapter: ItemProductAdditionalAdapter
    private lateinit var imageAdapter: AdditionalImageAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AddReviewActivity
        addReviewModel = activity.addReviewModel
        masterApp = activity.masterApp
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionalProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                setSearchData(p0.toString())
            }

        })
    }

    private fun initView() {
        //어댑터 연결
        searchAdapter = ItemProductAdditionalAdapter(activity)
        binding.searchRecycler.adapter = searchAdapter
        setNewImageAdapter()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpListener() {
        searchAdapter.apply {
            setOnItemClickListener(object : ItemProductAdditionalAdapter.OnItemClickListener {
                override fun delData(item: Product) {
                    addReviewModel.delSelectData(item)
                    setNewImageAdapter() //새로 어댑터 생성
                    notifyDataSetChanged() //변화 적용 : searchAdapter
                    setSelectedNum(addReviewModel.getSelectNum())
                }

                override fun addData(item: Product) {
                    addReviewModel.addSelectData(item)
                    setNewImageAdapter() //새로 어댑터 생성
                    notifyDataSetChanged() //변화 적용 : searchAdapter
                    setSelectedNum(addReviewModel.getSelectNum())
                }

            })
        }


        binding.cancelButton.setOnClickListener {
            backFragment()
        }

        binding.commitButton.setOnClickListener {
            // 부모 프래그 먼트 recycler 갱신
            (activity.supportFragmentManager.findFragmentByTag("additional product")
            as UploadReviewFragment).setSelectedData()
            backFragment()
        }
    }

    private fun setNewImageAdapter(){
        imageAdapter = AdditionalImageAdapter(activity,"addView").apply {
            setOnItemClickListener(object : AdditionalImageAdapter.OnItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun delData(item: Product) {
                    addReviewModel.delSelectData(item)

                    imageAdapter = AdditionalImageAdapter(activity,"addView")
                    setNewImageAdapter()//다시 등록

                    searchAdapter.notifyDataSetChanged() //변화 적용 : searchAdapter
                    setSelectedNum(addReviewModel.getSelectNum())
                }
            })
        }
        binding.imageRecycler.adapter = imageAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun setSearchData(p0: String?) {
        if (p0 != null && p0 != "") {
            val searchData = p0.toString()
            addReviewModel.searchText = searchData
            ProductManager(masterApp).searchProduct(searchData,
                paramFun = { dataList, _ ->
                    if (dataList != null) {
                        setSearchNum(dataList.size)
                        addReviewModel.setSearchData(dataList)
                    } else {
                        setSearchNum(0)
                        addReviewModel.resetSearchData()
                    }
                    searchAdapter.setSearchData()
                })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSearchNum(num: Int) {
        binding.searchNum.text = "검색결과 ($num)"
    }

    @SuppressLint("SetTextI18n")
    private fun setSelectedNum(num: Int) {
        binding.uploadNum.text = "현재 업로드한 상품 ($num)"
    }

    private fun backFragment(){
        activity.returnView(this@AdditionalProductFragment)
        addReviewModel.resetSearchData()
    }


}
