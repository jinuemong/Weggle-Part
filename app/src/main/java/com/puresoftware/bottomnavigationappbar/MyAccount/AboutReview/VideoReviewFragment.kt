package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.MyAccount.ViewModel.AddReviewViewModel
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getImageFilePath
import com.puresoftware.bottomnavigationappbar.databinding.FragmentVideoReviewBinding
import java.text.DecimalFormat
import java.util.jar.Manifest

//step 2 : video add
// 영상을 등록하고 리뷰 작성

class VideoReviewFragment : Fragment() {
    private var _binding : FragmentVideoReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var addReviewModel : AddReviewViewModel
    private var videoUrl : Uri? = null // 리뷰 비디오
    private lateinit var activity: AddReviewActivity
    private lateinit var onBackPressedCallback: OnBackPressedCallback //뒤로가기 동작 제어
    private lateinit var readGalleryListener : PermissionListener //갤러리 접근 인증

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AddReviewActivity
        addReviewModel = activity.addReviewModel
        onBackPressedCallback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity.returnView(this@VideoReviewFragment)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoReviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        onBackPressedCallback.remove()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("VideoReviewFragment:Id ",addReviewModel.reviewProduct?.productId.toString())

        // 권한 설정
        readGalleryListener = object : PermissionListener{
            override fun onPermissionGranted() {
                getVideo()
            }

            // 권한 실패
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(activity,"권한 필요",Toast.LENGTH_SHORT)
                    .show()
                activity.returnView(this@VideoReviewFragment)
            }

        }

        // 갤러리 권한 요청
        TedPermission.with(activity.applicationContext)
            .setPermissionListener(readGalleryListener)
            .setPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).check()

        // 뷰 초기화
        initView()
        setUpListener()

        //리뷰 텍스트 기능
        binding.reviewText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                binding.textLen.text = "${p0.toString().length}/최대300자"
            }

        })
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode==RESULT_OK && it.data != null){
            videoUrl  = it.data!!.data

            if (!videoUrl.toString().contains("video")) { //비디오 확인
                Toast.makeText(activity,"비디오만 업로드 가능합니다."
                    ,Toast.LENGTH_LONG)
                    .show()
                activity.returnView(this@VideoReviewFragment)

            }else if(!checkVideoTime()){
                Toast.makeText(activity,"영상 길이 : 10 ~ 30초"
                    ,Toast.LENGTH_LONG)
                    .show()
                activity.returnView(this@VideoReviewFragment)

            }else {
                //비디오 뷰 설정
                Log.d("videoUrl : ", videoUrl.toString())
                binding.videoView.apply {
                    layoutParams = binding.videoViewShell.layoutParams
                    setVideoURI(videoUrl)
                    setMediaController(null)
                    setOnClickListener {
                        if (this.isPlaying){
                            binding.playButton.visibility = View.VISIBLE
                            pause()
                        }else {
                            binding.playButton.visibility = View.GONE
                            start()
                        }
                    }
                    setOnCompletionListener {
                        binding.playButton.visibility = View.VISIBLE
                    }
                }
            }

        }
    }


    @SuppressLint("SetTextI18n")
    private fun initView(){
        //프로덕트 등록
        val product = addReviewModel.reviewProduct
        if (product != null) {
            binding.productCompany.text = product.body.company
            binding.productName.text = product.name
            //가격 변환 (컴마찍기) 등록
            val decimal = DecimalFormat("#,###")
            binding.productPrice.text = "${decimal.format(product.body.price)}원"
            //이미지 등록
            Glide.with(activity)
                .load(product.subjectFiles[0])
                .into(binding.productImage)
            //판매 수수료 등록
            binding.rewardCost.text = "리워드 ${(product.body.price * 0.05).toInt()}원"
        }

        setButtonColor()

    }

    private fun setUpListener(){
        binding.selectVideo.setOnClickListener {
            getVideo()
        }
        binding.backButton.setOnClickListener {
            activity.returnView(this@VideoReviewFragment)
        }

        // 다른 프로덕트 선택으로 이동
        binding.selectProduct.setOnClickListener {
            activity.changeView(AdditionalProductFragment())
        }
    }

    //갤러리의 비디오 얻어오기
    private fun getVideo(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "video/*"
        activityResult.launch(intent)
        setButtonColor()
    }

    //비디오 시간 체크
    private fun checkVideoTime(): Boolean{
        if (videoUrl!=null) {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(activity,videoUrl)
            val time  = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val duration = time!!.toLong()/1000
            val hours = duration/3600
            val minutes = (duration -hours*3600)/60
            val seconds = duration - (hours * 3600 + minutes*60)
            Log.d("video seconds : ",seconds.toString())
            if (seconds in 10..30){
                return true
            }

        }
        return false
    }

    // 리뷰 등록 가능할 때 버튼 색상 변경
    private fun setButtonColor(){
        if (videoUrl!=null && binding.reviewText.text.toString()!=""){
            binding.commitButton.setBackgroundResource(R.drawable.round_border_selected)
        }else{
            binding.commitButton.setBackgroundResource(R.drawable.round_border_unselected)
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(id : Int) =
            VideoReviewFragment().apply {
                arguments = Bundle().apply {
                    putInt("productId",id)
                }
            }
    }
}