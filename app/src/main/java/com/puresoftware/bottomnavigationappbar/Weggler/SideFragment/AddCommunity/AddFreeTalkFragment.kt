package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityManagerWithReview
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityDataBody
import com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityFragment.ShellFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAddFreeTalkBinding


class AddFreeTalkFragment : Fragment() {
    private var _binding : FragmentAddFreeTalkBinding? = null
    private val binding get()=_binding!!
    private lateinit var mainActivity:MainActivity
    private lateinit var fm:FragmentManager
    private val type = 2 //free
    private var subject = ""
    private var text  = ""
    private var filePath : Uri?=null //이미지 파일
    private var linkUrl = ""
    private var gallerySlideFragment  :GallerySlideFragment?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        fm = mainActivity.fragmentManager!!
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

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("InflateParams", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 다른 뷰 접근

        gallerySlideFragment = GallerySlideFragment()
                //클릭 이벤트 적용
            .apply {
                setOnItemClickListener(object :GallerySlideFragment.OnItemClickListener{
                    override fun onItemClick(imageUri: Uri?) {
                        setSlideView()
                        if (imageUri!=null){
                            binding.uploadText.visibility = View.INVISIBLE
                            binding.uploadImage.visibility = View.INVISIBLE
                            filePath = imageUri
                            //Linear에 이미지 넣기
                            Glide.with(mainActivity)
                                .load(imageUri)
                                .into(object : CustomTarget<Drawable>(){
                                    override fun onResourceReady(
                                        resource: Drawable,
                                        transition: Transition<in Drawable>?
                                    ) {
                                        val layout = binding.uploadLinear
                                        layout.background = resource
                                    }

                                    override fun onLoadCleared(placeholder: Drawable?) {}

                                })
                        }else{
                            filePath = null
                            binding.uploadText.visibility = View.VISIBLE
                            binding.uploadImage.visibility = View.VISIBLE
                            binding.uploadLinear.setBackgroundResource(R.drawable.round_border_plus)
                        }
                    }
                })
            }
        //슬라이드 레이아웃 view 설정
        gallerySlideFragment?.let { fragment ->
            fm.beginTransaction()
                .replace(R.id.slide_layout_in_shell, fragment)
                .commit()
        }
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
    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ResourceType")
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
        // 사진 불러오기
        binding.uploadLinear.setOnClickListener {
            setSlideView()
        }

        // 게시물 작성 가능 : Post
        binding.uploadButton.setOnClickListener {
            if (subject != "" && text.length >= 10) {
                if (mainActivity.communityViewModel.communityProduct != null) {
                    val multiCommunityData = MultiCommunityDataBody(
                        type, subject, text, linkUrl, -1
                    )
                    CommunityManagerWithReview(mainActivity.masterApp)
                        .addCommunityReviewTypeFree(
                            mainActivity.communityViewModel.communityProduct!!.productId,multiCommunityData, filePath,
                            mainActivity, paramFunc = { data , message ->
                                if (data == null) {
                                    Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT)
                                        .show()
                                    Log.d("message",message!!)
                                } else {
                                    //view model에 데이터 추가 후 메인으로 이동
                                    mainActivity.communityViewModel.addCommunityData(data)
                                    mainActivity.communityViewModel.addMyPostingData(data)
                                    mainActivity.communityViewModel.addPopularPostingData(data)
                                    mainActivity.goBackFragment(this@AddFreeTalkFragment)
                                    mainActivity.setMainViewVisibility(true)
                                }
                            })
                }
            }
        }
    }
    private fun setSlideView(){
        (mainActivity.supportFragmentManager.findFragmentByTag("add free talk")
                as ShellFragment).setSlide()
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