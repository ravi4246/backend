package com.simats.siddha.myapplication.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch



@HiltViewModel
class HealthViewModel @Inject constructor(
    private val repository: Repository, 
    private val authManager: AuthManager
) : ViewModel() {
    var symptoms by mutableStateOf("")
    var sleepQuality by mutableStateOf(5)
    var digestionStatus by mutableStateOf("Good")
    var activityLevel by mutableStateOf("Average")

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var logSaved by mutableStateOf(false)
    var healthLogs by mutableStateOf<List<HealthLogDto>>(emptyList())
    
    var analysisResult by mutableStateOf<AnalysisResponse?>(null)

    fun fetchHealthLogs() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = repository.getHealthLogs()
                if (response.isSuccessful) {
                    healthLogs = response.body() ?: emptyList()
                } else {
                    error = "Failed to fetch logs: ${response.code()}"
                }
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }

    fun saveLog() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val logDto = HealthLogDto(
                    symptoms = symptoms,
                    sleepQuality = sleepQuality,
                    digestionStatus = digestionStatus,
                    activityLevel = activityLevel
                )
                val response = repository.createHealthLog(logDto)
                if (response.isSuccessful) {
                    logSaved = true
                } else {
                    error = "Failed to save log: ${response.message()}"
                }
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchAnalysis() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repository.getAnalysis()
                if (response.isSuccessful) {
                    analysisResult = response.body()
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                isLoading = false
            }
        }
    }

    fun clearState() {
        symptoms = ""
        sleepQuality = 5
        digestionStatus = "Good"
        activityLevel = "Average"
        analysisResult = null
    }
}
