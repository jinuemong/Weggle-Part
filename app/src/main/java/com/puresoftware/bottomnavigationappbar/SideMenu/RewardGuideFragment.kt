package com.puresoftware.bottomnavigationappbar.SideMenu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentRewardGuideBinding


class RewardGuideFragment : Fragment() {
    private var viewStack = 1
    private var _binding : FragmentRewardGuideBinding? = null
    private val binding get() = _binding!!

    private lateinit var  callback : OnBackPressedCallback
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        callback =object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                mainActivity.setSubFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this,callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewStack = it.getInt("stack")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRewardGuideBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = listOf(
            "http://kooru.be/dev/image/4cac8822f850e101769e9651d3d2047d.png",
            "http://kooru.be/dev/image/5d68feb1b9f6c9ca82fa15711ea2506c.png",
        "http://kooru.be/dev/image/870a0cb1db257c9fc5be1739996654b5.png",
        "http://kooru.be/dev/image/45ad9fc219d522db12f00108d9f754e9.png",
        "http://kooru.be/dev/image/81b4c18c81c7877497be759a96151dd4.png",
        "http://kooru.be/dev/image/783fd1997be5a88306f0cb89d27a1d13.png",
        "http://kooru.be/dev/image/ffdf21cdc825ce10dc8b8abb7785c331.png",
        "http://kooru.be/dev/image/b8873a9b7ae62037b0797dff64da369c.png",
        "http://kooru.be/dev/image/7ccbe7b4ce67c5ab501896ee42b7f782.png",
        "http://kooru.be/dev/image/de03ef3c8cbee8c12df1d8e481c92255.png"
        )
        binding.backButton.setOnClickListener {
            if (viewStack==1){
                mainActivity.setSubFragment()
            }
        }
    }

    companion object{

        @JvmStatic
        fun newInstance(stack : Int)=
            RewardGuideFragment().apply {
                arguments=Bundle().apply {
                    putInt("stack",stack)
                }
            }
        }

}



