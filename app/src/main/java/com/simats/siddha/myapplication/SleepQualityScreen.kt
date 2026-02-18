package com.simats.siddha.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme

@Composable
fun SleepQualityScreen(modifier: Modifier = Modifier, onContinue: () -> Unit) {
    var sliderPosition by remember { mutableStateOf(50f) }
    val sleepQuality = when {
        sliderPosition < 33f -> "Poor"
        sliderPosition < 66f -> "Good"
        else -> "Excellent"
    }
    val sleepDescription = when (sleepQuality) {
        "Poor" -> "Restless, less than 6 hours."
        "Good" -> "Generally restful, 6-7 hours."
        else -> "Deeply restful, 7-8 hours."
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sleep Quality",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Step 4 of 5",
                fontSize = 14.sp,
                color = Color(0xFF4CAF50)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(painter = painterResource(id = R.drawable.img), contentDescription = "Sleep", tint = Color(0xFF87CEEB))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "How is your sleep?", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = sleepQuality, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Poor")
            Text(text = "Excellent")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = sleepDescription)

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepQualityScreenPreview() {
    MyApplicationTheme {
        SleepQualityScreen(onContinue = {})
    }
}