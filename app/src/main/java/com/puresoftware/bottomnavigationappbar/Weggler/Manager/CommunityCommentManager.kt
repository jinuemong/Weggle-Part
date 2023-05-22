package com.puresoftware.bottomnavigationappbar.Weggler.Manager

import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommentPost
import com.puresoftware.bottomnavigationappbar.Weggler.Model.Comment
import com.puresoftware.bottomnavigationappbar.Weggler.Model.CommentList
import com.puresoftware.bottomnavigationappbar.Server.MasterApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityCommentManager(
    private val wApp : MasterApplication
) {

    //내가 쓴  댓글 조회
    fun getMyCommentList(paramFunc:(ArrayList<Comment>?,String?)->Unit){
        wApp.service.getMyCommentList(null,null,null)
            .enqueue(object : Callback<CommentList>{
                override fun onResponse(call: Call<CommentList>, response: Response<CommentList>) {
                    if (response.isSuccessful){
                        paramFunc(response.body()!!.content,null)
                    }else{
                        paramFunc(null, response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<CommentList>, t: Throwable) {
                    paramFunc(null,"error")
                }
            })
    }

    //리뷰 아이디로 댓글 가져오기
    fun getReviewCommentList(reviewId : Int,paramFunc: (ArrayList<Comment>?, String?) -> Unit){
        wApp.service.getReviewCommentList(reviewId,null,null,null)
            .enqueue(object : Callback<CommentList>{
                override fun onResponse(call: Call<CommentList>, response: Response<CommentList>) {
                    if (response.isSuccessful){
                        paramFunc(response.body()!!.content,null)
                    }else{
                        paramFunc(null, response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<CommentList>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    //댓글 추가하기
    fun addReviewComment(reviewId: Int, parentCommentId:Int?, body:String
                         ,paramFunc: (Comment?, String?) -> Unit){
        wApp.service.addReviewComment(reviewId,CommentPost(parentCommentId,body))
            .enqueue(object :Callback<Comment>{
                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody()!!.string())
                    }
                }

                override fun onFailure(call: Call<Comment>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }

    //댓글 좋아요
    fun commentLike(commentId:Int,like:Boolean,paramFunc: (Boolean) -> Unit){
        wApp.service.putCommentLike(commentId, like)
            .enqueue(object :Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful){
                        paramFunc(true)
                    }else{
                        paramFunc(false)
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    paramFunc(false)
                }
            })
    }

    fun delComment(commentId: Int,reviewId: Int,paramFunc: (Int?,String?) -> Unit){
        wApp.service.deleteComment(reviewId, commentId)
            .enqueue(object : Callback<Int>{
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful){
                        paramFunc(response.body(),null)
                    }else{
                        paramFunc(null,response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    paramFunc(null,"error")
                }

            })
    }
}