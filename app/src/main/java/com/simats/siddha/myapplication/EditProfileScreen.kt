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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.R
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme






@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val profile = viewModel.userProfile
    var name by remember(profile) { mutableStateOf(profile?.fullName ?: "") }
    var age by remember(profile) { mutableStateOf(profile?.age?.toString() ?: "") }
    var gender by remember(profile) { mutableStateOf(profile?.gender ?: "Male") }
    var bloodGroup by remember(profile) { mutableStateOf(profile?.bloodGroup ?: "") }

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
                        onClick = {
                            viewModel.updateProfile(name, age, gender, bloodGroup)
                            onSave()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .height(64.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF859A73))
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Save Changes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                        text = "Edit Profile",
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
                        Text(
                            "Personal Details",
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF859A73)) },
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

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = age,
                            onValueChange = { age = it },
                            label = { Text("Age") },
                            leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color(0xFF859A73)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                            "Gender Selection",
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            GenderButton(text = "Male", selected = gender == "Male") { gender = "Male" }
                            GenderButton(text = "Female", selected = gender == "Female") { gender = "Female" }
                            GenderButton(text = "Other", selected = gender == "Other") { gender = "Other" }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        OutlinedTextField(
                            value = bloodGroup,
                            onValueChange = { bloodGroup = it },
                            label = { Text("Blood Group") },
                            leadingIcon = { Icon(painter = painterResource(id = R.drawable.meditation), contentDescription = null, tint = Color(0xFF859A73)) },
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant) },
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
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun GenderButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = if (selected) Color(0xFF4CAF50) else Color.White, contentColor = if(selected) Color.White else Color.Black)
    ) {
        Text(text)
    }
}


@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
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
        EditProfileScreen(
            viewModel = UserViewModel(com.simats.siddha.myapplication.api.Repository(dummyApiService), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current)),
            onBack = {}, 
            onSave = {}
        )
    }
}
