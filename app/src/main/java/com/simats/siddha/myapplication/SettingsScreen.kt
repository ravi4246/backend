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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.api.AuthManager
import com.simats.siddha.myapplication.api.Repository
import com.simats.siddha.myapplication.api.ApiService
import androidx.compose.ui.platform.LocalContext



@Composable
fun SettingsScreen(modifier: Modifier = Modifier, viewModel: UserViewModel, onBack: () -> Unit) {
    var pushNotifications by remember { mutableStateOf(true) }
    var dailyReminder by remember { mutableStateOf(true) }
    val darkMode = viewModel.isDarkMode

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
                        text = "Settings",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        SettingsItem(icon = Icons.Default.Notifications, text = "Push Notifications") {
                            Switch(
                                checked = pushNotifications,
                                onCheckedChange = { pushNotifications = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF859A73),
                                    uncheckedThumbColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                                    uncheckedTrackColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                        HorizontalDivider(color = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 8.dp))

                        SettingsItem(icon = Icons.Default.Schedule, text = "Daily Reminder") {
                            Switch(
                                checked = dailyReminder,
                                onCheckedChange = { dailyReminder = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF859A73),
                                    uncheckedThumbColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                                    uncheckedTrackColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 48.dp, top = 4.dp, bottom = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Reminder Time", color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                            Surface(
                                color = Color(0xFFE8F5E9),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    "07:00 AM",
                                    color = Color(0xFF2E7D32),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        HorizontalDivider(color = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 8.dp))

                        SettingsItem(icon = Icons.Default.Language, text = "Language") {
                            Text("English", color = Color(0xFF859A73), fontWeight = FontWeight.Bold)
                        }

                        HorizontalDivider(color = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.padding(vertical = 8.dp))

                        SettingsItem(icon = Icons.Default.WbSunny, text = "Dark Mode") {
                            Switch(
                                checked = darkMode,
                                onCheckedChange = { viewModel.toggleDarkMode(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF859A73),
                                    uncheckedThumbColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                                    uncheckedTrackColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsItem(icon: ImageVector, text: String, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFF859A73).copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = Color(0xFF859A73), modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Medium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
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
    val viewModel = UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApiService), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current))
    MyApplicationTheme {
        SettingsScreen(viewModel = viewModel, onBack = {})
    }
}
