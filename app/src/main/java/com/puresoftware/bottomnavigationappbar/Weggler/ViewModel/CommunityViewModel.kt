package com.puresoftware.bottomnavigationappbar.Weggler.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommunityContent

class CommunityViewModel :ViewModel(){
    //커뮤니티 데이터 리스트
    var communityLiveData = MutableLiveData<ArrayList<CommunityContent>>()
    //내가 쓴 글 데이터 리스트
    var myPostingLiveData = MutableLiveData<ArrayList<CommunityContent>>()
    //인기 게시글 데이터 리스트
    
    // 내가 쓴 댓글 데이터 리스트
//    var myCommentLiveData = MutableLiveData<ArrayList<Comment>>()

    init {
        communityLiveData.value = ArrayList()
        myPostingLiveData.value = ArrayList()
//        myCommentLiveData.value = ArrayList()
    }

    //커뮤니티 데이터 전체 초기화
    fun setCommunityData(data : ArrayList<CommunityContent>){
        communityLiveData.value = data
    }

    //내 포스팅 데이터 전체 초기화
    fun setMyPostingData(data : ArrayList<CommunityContent>){
        myPostingLiveData.value = data
    }

    //커뮤니티 데이터 추가
    fun addCommunityData(data : CommunityContent){
        communityLiveData.value?.add(data)
    }

    //내 포스팅 데이터 추가
    fun addMyPostingData(data : CommunityContent){
        myPostingLiveData.value?.add(data)
    }

    //내 포스팅 데이터 수정
    fun updateMyPostingData(currentData:CommunityContent,newData:CommunityContent){
        val index = communityLiveData.value?.indexOf(currentData)
        if (index != null) {
            communityLiveData.value?.set(index,newData)
        }
    }

    //내 포스팅 데이터 삭제
    fun delMyPostingData(data: CommunityContent){
        myPostingLiveData.value?.remove(data)
    }
}