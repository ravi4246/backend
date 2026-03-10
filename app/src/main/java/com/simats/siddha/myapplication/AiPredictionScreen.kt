package com.simats.siddha.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.R
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.cos
import kotlin.math.sin






@Composable
fun AiPredictionScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onBack: () -> Unit,
    onViewExplanation: () -> Unit
) {
    val analysis = viewModel.analysisData
    
    AiPredictionContent(
        modifier = modifier,
        adherencePercent = analysis?.adherenceLevel ?: 0,
        riskLevel = analysis?.riskLevel ?: "Unknown Risk",
        trend = analysis?.trend ?: "Stable",
        trendGraph = analysis?.trendGraph ?: listOf(0.5f, 0.5f),
        symptomTrend = analysis?.symptomTrend ?: listOf(0.4f, 0.5f, 0.45f, 0.6f, 0.55f, 0.7f, 0.65f, 0.8f),
        confidenceScore = analysis?.confidenceScore ?: 0,
        riskFactors = analysis?.riskFactors ?: emptyList(),
        onBack = onBack,
        onViewExplanation = onViewExplanation
    )
}

@Composable
fun AiPredictionContent(
    adherencePercent: Int,
    riskLevel: String,
    trend: String,
    trendGraph: List<Float>,
    symptomTrend: List<Float>,
    confidenceScore: Int,
    riskFactors: List<com.simats.siddha.myapplication.api.RiskFactor>,
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onViewExplanation: () -> Unit
) {
    val adherenceRatio = adherencePercent / 100f
    val riskColor = when {
        riskLevel.contains("Low") -> Color(0xFF4CAF50)
        riskLevel.contains("Medium") -> Color(0xFFFFC107)
        riskLevel.contains("High") -> Color(0xFFF44336)
        else -> Color.Gray
    }
    val trendIcon = when (trend) {
        "Improving" -> Icons.Default.TrendingUp
        "Declining" -> Icons.Default.Warning
        else -> Icons.Default.CheckCircle // Stable
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background) // SandyBeige
    ) {
        // Organic Animation Layer
        FallingLeavesBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack, 
                        contentDescription = "Back",
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "AI Health Insight", 
                    fontSize = 28.sp, 
                    fontWeight = FontWeight.ExtraBold,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFE8F5E9),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp), 
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_brain_ai), 
                        contentDescription = null, 
                        tint = Color.Unspecified, 
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Siddha-AI Adherence Analysis", 
                        color = Color(0xFF2E7D32), 
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(), 
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("CURRENT VITALITY RISK", color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val needleValue = 100f - adherencePercent
                    GaugeIndicator(modifier = Modifier.height(120.dp), value = needleValue) 
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(riskLevel, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = riskColor)
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(trendIcon, contentDescription = null, tint = riskColor, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(trend, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                    }
                    
                    HorizontalDivider(color = Color(0xFFF5F5F5), modifier = Modifier.padding(vertical = 24.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically, 
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicatorWithText(
                            progress = { adherenceRatio }, 
                            text = "$adherencePercent%",
                            color = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text("Adherence Probability", fontWeight = FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground)
                            Text("Based on your unique log history", color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(), 
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Risk Trend (Last 8 Days)", 
                        fontWeight = FontWeight.Bold, 
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    RiskTrendChart(
                    modifier = Modifier.height(180.dp).fillMaxWidth(),
                    points = symptomTrend,
                    color = Color(0xFF2E7D32)
                )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Key Indicators", 
                fontWeight = FontWeight.ExtraBold, 
                fontSize = 20.sp,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            riskFactors.forEach { factor ->
                RiskFactorItem(factor.title, factor.subtitle, factor.isPositive)
            }
            if (riskFactors.isEmpty()) {
                Text(
                    "No specific risk factors detected yet.", 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, 
                    fontSize = 14.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    color = Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Prediction Confidence: $confidenceScore%", 
                        color = Color(0xFF2E7D32), 
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Confidence increases with more consistent daily logging.", 
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, 
                    fontSize = 12.sp, 
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onViewExplanation,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant)
            ) {
                Text("View Scientific Methodology", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun RiskTrendChart(modifier: Modifier = Modifier, points: List<Float>, color: Color) {
    val backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background
    val gridLineColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
    val labelColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = createPath(points, size)
            
            // Draw Chart Background
            drawRect(
                color = backgroundColor,
                size = size
            )
            
            // Draw Grid Lines (Horizontal)
            val gridColor = gridLineColor
            for (i in 0..4) {
                val y = size.height * (i / 4f)
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }
            
            drawPath(
                path = path, 
                color = color, 
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )
            
            // Draw Points
            points.forEachIndexed { i, point ->
                val x = i * size.width / (points.size - 1)
                val y = size.height * (1 - point)
                drawCircle(
                    color = color,
                    radius = 12f,
                    center = Offset(x, y)
                )
                drawCircle(
                    color = Color.White,
                    radius = 6f,
                    center = Offset(x, y)
                )
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Day 1", fontSize = 10.sp, color = labelColor, fontWeight = FontWeight.Bold)
            Text("Recent", fontSize = 10.sp, color = labelColor, fontWeight = FontWeight.Bold)
        }
    }
}

fun createPath(points: List<Float>, size: Size): Path {
    return Path().apply {
        moveTo(0f, size.height * (1 - points.first()))
        for (i in 1 until points.size) {
            val x = i * size.width / (points.size - 1)
            val y = size.height * (1 - points[i])
            lineTo(x, y)
        }
    }
}

@Composable
fun GaugeIndicator(modifier: Modifier = Modifier, value: Float) {
    val greenColor = Color(0xFF2E7D32)
    val yellowColor = Color(0xFFFFA000)
    val redColor = Color(0xFFD32F2F)
    val trackColor = Color(0xFFF5F5F5)
    val onBackgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground

    Canvas(modifier = modifier.fillMaxWidth()) {
        val angle = (value / 100 * 180).coerceIn(0f, 180f)
        val strokeWidth = 36f

        // Background Arc
        drawArc(
            color = trackColor,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        // Green Arc
        drawArc(
            color = greenColor,
            startAngle = 180f,
            sweepAngle = 60f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        // Yellow Arc
        drawArc(
            color = yellowColor,
            startAngle = 240f,
            sweepAngle = 60f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
        // Red Arc
        drawArc(
            color = redColor,
            startAngle = 300f,
            sweepAngle = 60f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Needle
        val needleRotation = 180 + angle
        val lineLength = size.minDimension / 2.2f
        val angleInRad = (needleRotation * Math.PI / 180).toFloat()
        val endOffset = Offset(
            center.x + lineLength * cos(angleInRad),
            center.y + lineLength * sin(angleInRad)
        )
        
        // Needle Shadow
        drawLine(
            color = Color.Black.copy(alpha = 0.1f),
            start = center,
            end = endOffset.copy(y = endOffset.y + 2f),
            strokeWidth = 10f,
            cap = StrokeCap.Round
        )
        
        drawLine(
            color = onBackgroundColor,
            start = center,
            end = endOffset,
            strokeWidth = 10f,
            cap = StrokeCap.Round
        )
        drawCircle(onBackgroundColor, radius = 18f, center = center)
        drawCircle(Color.White, radius = 6f, center = center)
    }
}

@Composable
fun CircularProgressIndicatorWithText(progress: () -> Float, text: String, color: Color) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(64.dp)) {
        CircularProgressIndicator(
            progress = progress, 
            strokeWidth = 5.dp, 
            color = color, 
            trackColor = color.copy(alpha = 0.1f),
            strokeCap = StrokeCap.Round,
            modifier = Modifier.fillMaxSize()
        )
        Text(text, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = color)
    }
}

@Composable
fun RiskFactorItem(title: String, subtitle: String, isPositive: Boolean) {
    val color = if (isPositive) Color(0xFF2E7D32) else Color(0xFFFFA000)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp), 
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = color.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        if (isPositive) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)
                Text(subtitle, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 13.sp)
            }
            Surface(
                color = if(isPositive) Color(0xFFE8F5E9) else Color(0xFFFFF3E0), 
                shape = RoundedCornerShape(8.dp)
            ) {
                 Text(
                     if(isPositive) "Stable" else "Attention", 
                     color = color, 
                     modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                     fontSize = 11.sp,
                     fontWeight = FontWeight.Bold
                 )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AiPredictionScreenPreview() {
    MyApplicationTheme {
        AiPredictionContent(
            adherencePercent = 82,
            riskLevel = "Low Risk",
            trend = "Improving",
            trendGraph = listOf(0.9f, 0.82f),
            symptomTrend = listOf(0.4f, 0.5f, 0.6f, 0.7f),
            confidenceScore = 80,
            riskFactors = emptyList(),
            onBack = {},
            onViewExplanation = {}
        )
    }
}
