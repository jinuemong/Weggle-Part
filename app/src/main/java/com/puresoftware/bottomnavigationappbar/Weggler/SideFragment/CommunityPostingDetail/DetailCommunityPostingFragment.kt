package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPostingDetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ProductManager
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommentAdapter
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityCommentManager
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityManagerWithReview
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.MessageFragment
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.isVideo
import com.puresoftware.bottomnavigationappbar.databinding.FragmentDetailCommunityPostingBinding
import java.text.DecimalFormat

//type : MainFragment에서 왔다면 setMainViewVisibility (뷰 감추기 )
class DetailCommunityPostingFragment : Fragment() {
    private var reviewId: Int = -1
    private var type: String? = null
    private var _binding: FragmentDetailCommunityPostingBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var wegglerApp: MasterApplication
    private lateinit var commentAdapter: ItemCommentAdapter
    private lateinit var communityComment: CommunityCommentManager
    private lateinit var communityPost: CommunityManagerWithReview
    private lateinit var callback: OnBackPressedCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        wegglerApp = mainActivity.masterApp
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainActivity.goBackFragment(this@DetailCommunityPostingFragment)
                if (type == "main") {
                    mainActivity.setMainViewVisibility(true)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            reviewId = it.getInt("reviewId", -1)
            type = it.getString("type", null)
            if (type == "main") { //main에서 왔다면 뷰 숨기기
                mainActivity.setMainViewVisibility(false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCommunityPostingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communityComment = CommunityCommentManager(wegglerApp)
        communityPost = CommunityManagerWithReview(wegglerApp)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        if (reviewId != -1) {
            communityPost.getReviewFromId(reviewId, paramFunc = { posting, message ->
                if (posting != null) {
                    binding.sujectText.text = posting.body.subject
                    binding.contentText.text = posting.body.text
                    binding.createTime.text = getTimeText(posting.createTime)
                    binding.likeNum.text = posting.likeCount.toString()

                    if (posting.body.type == 1) {
                        setType1()
                        setNotRe() // 프로덕트는 이미지 x
                    } else {
                        // 프리토크
                        setType2()
                        when (isVideo(posting.resource)) {
                            true -> {
                                //video
                                setVideo()
                                videoPlaySetting(posting)
                            }
                            false -> {
                                //image
                                setImage()
                                Glide.with(mainActivity)
                                    .load(posting.resource)
                                    .into(binding.imageView)
                            }
                            else -> {
                                setNotRe() // null
                            }
                        }
                    }


                    //Url 구분
                    if (posting.body.linkUrl == "") {
                        binding.linkView.visibility = View.GONE
                    } else {
                        binding.linkUrl.text = posting.body.linkUrl
                    }

                    //product 구분
                    if (posting.body.jointProductId >= 0) {
                        setJointProduct(posting.body.jointProductId)
                    } else {
                        binding.groupBuyProduct.visibility = View.GONE
                    }

                    initComment(posting.reviewId)
                    setUpListener(posting)
                } else {
                    // 존재하지 않는 경우
                    val messageBox = MessageFragment.newInstance("존재하지 않는 게시물 입니다. $message")
                    messageBox.show(mainActivity.fragmentManager!!, null)
                    messageBox.apply {
                        setItemClickListener(object : MessageFragment.OnItemClickListener {
                            override fun onItemClick() {
                                mainActivity.goBackFragment(this@DetailCommunityPostingFragment)
                            }
                        })
                    }
                }
            })
        }
    }

    private fun setUpListener(posting: ReviewInCommunity) {
        binding.backButton.setOnClickListener {
            mainActivity.goBackFragment(this@DetailCommunityPostingFragment)
            if (type == "main") {
                mainActivity.setMainViewVisibility(true)
            }
        }

        //링크 클릭 - 외부로 연결
        binding.linkUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(posting.body.linkUrl))
            startActivity(intent)
        }

        //comment 추가 버튼
        binding.postComment.setOnClickListener {
            addComment()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setJointProduct(productId: Int) {
        ProductManager(mainActivity.masterApp)
            .getProductFromProductId(productId, paramFun = { item, _ ->
                if (item != null) {
                    //view visible 세팅
                    binding.groupBuyProduct.visibility = View.VISIBLE

                    item.subjectFiles[0].let {
                        Glide.with(mainActivity)
                            .load(it)
                            .into(binding.productImage)
                    }
                    binding.productCompany.text = item.body.company
                    binding.productName.text = item.name
                    binding.salePer.text = "${item.body.discount}%"
                    val decimal = DecimalFormat("#,###")
                    binding.salePrice.text = "${decimal.format(item.body.price)}원"

                    binding.groupBuyProduct.setOnClickListener {
                        //프로덕트 뷰로 이동
                    }
                } else {
                    binding.groupBuyProduct.visibility = View.GONE
                }
            })
    }

    private fun initComment(reviewId: Int) {
        commentAdapter = ItemCommentAdapter(mainActivity, arrayListOf())
        binding.commentView.commentList.adapter = commentAdapter

        communityComment.getReviewCommentList(
            reviewId,
            paramFunc = { data, message ->
                if (message == null) {
                    commentAdapter.setData(data!!)
                    binding.commentNum.text = commentAdapter.itemCount.toString()
                } else {
                    Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun videoPlaySetting(posting: ReviewInCommunity) {
        binding.videoView.apply {
            layoutParams = binding.videoContainer.layoutParams
            setVideoURI(Uri.parse(posting.resource))
            setMediaController(null)

            //썸네일 넣기
            getThumbnail(posting.thumbnail)

            setOnClickListener {
                binding.videoView.setBackgroundResource(0)
                if (this.isPlaying) {
                    binding.playButton.visibility = View.VISIBLE
                    pause()
                } else {
                    binding.playButton.visibility = View.GONE
                    start()
                }
            }
            setOnCompletionListener {
                binding.playButton.visibility = View.VISIBLE
            }
        }
    }

    //back ground 이미지로 썸네일 넣기
    private fun getThumbnail(path: String) {
        Glide.with(mainActivity)
            .load(path)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val layout = binding.videoView
                    layout.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun addComment() {
        binding.commentEdit.apply {
            if (text.toString() != "") {
                //comment 추가
                communityComment.addReviewComment(
                    reviewId,
                    text.toString(),
                    paramFunc = { newData, message ->
                        if (message == null) {
                            binding.commentNum.text = commentAdapter.addData(newData!!)
                            mainActivity.communityViewModel.apply {
                                addMyCommentData(newData)
                            }

                        } else {
                            Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
            setText("")
        }
    }

    private fun delComment() {

    }

    // Like or UnLike
    private fun commitLike() {

    }

    private fun setType1() {
        binding.type1.visibility = View.VISIBLE
        binding.type2.visibility = View.GONE
    }

    private fun setType2() {
        binding.type2.visibility = View.VISIBLE
        binding.type1.visibility = View.GONE
    }

    private fun setImage() {
        binding.imageContainer.visibility = View.VISIBLE
        binding.videoContainer.visibility = View.GONE
    }

    private fun setVideo() {
        binding.videoContainer.visibility = View.VISIBLE
        binding.imageContainer.visibility = View.GONE
    }

    private fun setNotRe() {
        binding.videoContainer.visibility = View.GONE
        binding.imageContainer.visibility = View.GONE
    }

    companion object {
        fun newInstance(reviewId: Int, type: String) =

            DetailCommunityPostingFragment().apply {
                arguments = Bundle().apply {
                    putInt("reviewId", reviewId)
                    putString("type", type)
                }
            }

    }
}