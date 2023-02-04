package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent
import com.puresoftware.bottomnavigationappbar.Weggler.Model.type_free
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.databinding.FragmentDetailCommunityPostingBinding

class DetailCommunityPostingFragment(
    val type:String,
    data : CommunityContent
) : Fragment() {

    private var _binding : FragmentDetailCommunityPostingBinding? = null
    private val binding  get() = _binding!!
    private lateinit var mainActivity: MainActivity

    val posting = data

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentDetailCommunityPostingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }

    private fun initView(){
        binding.sujectText.text = posting.body.subject
        binding.contentText.text = posting.body.text
        binding.createTime.text= getTimeText(posting.createTime)
    }
    private fun setUpListener(){
        binding.backButton.setOnClickListener {
            mainActivity.goBackFragment(this@DetailCommunityPostingFragment)
            if (type=="main"){
                mainActivity.setMainViewVisibility(true)
            }
        }
    }

    private fun addComment(){
        binding.commentEdit.apply {
            if (text.toString()!=""){
                //comment 추가
            }
            setText("")
        }
    }

    private fun delComment(){

    }

}