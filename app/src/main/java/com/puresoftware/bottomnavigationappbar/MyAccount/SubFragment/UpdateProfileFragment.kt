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
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.KeywordAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.UserManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserBody
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.AboutCommunity.AddCommunity.GallerySlideFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentUpdateProfileBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class UpdateProfileFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private var _binding : FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback : OnBackPressedCallback
    private lateinit var keywordAdapter: KeywordAdapter

    private var gallerySlideFragment : GallerySlideFragment? = null

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

    @SuppressLint("CommitTransaction")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gallerySlideFragment = GallerySlideFragment()
        //갤러리 뷰 변경
        gallerySlideFragment?.let {fragment->
            mainActivity.fragmentManager!!
                .beginTransaction()
                .replace(R.id.slide_layout,fragment)
                .commit()
        }
        initView()
        setUpListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
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
                binding.commentNum.text = "${user.body.userComment.toString().length}/50"
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

        //프로필 선택
        binding.selectProfile.setOnClickListener {
            gallerySlideFragment?.apply {
                setOnItemClickListener(object : GallerySlideFragment.OnItemClickListener{
                    override fun onItemClick(imageUri: Uri?) {
                        setSlide() //클릭 시 닫기
                        mainActivity.myAccountViewModel.newProfileImage = imageUri
                        imageUri?.let {
                            Glide.with(mainActivity)
                                .load(it)
                                .into(binding.userImage)
                        }
                    }
                })
            }
            setSlide() //클릭 시 열기
        }

        // 배경 선택
        binding.selectBackground.setOnClickListener {
            gallerySlideFragment?.apply {
                setOnItemClickListener(object : GallerySlideFragment.OnItemClickListener{
                    override fun onItemClick(imageUri: Uri?) {
                        setSlide() //클릭 시 닫기
                        mainActivity.myAccountViewModel.newBackgroundImage = imageUri
                        imageUri?.let {
                            Glide.with(mainActivity)
                                .load(it)
                                .into(binding.userBackImage)
                        }
                    }
                })
            }
            setSlide() //클릭 시 열기
        }

        //글자 수 카운팅
        binding.typeUserComment.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                binding.commentNum.text = "${p0.toString().length}/50"
            }
        })

        // 수정
        binding.commitButton.setOnClickListener {
            mainActivity.myAccountViewModel.apply {
                if(this.newProfileImage!=null && this.newBackgroundImage!=null){
                    this.updateUserImages(mainActivity, paramFunc = { data,message->
                        if (data!=null){ this.userProfile = data }
                        else{
                            Toast.makeText(mainActivity,"error:$message"
                                ,Toast.LENGTH_LONG).show()
                        }
                    })
                }
                if (binding.typeName.text.length in 2..10) {
                    UserManager(mainActivity.masterApp)
                        .updateUserInfo(
                            null, null, null,
                            UserBody(getComment(),getKeyword(),
                            getInstagramUrl(),getBlogUrl(),getYoutubeUrl()),
                        paramFun = {data,message->
                            if (data!=null){ this.userProfile = data }
                            else{
                                Toast.makeText(mainActivity,"error:$message"
                                    ,Toast.LENGTH_LONG).show()
                            }
                        })
                }else{
                    Toast.makeText(mainActivity,"user name error"
                        ,Toast.LENGTH_LONG).show()
                }

                Thread.sleep(500)
                backFragment()
            }
        }
    }

    private fun getComment() : String?{
        return if(binding.typeUserComment.text.toString()==""){
            null
        }else{
            binding.typeUserComment.text.toString()
        }
    }

    private fun getKeyword() : ArrayList<String>?{
        val keywordList = keywordAdapter.getSelectedList()
        return if(keywordList.size==0){null}
        else keywordList
    }

    private fun getInstagramUrl():String?{
        return if(binding.instagramUrl.text.toString()==""){
            null
        }else{
            binding.instagramUrl.text.toString()
        }
    }

    private fun getBlogUrl():String?{
        return if(binding.blogUrl.text.toString()==""){
            null
        }else{
            binding.blogUrl.text.toString()
        }
    }

    private fun getYoutubeUrl():String?{
        return if(binding.youtubeUrl.text.toString()==""){
            null
        }else{
            binding.youtubeUrl.text.toString()
        }
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
        mainActivity.myAccountViewModel.apply {
            newProfileImage = null
            newBackgroundImage = null
        }
        mainActivity.goBackFragment(this@UpdateProfileFragment)
        mainActivity.setMainViewVisibility(true)
    }

}