package com.simats.siddha.myapplication.api

import com.google.gson.annotations.SerializedName



data class ProfileDto(
    @SerializedName("full_name") val fullName: String,
    val age: Int?,
    val gender: String,
    @SerializedName("blood_group") val bloodGroup: String,
    val phone: String? = null,
    @SerializedName("initial_symptoms") val initialSymptoms: String? = null,
    @SerializedName("initial_sleep") val initialSleep: String? = null,
    @SerializedName("initial_digestion") val initialDigestion: String? = null,
    @SerializedName("initial_activity") val initialActivity: String? = null,
    val habits: String? = null,
    val email: String? = null
)

data class RegisterRequest(
    val phone: String,
    val email: String,
    val password: String,
    val profile: ProfileDto
)

data class LoginRequest(
    val phone: String,
    val password: String
)

data class RefreshTokenRequest(
    val refresh: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class ResetPasswordRequest(
    val email: String,
    val code: String,
    @SerializedName("new_password") val newPassword: String
)

data class GenericResponse(
    val message: String
)

data class UserDto(
    val id: Int,
    val phone: String,
    val email: String,
    val profile: ProfileDto?
)

data class RegisterResponse(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String,
    val user: UserDto
)

data class TokenResponse(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String
)

data class HealthLogDto(
    val id: Int? = null,
    val symptoms: String,
    @SerializedName("sleep_quality") val sleepQuality: Int,
    @SerializedName("digestion_status") val digestionStatus: String,
    @SerializedName("activity_level") val activityLevel: String,
    @SerializedName("created_at") val createdAt: String? = null
)

data class RiskFactor(
    val title: String,
    val subtitle: String,
    val isPositive: Boolean
)

data class RecommendedPlanDto(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("duration_days") val durationDays: Int,
    @SerializedName("diet_plan") val dietPlan: String,
    @SerializedName("herbs_plan") val herbsPlan: String,
    @SerializedName("lifestyle_plan") val lifestylePlan: String
)

data class AnalysisResponse(
    @SerializedName("risk_level") val riskLevel: String,
    val trend: String,
    @SerializedName("adherence_level") val adherenceLevel: Int,
    @SerializedName("confidence_score") val confidenceScore: Int,
    @SerializedName("trend_graph") val trendGraph: List<Float>,
    @SerializedName("risk_factors") val riskFactors: List<RiskFactor>,
    val recommendation: String,
    @SerializedName("recommended_plan") val recommendedPlan: RecommendedPlanDto?,
    @SerializedName("days_logged") val daysLogged: Int,
    @SerializedName("total_days") val totalDays: Int,
    val streak: Int,
    @SerializedName("can_log") val canLog: Boolean,
    val insights: List<String>,
    @SerializedName("symptom_trend") val symptomTrend: List<Float>? = null
)

data class ChatRequest(
    val message: String
)

data class ChatResponse(
    val response: String
)
