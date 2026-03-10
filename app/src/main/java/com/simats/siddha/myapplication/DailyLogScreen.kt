package com.simats.siddha.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.AnalysisResponse
import com.simats.siddha.myapplication.api.ApiService
import com.simats.siddha.myapplication.api.AuthManager
import com.simats.siddha.myapplication.api.HealthLogDto
import com.simats.siddha.myapplication.api.HealthViewModel
import com.simats.siddha.myapplication.api.LoginRequest
import com.simats.siddha.myapplication.api.ProfileDto
import com.simats.siddha.myapplication.api.RegisterRequest
import com.simats.siddha.myapplication.api.Repository
import com.simats.siddha.myapplication.api.TokenResponse
import com.simats.siddha.myapplication.api.UserDto
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme
import retrofit2.Response






@Composable
fun DailyLogScreen(
    modifier: Modifier = Modifier,
    viewModel: HealthViewModel,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.logSaved) {
        if (viewModel.logSaved) {
            Toast.makeText(context, "Log saved successfully!", Toast.LENGTH_SHORT).show()
            viewModel.logSaved = false // Reset state
            onSave() // Returns to previous screen
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background) // SandyBeige
    ) {
        // Organic Animation Layer
        FallingLeavesBackground(modifier = Modifier.fillMaxSize())

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White.copy(alpha = 0.8f),
                    shadowElevation = 8.dp
                ) {
                    Button(
                        onClick = { viewModel.saveLog() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .height(64.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF859A73)),
                        enabled = !viewModel.isLoading
                    ) {
                        if (viewModel.isLoading) {
                            androidx.compose.material3.CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Save Daily Log", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        text = "Daily Log",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    "Reflect on your health journey today",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            "How are you feeling?",
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = viewModel.symptoms,
                            onValueChange = { viewModel.symptoms = it },
                            label = { Text("Describe any symptoms") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF859A73),
                                unfocusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant,
                                focusedLabelColor = Color(0xFF859A73),
                                unfocusedLabelColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = TextStyle(color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            "Sleep Quality",
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "${viewModel.sleepQuality}/10",
                                color = Color(0xFF859A73),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Slider(
                                value = viewModel.sleepQuality.toFloat(),
                                onValueChange = { viewModel.sleepQuality = it.toInt() },
                                valueRange = 0f..10f,
                                steps = 9,
                                modifier = Modifier.weight(1f),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF859A73),
                                    activeTrackColor = Color(0xFF859A73),
                                    inactiveTrackColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Digestion Status Selection
                        Text(
                            "Digestion Status",
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        androidx.compose.foundation.layout.FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                        ) {
                            val digestionOptions = listOf("Good", "Bloated", "Constipated", "Acidity", "Loose Motion")
                            digestionOptions.forEach { option ->
                                val isSelected = viewModel.digestionStatus == option
                                androidx.compose.material3.FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.digestionStatus = option },
                                    label = { Text(option) },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF859A73),
                                        selectedLabelColor = Color.White,
                                        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                        labelColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Activity Level Selection
                        Text(
                            "Activity Level",
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        androidx.compose.foundation.layout.FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                        ) {
                            val activityOptions = listOf("Sedentary", "Light", "Moderate", "Active", "Very Active")
                            activityOptions.forEach { option ->
                                val isSelected = viewModel.activityLevel == option
                                androidx.compose.material3.FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.activityLevel = option },
                                    label = { Text(option) },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF859A73),
                                        selectedLabelColor = Color.White,
                                        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                        labelColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }
                }

                if (viewModel.error != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = viewModel.error!!,
                        color = Color(0xFFD32F2F),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyLogScreenPreview() {
    MyApplicationTheme {
        val dummyApiService = object : ApiService {
            override suspend fun register(request: RegisterRequest): Response<com.simats.siddha.myapplication.api.RegisterResponse> {
                return Response.success(com.simats.siddha.myapplication.api.RegisterResponse("", "", UserDto(1, "", "", null)))
            }

            override suspend fun login(credentials: LoginRequest): Response<TokenResponse> {
                return Response.success(TokenResponse("", ""))
            }

            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest): Response<TokenResponse> {
                return Response.success(TokenResponse("", ""))
            }

            override suspend fun getProfile(): Response<ProfileDto> {
                return Response.success(ProfileDto("", 0, "", "", "", "", "", "", "", ""))
            }

            override suspend fun updateProfile(profile: ProfileDto): Response<ProfileDto> {
                return Response.success(profile)
            }

            override suspend fun getHealthLogs(): Response<List<HealthLogDto>> {
                return Response.success(emptyList<HealthLogDto>())
            }

            override suspend fun createHealthLog(
                log: HealthLogDto
            ): Response<HealthLogDto> {
                return Response.success(log)
            }

            override suspend fun getAnalysis(): Response<AnalysisResponse> {
                return Response.success(AnalysisResponse("Low Risk", "Improving", 82, 85, listOf(0.9f, 0.82f), emptyList(), "Keep up good habits", null, 8, 21, 5, true, emptyList()))
            }
            override suspend fun forgotPassword(request: com.simats.siddha.myapplication.api.ForgotPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun resetPassword(request: com.simats.siddha.myapplication.api.ResetPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun chat(request: com.simats.siddha.myapplication.api.ChatRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.ChatResponse(""))


        }
        val context = LocalContext.current
        val repository = Repository(dummyApiService)
        val authManager = AuthManager(context)
        DailyLogScreen(viewModel = HealthViewModel(repository, authManager), onBack = {}, onSave = {})
    }
}
