package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityPostManager
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAddFreeTalkBinding


class AddFreeTalkFragment : Fragment() {
    private var _binding : FragmentAddFreeTalkBinding? = null
    private val binding get()=_binding!!
    private lateinit var mainActivity:MainActivity
    private val type = 2 //free
    private var mainImage =""
    private var subject = ""
    private var text  = ""
    private var linkUrl = ""
    private var urlList  = ArrayList<String>()
    private val totalLike =0
    private  val totalComment =0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
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
        setButtonColor()
        binding.typSubject.addTextChangedListener(object :TextWatcher{
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

        binding.typContent.addTextChangedListener(object : TextWatcher{
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
        binding.delLink.setOnClickListener {
            binding.typLink.text = null
            linkUrl = ""
        }
        // 게시물 작성 가능 : Post
        binding.uploadButton.setOnClickListener {
            if (subject != "" && text.length >= 10) {
                val multiCommunityData = MultiCommunityData(
                    type, mainImage, subject, text, linkUrl, arrayListOf(),
                "",0,0)
                CommunityPostManager(mainActivity.application as WegglerApplication)
                    .addCommunityFreeTalk(multiCommunityData, paramFunc = {
                        if (it==null){
                            Toast.makeText(mainActivity,"NetworkErr", Toast.LENGTH_SHORT)
                                .show()
                        }else{
                            mainActivity.goBackFragment(this@AddFreeTalkFragment)
                        }
                    })
            }
        }
    }
    @SuppressLint( "ResourceType")
    private fun setButtonColor(){
        if (subject!="" && text.length>=10){
            binding.uploadButton.setBackgroundResource(R.drawable.round_border_selected)
        }else{
            binding.uploadButton.setBackgroundResource(R.drawable.round_border_unselected)
        }

    }

}