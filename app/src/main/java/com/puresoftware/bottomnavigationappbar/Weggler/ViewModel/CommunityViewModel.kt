package com.puresoftware.bottomnavigationappbar.Weggler.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Product
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity

class CommunityViewModel :ViewModel(){
    // 카뮤니티 Product
    var communityProduct : Product? = null
    //커뮤니티 데이터 리스트
    var communityLiveData = MutableLiveData<ArrayList<ReviewInCommunity>>()
    //내가 쓴 글 데이터 리스트
    var myPostingLiveData = MutableLiveData<ArrayList<ReviewInCommunity>>()
    //인기 게시글 데이터 리스트
    var popularPostingLiveData = MutableLiveData<ArrayList<ReviewInCommunity>>()
    // 내가 쓴 댓글 데이터 리스트
    var myCommentLiveData = MutableLiveData<ArrayList<Comment>>()

    init {
        communityLiveData.value = ArrayList()
        myPostingLiveData.value = ArrayList()
        popularPostingLiveData.value = ArrayList()
        myCommentLiveData.value = ArrayList()
    }

    //커뮤니티 데이터 전체 초기화
    fun setCommunityData(data : ArrayList<ReviewInCommunity>){
        communityLiveData.value = data
    }

    //내 포스팅 데이터 전체 초기화
    fun setMyPostingData(data : ArrayList<ReviewInCommunity>){
        myPostingLiveData.value = data
    }

    //인기 데이터 전체 초기화
    fun setPopularPostingData(data : ArrayList<ReviewInCommunity>){
        popularPostingLiveData.value = data
    }

    //내 댓글 데이터 전체 초기화
    fun setMyCommentData(data : ArrayList<Comment>){
        myCommentLiveData.value = data
    }

    //커뮤니티 데이터 추가
    fun addCommunityData(data : ReviewInCommunity){
        communityLiveData.value?.add(data)
    }

    //내 포스팅 데이터 추가
    fun addMyPostingData(data : ReviewInCommunity){
        myPostingLiveData.value?.add(data)
    }

    // 내 댓글 데이터 추가
    fun addMyCommentData(data:Comment){
        myCommentLiveData.value?.add(data)
    }

    //인기 게시글에 데이터 추가
    fun addPopularPostingData(data:ReviewInCommunity){
        popularPostingLiveData.value?.add(data)
    }

    //내 포스팅 데이터 수정
    fun updateMyPostingData(reviewId : Int, newData:ReviewInCommunity){
        val data = communityLiveData.value?.find { it.reviewId == reviewId }
        val index = communityLiveData.value?.indexOf(data)
        if (data!=null && index!=null && index!=-1){
            communityLiveData.value?.set(index,newData)
            val data2 = communityLiveData.value?.find { it.reviewId == reviewId }
            Log.d("update",data2!!.userLike.toString())
            Log.d("update",data2!!.likeCount.toString())
        }
    }

    //내 포스팅 데이터 삭제
    fun delMyPostingData(data: ReviewInCommunity){
        myPostingLiveData.value?.remove(data)
    }


}