package com.puresoftware.bottomnavigationappbar.Home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentDetailSellInfoBinding
import com.puresoftware.bottomnavigationappbar.databinding.FragmentFashionBinding


class DetailSellInfoFragment : Fragment() {
    private lateinit var binding:FragmentDetailSellInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailSellInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

}