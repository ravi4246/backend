package com.simats.siddha.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme

@Composable
fun OtpScreen(modifier: Modifier = Modifier, onVerify: () -> Unit) {
    var otp by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Enter OTP",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We have sent an OTP to your mobile number",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        Box(contentAlignment = Alignment.Center) {
            BasicTextField(
                value = otp,
                onValueChange = { if (it.length <= 6) otp = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(6) { index ->
                            val char = otp.getOrNull(index)?.toString() ?: ""
                            OutlinedTextField(
                                value = char,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .width(50.dp)
                                    .padding(horizontal = 4.dp),
                                textStyle = TextStyle(color = Color.Black, textAlign = TextAlign.Center)
                            )
                        }
                    }
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onVerify,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            enabled = otp.length == 6
        ) {
            Text(text = "Verify")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { /* TODO: Resend OTP */ }) {
            Text(text = "Resend OTP", color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OtpScreenPreview() {
    MyApplicationTheme {
        OtpScreen(onVerify = {})
    }
}