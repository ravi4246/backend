package com.simats.siddha.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
fun ReviewAssessmentScreen(modifier: Modifier = Modifier, onSubmit: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Review Assessment",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Profile", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    IconButton(onClick = { /* Handle edit */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = Color(0xFF4CAF50))
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Name: Jane Doe")
                    Text("Age: 30")
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Gender: Female")
                    Text("Blood: O+")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Symptoms Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Symptoms", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    IconButton(onClick = { /* Handle edit */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Symptoms", tint = Color(0xFF4CAF50))
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Chip("Headache")
                    Chip("Joint Pain")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Body Metrics Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Body Metrics", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    IconButton(onClick = { /* Handle edit */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Body Metrics", tint = Color(0xFF4CAF50))
                    }
                }
                BodyMetricItem("Digestion", 0.7f, "Good", Color.Yellow)
                BodyMetricItem("Sleep", 0.6f, "Good", Color.Blue)
                BodyMetricItem("Activity", 0.3f, "Low", Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lifestyle Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Lifestyle", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    IconButton(onClick = { /* Handle edit */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Lifestyle", tint = Color(0xFF4CAF50))
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Column {
                        LifestyleItem("smoking", false)
                        LifestyleItem("vegetarian", true)
                        LifestyleItem("meditation", true)
                    }
                    Column {
                        LifestyleItem("alcohol", false)
                        LifestyleItem("exercise", true)
                        LifestyleItem("water", true)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Submit Assessment")
        }
    }
}

@Composable
private fun Chip(text: String) {
    Surface(
        color = Color(0xFFE8F5E9),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, Color(0xFF4CAF50))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = Color(0xFF4CAF50)
        )
    }
}

@Composable
private fun BodyMetricItem(name: String, value: Float, status: String, color: Color) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(name)
            Text(status)
        }
        LinearProgressIndicator(progress = value, modifier = Modifier.fillMaxWidth(), color = color)
    }
}

@Composable
private fun LifestyleItem(name: String, value: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(
            if (value) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = if (value) Color.Green else Color.Red
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(name)
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewAssessmentScreenPreview() {
    MyApplicationTheme {
        ReviewAssessmentScreen(onSubmit = {})
    }
}