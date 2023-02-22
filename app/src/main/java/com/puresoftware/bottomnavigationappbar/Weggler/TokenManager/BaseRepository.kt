package com.puresoftware.bottomnavigationappbar.Weggler.TokenManager


//로그아웃 기능을 하는 api
//토큰 재 요청시 null값이 오면 사용자 로그아웃 진행
abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {
    suspend fun logout() = safeApiCall {
        api.logout()
    }
}