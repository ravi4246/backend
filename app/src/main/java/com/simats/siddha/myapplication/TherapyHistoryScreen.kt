package com.simats.siddha.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.HealthLogDto
import com.simats.siddha.myapplication.api.HealthViewModel
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme
import java.time.format.DateTimeFormatter
import java.time.ZonedDateTime
import java.util.Locale





@Composable
fun TherapyHistoryScreen(
    modifier: Modifier = Modifier, 
    viewModel: HealthViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchHealthLogs()
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
                .padding(16.dp)
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
                    text = "Therapy History", 
                    fontSize = 24.sp, 
                    fontWeight = FontWeight.Bold, 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground // EarthyBrown
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            if (viewModel.isLoading && viewModel.healthLogs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF2E7D32))
                }
            } else if (viewModel.healthLogs.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.History, 
                            contentDescription = null, 
                            modifier = Modifier.size(80.dp), 
                            tint = Color(0xFF859A73).copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No therapy logs found yet", color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 16.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(viewModel.healthLogs) { log ->
                        HistoryItem(log)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItem(log: HealthLogDto) {
    val displayDate = try {
        val zonedDateTime = ZonedDateTime.parse(log.createdAt)
        zonedDateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault()))
    } catch (e: Exception) {
        log.createdAt ?: "Unknown Date"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = Color(0xFFE8F5E9)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32), // ForestGreen
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            
            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(
                    displayDate, 
                    fontWeight = FontWeight.Bold, 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                    fontSize = 17.sp
                )
                
                if (!log.symptoms.isNullOrBlank()) {
                    Text(
                        "Symptoms: ${log.symptoms}", 
                        color = Color(0xFFD32F2F), 
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.SuggestionChip(
                        onClick = { },
                        label = { Text("Digestion: ${log.digestionStatus}", fontSize = 11.sp) },
                        shape = RoundedCornerShape(8.dp),
                        colors = androidx.compose.material3.SuggestionChipDefaults.suggestionChipColors(
                            labelColor = Color(0xFF2E7D32),
                            containerColor = Color(0xFFE8F5E9)
                        ),
                        border = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    androidx.compose.material3.SuggestionChip(
                        onClick = { },
                        label = { Text("Activity: ${log.activityLevel}", fontSize = 11.sp) },
                        shape = RoundedCornerShape(8.dp),
                        colors = androidx.compose.material3.SuggestionChipDefaults.suggestionChipColors(
                            labelColor = Color(0xFF4A5D3F),
                            containerColor = Color(0xFF859A73).copy(alpha = 0.1f)
                        ),
                        border = null
                    )
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "Sleep", 
                    fontSize = 10.sp, 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${log.sleepQuality}/10", 
                    fontSize = 18.sp, 
                    color = Color(0xFF859A73),
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TherapyHistoryScreenPreview() {
    MyApplicationTheme {
        val dummyApiService = object : com.simats.siddha.myapplication.api.ApiService {
            override suspend fun register(request: com.simats.siddha.myapplication.api.RegisterRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.RegisterResponse("", "", com.simats.siddha.myapplication.api.UserDto(1, "", "", null)))
            override suspend fun login(credentials: com.simats.siddha.myapplication.api.LoginRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))
            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))
            override suspend fun getProfile() = retrofit2.Response.success(com.simats.siddha.myapplication.api.ProfileDto("", 0, "", "", "", "", "", "", "", ""))
            override suspend fun updateProfile(profile: com.simats.siddha.myapplication.api.ProfileDto) = retrofit2.Response.success(profile)
            override suspend fun getHealthLogs() = retrofit2.Response.success(listOf(
                com.simats.siddha.myapplication.api.HealthLogDto(1, "Constipation", 8, "Good", "Active", "2024-03-05T10:00:00Z")
            ))
            override suspend fun createHealthLog(log: com.simats.siddha.myapplication.api.HealthLogDto) = retrofit2.Response.success(log)
            override suspend fun getAnalysis() = retrofit2.Response.success(com.simats.siddha.myapplication.api.AnalysisResponse("Low Risk", "Improving", 82, 85, listOf(0.9f, 0.82f), emptyList(), "Keep up good habits", null, 8, 21, 5, true, emptyList()))
            override suspend fun forgotPassword(request: com.simats.siddha.myapplication.api.ForgotPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun resetPassword(request: com.simats.siddha.myapplication.api.ResetPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun chat(request: com.simats.siddha.myapplication.api.ChatRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.ChatResponse(""))

        }
        TherapyHistoryScreen(
            viewModel = HealthViewModel(com.simats.siddha.myapplication.api.Repository(dummyApiService), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current)),
            onBack = {}
        )
    }
}
