package com.simats.siddha.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
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
fun RiskAnalysisScreen(modifier: Modifier = Modifier, viewModel: UserViewModel, onBack: () -> Unit) {
    val analysis = viewModel.analysisData
    
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
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
                    text = "Risk Analysis", 
                    fontSize = 28.sp, 
                    fontWeight = FontWeight.ExtraBold,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Contributing Factors", 
                fontWeight = FontWeight.Bold, 
                fontSize = 20.sp,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            analysis?.riskFactors?.forEach { factor ->
                val color = if (factor.isPositive) Color(0xFF2E7D32) else Color(0xFFFFA000)
                val impact = if (factor.isPositive) "Low Risk" else "Medium Risk"
                ContributingFactorItem(factor.title, factor.subtitle, impact, color)
            }
            
            if (analysis?.riskFactors.isNullOrEmpty()) {
                Text(
                    "No risk factors calculated yet.", 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Assessment Patterns", 
                fontWeight = FontWeight.Bold, 
                fontSize = 20.sp,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            BehaviorPatternItem(R.drawable.pic_3, "Evening Logger", "You typically log between 8-10 PM. Consistent timing is good.")
            BehaviorPatternItem(R.drawable.pic_4, "Weekend Dip", "Activity drops on Saturdays. Consider setting weekend reminders.")
            BehaviorPatternItem(R.drawable.smoking, "Strong Start", "Your first week engagement was excellent. Maintain this momentum.")

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9).copy(alpha = 0.7f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                border = BorderStroke(1.dp, Color(0xFF859A73).copy(alpha = 0.3f))
            ) {
                Row(modifier = Modifier.padding(24.dp)) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF2E7D32))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            "AI Methodology", 
                            fontWeight = FontWeight.ExtraBold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Our predictive model analyzes your interaction patterns, time of logging, and symptom feedback to forecast adherence probability. This helps us provide timely nudges to keep you on track.",
                            fontSize = 14.sp,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ContributingFactorItem(title: String, subtitle: String, impact: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), 
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp), 
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(4.dp, 40.dp),
                color = color,
                shape = RoundedCornerShape(2.dp)
            ) {}
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)
                Text(subtitle, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp, lineHeight = 18.sp)
            }
            Surface(
                color = color.copy(alpha = 0.1f), 
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    impact, 
                    color = color, 
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), 
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun BehaviorPatternItem(icon: Int, title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), 
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp), 
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(52.dp),
                shape = CircleShape,
                color = Color(0xFFE8F5E9)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(id = icon), 
                        contentDescription = null, 
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)
                Text(subtitle, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp, lineHeight = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RiskAnalysisScreenPreview() {
    MyApplicationTheme {
        val dummyApi = object : com.simats.siddha.myapplication.api.ApiService {
            override suspend fun register(request: com.simats.siddha.myapplication.api.RegisterRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.RegisterResponse("", "", com.simats.siddha.myapplication.api.UserDto(1, "", "", null)))
            override suspend fun login(credentials: com.simats.siddha.myapplication.api.LoginRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))
            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))
            override suspend fun getProfile() = retrofit2.Response.success(com.simats.siddha.myapplication.api.ProfileDto("", 0, "", "", "", "", "", "", "", ""))
            override suspend fun updateProfile(profile: com.simats.siddha.myapplication.api.ProfileDto) = retrofit2.Response.success(profile)
            override suspend fun getHealthLogs() = retrofit2.Response.success(emptyList<com.simats.siddha.myapplication.api.HealthLogDto>())
            override suspend fun createHealthLog(log: com.simats.siddha.myapplication.api.HealthLogDto) = retrofit2.Response.success(log)
            override suspend fun getAnalysis() = retrofit2.Response.success(com.simats.siddha.myapplication.api.AnalysisResponse(riskLevel = "Low Risk", trend = "Improving", adherenceLevel = 82, confidenceScore = 80, trendGraph = listOf(0.9f, 0.82f), riskFactors = emptyList(), recommendation = "Keep it up", recommendedPlan = null, daysLogged = 8, totalDays = 21, streak = 5, canLog = true, insights = emptyList()))
            override suspend fun forgotPassword(request: com.simats.siddha.myapplication.api.ForgotPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun resetPassword(request: com.simats.siddha.myapplication.api.ResetPasswordRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.GenericResponse(""))
            override suspend fun chat(request: com.simats.siddha.myapplication.api.ChatRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.ChatResponse(""))
        }
        val vm = UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApi), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current))
        RiskAnalysisScreen(viewModel = vm, onBack = {})
    }
}
