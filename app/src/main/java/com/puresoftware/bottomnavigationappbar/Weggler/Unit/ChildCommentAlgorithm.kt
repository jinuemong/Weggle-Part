package com.puresoftware.bottomnavigationappbar.Weggler.Unit


import android.util.Log
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment

fun makeChildComment(dataSet : ArrayList<Comment>): ArrayList<Comment>{
    val newDataSet = ArrayList<Comment>()
    val hashList = HashMap<Int,ArrayList<Comment>>()
    for (data in dataSet){
        val parentId = data.parentCommentId ?: -1

        if (parentId<1){ //부모 뷰
            newDataSet.add(data)
        }else{ //자식 뷰
            if (hashList.containsKey(parentId)){ //이미 존재
                hashList[parentId]!!.add(data)
            }else{ //0번째 인덱스 추가
                hashList[parentId] = arrayListOf(data)
            }
        }
    }

    var i = 0
    while(i < newDataSet.size){
        // 자식 뷰 발견 -> 위치에 자식뷰 리스트 추가
        if (hashList.containsKey(newDataSet[i].commentId)){
            val subData = hashList[newDataSet[i].commentId]!!
            newDataSet.addAll(i+1,subData)
            i+=subData.size-1
        }
        i+=1
    }

    /*
    해쉬맵 생성 -> parentId : ArrayList<comment> 형태로 제작
    해쉬에 데이터 넣을때 해당 데이터 삭제
    해쉬에 해당하는 댓글 바로 뒤 인덱스에 해당 데이터셋 추가

     */


    return newDataSet
}

fun addChildComment(dataSet: ArrayList<Comment>, newData:Comment) : ArrayList<Comment>{
    val parent = dataSet.find {
        it.commentId == newData.parentCommentId
    }
    val lastChild = dataSet.findLast {
        it.parentCommentId==newData.parentCommentId
    }
    if (parent != null && lastChild != null){
        if (lastChild == null) {
            // 존재하는 자식 뷰가 없음 -> 부모뷰 뒤에 add
            dataSet.indexOf(newData).let {
                if (it != -1) {
                    dataSet.add(it + 1, newData)
                }
            }
        } else {
            // 존재하는 자식 뷰 중에 가장 뒤의 값 찾음 -> 마지막 자식 뒤에 add
            dataSet.indexOf(lastChild).let {
                if (it != -1) {
                    dataSet.add(it + 1, newData)
                }
            }
        }
    }
    return dataSet
}

