package com.simats.siddha.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun TherapyDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onBack: () -> Unit
) {
    val plan = viewModel.analysisData?.recommendedPlan
    val confidence = viewModel.analysisData?.confidenceScore ?: 0

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background) // SandyBeige
    ) {
        // Organic Animation Layer
        FallingLeavesBackground(modifier = Modifier.fillMaxSize())

        Scaffold(
            containerColor = Color.Transparent
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
                            Icons.Default.ArrowBack, 
                            contentDescription = "Back",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        text = "Therapy Details", 
                        fontSize = 28.sp, 
                        fontWeight = FontWeight.ExtraBold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = plan?.name ?: "No Plan Recommended",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = Color(0xFF859A73)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                modifier = Modifier.size(32.dp),
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF859A73).copy(alpha = 0.1f)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Schedule, 
                                        contentDescription = "Duration", 
                                        tint = Color(0xFF859A73), 
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Duration: ${plan?.durationDays ?: "--"} Days", 
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, 
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                modifier = Modifier.size(32.dp),
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF859A73).copy(alpha = 0.1f)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.img), 
                                        contentDescription = "Confidence", 
                                        tint = Color(0xFF859A73), 
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "AI Confidence: $confidence%", 
                                color = Color(0xFF859A73), 
                                fontSize = 15.sp, 
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    "About This Therapy", 
                    fontWeight = FontWeight.Bold, 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = plan?.description ?: "Complete your daily health logs to get a personalized AI therapy recommendation.",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "AI Therapy Insights", 
                    fontWeight = FontWeight.Bold, 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                val insights = viewModel.analysisData?.insights ?: emptyList()
                if (insights.isNotEmpty()) {
                    insights.forEachIndexed { index, insight ->
                        val color = if (index % 2 == 0) Color(0xFFFFB300) else Color(0xFF4CAF50)
                        val tag = if (index % 2 == 0) "AI Suggestion" else "Positive Trend"
                        val icon = if (index % 2 == 0) R.drawable.ic_brain_amber else null // Using amber brain icon for suggestion
                        
                        InsightItem(
                            tag = tag,
                            description = insight,
                            recommendation = if (index % 2 == 0) "Recommendation: Consider adding a morning warm water routine." else "Continue current evening meditation practice.",
                            color = color,
                            iconRes = icon
                        )
                    }
                } else {
                    InsightItem(
                        tag = "AI Suggestion",
                        description = "Data collection in progress",
                        recommendation = "Keep logging your health daily for more accurate AI insights.",
                        color = Color(0xFFFFB300),
                        iconRes = R.drawable.ic_brain_amber
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Daily Regimen", 
                    fontWeight = FontWeight.Bold, 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                RegimenItem(R.drawable.ic_diet, "Diet", plan?.dietPlan ?: "Loading...", Color(0xFF4CAF50))
                RegimenItem(R.drawable.ic_herbs, "Herbs", plan?.herbsPlan ?: "Loading...", Color(0xFFFFB300))
                RegimenItem(R.drawable.ic_lifestyle, "Lifestyle", plan?.lifestylePlan ?: "Loading...", Color(0xFFFF9800))
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun RegimenItem(iconRes: Int, title: String, description: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Accent Bar
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
                    .background(color)
            )
            
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = color.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        title,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        description,
                        color = Color(0xFF666666),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun InsightItem(tag: String, description: String, recommendation: String, color: Color, iconRes: Int? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            // Left Accent Bar
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
                    .background(color)
            )
            
            Column(modifier = Modifier.padding(16.dp)) {
                Surface(
                    color = color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (iconRes != null) {
                            Icon(
                                painter = painterResource(id = iconRes),
                                contentDescription = null,
                                tint = color,
                                modifier = Modifier.size(14.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable. pic_2), // Trend icon
                                contentDescription = null,
                                tint = color,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            tag,
                            fontWeight = FontWeight.Bold,
                            color = color,
                            fontSize = 11.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    description,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    recommendation,
                    color = Color(0xFF666666),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
                
                if (tag.contains("Suggestion")) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "This is an AI suggestion, not medical advice.",
                        color = Color.LightGray,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TherapyDetailsScreenPreview() {
    MyApplicationTheme {
        val dummyApiService = object : com.simats.siddha.myapplication.api.ApiService {
            override suspend fun register(request: com.simats.siddha.myapplication.api.RegisterRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.RegisterResponse("", "", com.simats.siddha.myapplication.api.UserDto(1, "", "", null)))
            override suspend fun login(credentials: com.simats.siddha.myapplication.api.LoginRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))
            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))
            override suspend fun getProfile() = retrofit2.Response.success(com.simats.siddha.myapplication.api.ProfileDto("", 0, "", "", "", "", "", "", "", ""))
            override suspend fun updateProfile(profile: com.simats.siddha.myapplication.api.ProfileDto) = retrofit2.Response.success(profile)
            override suspend fun getHealthLogs() = retrofit2.Response.success(emptyList<com.simats.siddha.myapplication.api.HealthLogDto>())
            override suspend fun createHealthLog(log: com.simats.siddha.myapplication.api.HealthLogDto) = retrofit2.Response.success(log)
            override suspend fun getAnalysis() = retrofit2.Response.success(com.simats.siddha.myapplication.api.AnalysisResponse("Low Risk", "Improving", 82, 85, listOf(0.9f, 0.82f), emptyList(), "Keep up good habits", null, 8, 21, 5, true, emptyList()))
            override suspend fun forgotPassword(request: com.simats.siddha.myapplication.api.ForgotPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun resetPassword(request: com.simats.siddha.myapplication.api.ResetPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun chat(request: com.simats.siddha.myapplication.api.ChatRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.ChatResponse(""))

        }
        TherapyDetailsScreen(
            viewModel = UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApiService), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current)),
            onBack = {}
        )
    }
}
