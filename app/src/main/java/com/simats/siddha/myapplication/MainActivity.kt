package com.simats.siddha.myapplication

import android.content.pm.PackageManager
import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.simats.siddha.myapplication.AboutScreen
import com.simats.siddha.myapplication.ActivityLevelScreen
import com.simats.siddha.myapplication.AiAnalysisScreen
import com.simats.siddha.myapplication.AiAssessmentScreen
import com.simats.siddha.myapplication.AiPredictionScreen
import com.simats.siddha.myapplication.api.AuthManager
import com.simats.siddha.myapplication.api.ChatViewModel
import com.simats.siddha.myapplication.api.HealthViewModel
import com.simats.siddha.myapplication.api.Repository
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.CreateAccountScreen
import com.simats.siddha.myapplication.CreateProfileScreen
import com.simats.siddha.myapplication.DailyLogScreen
import com.simats.siddha.myapplication.DashboardScreen
import com.simats.siddha.myapplication.DigestionScreen
import com.simats.siddha.myapplication.DisclaimerScreen
import com.simats.siddha.myapplication.EditProfileScreen
import com.simats.siddha.myapplication.ForgotPasswordScreen
import com.simats.siddha.myapplication.HelpAndSupportScreen
import com.simats.siddha.myapplication.LifestyleHabitsScreen
import com.simats.siddha.myapplication.LoadingScreen
import com.simats.siddha.myapplication.LogoutScreen
import com.simats.siddha.myapplication.NotificationsScreen
import com.simats.siddha.myapplication.OnboardingScreen
import com.simats.siddha.myapplication.OnboardingScreen2
import com.simats.siddha.myapplication.OnboardingScreen3
import com.simats.siddha.myapplication.PrivacyAndAiEthicsScreen
import com.simats.siddha.myapplication.ProfileScreen
import com.simats.siddha.myapplication.ResetPasswordScreen
import com.simats.siddha.myapplication.ReviewAssessmentScreen
import com.simats.siddha.myapplication.RiskAnalysisScreen
import com.simats.siddha.myapplication.SettingsScreen
import com.simats.siddha.myapplication.SleepQualityScreen
import com.simats.siddha.myapplication.SplashScreen
import com.simats.siddha.myapplication.StartTherapyScreen
import com.simats.siddha.myapplication.SymptomsScreen
import com.simats.siddha.myapplication.TherapyDetailsScreen
import com.simats.siddha.myapplication.TherapyHistoryScreen
import com.simats.siddha.myapplication.TherapyPlanScreen
import com.simats.siddha.myapplication.TherapyProgressScreen
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme
import com.simats.siddha.myapplication.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint



// Import all the screen composables


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted
        } else {
            // Permission denied
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            val userViewModel: UserViewModel = hiltViewModel()
            MyApplicationTheme(darkTheme = userViewModel.isDarkMode) {
                AppNavigation(userViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(userViewModel: UserViewModel) {
    val navController = rememberNavController()
    val healthViewModel: HealthViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(onTimeout = { navController.navigate("onboarding1") }) }
        composable("onboarding1") {
            OnboardingScreen(
                onNext = { navController.navigate("onboarding2") },
                onSkip = { navController.navigate("welcome") })
        }
        composable("onboarding2") {
            OnboardingScreen2(
                onNext = { navController.navigate("onboarding3") },
                onSkip = { navController.navigate("welcome") })
        }
        composable("onboarding3") {
            OnboardingScreen3(
                onNext = { navController.navigate("welcome") },
                onSkip = { navController.navigate("welcome") })
        }
        composable("welcome") { 
            WelcomeScreen(
                viewModel = userViewModel,
                onContinue = { navController.navigate("aiAssessment") }, 
                onGoToDashboard = { navController.navigate("dashboard") },
                onSignUp = { navController.navigate("createAccount") },
                onForgotPassword = { navController.navigate("forgot_password") }
            ) 
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                viewModel = userViewModel,
                onBack = { navController.popBackStack() },
                onNavigateToReset = { navController.navigate("reset_password") },
                onNavigateToSignIn = { navController.navigate("welcome") }
            )
        }
        composable("reset_password") {
            ResetPasswordScreen(
                viewModel = userViewModel,
                onBack = { navController.popBackStack() },
                onNavigateToSignIn = { navController.navigate("welcome") }
            )
        }
        composable("aiAssessment") {
            AiAssessmentScreen(onBeginAssessment = {
                navController.navigate(
                    "disclaimer"
                )
            })
        }
        composable("disclaimer") { DisclaimerScreen(onContinue = { navController.navigate("createProfile") }) }
        composable("createProfile") { CreateProfileScreen(viewModel = userViewModel, onContinue = { navController.navigate("symptoms") }) }
        composable("symptoms") { SymptomsScreen(viewModel = userViewModel, onContinue = { navController.navigate("digestion") }) }
        composable("digestion") { DigestionScreen(viewModel = userViewModel, onContinue = { navController.navigate("sleepQuality") }) }
        composable("sleepQuality") { SleepQualityScreen(viewModel = userViewModel, onContinue = { navController.navigate("activityLevel") }) }
        composable("activityLevel") { ActivityLevelScreen(viewModel = userViewModel, onContinue = { navController.navigate("lifestyleHabits") }) }
        composable("lifestyleHabits") {
            LifestyleHabitsScreen(viewModel = userViewModel, onContinue = {
                navController.navigate(
                    "reviewAssessment"
                )
            })
        }
        composable("reviewAssessment") {
            ReviewAssessmentScreen(
                viewModel = userViewModel,
                onSubmit = { navController.navigate("loading") },
                onEditProfile = { navController.navigate("editProfile") },
                onEditSymptoms = { navController.navigate("editSymptoms") },
                onEditBodyMetrics = { navController.navigate("editBodyMetrics") },
                onEditLifestyle = { navController.navigate("editLifestyle") }
            )
        }
        composable("editSymptoms") {
            SymptomsScreen(
                viewModel = userViewModel,
                onContinue = { navController.navigate("reviewAssessment") },
                onBack = { navController.popBackStack() })
        }
        composable("editBodyMetrics") {
            DigestionScreen(
                viewModel = userViewModel,
                onContinue = { navController.navigate("reviewAssessment") },
                onBack = { navController.popBackStack() })
        }
        composable("editLifestyle") {
            LifestyleHabitsScreen(
                viewModel = userViewModel,
                onContinue = { navController.navigate("reviewAssessment") },
                onBack = { navController.popBackStack() })
        }
        composable("loading") { LoadingScreen(onTimeout = { navController.navigate("therapyPlan") }) }
        composable("therapyPlan") {
            TherapyPlanScreen(
                viewModel = userViewModel,
                onStartTherapy = { navController.navigate("startTherapy") },
                onViewExplanation = { navController.navigate("aiAnalysis") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("aiAnalysis") { AiAnalysisScreen(viewModel = userViewModel, onBack = { navController.popBackStack() }) }
        composable("startTherapy") {
            StartTherapyScreen(
                viewModel = userViewModel,
                onStartMyTherapy = {
                    navController.navigate("dashboard")
                }, 
                onBack = { navController.popBackStack() }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                viewModel = userViewModel,
                onTherapyClick = { navController.navigate("therapyDetails") },
                onPredictionClick = { navController.navigate("aiPrediction") },
                onLogTodayClick = { navController.navigate("dailyLog") },
                onHistoryClick = { navController.navigate("therapyHistory") },
                onProgressClick = { navController.navigate("therapyProgress") },
                onProfileClick = { navController.navigate("profile") },
                onAiRiskClick = { navController.navigate("aiPrediction") },
                onNotificationsClick = { navController.navigate("notifications") },
                onReassessClick = { navController.navigate("reviewAssessment") },
                onChatClick = { navController.navigate("chat") },
                onUnauthorized = {
                    userViewModel.logout()
                    navController.navigate("welcome") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable("therapyDetails") { TherapyDetailsScreen(viewModel = userViewModel, onBack = { navController.popBackStack() }) }
        composable("aiPrediction") {
            AiPredictionScreen(
                viewModel = userViewModel,
                onBack = { navController.popBackStack() },
                onViewExplanation = { navController.navigate("riskAnalysis") }
            )
        }
        composable("dailyLog") {
            DailyLogScreen(
                viewModel = healthViewModel,
                onBack = { navController.popBackStack() },
                onSave = { 
                    userViewModel.fetchAnalysis()
                    navController.popBackStack() 
                })
        }
        composable("therapyHistory") {
            TherapyHistoryScreen(viewModel = healthViewModel, onBack = { navController.popBackStack() })
        }
        composable("riskAnalysis") {
            RiskAnalysisScreen(
                viewModel = userViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("therapyProgress") {
            TherapyProgressScreen(
                viewModel = userViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("profile") {
            ProfileScreen(
                viewModel = userViewModel,
                onBack = { navController.popBackStack() },
                onEditProfile = { navController.navigate("editProfile") },
                onSettingsClick = { navController.navigate("settings") },
                onPrivacyAndAiEthicsClick = { navController.navigate("privacyAndAiEthics") },
                onHelpAndSupportClick = { navController.navigate("helpAndSupport") },
                onAboutClick = { navController.navigate("about") },
                onLogoutClick = { navController.navigate("logout") }
            )
        }
        composable("editProfile") {
            EditProfileScreen(viewModel = userViewModel, onBack = { navController.popBackStack() }, onSave = { navController.popBackStack() })
        }
        composable("settings") {
            SettingsScreen(viewModel = userViewModel, onBack = { navController.popBackStack() })
        }
        composable("privacyAndAiEthics") {
            PrivacyAndAiEthicsScreen(onBack = { navController.popBackStack() })
        }
        composable("helpAndSupport") {
            HelpAndSupportScreen(onBack = { navController.popBackStack() })
        }
        composable("about") {
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable("logout") {
            LogoutScreen(
                onLogout = { 
                    userViewModel.logout()
                    healthViewModel.clearState()
                    navController.navigate("welcome") {
                        popUpTo(0) { inclusive = true }
                    }
                }, 
                onCancel = { navController.popBackStack() }
            )
        }
        composable("notifications") {
            NotificationsScreen(onBack = { navController.popBackStack() })
        }
        composable("createAccount") {
            CreateAccountScreen(
                viewModel = userViewModel,
                onCreateAccount = { navController.navigate("aiAssessment") }, 
                onSignIn = { navController.navigate("welcome") }
            )
        }
        composable("chat") {
            val chatViewModel: ChatViewModel = hiltViewModel()
            ChatScreen(
                viewModel = chatViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
