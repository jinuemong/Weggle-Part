package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.AddCommunity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityPostManager
import com.puresoftware.bottomnavigationappbar.Weggler.Model.MultiCommunityData
import com.puresoftware.bottomnavigationappbar.Weggler.Server.WegglerApplication
import com.puresoftware.bottomnavigationappbar.databinding.FragmentAddFreeTalkBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class AddFreeTalkFragment( private val mainFrame : SlidingUpPanelLayout) : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gallerySlideFragment = GallerySlideFragment(mainFrame)
                //클릭 이벤트 적용
            .apply {
                setOnItemClickListener(object :GallerySlideFragment.OnItemClickListener{
                    override fun onItemClick(imageUri: Uri?) {
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
        // 사진 촬영 기능
        binding.uploadLinear.setOnClickListener {
            val state = mainFrame.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                mainFrame.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
            // 열린 상태일 경우 닫기
            else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                mainFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }

        // 게시물 작성 가능 : Post
        binding.uploadButton.setOnClickListener {
            if (subject != "" && text.length >= 10) {
                val multiCommunityData = MultiCommunityData(
                    type, subject, text, linkUrl,"")

                CommunityPostManager(mainActivity.application as WegglerApplication)
                    .addCommunityData(multiCommunityData,filePath,
                        mainActivity,paramFunc = {
                        if (it==null){
                            Toast.makeText(mainActivity,"NetworkErr", Toast.LENGTH_SHORT)
                                .show()
                        }else{
                            mainActivity.goBackFragment(this@AddFreeTalkFragment)
                            mainActivity.setMainViewVisibility(true)
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