package com.simats.siddha.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PublishedWithChanges
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.R
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme





@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onTherapyClick: () -> Unit,
    onPredictionClick: () -> Unit,
    onLogTodayClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProgressClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAiRiskClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onReassessClick: () -> Unit,
    onChatClick: () -> Unit,
    onUnauthorized: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchAnalysis()
        viewModel.fetchProfile()
    }

    LaunchedEffect(viewModel.isUnauthorized) {
        if (viewModel.isUnauthorized) {
            onUnauthorized()
            viewModel.isUnauthorized = false
        }
    }

    DashboardContent(
        modifier = modifier,
        isLoading = viewModel.isLoading,
        error = viewModel.error,
        userProfile = viewModel.userProfile,
        fullNameFallback = viewModel.fullName,
        analysisData = viewModel.analysisData,
        onTherapyClick = onTherapyClick,
        onPredictionClick = onPredictionClick,
        onLogTodayClick = onLogTodayClick,
        onHistoryClick = onHistoryClick,
        onProgressClick = onProgressClick,
        onProfileClick = onProfileClick,
        onAiRiskClick = onAiRiskClick,
        onNotificationsClick = onNotificationsClick,
        onReassessClick = onReassessClick,
        onChatClick = onChatClick
    )
}

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    error: String?,
    userProfile: com.simats.siddha.myapplication.api.ProfileDto?,
    fullNameFallback: String,
    analysisData: com.simats.siddha.myapplication.api.AnalysisResponse?,
    onTherapyClick: () -> Unit,
    onPredictionClick: () -> Unit,
    onLogTodayClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProgressClick: () -> Unit,
    onProfileClick: () -> Unit,
    onAiRiskClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onReassessClick: () -> Unit,
    onChatClick: () -> Unit
) {
    Scaffold(
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomNavigationBar(onProgressClick = onProgressClick, onProfileClick = onProfileClick, onAiRiskClick = onAiRiskClick)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onChatClick,
                containerColor = Color(0xFF2E7D32),
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_brain_ai),
                    contentDescription = "Chat with AI",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Animation
            FallingLeavesBackground(modifier = Modifier.fillMaxSize())

            if (isLoading && analysisData == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF2E7D32))
                }
            } else {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("Good Morning,", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                            Text(
                                userProfile?.fullName ?: fullNameFallback.ifEmpty { "User" }, 
                                fontSize = 20.sp, 
                                color = Color(0xFF2E7D32), // ForestGreen
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = onNotificationsClick) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                        }
                        Surface(
                            modifier = Modifier.size(45.dp), 
                            shape = CircleShape, 
                            color = Color(0xFF859A73).copy(alpha = 0.2f),
                            border = BorderStroke(2.dp, Color(0xFF859A73))
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    userProfile?.fullName?.firstOrNull()?.toString()?.uppercase() ?: fullNameFallback.firstOrNull()?.toString()?.uppercase() ?: "U", 
                                    color = Color(0xFF2E7D32), 
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Vata-Pitta Balancing Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onTherapyClick),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                val planName = analysisData?.recommendedPlan?.name ?: "Vata-Pitta Balancing"
                                Text(planName, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF2E7D32))
                                val planDescription = analysisData?.recommendedPlan?.description ?: "Please complete assessment"
                                Text(
                                    text = planDescription,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 13.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                            val adherenceLevel = analysisData?.adherenceLevel ?: 0
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(56.dp)) {
                                CircularProgressIndicator(
                                    progress = adherenceLevel / 100f, 
                                    strokeWidth = 3.dp,
                                    color = Color(0xFF2E7D32),
                                    trackColor = Color(0xFFE8F5E9)
                                )
                                Text("$adherenceLevel%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = "Go to therapy", tint = Color(0xFF859A73))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 8.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_brain_ai), 
                            contentDescription = null, 
                            tint = Color.Unspecified, 
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("AI Confidence: ${analysisData?.confidenceScore ?: "--"}%", color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // AI Adherence Prediction Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onPredictionClick),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("AI Adherence Prediction", color = Color(0xFF2E7D32), fontWeight = FontWeight.Medium)
                                Text(
                                    "${analysisData?.riskLevel ?: "Loading..."} • ${analysisData?.adherenceLevel ?: "--"}% Completion", 
                                    fontWeight = FontWeight.Bold, 
                                    fontSize = 18.sp,
                                    color = Color(0xFF1B5E20)
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_brain_ai), 
                                contentDescription = null, 
                                tint = Color.Unspecified,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // AI Recommendation Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                    ) {
                        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Top) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_brain_ai), 
                                contentDescription = null, 
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp)
                            )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("AI Recommendation", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    analysisData?.recommendation ?: "Loading recommendation based on your health logs...", 
                                    fontSize = 14.sp,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                                    lineHeight = 20.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = onReassessClick, 
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA000)),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Reassess Now", color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    if (error != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Error: $error", color = Color.Red, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        val canLog = analysisData?.canLog ?: true
                        Button(
                            onClick = onLogTodayClick, 
                            modifier = Modifier.weight(1f).height(56.dp), 
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (canLog) Color(0xFF2E7D32) else Color.Gray
                            ),
                            shape = RoundedCornerShape(16.dp),
                            enabled = canLog
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (canLog) "Log Today" else "Logged", fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = onHistoryClick, 
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.5.dp, Color(0xFF2E7D32)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2E7D32))
                        ) {
                            Icon(Icons.Default.History, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("History", fontWeight = FontWeight.Bold)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // This week
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Weekly Trend", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                        TextButton(onClick = { /* TODO */ }) {
                            Text("View All", color = Color(0xFF2E7D32), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp), 
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        val fallbackTrends = listOf(1f, 1f, 1f, 1f, 1f, 0.6f, 0f)
                        val trends = analysisData?.trendGraph?.takeLast(7) ?: fallbackTrends
                        val days = listOf("M", "T", "W", "T", "F", "S", "S")

                        for (i in 0 until 7) {
                            WeekDayProgress(days[i], trends.getOrNull(i) ?: 0f)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    OutlinedButton(
                        onClick = onReassessClick, 
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.5.dp, Color(0xFF859A73)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF4A5D3F))
                    ) {
                        Icon(Icons.Default.PublishedWithChanges, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reassess Health Profile", fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}

@Composable
fun WeekDayProgress(day: String, progress: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .height(50.dp)
            .width(30.dp)
            .clip(RoundedCornerShape(8.dp)) 
            .background(Color.LightGray),
        contentAlignment = Alignment.BottomCenter) {
            if (progress > 0) {
                 Box(modifier = Modifier
                     .fillMaxWidth()
                     .height(50.dp * progress)
                     .background(Color(0xFF4CAF50)))
            }
        }
        Text(day)
    }
}

@Composable
fun BottomNavigationBar(onProgressClick: () -> Unit, onProfileClick: () -> Unit, onAiRiskClick: () -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(24.dp), tint = Color(0xFF666666)) },
            label = { Text("Home") },
            selected = true,
            onClick = { /* TODO */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Progress", modifier = Modifier.size(24.dp), tint = Color(0xFF666666)) },
            label = { Text("Progress") },
            selected = false,
            onClick = onProgressClick
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.pic_2), contentDescription = "AI Risk", modifier = Modifier.size(24.dp), tint = Color(0xFF666666)) },
            label = { Text("AI Risk") },
            selected = false,
            onClick = onAiRiskClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile", modifier = Modifier.size(24.dp), tint = Color(0xFF666666)) },
            label = { Text("Profile") },
            selected = false,
            onClick = onProfileClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MyApplicationTheme {
        DashboardContent(
            isLoading = false,
            error = null,
            userProfile = com.simats.siddha.myapplication.api.ProfileDto("Test User", 25, "Male", "O+"),
            fullNameFallback = "User",
            analysisData = com.simats.siddha.myapplication.api.AnalysisResponse("Low Risk", "Improving", 82, 85, listOf(0.9f, 0.82f), emptyList(), "Keep up good habits", null, 8, 21, 5, true, emptyList()),
            onTherapyClick = {},
            onPredictionClick = {},
            onLogTodayClick = {},
            onHistoryClick = {},
            onProgressClick = {},
            onProfileClick = {},
            onAiRiskClick = {},
            onNotificationsClick = {},
            onReassessClick = {},
            onChatClick = {}
        )
    }
}
