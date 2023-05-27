package com.puresoftware.bottomnavigationappbar.Home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.puresoftware.bottomnavigationappbar.Home.adapter.ImageRecyclerAdapter
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFashionBinding
import com.puresoftware.bottomnavigationappbar.databinding.FragmentProductInfoBinding

class ProductInfoFragment(private val list:List<String>) : Fragment() {
    private lateinit var binding:FragmentProductInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productInfoRecyclerView.adapter = ImageRecyclerAdapter(requireContext(),list)
        binding.productInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }

}