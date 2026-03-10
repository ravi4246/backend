package com.simats.siddha.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.R
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme






@Composable
fun LifestyleHabitsScreen(modifier: Modifier = Modifier, viewModel: UserViewModel, onContinue: () -> Unit, onBack: (() -> Unit)? = null) {
    val habits = remember {
        listOf(
            "Smoking" to R.drawable.smoking,
            "Alcohol Consumption" to R.drawable.drink,
            "Vegetarian Diet" to R.drawable.vegetarian,
            "Regular Exercise" to R.drawable.exercise,
            "Meditation / Yoga" to R.drawable.meditation,
            "Adequate Water Intake" to R.drawable.water
        ).map { (habit, icon) -> Triple(habit, icon, mutableStateOf(false)) }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background) // SandyBeige
    ) {
        // Organic Animation Layer
        FallingLeavesBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), 
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    onBack?.let {
                        IconButton(onClick = it) {
                            Icon(
                                Icons.Default.ArrowBack, 
                                contentDescription = "Back",
                                tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    Text(
                        text = "Lifestyle Habits",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        habits.forEach { (habit, icon, isChecked) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = CircleShape,
                                    color = Color(0xFFE8F5E9)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            painter = painterResource(id = icon),
                                            contentDescription = habit,
                                            modifier = Modifier.size(24.dp),
                                            tint = Color.Unspecified
                                        )
                                    }
                                }
                                Spacer(Modifier.width(16.dp))
                                Text(
                                    text = habit, 
                                    modifier = Modifier.weight(1f), 
                                    fontSize = 16.sp,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Medium
                                )
                                Switch(
                                    checked = isChecked.value,
                                    onCheckedChange = { isChecked.value = it },
                                    colors = androidx.compose.material3.SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = Color(0xFF2E7D32),
                                        uncheckedThumbColor = Color(0xFFE8F5E9),
                                        uncheckedTrackColor = Color(0xFFF5F5F5)
                                    )
                                )
                            }
                        }
                    }
                }
            }
            
            // Bottom Action Area
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White.copy(alpha = 0.8f),
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = {
                        viewModel.hasSmokingHabit = habits.find { it.first == "Smoking" }?.third?.value ?: false
                        viewModel.hasAlcoholHabit = habits.find { it.first == "Alcohol Consumption" }?.third?.value ?: false
                        viewModel.hasVegetarianDiet = habits.find { it.first == "Vegetarian Diet" }?.third?.value ?: false
                        viewModel.hasRegularExercise = habits.find { it.first == "Regular Exercise" }?.third?.value ?: false
                        viewModel.hasMeditationYoga = habits.find { it.first == "Meditation / Yoga" }?.third?.value ?: false
                        viewModel.hasAdequateWater = habits.find { it.first == "Adequate Water Intake" }?.third?.value ?: false
                        onContinue()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(64.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text(text = "Finalize & Review", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LifestyleHabitsScreenPreview() {
    MyApplicationTheme {
        val dummyApiService = object : com.simats.siddha.myapplication.api.ApiService {
            override suspend fun register(request: com.simats.siddha.myapplication.api.RegisterRequest) = retrofit2.Response.success(
                com.simats.siddha.myapplication.api.RegisterResponse("", "", com.simats.siddha.myapplication.api.UserDto(1, "", "", null))
            )
            override suspend fun login(credentials: com.simats.siddha.myapplication.api.LoginRequest) = retrofit2.Response.success(
                com.simats.siddha.myapplication.api.TokenResponse("", "")
            )
            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))
            override suspend fun getProfile() = retrofit2.Response.success(
                com.simats.siddha.myapplication.api.ProfileDto("", 0, "", "", "", "", "", "", "", "")
            )
            override suspend fun updateProfile(profile: com.simats.siddha.myapplication.api.ProfileDto) = retrofit2.Response.success(profile)
            override suspend fun getHealthLogs() = retrofit2.Response.success(
                emptyList<com.simats.siddha.myapplication.api.HealthLogDto>()
            )
            override suspend fun createHealthLog(log: com.simats.siddha.myapplication.api.HealthLogDto) = retrofit2.Response.success(log)
            override suspend fun getAnalysis() = retrofit2.Response.success(
                com.simats.siddha.myapplication.api.AnalysisResponse("Low Risk", "Improving", 82, 85, listOf(0.9f, 0.82f), emptyList(), "Keep up good habits", null, 8, 21, 5, true, emptyList())
            )

            override suspend fun forgotPassword(request: com.simats.siddha.myapplication.api.ForgotPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun resetPassword(request: com.simats.siddha.myapplication.api.ResetPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun chat(request: com.simats.siddha.myapplication.api.ChatRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.ChatResponse(""))

        }
        LifestyleHabitsScreen(viewModel = UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApiService), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current)), onContinue = {}, onBack = {})
    }
}
