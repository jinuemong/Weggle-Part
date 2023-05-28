package com.puresoftware.bottomnavigationappbar.Weggler.AboutCommunity.AddCommunity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemProductDetailAdapter
import com.puresoftware.bottomnavigationappbar.databinding.FragmentSearchGroupBuyBinding


class SearchGroupBuyFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private var _binding: FragmentSearchGroupBuyBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback : OnBackPressedCallback
    private lateinit var adapter : ItemProductDetailAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                cancel()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchGroupBuyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ItemProductDetailAdapter(mainActivity, "no category")
        binding.productList.adapter = adapter
        setUpListener()

        binding.searchBar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                setSearchData(p0.toString())
            }

        })

    }

    private fun setUpListener(){
        binding.cancelButton.setOnClickListener {
            cancel()
        }

        adapter.apply {
            // 클릭 시 데이터 전달
            setOnItemClickListener(object:ItemProductDetailAdapter.OnItemClickListener{
                override fun click(id: Int) {
                    select(id)
                }

            })
        }
    }

    private fun setSearchData(p0:String?){
        if (p0!=null){
            ProductManager(mainActivity.masterApp)
                .searchProduct(p0, paramFun = {newData,_->
                    if (newData!=null){
                        adapter.setSearchData(newData,p0)
                    }else{
                        adapter.setSearchData(ArrayList(),"text")
                    }

                })
        }else{
            adapter.setSearchData(ArrayList(),"text")
        }

    }

    private fun cancel(){
        mainActivity.goBackFragment(this@SearchGroupBuyFragment)
    }

    private fun select(pId : Int){
        val bundle  = Bundle()
        bundle.putInt("pId",pId)
        mainActivity.supportFragmentManager.setFragmentResult("productId",bundle)
        mainActivity.goBackFragment(this@SearchGroupBuyFragment)
    }
}