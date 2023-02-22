package com.puresoftware.bottomnavigationappbar.Weggler.TokenManager

import okhttp3.ResponseBody

//Resource : 통신의 에러를 구분
// 성공적인 통신일 경우 Success
// 실패한 경우 Failure을 통한 에러 조사
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()
}