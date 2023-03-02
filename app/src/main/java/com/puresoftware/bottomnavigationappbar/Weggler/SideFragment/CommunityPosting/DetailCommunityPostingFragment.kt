package com.puresoftware.bottomnavigationappbar.Weggler.SideFragment.CommunityPosting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.Weggler.Adapter.ItemCommentAdapter
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import com.puresoftware.bottomnavigationappbar.Weggler.Manager.CommunityCommentManager
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.getTimeText
import com.puresoftware.bottomnavigationappbar.Weggler.Unit.isVideo
import com.puresoftware.bottomnavigationappbar.databinding.FragmentDetailCommunityPostingBinding

//type : MainFragment에서 왔다면 setMainViewVisibility (뷰 감추기 )
class DetailCommunityPostingFragment(
    val type:String,
    postingData : ReviewInCommunity,
) : Fragment() {

    private var _binding : FragmentDetailCommunityPostingBinding? = null
    private val binding  get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var wegglerApp : MasterApplication
    private lateinit var commentAdapter:ItemCommentAdapter
    private val posting = postingData
    private lateinit var community : CommunityCommentManager
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        wegglerApp = mainActivity.masterApp
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        community = CommunityCommentManager(wegglerApp)
        initView()
        setUpListener()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView(){
        binding.sujectText.text = posting.body.subject
        binding.contentText.text = posting.body.text
        binding.createTime.text= getTimeText(posting.createTime)
        binding.likeNum.text = posting.likeCount.toString()

        if (posting.body.type==1){
            setType1()
        }else{
            setType2()
        }

        //비디오인지 이미지인지 판별
        if (isVideo(posting.resource)==null){
            setNotRe()
        }else {
            if (isVideo(posting.resource) == true) {
                setVideo()
                binding.videoView.setVideoPath(posting.resource)
            } else {
                setImage()
                Glide.with(mainActivity)
                    .load(posting.resource)
                    .into(binding.imageView)
            }
        }

        //Url 구분
        if (posting.body.linkUrl==""){
            binding.linkView.visibility = View.GONE
        }else{
            binding.linkUrl.text = posting.body.linkUrl
        }

        commentAdapter = ItemCommentAdapter(mainActivity, arrayListOf())
        binding.commentView.commentList.adapter = commentAdapter

        community.getReviewCommentList(posting.reviewId, paramFunc = { data, message->
            if (message==null){
                commentAdapter.setData(data!!)
                binding.commentNum.text = commentAdapter.itemCount.toString()
            }else{
                Toast.makeText(mainActivity,message,Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setUpListener(){
        binding.backButton.setOnClickListener {
            mainActivity.goBackFragment(this@DetailCommunityPostingFragment)
            if (type=="main"){
                mainActivity.setMainViewVisibility(true)
            }
        }

        //링크 클릭
        binding.linkUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(posting.body.linkUrl))
            startActivity(intent)
        }

        //comment 추가 버튼
        binding.postComment.setOnClickListener {
            addComment()
        }
    }

    private fun addComment(){
        binding.commentEdit.apply {
            if (text.toString()!=""){
                //comment 추가
                community.addReviewComment(posting.reviewId,text.toString(), paramFunc = { newData, message->
                    if(message==null) {
                        binding.commentNum.text = commentAdapter.addData(newData!!)
                        mainActivity.communityViewModel.apply {
                            addMyCommentData(newData)
                        }

                    }else{
                        Toast.makeText(mainActivity,message,Toast.LENGTH_SHORT).show()
                    }
                })
            }
            setText("")
        }
    }

    private fun delComment(){

    }

    private fun setType1() {
        binding.type1.visibility =View.VISIBLE
        binding.type2.visibility =View.GONE
    }

    private fun setType2() {
        binding.type2.visibility =View.VISIBLE
        binding.type1.visibility =View.GONE
    }

    private fun setImage(){
        binding.imageView.visibility =View.VISIBLE
        binding.videoView.visibility =View.GONE
    }
    private fun setVideo(){
        binding.videoView.visibility =View.VISIBLE
        binding.imageView.visibility =View.GONE
    }

    private fun setNotRe(){
        binding.videoView.visibility =View.GONE
        binding.imageView.visibility =View.GONE
    }
}