package com.simats.siddha.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SymptomsScreen(modifier: Modifier = Modifier, onContinue: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val symptoms = listOf(
        "Headache", "Fatigue", "Joint Pain",
        "Digestive Issues", "Skin Problems", "Insomnia",
        "Anxiety", "Back Pain", "Cold/Cough",
        "Acidity", "Hair Loss", "Eye Strain",
        "Body Heat", "Constipation", "Stress"
    )
    var selectedSymptoms by remember { mutableStateOf<Set<String>>(emptySet()) }
    val filteredSymptoms = symptoms.filter { it.contains(searchQuery, ignoreCase = true) }

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
                text = "Select Symptoms",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Step 2 of 5",
                fontSize = 14.sp,
                color = Color(0xFF4CAF50)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search symptoms...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            filteredSymptoms.forEach { symptom ->
                val isSelected = selectedSymptoms.contains(symptom)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedSymptoms = if (isSelected) {
                            selectedSymptoms - symptom
                        } else {
                            selectedSymptoms + symptom
                        }
                    },
                    label = { Text(symptom) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Selected: ")
            Spacer(modifier = Modifier.weight(1f))
            Text("${selectedSymptoms.size} symptoms", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            enabled = selectedSymptoms.isNotEmpty()
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SymptomsScreenPreview() {
    MyApplicationTheme {
        SymptomsScreen(onContinue = {})
    }
}