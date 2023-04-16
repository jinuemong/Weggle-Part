package com.puresoftware.bottomnavigationappbar.Weggler.ViewModel

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
    fun updateCommunityData(reviewId : Int, newData:ReviewInCommunity){
        val mainData = communityLiveData.value?.find { it.reviewId == reviewId }
        val mainIndex = communityLiveData.value?.indexOf(mainData)
        if (mainData!=null && mainIndex!=null && mainIndex!=-1){
            communityLiveData.value?.set(mainIndex,newData)
        }
        val popularData = popularPostingLiveData.value?.find { it.reviewId==reviewId }
        val popularIndex = popularPostingLiveData.value?.indexOf(popularData)
        if (popularData!=null && popularIndex!=null && popularIndex!=-1){
            popularPostingLiveData.value?.set(popularIndex,newData)
        }

    }

    fun updateMyPosting(reviewId:Int,newData: ReviewInCommunity){
        val myData = myPostingLiveData.value?.find{it.reviewId == reviewId}
        val myIndex = myPostingLiveData.value?.indexOf(myData)
        if (myData!=null && myIndex!=null && myIndex!=-1){
            myPostingLiveData.value?.set(myIndex,newData)
        }
    }

    //내 포스팅 데이터 삭제
    fun delMyPostingData(data: ReviewInCommunity){
        myPostingLiveData.value?.remove(data)
    }


}