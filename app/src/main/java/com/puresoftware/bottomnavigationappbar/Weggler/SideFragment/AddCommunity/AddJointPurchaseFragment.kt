package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAddJointPurchaseBinding

class AddJointPurchaseFragment : Fragment() {
    private var _binding : FragmentAddJointPurchaseBinding? = null
    private val binding get()=_binding!!
    private val type = 1 //joint
    private var mainImage = ""
    private var subject=""
    private var text = ""
    private var jointProduct = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddJointPurchaseBinding.inflate(inflater,container,false)
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
        binding.typSubject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!=null) {
                    binding.subjectNum.text=p0.length.toString()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0!=null) {
                    subject = p0.length.toString()
                    setButtonColor()
                }
            }
        })

        binding.typContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!=null) {
                    binding.commentNum.text=p0.length.toString()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0!=null) {
                    text = p0.length.toString()
                    setButtonColor()
                }
            }
        })
    }
    private fun setUpListener(){
        binding.delSubject.setOnClickListener {
            binding.typSubject.text = null
            binding.subjectNum.text = "0"
            subject = ""
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun setButtonColor(){
        if (subject!="" && text.length>=10 && jointProduct!=""){
            binding.uploadButton.setBackgroundColor(R.color.community__my_color)
        }else{
            binding.uploadButton.setBackgroundColor(R.color.line_color)
        }

    }
}