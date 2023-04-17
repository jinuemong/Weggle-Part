package com.puresoftware.bottomnavigationappbar.SideMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentUserAddressBinding


//배송지 관리 - 보류

class UserAddressFragment : Fragment() {
    private var _binding : FragmentUserAddressBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_address, container, false)
    }

}