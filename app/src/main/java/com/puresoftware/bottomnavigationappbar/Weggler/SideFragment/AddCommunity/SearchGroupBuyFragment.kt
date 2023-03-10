package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentSearchGroupBuyBinding


class SearchGroupBuyFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private var _binding: FragmentSearchGroupBuyBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback : OnBackPressedCallback
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
        setUpListener()
    }

    private fun setUpListener(){
        binding.cancelButton.setOnClickListener {
            cancel()
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