package com.puresoftware.bottomnavigationappbar.MyAccount.SubFragment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.KeywordAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity.GallerySlideFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentUpdateProfileBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class UpdateProfileFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private var _binding : FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback : OnBackPressedCallback
    private lateinit var keywordAdapter: KeywordAdapter

    private var gallerySlideFragment : GallerySlideFragment? = null

    // new Data
    private var newProfileImage : Uri? = null
    private var newBackgroundImage : Uri? = null

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

        gallerySlideFragment = GallerySlideFragment()
            .apply {
                setOnItemClickListener(object : GallerySlideFragment.OnItemClickListener{
                    override fun onItemClick(imageUri: Uri?) {
                        setSlide()
                        if ()
                    }
                })
            }
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
            val userKeyword = user.body?.userKeyword?: arrayListOf()
            keywordAdapter = KeywordAdapter(mainActivity,userKeyword,"updateProfile")
            binding.tagBox.adapter = keywordAdapter

            user.body?.instagramUrl?.let {
                binding.instagramUrl.setText(it)
            }
            user.body?.blogUrl?.let {
                binding.blogUrl.setText(it)
            }

            user.body?.youtubeUrl?.let {
                binding.youtubeUrl.setText(it)
            }
        }

    }

    private fun setUpListener(){
        binding.backButton.setOnClickListener {
            backFragment()
        }

        binding.selectProfile.setOnClickListener {
            setSlide()
        }

        binding.selectBackground.setOnClickListener {
            setSlide()
        }

        binding.typeUserComment.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                binding.commentNum.text = "${p0.toString().length}/50"
            }
        })
    }

    private fun setSlide(){
        val state = binding.frameLayout.panelState
        if (state == SlidingUpPanelLayout.PanelState.COLLAPSED){
            binding.frameLayout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }else if(state == SlidingUpPanelLayout.PanelState.EXPANDED){
            binding.frameLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }
    }
    private fun backFragment(){
        mainActivity.goBackFragment(this@UpdateProfileFragment)
        mainActivity.setMainViewVisibility(true)
    }

}