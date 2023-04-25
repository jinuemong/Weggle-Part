package com.puresoftware.bottomnavigationappbar.MyAccount

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.puresoftware.bottomnavigationappbar.MainActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutChallenge.AddChallengeActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.AddReviewActivity
import com.puresoftware.bottomnavigationappbar.MyAccount.AboutReview.DetailReviewFragment
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.KeywordAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Adapter.MyFeedReviewAdapter
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.ReviewManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Manager.UserManager
import com.puresoftware.bottomnavigationappbar.MyAccount.Model.ReviewData
import com.puresoftware.bottomnavigationappbar.MyAccount.SubFragment.UpdateProfileFragment
import com.puresoftware.bottomnavigationappbar.R
import com.puresoftware.bottomnavigationappbar.databinding.MyAccountFragmentBinding

class MyAccountFragment : Fragment() {
    lateinit var binding: MyAccountFragmentBinding
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyAccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리뷰 & 챌린지 작성
        binding.myAccountWriteButton.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            activity?.menuInflater?.inflate(R.menu.my_account_popup, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener { it2 ->
                when (it2.itemId) {
                    R.id.review_write -> {
                        val intent =
                            Intent(mainActivity.applicationContext, AddReviewActivity::class.java)
                        startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.challenge -> {
                        val intent = Intent(
                            mainActivity.applicationContext,
                            AddChallengeActivity::class.java
                        )
                        startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }

        initView()
        setUpListener()
    }

    private fun initView() {

        // 유저 정보 불러오기
        UserManager(mainActivity.masterApp)
            .getUser { user, _ ->
                if (user != null) {
                    mainActivity.myAccountViewModel.userProfile = user

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

                    // 유저 조회로 리뷰 리스트 얻기
                    ReviewManager(mainActivity.masterApp,null)
                        .getUserReviewData(user.name, paramFunc = {data,_->
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
                                                mainActivity.changeFragment(DetailReviewFragment
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
            }

    }

    private fun setUpListener() {
        binding.userImage.setOnClickListener {
            mainActivity.setMainViewVisibility(false)
            mainActivity.changeFragment(UpdateProfileFragment())
        }
    }

    private fun onUrl(view:ImageView,url:String){
        view.visibility = View.VISIBLE
        view.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
    private fun offUrl(view:ImageView){
        view.visibility = View.GONE
    }
}