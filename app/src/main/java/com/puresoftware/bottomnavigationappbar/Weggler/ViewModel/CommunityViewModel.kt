package com.puresoftware.bottomnavigationappbar.Weggler.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puresoftware.bottomnavigationappbar.Weggler.Model.ReviewInCommunity

class CommunityViewModel :ViewModel(){
    //커뮤니티 데이터 리스트
    var communityLiveData = MutableLiveData<ArrayList<ReviewInCommunity>>()
    //내가 쓴 글 데이터 리스트
    var myPostingLiveData = MutableLiveData<ArrayList<ReviewInCommunity>>()
    //인기 게시글 데이터 리스트
    var popularPostingLiveData = MutableLiveData<ArrayList<ReviewInCommunity>>()
    // 내가 쓴 댓글 데이터 리스트
//    var myCommentLiveData = MutableLiveData<ArrayList<Comment>>()

    init {
        communityLiveData.value = ArrayList()
        myPostingLiveData.value = ArrayList()
        popularPostingLiveData.value = ArrayList()
//        myCommentLiveData.value = ArrayList()
    }

    //커뮤니티 데이터 전체 초기화
    fun setCommunityData(data : ArrayList<ReviewInCommunity>){
        communityLiveData.value = data
    }

    //내 포스팅 데이터 전체 초기화
    fun setMyPostingData(data : ArrayList<ReviewInCommunity>){
        myPostingLiveData.value = data
    }

    //커뮤니티 데이터 추가
    fun addCommunityData(data : ReviewInCommunity){
        communityLiveData.value?.add(data)
    }

    //내 포스팅 데이터 추가
    fun addMyPostingData(data : ReviewInCommunity){
        myPostingLiveData.value?.add(data)
    }

    //내 포스팅 데이터 수정
    fun updateMyPostingData(currentData:ReviewInCommunity, newData:ReviewInCommunity){
        val index = communityLiveData.value?.indexOf(currentData)
        if (index != null) {
            communityLiveData.value?.set(index,newData)
        }
    }

    //내 포스팅 데이터 삭제
    fun delMyPostingData(data: ReviewInCommunity){
        myPostingLiveData.value?.remove(data)
    }

}