package com.simats.siddha.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme

@Composable
fun AiAnalysisScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(text = "AI Analysis", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Key Factors", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            AnalysisFactorItem("Body Constitution", "Dominant Pitta detected.", "High Impact", Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
            AnalysisFactorItem("Digestive Health", "Irregular appetite patterns.", "Medium Impact", Color.Yellow)
            Spacer(modifier = Modifier.height(16.dp))
            AnalysisFactorItem("Sleep Quality", "Insufficient restorative sleep.", "High Impact", Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
            AnalysisFactorItem("Lifestyle Score", "Good activity levels.", "Low Impact", Color.Green)
        }

        Spacer(modifier = Modifier.weight(1f))

        // How AI Determined This
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("How AI Determined This", fontWeight = FontWeight.Bold)
                Text("Our algorithm cross-referenced your symptom pattern and body metrics against 5,000+ traditional Siddha case studies to identify the root cause of your imbalances.")
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text(text = "Back to Suggestion", color = Color.Black)
        }
    }
}

@Composable
private fun AnalysisFactorItem(title: String, subtitle: String, impact: String, color: Color) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.height(80.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(10.dp)
                    .background(color)
            )
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(title, fontWeight = FontWeight.Bold)
                    Surface(color = color.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small) {
                        Text(text = impact, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = color, fontSize = 12.sp)
                    }
                }
                Text(subtitle, color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AiAnalysisScreenPreview() {
    MyApplicationTheme {
        AiAnalysisScreen(onBack = {})
    }
}