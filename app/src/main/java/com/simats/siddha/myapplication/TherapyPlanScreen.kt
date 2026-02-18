package com.simats.siddha.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme

@Composable
fun TherapyPlanScreen(modifier: Modifier = Modifier, onStartTherapy: () -> Unit, onViewExplanation: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Your Therapy Plan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        // AI Confidence Score
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(150.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawArc(
                    color = Color(0xFF4CAF50),
                    startAngle = -90f,
                    sweepAngle = 360 * 0.87f,
                    useCenter = false,
                    style = Stroke(width = 15f, cap = StrokeCap.Round)
                )
            }
            Text(text = "87%", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
        Text("AI Confidence Score", color = Color.Gray)
        Text(
            text = "Initial assessment confidence. This score will adjust based on your therapy engagement over time.",
            textAlign = TextAlign.Center, color = Color.Gray, modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Vata-Pitta Balancing Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Vata-Pitta Balancing", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("21 Days", color = Color(0xFF4CAF50))
                }
                Text("A comprehensive protocol to restore balance to your bio-energies through cooling foods and grounding practices.")
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    TherapyItem(R.drawable.img, "Diet", "Cooling")
                    TherapyItem(R.drawable.img, "Herbs", "Triphala")
                    TherapyItem(R.drawable.img, "Lifestyle", "Yoga")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onViewExplanation) {
            Text("View Detailed Explanation")
        }

        Button(
            onClick = onStartTherapy,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Start Therapy")
        }
    }
}

@Composable
private fun TherapyItem(iconRes: Int, title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = iconRes), contentDescription = null, tint = Color.Unspecified)
        Text(title, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun TherapyPlanScreenPreview() {
    MyApplicationTheme {
        TherapyPlanScreen(onStartTherapy = {}, onViewExplanation = {})
    }
}