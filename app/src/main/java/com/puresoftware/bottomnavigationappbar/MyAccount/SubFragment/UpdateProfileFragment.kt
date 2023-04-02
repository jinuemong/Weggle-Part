package com.puresoftware.bottomnavigationappbar.MyAccount.SubFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.KeywordAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Unit.tagList
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
        mainActivity.myAccountViewModel.userProfile?.let {user->
            user.profile?.let {
                Glide.with(mainActivity)
                    .load(user.profile)
                    .into(binding.userImage)
            }
            user.background?.let{
                Glide.with(mainActivity)
                    .load(user.background)
                    .into(binding.userBackImage)
            }

            binding.typeName.setText(user.name)
            binding.emailText.text = user.email

            if (user.body?.userComment!=null){
                binding.typeUserComment.setText(user.body.userComment)
            }
            val userKeyword = user.body?.userKeyword?: listOf()
            binding.tagBox.adapter = KeywordAdapter(mainActivity,userKeyword,"updateProfile")
                .apply {
                    setOnItemClickListener(object :KeywordAdapter.OnItemClickListener{
                        override fun onSelected(item: String) {

                        }
                    })
                }
        }

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