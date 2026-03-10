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
class UserViewModel @Inject constructor(
    private val repository: Repository,
    private val authManager: AuthManager
) : ViewModel() {
    var fullName by mutableStateOf("")
    var age by mutableStateOf("")
    var gender by mutableStateOf("")
    var bloodGroup by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")

    // Onboarding Assessment Data
    var selectedSymptoms by mutableStateOf<Set<String>>(emptySet())
    var digestionStatus by mutableStateOf("Good")
    var sleepQuality by mutableStateOf("Good")
    var activityLevel by mutableStateOf("Moderate")
    var hasSmokingHabit by mutableStateOf(false)
    var hasAlcoholHabit by mutableStateOf(false)
    var hasVegetarianDiet by mutableStateOf(false)
    var hasRegularExercise by mutableStateOf(false)
    var hasMeditationYoga by mutableStateOf(false)
    var hasAdequateWater by mutableStateOf(false)

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var isRegistered by mutableStateOf(false)
    var isLoggedIn by mutableStateOf(false)
    var isUnauthorized by mutableStateOf(false)
    var isProfileLoading by mutableStateOf(false)
    
    var isDarkMode by mutableStateOf(authManager.isDarkMode())
        private set

    fun toggleDarkMode(enabled: Boolean) {
        authManager.setDarkMode(enabled)
        isDarkMode = enabled
    }
    
    val isProfileComplete: Boolean
        get() = userProfile != null && !userProfile!!.fullName.isNullOrEmpty()

    fun register() {
        viewModelScope.launch {
            if (phone.isEmpty()) {
                error = "Phone number is required"
                return@launch
            }
            if (password.isEmpty()) {
                error = "Password is required"
                return@launch
            }
            if (email.isEmpty()) {
                error = "Email address is required"
                return@launch
            }
            if (fullName.isEmpty()) {
                error = "Full Name is required"
                return@launch
            }
            if (age.isEmpty() || age.toIntOrNull() == null) {
                error = "Valid Age is required"
                return@launch
            }

            isLoading = true
            error = null
            try {
                val profile = ProfileDto(
                    fullName = fullName,
                    age = age.toIntOrNull(),
                    gender = gender,
                    bloodGroup = bloodGroup,
                    phone = phone,
                    initialSymptoms = selectedSymptoms.joinToString(", "),
                    initialSleep = sleepQuality,
                    initialDigestion = digestionStatus,
                    initialActivity = activityLevel,
                    habits = buildString {
                        if (hasSmokingHabit) append("Smoking, ")
                        if (hasAlcoholHabit) append("Alcohol, ")
                        if (hasVegetarianDiet) append("Vegetarian, ")
                        if (hasRegularExercise) append("Exercise, ")
                        if (hasMeditationYoga) append("Yoga/Meditation, ")
                        if (hasAdequateWater) append("Adequate Water")
                    }.trimEnd(',', ' ')
                )
                val request = RegisterRequest(
                    phone = phone,
                    email = email,
                    password = password,
                    profile = profile
                )
                val response = repository.register(request)
                if (response.isSuccessful) {
                    response.body()?.let { regResponse ->
                        authManager.saveToken(regResponse.accessToken, regResponse.refreshToken)
                        repository.clearAnalysisCache()
                        analysisData = null
                        isRegistered = true
                    }
                } else {
                    error = "Registration failed: ${response.errorBody()?.string() ?: response.message()}"
                }
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }

    var isReassessmentComplete by mutableStateOf(false)

    fun submitReassessment() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val newHabits = buildString {
                    if (hasSmokingHabit) append("Smoking, ")
                    if (hasAlcoholHabit) append("Alcohol, ")
                    if (hasVegetarianDiet) append("Vegetarian, ")
                    if (hasRegularExercise) append("Exercise, ")
                    if (hasMeditationYoga) append("Yoga/Meditation, ")
                    if (hasAdequateWater) append("Adequate Water")
                }.trimEnd(',', ' ')

                val updatedProfile = userProfile?.copy(
                    fullName = fullName,
                    age = age.toIntOrNull() ?: userProfile?.age,
                    gender = gender,
                    bloodGroup = bloodGroup,
                    initialSymptoms = selectedSymptoms.joinToString(", "),
                    initialSleep = sleepQuality,
                    initialDigestion = digestionStatus,
                    initialActivity = activityLevel,
                    habits = newHabits
                ) ?: ProfileDto(
                    fullName = fullName,
                    age = age.toIntOrNull(),
                    gender = gender,
                    bloodGroup = bloodGroup,
                    phone = phone,
                    initialSymptoms = selectedSymptoms.joinToString(", "),
                    initialSleep = sleepQuality,
                    initialDigestion = digestionStatus,
                    initialActivity = activityLevel,
                    habits = newHabits
                )

                val response = repository.updateProfile(updatedProfile)
                if (response.isSuccessful) {
                    userProfile = response.body()
                    repository.clearAnalysisCache()
                    analysisData = null
                    isReassessmentComplete = true
                } else {
                    error = "Update failed: ${response.code()}"
                }
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Network error"
            } finally {
                isLoading = false
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            if (phone.isEmpty() || password.isEmpty()) {
                error = "Phone and password are required"
                return@launch
            }
            isLoading = true
            error = null
            try {
                val response = repository.login(LoginRequest(phone, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        authManager.saveToken(it.accessToken, it.refreshToken)
                        repository.clearAnalysisCache()
                        analysisData = null
                        fetchProfile()
                        isLoggedIn = true
                    }
                } else {
                    error = "Login failed: Invalid credentials"
                }
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Unknown error"
            } finally {
                isLoading = false
            }
        }
    }

    var forgotPasswordEmail by mutableStateOf("")
    var resetPasswordCode by mutableStateOf("")
    var resetPasswordNew by mutableStateOf("")
    var resetPasswordConfirm by mutableStateOf("")
    var forgotPasswordSuccess by mutableStateOf(false)
    var resetPasswordSuccess by mutableStateOf(false)
    var successMessage by mutableStateOf<String?>(null)

    fun sendPasswordResetCode() {
        viewModelScope.launch {
            if (forgotPasswordEmail.isEmpty()) {
                error = "Email address is required"
                return@launch
            }
            isLoading = true
            error = null
            forgotPasswordSuccess = false
            successMessage = null
            try {
                val response = repository.forgotPassword(ForgotPasswordRequest(email = forgotPasswordEmail))
                if (response.isSuccessful) {
                    val msg = response.body()?.message ?: "Verification code sent successfully"
                    successMessage = msg
                    forgotPasswordSuccess = true
                    error = null
                } else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    if (errorBody.contains("debug_code")) {
                        // Extract debug code for easier testing if email fails
                        val regex = "\"debug_code\":\"(\\d+)\"".toRegex()
                        val match = regex.find(errorBody)
                        val code = match?.groupValues?.get(1) ?: ""
                        error = "Email failed. USE DEBUG CODE: $code"
                        forgotPasswordSuccess = true // Allow moving to next screen to use the code
                    } else if (errorBody.contains("error")) {
                         val regex = "\"error\":\"(.*?)\"".toRegex()
                         val match = regex.find(errorBody)
                         error = match?.groupValues?.get(1) ?: "User not found"
                    } else {
                        error = "User not found or error occurred"
                    }
                }
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Network error"
            } finally {
                isLoading = false
            }
        }
    }

    fun resetPassword() {
        viewModelScope.launch {
            if (resetPasswordCode.isEmpty()) {
                error = "Verification code is required"
                return@launch
            }
            if (resetPasswordNew.isEmpty()) {
                error = "New password is required"
                return@launch
            }
            if (resetPasswordNew != resetPasswordConfirm) {
                error = "Passwords do not match"
                return@launch
            }
            isLoading = true
            error = null
            resetPasswordSuccess = false
            successMessage = null
            try {
                // Submit email, code, and new password
                val request = ResetPasswordRequest(
                    email = forgotPasswordEmail, 
                    code = resetPasswordCode, 
                    newPassword = resetPasswordNew
                )
                val response = repository.resetPassword(request)
                if (response.isSuccessful) {
                    val msg = response.body()?.message ?: "Password reset successfully"
                    successMessage = msg
                    resetPasswordSuccess = true
                    error = null
                } else {
                    error = "Failed to reset password"
                }
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Network error"
            } finally {
                isLoading = false
            }
        }
    }

    // AI Analysis Data
    var analysisData by mutableStateOf<AnalysisResponse?>(null)
    var userProfile by mutableStateOf<ProfileDto?>(null)

    fun fetchProfile() {
        viewModelScope.launch {
            isLoading = true
            isProfileLoading = true
            try {
                val response = repository.getProfile()
                if (response.isSuccessful) {
                    val profile = response.body()
                    userProfile = profile
                    profile?.let {
                        fullName = it.fullName ?: ""
                        age = it.age?.toString() ?: ""
                        gender = it.gender ?: "Male"
                        bloodGroup = it.bloodGroup ?: ""
                        phone = it.phone ?: ""
                        email = it.email ?: ""
                        selectedSymptoms = it.initialSymptoms?.split(",")?.map { s -> s.trim() }?.filter { s -> s.isNotEmpty() }?.toSet() ?: emptySet()
                        digestionStatus = it.initialDigestion ?: "Good"
                        sleepQuality = it.initialSleep ?: "Good"
                        activityLevel = it.initialActivity ?: "Moderate"
                        
                        val habitsStr = it.habits ?: ""
                        hasSmokingHabit = habitsStr.contains("Smoking", ignoreCase = true)
                        hasAlcoholHabit = habitsStr.contains("Alcohol", ignoreCase = true)
                        hasVegetarianDiet = habitsStr.contains("Vegetarian", ignoreCase = true)
                        hasRegularExercise = habitsStr.contains("Exercise", ignoreCase = true)
                        hasMeditationYoga = habitsStr.contains("Yoga/Meditation", ignoreCase = true) || habitsStr.contains("Meditation", ignoreCase = true)
                        hasAdequateWater = habitsStr.contains("Adequate Water", ignoreCase = true)
                    }
                } else {
                    android.util.Log.e("UserViewModel", "fetchProfile failed: ${response.code()} ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                android.util.Log.e("UserViewModel", "fetchProfile exception", e)
            } finally {
                isLoading = false
                isProfileLoading = false
            }
        }
    }

    fun updateProfile(name: String, ageStr: String, genderStr: String, blood: String) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                // Determine habits based on boolean flags
                val newHabits = buildString {
                    if (hasSmokingHabit) append("Smoking, ")
                    if (hasAlcoholHabit) append("Alcohol, ")
                    if (hasVegetarianDiet) append("Vegetarian, ")
                    if (hasRegularExercise) append("Exercise, ")
                    if (hasMeditationYoga) append("Yoga/Meditation, ")
                    if (hasAdequateWater) append("Adequate Water")
                }.trimEnd(',', ' ')

                val updatedProfile = userProfile?.copy(
                    fullName = name,
                    age = ageStr.toIntOrNull() ?: userProfile?.age,
                    gender = genderStr,
                    bloodGroup = blood,
                    initialSymptoms = selectedSymptoms.joinToString(", "),
                    initialSleep = sleepQuality,
                    initialDigestion = digestionStatus,
                    initialActivity = activityLevel,
                    habits = newHabits
                ) ?: ProfileDto(
                    fullName = name,
                    age = ageStr.toIntOrNull(),
                    gender = genderStr,
                    bloodGroup = blood,
                    phone = phone,
                    initialSymptoms = selectedSymptoms.joinToString(", "),
                    initialSleep = sleepQuality,
                    initialDigestion = digestionStatus,
                    initialActivity = activityLevel,
                    habits = newHabits
                )
                
                val response = repository.updateProfile(updatedProfile)
                if (response.isSuccessful) {
                    userProfile = response.body()
                } else {
                    error = "Failed to update profile"
                }
            } catch (e: Exception) {
                error = e.localizedMessage
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
                    analysisData = response.body()
                } else {
                    if (response.code() == 401) {
                        isUnauthorized = true
                    } else {
                        error = "Failed to load analysis: ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                error = e.localizedMessage ?: "Network error"
            } finally {
                isLoading = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authManager.clearToken()
            repository.clearAnalysisCache()
            isRegistered = false
            isLoggedIn = false
            isUnauthorized = false
            isReassessmentComplete = false
            fullName = ""
            age = ""
            gender = ""
            bloodGroup = ""
            phone = ""
            password = ""
            email = ""
            selectedSymptoms = emptySet()
            digestionStatus = "Good"
            sleepQuality = "Good"
            activityLevel = "Moderate"
            hasSmokingHabit = false
            hasAlcoholHabit = false
            hasVegetarianDiet = false
            hasRegularExercise = false
            hasMeditationYoga = false
            hasAdequateWater = false
            analysisData = null
            userProfile = null
        }
    }
}
