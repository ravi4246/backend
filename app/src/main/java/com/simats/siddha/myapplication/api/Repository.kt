package com.simats.siddha.myapplication.api

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.simats.siddha.myapplication.data.local.AnalysisDao
import com.simats.siddha.myapplication.data.local.toEntity
import com.simats.siddha.myapplication.data.local.toResponse



class AuthManager(private val context: Context) {
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_auth_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun setDarkMode(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("is_dark_mode", enabled).apply()
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean("is_dark_mode", false)
    }

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        sharedPreferences.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .apply()
    }

    suspend fun clearToken() {
        sharedPreferences.edit()
            .remove("access_token")
            .remove("refresh_token")
            .apply()
    }

    suspend fun getToken(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    fun getTokenSync(): String? {
        return sharedPreferences.getString("access_token", null)
    }

    fun getRefreshTokenSync(): String? {
        return sharedPreferences.getString("refresh_token", null)
    }

    fun getLastLogDate(): String? {
        return sharedPreferences.getString("last_log_date", null)
    }

    fun setLastLogDate(date: String) {
        sharedPreferences.edit().putString("last_log_date", date).apply()
    }

    fun getMockLogsCount(): Int {
        return sharedPreferences.getInt("mock_logs_count", 2) // Default to 2 to match initial mock logs
    }

    fun incrementMockLogsCount() {
        val current = getMockLogsCount()
        sharedPreferences.edit().putInt("mock_logs_count", current + 1).apply()
    }

    fun getStreak(): Int {
        return sharedPreferences.getInt("mock_streak", 1) // Default to 1
    }

    fun incrementStreak() {
        val current = getStreak()
        sharedPreferences.edit().putInt("mock_streak", current + 1).apply()
    }

    fun getWeeklyTrend(): List<Float> {
        val trendStr = sharedPreferences.getString("mock_weekly_trend", "1.0,1.0,1.0,1.0,1.0,0.6,0.0") ?: "1.0,1.0,1.0,1.0,1.0,0.6,0.0"
        return trendStr.split(",").mapNotNull { it.toFloatOrNull() }
    }

    fun updateWeeklyTrend() {
        val currentTrend = getWeeklyTrend().toMutableList()
        // Shift left and add 1.0 at the end for today
        if (currentTrend.size >= 7) {
            currentTrend.removeAt(0)
        }
        currentTrend.add(1.0f)
        sharedPreferences.edit().putString("mock_weekly_trend", currentTrend.joinToString(",")).apply()
    }

    fun getSymptomTrend(): List<Float> {
        val trendStr = sharedPreferences.getString("mock_symptom_trend", "0.3,0.35,0.4,0.38,0.45,0.5,0.55") ?: "0.3,0.35,0.4,0.38,0.45,0.5,0.55"
        return trendStr.split(",").mapNotNull { it.toFloatOrNull() }
    }

    fun updateSymptomTrend() {
        val currentTrend = getSymptomTrend().toMutableList()
        val lastScore = currentTrend.lastOrNull() ?: 0.3f
        // Improvement: slightly higher than last, with some randomness
        val nextScore = (lastScore + 0.05f + (Math.random().toFloat() * 0.05f)).coerceIn(0.1f, 0.95f)
        
        if (currentTrend.size >= 10) {
            currentTrend.removeAt(0)
        }
        currentTrend.add(nextScore)
        sharedPreferences.edit().putString("mock_symptom_trend", currentTrend.joinToString(",")).apply()
    }

    suspend fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}

class Repository(
    private val apiService: ApiService,
    private val analysisDao: AnalysisDao? = null,
    private val authManager: AuthManager? = null
) {
    private fun isBackendEnabled(): Boolean {
        // Decoupled from Dark Mode: Backend is now always enabled for sync
        return true
    }

    suspend fun register(request: RegisterRequest): retrofit2.Response<RegisterResponse> = apiService.register(request)
    suspend fun login(credentials: LoginRequest) = apiService.login(credentials)
    suspend fun forgotPassword(request: ForgotPasswordRequest) = apiService.forgotPassword(request)
    suspend fun resetPassword(request: ResetPasswordRequest) = apiService.resetPassword(request)
    
    suspend fun getProfile(): retrofit2.Response<ProfileDto> {
        if (!isBackendEnabled()) {
            return retrofit2.Response.success(
                ProfileDto(
                    fullName = "Mock User (Light Mode)",
                    age = 30,
                    gender = "Male",
                    bloodGroup = "O+",
                    phone = "1234567890",
                    email = "mock@example.com",
                    initialSymptoms = "Fatigue, Stress",
                    initialSleep = "Fair",
                    initialDigestion = "Good",
                    initialActivity = "Moderate",
                    habits = "Vegetarian"
                )
            )
        }
        return apiService.getProfile()
    }

    suspend fun updateProfile(profile: ProfileDto): retrofit2.Response<ProfileDto> {
        if (!isBackendEnabled()) return retrofit2.Response.success(profile)
        return apiService.updateProfile(profile)
    }

    suspend fun getHealthLogs(): retrofit2.Response<List<HealthLogDto>> {
        if (!isBackendEnabled()) {
            return retrofit2.Response.success(
                listOf(
                    HealthLogDto(1, "Feeling better", 8, "Good", "High", "2024-03-01T10:00:00Z"),
                    HealthLogDto(2, "Slight headache", 6, "Fair", "Moderate", "2024-03-02T10:00:00Z")
                )
            )
        }
        return apiService.getHealthLogs()
    }

    suspend fun createHealthLog(log: HealthLogDto): retrofit2.Response<HealthLogDto> {
        if (!isBackendEnabled()) {
            authManager?.incrementMockLogsCount()
            authManager?.incrementStreak()
            authManager?.updateWeeklyTrend()
            authManager?.updateSymptomTrend()
            val today = java.time.LocalDate.now().toString()
            authManager?.setLastLogDate(today)
            return retrofit2.Response.success(log)
        }
        return apiService.createHealthLog(log)
    }
    
    suspend fun clearAnalysisCache() {
        analysisDao?.clearCache()
    }
    
    suspend fun getAnalysis(): retrofit2.Response<AnalysisResponse> {
        if (!isBackendEnabled()) {
            return retrofit2.Response.success(
                AnalysisResponse(
                    riskLevel = "Low Risk (Mock)",
                    trend = "Improving",
                    adherenceLevel = 85,
                    confidenceScore = 90,
                    trendGraph = authManager?.getWeeklyTrend() ?: listOf(0.5f, 0.6f, 0.7f, 0.65f, 0.8f, 0.85f, 0.9f),
                    riskFactors = listOf(
                        RiskFactor("Hydration", "Excellent water intake", true),
                        RiskFactor("Sleep", "Regular sleep pattern", true)
                    ),
                    recommendation = "You are doing great in Light Mode! Enable Dark Mode to sync with the backend.",
                    recommendedPlan = RecommendedPlanDto(
                        id = 1,
                        name = "Mock Balancing Plan",
                        description = "A synthetic plan for demonstration purposes.",
                        durationDays = 21,
                        dietPlan = "Mock Diet",
                        herbsPlan = "Mock Herbs",
                        lifestylePlan = "Mock Lifestyle"
                    ),
                    daysLogged = authManager?.getMockLogsCount() ?: 1,
                    totalDays = 21,
                    streak = authManager?.getStreak() ?: 1,
                    canLog = authManager?.getLastLogDate() != java.time.LocalDate.now().toString(),
                    insights = listOf("Your health data is tracked locally in Light Mode.", "Enable Dark Mode to sync with our servers."),
                    symptomTrend = authManager?.getSymptomTrend() ?: emptyList()
                )
            )
        }
        return try {
            val response = apiService.getAnalysis()
            if (response.isSuccessful && response.body() != null) {
                analysisDao?.insertAnalysis(response.body()!!.toEntity())
            }
            response
        } catch (e: Exception) {
            val cached = analysisDao?.getCachedAnalysis()
            if (cached != null) {
                retrofit2.Response.success(cached.toResponse())
            } else {
                throw e
            }
        }
    }

    suspend fun chat(message: String): retrofit2.Response<ChatResponse> {
        if (!isBackendEnabled()) {
            return retrofit2.Response.success(ChatResponse("AI Chat is in mock mode (Light Mode). Enable Dark Mode to talk to the real Siddha AI."))
        }
        return apiService.chat(ChatRequest(message))
    }
}
