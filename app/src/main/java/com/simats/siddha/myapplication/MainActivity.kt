package com.simats.siddha.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                var currentScreen by remember { mutableStateOf(1) }
                var showAiAnalysis by remember { mutableStateOf(false) }

                if (showAiAnalysis) {
                    AiAnalysisScreen(onBack = { showAiAnalysis = false })
                } else {
                    when (currentScreen) {
                        1 -> OnboardingScreen(
                            onNext = { currentScreen = 2 },
                            onSkip = { currentScreen = 4 }
                        )
                        2 -> OnboardingScreen2(
                            onNext = { currentScreen = 3 },
                            onSkip = { currentScreen = 4 }
                        )
                        3 -> OnboardingScreen3(
                            onNext = { currentScreen = 4 },
                            onSkip = { currentScreen = 4 }
                        )
                        4 -> WelcomeScreen(onContinue = { currentScreen = 5 })
                        5 -> OtpScreen(onVerify = { currentScreen = 6 })
                        6 -> AiAssessmentScreen(onBeginAssessment = { currentScreen = 7 })
                        7 -> DisclaimerScreen(onContinue = { currentScreen = 8 })
                        8 -> CreateProfileScreen(onContinue = { currentScreen = 9 })
                        9 -> SymptomsScreen(onContinue = { currentScreen = 10 })
                        10 -> DigestionScreen(onContinue = { currentScreen = 11 })
                        11 -> SleepQualityScreen(onContinue = { currentScreen = 12 })
                        12 -> ActivityLevelScreen(onContinue = { currentScreen = 13 })
                        13 -> LifestyleHabitsScreen(onContinue = { currentScreen = 14 })
                        14 -> ReviewAssessmentScreen(onSubmit = { currentScreen = 15 })
                        15 -> {
                            LoadingScreen()
                            LaunchedEffect(Unit) {
                                delay(2000) // Simulate loading
                                currentScreen = 16
                            }
                        }
                        16 -> TherapyPlanScreen(
                            onStartTherapy = { currentScreen = 17 },
                            onViewExplanation = { showAiAnalysis = true }
                        )
                        17 -> StartTherapyScreen(onStartMyTherapy = { currentScreen = 18 })
                        18 -> HomeScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the Home Screen!")
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = "Smart Therapy Tracking"
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Smart Therapy Tracking",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Monitor adherence and progress with intelligent charts that adapt to your unique journey.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onNext,
            modifier = Modifier
                .width(200.dp)
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Next")
        }
        TextButton(
            onClick = onSkip
        ) {
            Text(text = "Skip", color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    MyApplicationTheme {
        OnboardingScreen(onNext = {}, onSkip = {})
    }
}
