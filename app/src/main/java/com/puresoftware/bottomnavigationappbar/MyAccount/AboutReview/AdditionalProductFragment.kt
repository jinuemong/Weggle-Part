package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

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
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.AdditionalImageAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemProductAdditionalAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAdditionalProductBinding

class AdditionalProductFragment : Fragment() {
    private lateinit var activity: AddReviewActivity
    private lateinit var masterApp : MasterApplication
    private var _binding : FragmentAdditionalProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var reviewProductId : Int =0  //현재 등록 중인 리뷰 제외
    private lateinit var searchAdapter : ItemProductAdditionalAdapter
    private lateinit var imageAdapter: AdditionalImageAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AddReviewActivity
        masterApp = activity.masterApp
        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity.returnView(this@AdditionalProductFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       arguments?.let {
           reviewProductId = it.getInt("productId")
       }
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
        searchAdapter = ItemProductAdditionalAdapter(activity,reviewProductId)
        imageAdapter = AdditionalImageAdapter(activity)
        binding.searchRecycler.adapter = searchAdapter
        binding.imageRecycler.adapter = imageAdapter

    }

    private fun setUpListener(){
        searchAdapter.apply {
            setOnItemClickListener(object :ItemProductAdditionalAdapter.OnItemClickListener{
                override fun addItem(data : Product) {
                    imageAdapter.addData(data)
                }

                override fun delItem(data: Product) {
                    imageAdapter.delData(data)
                }

            })
        }

        imageAdapter.apply {
            setOnItemClickListener(object :AdditionalImageAdapter.OnItemClickListener{
                override fun onItemClick(data: Product) {
                    searchAdapter.setDataFromFragment(data)
                }

            })
        }
    }

    private fun setSearchData(p0:String?){
        if (p0!=null && p0!="") {
            val searchData = p0.toString()
            ProductManager(masterApp)
                .searchProduct(searchData, paramFun = { dataSet, _ ->
                    if (dataSet!=null){
                        searchAdapter.setData(dataSet,searchData)
                    }else{
                        searchAdapter.setData(ArrayList(),"changeText")
                    }
                })
        }else{
            searchAdapter.setData(ArrayList(),"changeText")
        }
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
