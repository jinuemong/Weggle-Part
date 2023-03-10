package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAddJointPurchaseBinding

class AddJointPurchaseFragment : Fragment() {
    private var _binding : FragmentAddJointPurchaseBinding? = null
    private val binding get()=_binding!!
    private lateinit var mainActivity:MainActivity
    private val type = 1 //joint
    private var subject=""
    private var text = ""
    private var jointProductId = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
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

        setFragmentResultListener("productId"){ _, bundle ->
            jointProductId = bundle.getInt("pId",-1)
        }
    }

    private fun initView(){
        setButtonColor()
        binding.typSubject.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!=null) {
                    binding.subjectNum.text=p0.length.toString()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
                if(p0!=null) {
                    subject = p0.toString()
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
                    text = p0.toString()
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

        // 게시물 작성 가능 : Post

        binding.uploadButton.setOnClickListener {
            if (subject != "" && text.length >= 10 && jointProductId != -1) {
                // add free talk 처럼 제작 후 되돌아가기
                mainActivity.goBackFragment(this@AddJointPurchaseFragment)
                mainActivity.setMainViewVisibility(true)
            }
        }

        binding.selectProduct.setOnClickListener {
            mainActivity.changeFragment(SearchGroupBuyFragment())
        }

    }
    @SuppressLint("ResourceAsColor")
    private fun setButtonColor(){
        if (subject!="" && text.length>=10 && jointProductId!=-1){
            binding.uploadButton.setBackgroundResource(R.drawable.round_border_selected)
        }else{
            binding.uploadButton.setBackgroundResource(R.drawable.round_border_unselected)
        }

    }
}