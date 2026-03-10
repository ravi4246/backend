package com.simats.siddha.myapplication.api

import retrofit2.http.*
import retrofit2.Response



interface ApiService {
    @POST("register/")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("token/")
    suspend fun login(@Body body: LoginRequest): Response<TokenResponse>

    @POST("token/refresh/")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<TokenResponse>

    @POST("forgot-password/")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<GenericResponse>

    @POST("reset-password/")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<GenericResponse>

    @GET("profile/")
    suspend fun getProfile(): Response<ProfileDto>

    @PUT("profile/")
    suspend fun updateProfile(@Body profile: ProfileDto): Response<ProfileDto>

    @GET("health-logs/")
    suspend fun getHealthLogs(): Response<List<HealthLogDto>>

    @POST("health-logs/")
    suspend fun createHealthLog(
        @Body log: HealthLogDto
    ): Response<HealthLogDto>

    @GET("analysis/")
    suspend fun getAnalysis(): Response<AnalysisResponse>

    @POST("chat/")
    suspend fun chat(@Body request: ChatRequest): Response<ChatResponse>
}
