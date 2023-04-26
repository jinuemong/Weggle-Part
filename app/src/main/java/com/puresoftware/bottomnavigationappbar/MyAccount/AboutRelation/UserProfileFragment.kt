package com.puresoftware.bottomnavigationappbar.MyAccount.AboutRelation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ExploreProfile
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {
    private var userId: String? = null
    private var type: String? = null
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
                                        mainActivity.setMainViewVisibility(false)
                                        mainActivity.changeFragment(
                                            DetailReviewFragment
                                                .newInstance(review.reviewId)
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

        setUpListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 뷰가 삭제되면 검색 데이터 삭제
    }
    private fun setUpListener(){
        binding.backButton.setOnClickListener {
            mainActivity.goBackFragment(this@UserProfileFragment)
            if (type == "main") {
                mainActivity.setMainViewVisibility(true)
            }
        }
        userId?.let {userName->
            binding.followerBox.setOnClickListener {
                mainActivity.changeFragment(FollowDataFragment.newInstance(userName ,0,"sub"))
            }

            binding.followingBox.setOnClickListener {
                mainActivity.changeFragment(FollowDataFragment.newInstance(userName ,1,"sub"))
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