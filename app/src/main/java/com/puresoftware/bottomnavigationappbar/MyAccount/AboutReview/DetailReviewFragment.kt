package com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.ItemProductHoAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ReviewManager
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.MessageFragment
import com.puresoftware.bottomnavigationappbar.databinding.FragmentDetailReviewBinding
import org.mozilla.javascript.tools.jsc.Main

// 리뷰 상세보기
class DetailReviewFragment : Fragment() {
    private var reviewId = -1
    private lateinit var mainActivity: MainActivity
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var _binding: FragmentDetailReviewBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backEvent()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            reviewId = it.getInt("reviewId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ReviewManager(mainActivity.masterApp, null)
            .getDetailReview(reviewId, paramFunc = { data, message ->
                if (data != null) {

                    binding.reviewBox.apply {
                        //비디오 설정
                        videoView.layoutParams = videoContainer.layoutParams
                        getThumbnail(data.thumbnail)

                        videoView.setVideoPath(data.resource)
                        videoView.setMediaController(null)
                        videoView.setOnClickListener {
                            videoView.setBackgroundResource(0) // 썸네일 제거
                            if (videoView.isPlaying) {
                                playButton.visibility = View.VISIBLE
                                videoView.pause()
                            } else {
                                playButton.visibility = View.GONE
                                videoView.start()
                            }
                        }
                        videoView.setOnCompletionListener {
                            playButton.visibility = View.VISIBLE
                        }

                        likeNum.text = data.likeCount.toString()
                        commentNum.text = data.commentCount.toString()
                        bodyText.text = data.body.reviewText
                        data.userInfo?.let { user->
                            userName.text = user.id
                            Glide.with(mainActivity)
                                .load(user.profileFile)
                                .into(userImage)
                        }

                        // 하단 프로덕트 리스트에 리뷰 남긴 프로덕트 추가
                        var productList= arrayListOf<Int>()
                        if (data.body.additionalProduct!=null) productList=data.body.additionalProduct!!
                        productList.add(0,data.productId)
                        setProductList(productList)

                    }



                } else {
                    // 존재하지 않는 경우
                    val messageBox = MessageFragment.newInstance("존재하지 않는 리뷰 입니다. $message")
                    messageBox.show(mainActivity.fragmentManager!!, null)
                    messageBox.apply {
                        setItemClickListener(object : MessageFragment.OnItemClickListener {
                            override fun onItemClick() {
                                backEvent()
                            }
                        })
                    }
                }
            })

        setUpListener()
    }

    private fun setUpListener() {
        binding.backButton.setOnClickListener {
            backEvent()
        }

        binding.reviewBox.commentNum.setOnClickListener { goCommentList() }
        binding.reviewBox.commentImage.setOnClickListener { goCommentList() }

        binding.reviewBox.likeNum.setOnClickListener { setLikeReview() }
        binding.reviewBox.likeImage.setOnClickListener { setLikeReview() }
    }

    private fun setProductList(productList: ArrayList<Int>){
        ProductManager(mainActivity.masterApp)
            .getAdditionalProductList(productList, paramFunc = { dataSet,_->
                if(dataSet!=null){
                    binding.reviewBox.productListView.adapter = ItemProductHoAdapter(mainActivity,dataSet)
                }
            })
    }

    private fun getThumbnail(path: String) {
        Glide.with(mainActivity)
            .load(path)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val layout = binding.reviewBox.videoView
                    layout.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
    private fun goCommentList(){

    }

    private fun setLikeReview(){

    }

    companion object {

        @JvmStatic
        fun newInstance(reviewId: Int) =
            DetailReviewFragment().apply {
                arguments = Bundle().apply {
                    putInt("reviewId", reviewId)
                }
            }
    }

    // 뒤로가기 이벤트 처리
    private fun backEvent() {
        mainActivity.goBackFragment(this@DetailReviewFragment)
        mainActivity.setMainViewVisibility(true)
    }
}