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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.ApiService
import com.simats.siddha.myapplication.api.Repository
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme






@Composable
fun ReviewAssessmentScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onSubmit: () -> Unit,
    onEditProfile: () -> Unit,
    onEditSymptoms: () -> Unit,
    onEditBodyMetrics: () -> Unit,
    onEditLifestyle: () -> Unit
) {
    if (viewModel.isRegistered || viewModel.isReassessmentComplete) {
        androidx.compose.runtime.LaunchedEffect(viewModel.isRegistered, viewModel.isReassessmentComplete) {
            viewModel.isReassessmentComplete = false
            onSubmit()
        }
    }

    ReviewAssessmentContent(
        modifier = modifier,
        isLoading = viewModel.isLoading,
        error = viewModel.error,
        isLoggedIn = viewModel.isLoggedIn,
        fullName = viewModel.fullName,
        age = viewModel.age,
        phone = viewModel.phone,
        email = viewModel.email,
        gender = viewModel.gender,
        bloodGroup = viewModel.bloodGroup,
        selectedSymptoms = viewModel.selectedSymptoms,
        digestionStatus = viewModel.digestionStatus,
        sleepQuality = viewModel.sleepQuality,
        activityLevel = viewModel.activityLevel,
        hasSmokingHabit = viewModel.hasSmokingHabit,
        hasAlcoholHabit = viewModel.hasAlcoholHabit,
        hasVegetarianDiet = viewModel.hasVegetarianDiet,
        hasRegularExercise = viewModel.hasRegularExercise,
        hasMeditationYoga = viewModel.hasMeditationYoga,
        hasAdequateWater = viewModel.hasAdequateWater,
        onSubmitClick = {
            if (viewModel.isLoggedIn) {
                viewModel.submitReassessment()
            } else {
                viewModel.register()
            }
        },
        onEditProfile = onEditProfile,
        onEditSymptoms = onEditSymptoms,
        onEditBodyMetrics = onEditBodyMetrics,
        onEditLifestyle = onEditLifestyle
    )
}

@Composable
fun ReviewAssessmentContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    error: String?,
    isLoggedIn: Boolean,
    fullName: String,
    age: String,
    phone: String,
    email: String,
    gender: String,
    bloodGroup: String,
    selectedSymptoms: Set<String>,
    digestionStatus: String,
    sleepQuality: String,
    activityLevel: String,
    hasSmokingHabit: Boolean,
    hasAlcoholHabit: Boolean,
    hasVegetarianDiet: Boolean,
    hasRegularExercise: Boolean,
    hasMeditationYoga: Boolean,
    hasAdequateWater: Boolean,
    onSubmitClick: () -> Unit,
    onEditProfile: () -> Unit,
    onEditSymptoms: () -> Unit,
    onEditBodyMetrics: () -> Unit,
    onEditLifestyle: () -> Unit
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
                .padding(16.dp)
                .verticalScroll(androidx.compose.foundation.rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Review Assessment",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(), 
                        horizontalArrangement = Arrangement.SpaceBetween, 
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Profile Information", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                        IconButton(onClick = onEditProfile) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = Color(0xFF2E7D32))
                        }
                    }
                    HorizontalDivider(color = Color(0xFFE8F5E9), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        InfoLabelValue("Name", fullName)
                        InfoLabelValue("Age", age)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        InfoLabelValue("Phone", phone)
                        InfoLabelValue("Email", email)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        InfoLabelValue("Gender", gender)
                        InfoLabelValue("Blood", bloodGroup)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Symptoms Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(), 
                        horizontalArrangement = Arrangement.SpaceBetween, 
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Symptoms", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                        IconButton(onClick = onEditSymptoms) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Symptoms", tint = Color(0xFF2E7D32))
                        }
                    }
                    androidx.compose.foundation.layout.ExperimentalLayoutApi::class
                    androidx.compose.foundation.layout.FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (selectedSymptoms.isEmpty()) {
                            Text("No symptoms selected", color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                        } else {
                            selectedSymptoms.forEach { symptom ->
                                Chip(text = symptom)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Body Metrics Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(), 
                        horizontalArrangement = Arrangement.SpaceBetween, 
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Vital Assessment", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                        IconButton(onClick = onEditBodyMetrics) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Metrics", tint = Color(0xFF2E7D32))
                        }
                    }
                    BodyMetricItem("Digestion", if (digestionStatus == "Excellent") 1f else if (digestionStatus == "Good") 0.6f else 0.3f, digestionStatus, Color(0xFFFFA500))
                    Spacer(modifier = Modifier.height(8.dp))
                    BodyMetricItem("Sleep Quality", if (sleepQuality == "Excellent") 1f else if (sleepQuality == "Good") 0.6f else 0.3f, sleepQuality, Color(0xFF4FC3F7))
                    Spacer(modifier = Modifier.height(8.dp))
                    BodyMetricItem("Activity Level", if (activityLevel == "Active") 1f else if (activityLevel == "Moderate") 0.6f else 0.3f, activityLevel, Color(0xFFFF5252))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Lifestyle Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(), 
                        horizontalArrangement = Arrangement.SpaceBetween, 
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Lifestyle Habits", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                        IconButton(onClick = onEditLifestyle) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Lifestyle", tint = Color(0xFF2E7D32))
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            LifestyleItem("Smoking", hasSmokingHabit)
                            LifestyleItem("Alcohol", hasAlcoholHabit)
                            LifestyleItem("Vegetarian", hasVegetarianDiet)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            LifestyleItem("Exercise", hasRegularExercise)
                            LifestyleItem("Meditation", hasMeditationYoga)
                            LifestyleItem("Hydration", hasAdequateWater)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (error != null) {
                Surface(
                    color = Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Text(
                        text = error, 
                        color = Color(0xFFD32F2F), 
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp
                    )
                }
            }

            Button(
                onClick = onSubmitClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32),
                    disabledContainerColor = Color(0xFF859A73).copy(alpha = 0.3f)
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    androidx.compose.material3.CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "Confirm & Submit", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun InfoLabelValue(label: String, value: String) {
    Column {
        Text(text = label, fontSize = 12.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
private fun Chip(text: String) {
    Surface(
        color = Color(0xFFE8F5E9),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF859A73).copy(alpha = 0.5f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            color = Color(0xFF2E7D32),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun BodyMetricItem(name: String, value: Float, status: String, color: Color) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(), 
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(name, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Medium)
            Surface(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    status, 
                    color = color, 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { value }, 
            modifier = Modifier.fillMaxWidth().height(8.dp), 
            color = color,
            trackColor = Color(0xFFF5F5F5),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}

@Composable
private fun LifestyleItem(name: String, value: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
        Surface(
            modifier = Modifier.size(24.dp),
            shape = CircleShape,
            color = if (value) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    if (value) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (value) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(name, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground, fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewAssessmentScreenPreview() {
    MyApplicationTheme {
        ReviewAssessmentContent(
            isLoading = false,
            error = null,
            isLoggedIn = true,
            fullName = "John Doe",
            age = "30",
            phone = "1234567890",
            email = "john@example.com",
            gender = "Male",
            bloodGroup = "O+",
            selectedSymptoms = setOf("Headache", "Fatigue"),
            digestionStatus = "Good",
            sleepQuality = "Excellent",
            activityLevel = "Moderate",
            hasSmokingHabit = false,
            hasAlcoholHabit = false,
            hasVegetarianDiet = true,
            hasRegularExercise = true,
            hasMeditationYoga = false,
            hasAdequateWater = true,
            onSubmitClick = {},
            onEditProfile = {},
            onEditSymptoms = {},
            onEditBodyMetrics = {},
            onEditLifestyle = {}
        )
    }
}
