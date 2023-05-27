package com.puresoftware.bottomnavigationappbar.Home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentDetailAskBinding
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFashionBinding
import com.puresoftware.bottomnavigationappbar.databinding.FragmentProductDetailBinding


class DetailAskFragment : Fragment() {
    private lateinit var binding: FragmentDetailAskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailAskBinding.inflate(inflater, container, false)
        return binding.root
    }


}