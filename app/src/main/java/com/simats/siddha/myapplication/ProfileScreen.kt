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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme






@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onSettingsClick: () -> Unit,
    onPrivacyAndAiEthicsClick: () -> Unit,
    onHelpAndSupportClick: () -> Unit,
    onAboutClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.fetchProfile()
        viewModel.fetchAnalysis()
    }

    val profile = viewModel.userProfile
    val logged = viewModel.analysisData?.daysLogged ?: 0
    val active = if (viewModel.analysisData?.recommendedPlan != null) "1" else "0"
    val streak = viewModel.analysisData?.streak?.toString() ?: "0"

    ProfileContent(
        modifier = modifier,
        fullName = profile?.fullName ?: viewModel.fullName.ifEmpty { "User" },
        email = profile?.email ?: viewModel.email,
        phone = profile?.phone ?: viewModel.phone,
        age = profile?.age?.toString() ?: viewModel.age,
        gender = profile?.gender ?: viewModel.gender,
        bloodGroup = profile?.bloodGroup ?: viewModel.bloodGroup,
        activeCount = active,
        streakCount = streak,
        loggedCount = logged.toString(),
        onBack = onBack,
        onEditProfile = onEditProfile,
        onSettingsClick = onSettingsClick,
        onPrivacyAndAiEthicsClick = onPrivacyAndAiEthicsClick,
        onHelpAndSupportClick = onHelpAndSupportClick,
        onAboutClick = onAboutClick,
        onLogoutClick = onLogoutClick
    )
}

@Composable
fun ProfileContent(
    fullName: String,
    email: String,
    phone: String,
    age: String,
    gender: String,
    bloodGroup: String,
    activeCount: String,
    streakCount: String,
    loggedCount: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onSettingsClick: () -> Unit,
    onPrivacyAndAiEthicsClick: () -> Unit,
    onHelpAndSupportClick: () -> Unit,
    onAboutClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "My Profile", 
                    fontSize = 20.sp, 
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier.size(110.dp), 
                shape = CircleShape, 
                color = Color(0xFF859A73).copy(alpha = 0.2f),
                border = BorderStroke(2.dp, Color(0xFF859A73))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        fullName.firstOrNull()?.toString()?.uppercase() ?: "U",
                        color = Color(0xFF2E7D32), // ForestGreen
                        fontWeight = FontWeight.Bold,
                        fontSize = 44.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(fullName, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
            Text(email.ifEmpty { phone }, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                onClick = onEditProfile,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, Color(0xFF2E7D32)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2E7D32))
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profile", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InfoItem(label = "Age", value = age)
                    Box(modifier = Modifier.width(1.dp).height(30.dp).background(androidx.compose.material3.MaterialTheme.colorScheme.background))
                    InfoItem(label = "Gender", value = gender)
                    Box(modifier = Modifier.width(1.dp).height(30.dp).background(androidx.compose.material3.MaterialTheme.colorScheme.background))
                    InfoItem(label = "Blood", value = bloodGroup)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ProfileStat(value = activeCount, label = "Active")
                ProfileStat(value = streakCount, label = "Streak")
                ProfileStat(value = loggedCount, label = "Logged")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    ProfileMenuItem(icon = Icons.Default.Settings, text = "Settings", onClick = onSettingsClick)
                    androidx.compose.material3.HorizontalDivider(color = androidx.compose.material3.MaterialTheme.colorScheme.background, thickness = 1.dp)
                    ProfileMenuItem(
                        icon = Icons.Default.Security,
                        text = "Privacy & AI Ethics",
                        onClick = onPrivacyAndAiEthicsClick
                    )
                    androidx.compose.material3.HorizontalDivider(color = androidx.compose.material3.MaterialTheme.colorScheme.background, thickness = 1.dp)
                    ProfileMenuItem(
                        icon = Icons.Default.HelpOutline,
                        text = "Help & Support",
                        onClick = onHelpAndSupportClick
                    )
                    androidx.compose.material3.HorizontalDivider(color = androidx.compose.material3.MaterialTheme.colorScheme.background, thickness = 1.dp)
                    ProfileMenuItem(icon = Icons.Default.Info, text = "About App", onClick = onAboutClick)
                    androidx.compose.material3.HorizontalDivider(color = androidx.compose.material3.MaterialTheme.colorScheme.background, thickness = 1.dp)
                    ProfileMenuItem(
                        icon = Icons.Default.Logout,
                        text = "Log Out",
                        contentColor = Color.Red,
                        onClick = onLogoutClick
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileStat(value: String, label: String) {
    Card(modifier = Modifier.width(100.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(label, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
        Text(text = value.ifEmpty { "--" }, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, text: String, contentColor: Color = Color.Unspecified, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = if (contentColor == Color.Unspecified) LocalContentColor.current else contentColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, modifier = Modifier.weight(1f), color = if (contentColor == Color.Unspecified) Color.Unspecified else contentColor)
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MyApplicationTheme {
        ProfileContent(
            fullName = "Rahul Kumar",
            email = "rahul.kumar@example.com",
            phone = "1234567890",
            age = "25",
            gender = "Male",
            bloodGroup = "O+",
            activeCount = "1",
            streakCount = "5",
            loggedCount = "8",
            onBack = {},
            onEditProfile = {},
            onSettingsClick = {},
            onPrivacyAndAiEthicsClick = {},
            onHelpAndSupportClick = {},
            onAboutClick = {},
            onLogoutClick = {}
        )
    }
}
