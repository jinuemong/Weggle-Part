package com.puresoftware.bottomnavigationappbar.MyAccount.AboutRelation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.DetailReviewFragment
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.KeywordAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.MyFeedReviewAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.RelationManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ReviewManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.UserManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.FollowData
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.User
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.UserInfo
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {
    private var userId: String? = null
    private var type: String? = null
    private var isFollow = false
    private lateinit var mainActivity: MainActivity
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainActivity.goBackFragment(this@UserProfileFragment)
                if (type == "main") {
                    mainActivity.setMainViewVisibility(true)
                }
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString("userId", null)
            type = it.getString("type", null)
            mainActivity.setMainViewVisibility(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId?.let { userName ->
            UserManager(mainActivity.masterApp)
                .searchUserFromUserId(userName, paramFun = { user,_ ->
                    if (user != null) {
                        // 검색 중인 뷰
                        RelationManager(mainActivity.masterApp)
                            .getTargetUserFollowers(userName, paramFunc = {data,_->
                                if(data!=null) {binding.followerNum.text = data.size.toString()}
                            })
                        RelationManager(mainActivity.masterApp)
                            .getTargetUserFollowings(userName, paramFunc = {data,_->
                                if(data!=null) {binding.followingNum.text = data.size.toString()}
                            })

                        //유저 정보
                        user.profile?.let {
                            Glide.with(mainActivity)
                                .load(user.profile)
                                .into(binding.userImage)
                        }
                        user.background?.let {
                            Glide.with(mainActivity)
                                .load(user.background)
                                .into(binding.backImage)
                        }
                        binding.userName.text = user.name
                        user.body?.userComment?.let {
                            binding.userComment.text = it
                        }

                        //url 설정
                        if (user.body?.blogUrl==null){
                            offUrl(binding.blogUrl)
                        }else{
                            onUrl(binding.blogUrl,user.body.blogUrl!!)
                        }
                        if (user.body?.youtubeUrl==null){
                            offUrl(binding.youtubeUrl)
                        }else{
                            onUrl(binding.youtubeUrl,user.body.youtubeUrl!!)
                        }
                        if (user.body?.instagramUrl==null){
                            offUrl(binding.instagramUrl)
                        }else{
                            onUrl(binding.instagramUrl,user.body.instagramUrl!!)
                        }

                        //태그 정보
                        user.body?.userKeyword?.let {
                            binding.tagBox.adapter = KeywordAdapter(mainActivity,it,"userProfile")
                        }


                        //팔로잉 상태 확인
                        if(mainActivity.myAccountViewModel.userProfile?.name==user.name){ //자신인 경우 삭제
                            binding.followButton.visibility=View.GONE
                        }else{
                            setFollowButton(mainActivity.myAccountViewModel.checkUserFollow(user.name))
                        }
                        // 데이터 셋 확정 후 클릭 이벤트 설정
                        setUpListener(user)
                    }
                })

            // 유저 조회로 리뷰 리스트 얻기
            ReviewManager(mainActivity.masterApp, null)
                .getUserReviewData(userName, paramFunc = { data, _ ->
                    if (data != null && data.size>0) {
                        binding.postingNum.text = data.size.toString()
                        //adapter 연결
                        binding.noPostingView.visibility = View.GONE
                        binding.postingList.apply {
                            visibility = View.VISIBLE
                            adapter = MyFeedReviewAdapter(mainActivity, data).apply {
                                setOnItemClickListener(object :
                                    MyFeedReviewAdapter.OnItemClickListener {
                                    override fun itemClick(review: ReviewData) {
                                        mainActivity.changeFragment(
                                            DetailReviewFragment
                                                .newInstance(review.reviewId,"sub")
                                        )
                                    }
                                })
                            }
                        }

                    } else {
                        binding.noPostingView.visibility = View.VISIBLE
                        binding.postingList.visibility = View.GONE
                    }
                })
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setUpListener(user : User){
        binding.backButton.setOnClickListener {
            mainActivity.goBackFragment(this@UserProfileFragment)
            if (type == "main") {
                mainActivity.setMainViewVisibility(true)
            }
        }
        user.name.let { userName ->
            binding.followerBox.setOnClickListener {
                mainActivity.changeFragment(FollowDataFragment.newInstance(userName, 0, "sub"))
            }

            binding.followingBox.setOnClickListener {
                mainActivity.changeFragment(FollowDataFragment.newInstance(userName, 1, "sub"))
            }

            binding.followButton.setOnClickListener {
                if (isFollow) {
                    RelationManager(mainActivity.masterApp)
                        .delFollow(userName, paramFunc = { _ ,err->
                            if (err == null) { //언 팔로우 성공
                                setFollowButton(false)
                                mainActivity.myAccountViewModel.delFollow(userName)
                                binding.followerNum.text =( binding.followerNum.text.toString().toInt()-1).toString()
                            }
                        })
                } else {
                    RelationManager(mainActivity.masterApp)
                        .addFollow(userName, paramFunc = { _ ,err->
                            if (err == null) { // 팔로우 성공
                                setFollowButton(true)
                                val followData = FollowData(null,
                                    UserInfo(user.name,user.profile,user.background),"","")
                                mainActivity.myAccountViewModel.addFollow(followData)
                                binding.followerNum.text =( binding.followerNum.text.toString().toInt()+1).toString()
                            }
                        })
                }
            }
        }

    }

    private fun onUrl(view: ImageView, url: String) {
        view.visibility = View.VISIBLE
        view.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun offUrl(view: ImageView) {
        view.visibility = View.GONE
    }


    @SuppressLint("SetTextI18n")
    private fun setFollowButton(boolean: Boolean){
        if(boolean){ // 팔로우 취소
            binding.followButton.apply {
                isFollow = true
                val paddingList = listOf(paddingLeft,paddingTop,paddingRight,paddingBottom)
                setTextColor(Color.parseColor("#FF000000"))
                setBackgroundResource(R.drawable.round_border_stroke)
                setPadding(paddingList[0],paddingList[1],paddingList[2],paddingList[3])
                text = "팔로잉"
            }
        }else{ // 팔로우 신청
            binding.followButton.apply {
                isFollow = false
                val paddingList = listOf(paddingLeft,paddingTop,paddingRight,paddingBottom)
                setTextColor(Color.parseColor("#FFFFFFFF"))
                setBackgroundResource(R.drawable.round_border_selected)
                setPadding(paddingList[0],paddingList[1],paddingList[2],paddingList[3])
                text = "팔로우"

            }
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(userId: String, type: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString("userId", userId)
                    putString("type", type)
                }
            }
    }
}