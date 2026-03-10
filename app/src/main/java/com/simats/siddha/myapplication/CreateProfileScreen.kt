package com.simats.siddha.myapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.AnalysisResponse
import com.simats.siddha.myapplication.api.ApiService
import com.simats.siddha.myapplication.api.HealthLogDto
import com.simats.siddha.myapplication.api.LoginRequest
import com.simats.siddha.myapplication.api.ProfileDto
import com.simats.siddha.myapplication.api.RegisterRequest
import com.simats.siddha.myapplication.api.RegisterResponse
import com.simats.siddha.myapplication.api.Repository
import com.simats.siddha.myapplication.api.TokenResponse
import com.simats.siddha.myapplication.api.UserDto
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme
import retrofit2.Response





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onContinue: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("Male", "Female", "Other")
    val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")

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
                        onClick = onContinue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .height(64.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF859A73))
                    ) {
                        Text(text = "Continue to Assessment", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    onBack?.let {
                        IconButton(onClick = it) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack, 
                                contentDescription = "Back",
                                tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    Text(
                        text = "Create Your Profile",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        color = Color(0xFFE8F5E9),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Step 1 of 5",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
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
                            "Basic Information", 
                            fontWeight = FontWeight.Bold, 
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = viewModel.fullName,
                            onValueChange = { viewModel.fullName = it },
                            label = { Text("Full Name") },
                            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color(0xFF859A73)) },
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
                            value = viewModel.age,
                            onValueChange = { viewModel.age = it },
                            label = { Text("Age") },
                            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = Color(0xFF859A73)) },
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
                            text = "Gender Selection",
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                            genders.forEach { gender ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(end = 16.dp)
                                ) {
                                    RadioButton(
                                        selected = viewModel.gender == gender,
                                        onClick = { viewModel.gender = gender },
                                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF859A73))
                                    )
                                    Text(text = gender, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground, fontSize = 14.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Medical History Details",
                            fontWeight = FontWeight.Bold,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = viewModel.bloodGroup,
                                onValueChange = {},
                                label = { Text("Blood Group") },
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF859A73),
                                    unfocusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant,
                                    focusedLabelColor = Color(0xFF859A73),
                                    unfocusedLabelColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = RoundedCornerShape(12.dp),
                                textStyle = TextStyle(color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                            )
                            ExposedDropdownMenu(
                                expanded = expanded, 
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                bloodGroups.forEach { bloodGroup ->
                                    DropdownMenuItem(
                                        text = { Text(bloodGroup, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground) },
                                        onClick = {
                                            viewModel.bloodGroup = bloodGroup
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CreateProfileScreenPreview() {
    MyApplicationTheme {
        val dummyApiService = object : ApiService {
            override suspend fun register(request: RegisterRequest): Response<RegisterResponse> {
                return Response.success(RegisterResponse("", "", UserDto(1, "", "", null)))
            }

            override suspend fun login(credentials: LoginRequest): Response<TokenResponse> {
                return Response.success(TokenResponse("", ""))
            }

            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest): retrofit2.Response<com.simats.siddha.myapplication.api.TokenResponse> {
                return retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))
            }

            override suspend fun getProfile(): Response<ProfileDto> {
                return Response.success(ProfileDto("", 0, "", "", "", "", "", "", "", "", ""))
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
        CreateProfileScreen(viewModel = UserViewModel(Repository(dummyApiService), com.simats.siddha.myapplication.api.AuthManager(androidx.compose.ui.platform.LocalContext.current)), onContinue = {}, onBack = {})
    }
}
