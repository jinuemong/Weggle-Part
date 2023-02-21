package com.puresoftware.bottomnavigationappbar.Weggler.TokenManager


abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {

    suspend fun logout() = safeApiCall {
        api.logout()
    }
}