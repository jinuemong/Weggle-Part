package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAddFreeTalkBinding


class AddFreeTalkFragment : Fragment() {
    private var _binding : FragmentAddFreeTalkBinding? = null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFreeTalkBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }

    private fun initView(){

    }
    private fun setUpListener(){
        binding.delSubject.setOnClickListener {
            binding.typSubject.text = null
            binding.subjectNum.text = "0"
        }
        binding.delLink.setOnClickListener {
            binding.typLink.text = null
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun setButtonColor(isOk:Boolean){
        if (isOk){
            binding.uploadButton.setBackgroundColor(R.color.community__my_color)
        }else{
            binding.uploadButton.setBackgroundColor(R.color.line_color)
        }

    }

}