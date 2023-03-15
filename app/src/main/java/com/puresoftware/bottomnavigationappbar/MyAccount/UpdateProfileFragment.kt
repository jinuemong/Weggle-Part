package com.puresoftware.bottomnavigationappbar.MyAccount

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentUpdateProfileBinding


class UpdateProfileFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private var _binding : FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback : OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        callback = object  : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                backFragment()
            }

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){

    }

    private fun setUpListener(){
        binding.backButton.setOnClickListener {
            backFragment()
        }
    }

    private fun backFragment(){
        mainActivity.goBackFragment(this@UpdateProfileFragment)
        mainActivity.setMainViewVisibility(true)
    }


}