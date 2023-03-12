package com.puresoftware.bottomnavigationappbar.Server.TokenManager


// 토큰 반환 모델
data class Token (
    val accessToken : String,
    val refreshToken : String,
)