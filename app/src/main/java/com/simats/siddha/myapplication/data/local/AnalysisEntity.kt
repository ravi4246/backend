package com.simats.siddha.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.simats.siddha.myapplication.api.AnalysisResponse
import com.simats.siddha.myapplication.api.RecommendedPlanDto
import com.simats.siddha.myapplication.api.RiskFactor



@Entity(tableName = "analysis_cache")
data class AnalysisEntity(
    @PrimaryKey
    val id: Int = 1, // Store a single item using constant ID
    val riskLevel: String,
    val trend: String,
    val adherenceLevel: Int,
    val confidenceScore: Int,
    val trendGraph: List<Float>,
    val riskFactors: List<RiskFactor>,
    val recommendation: String,
    val recommendedPlan: RecommendedPlanDto?,
    val daysLogged: Int = 0,
    val totalDays: Int = 0,
    val streak: Int = 0,
    val canLog: Boolean = true,
    val insights: List<String>,
    val symptomTrend: List<Float> = emptyList(),
    val lastUpdated: Long = System.currentTimeMillis()
)

fun AnalysisEntity.toResponse(): AnalysisResponse {
    return AnalysisResponse(
        riskLevel = this.riskLevel,
        trend = this.trend,
        adherenceLevel = this.adherenceLevel,
        confidenceScore = this.confidenceScore,
        trendGraph = this.trendGraph,
        riskFactors = this.riskFactors,
        recommendation = this.recommendation,
        recommendedPlan = this.recommendedPlan,
        daysLogged = this.daysLogged,
        totalDays = this.totalDays,
        streak = this.streak,
        canLog = this.canLog,
        insights = this.insights,
        symptomTrend = this.symptomTrend
    )
}

fun AnalysisResponse.toEntity(): AnalysisEntity {
    return AnalysisEntity(
        riskLevel = this.riskLevel,
        trend = this.trend,
        adherenceLevel = this.adherenceLevel,
        confidenceScore = this.confidenceScore,
        trendGraph = this.trendGraph,
        riskFactors = this.riskFactors,
        recommendation = this.recommendation,
        recommendedPlan = this.recommendedPlan,
        daysLogged = this.daysLogged,
        totalDays = this.totalDays,
        streak = this.streak,
        canLog = this.canLog,
        insights = this.insights,
        symptomTrend = this.symptomTrend ?: emptyList()
    )
}
