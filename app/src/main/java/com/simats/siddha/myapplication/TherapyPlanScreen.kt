package com.simats.siddha.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.UserViewModel





@Composable
fun TherapyPlanScreen(
    modifier: Modifier = Modifier, 
    viewModel: UserViewModel, 
    onStartTherapy: () -> Unit, 
    onViewExplanation: () -> Unit, 
    onBack: () -> Unit
) {
    val analysis = viewModel.analysisData
    
    LaunchedEffect(Unit) {
        if (analysis == null) {
            viewModel.fetchAnalysis()
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background) // SandyBeige
            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        // "Suggestion" header
        Text(
            text = "Suggestion", 
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, 
            fontSize = 14.sp, 
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (viewModel.error != null) {
            Text("Error: ${viewModel.error}", color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(bottom = 16.dp))
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Your Therapy Plan",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground // EarthyBrown
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(36.dp))

        if (viewModel.isLoading && analysis == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                androidx.compose.material3.CircularProgressIndicator(color = Color(0xFF2E7D32))
            }
        } else {
            // AI Confidence Score
            val confidence = analysis?.confidenceScore ?: 0
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(130.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color(0xFFE8F5E9),
                        style = Stroke(width = 4.dp.toPx()),
                        radius = size.minDimension / 2
                    )
                }
                Text(text = "${confidence}%", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2E7D32))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("AI Confidence Score", color = Color(0xFF4CAF50), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Initial assessment confidence. This score will adjust\nbased on your therapy engagement over time.",
                textAlign = TextAlign.Center, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .fillMaxHeight()
                            .background(Color(0xFF2E7D32))
                    )
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                analysis?.recommendedPlan?.name ?: "No Plan Recommended", 
                                fontWeight = FontWeight.ExtraBold, 
                                fontSize = 18.sp, 
                                color = Color(0xFF2E7D32)
                            )
                            Surface(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp)) {
                                Text(
                                    "${analysis?.recommendedPlan?.durationDays ?: 0} Days", 
                                    color = Color(0xFF1B5E20), 
                                    fontWeight = FontWeight.Bold, 
                                    fontSize = 13.sp, 
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            analysis?.recommendation ?: "Unable to generate recommendation at this time.", 
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground, 
                            fontSize = 15.sp, 
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TherapyItem(Icons.Default.Eco, Color(0xFF2E7D32), "Diet", "Cooling", Modifier.weight(1f))
            TherapyItem(Icons.Default.LocalCafe, Color(0xFF859A73), "Herbs", "Triphala", Modifier.weight(1f))
            TherapyItem(Icons.Default.WbSunny, Color(0xFF4A5D3F), "Lifestyle", "Yoga", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(48.dp))

        TextButton(
            onClick = onViewExplanation,
            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2E7D32)),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("View Detailed Explanation", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = onStartTherapy,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Start Therapy", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(26.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TherapyItem(icon: ImageVector, iconTint: Color, title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 6.dp)
            .height(110.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), 
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon, 
                contentDescription = null, 
                tint = iconTint,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
    }
}


