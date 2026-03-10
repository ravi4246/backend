package com.simats.siddha.myapplication

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import android.graphics.pdf.PdfDocument
import android.text.StaticLayout
import android.text.TextPaint
import android.text.Layout
import android.graphics.Typeface
import com.simats.siddha.myapplication.api.UserViewModel
import com.simats.siddha.myapplication.ui.theme.MyApplicationTheme



@Composable
fun StartTherapyScreen(modifier: Modifier = Modifier, viewModel: UserViewModel, onStartMyTherapy: () -> Unit, onBack: () -> Unit) {
    val context = LocalContext.current
    val plan = viewModel.analysisData?.recommendedPlan
    val userName = viewModel.userProfile?.fullName ?: "User"

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
                .padding(24.dp),
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack, 
                    contentDescription = "Back",
                    tint = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                )
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, 
                verticalArrangement = Arrangement.Center, 
                modifier = Modifier.fillMaxSize()
            ) {
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = RoundedCornerShape(40.dp),
                    color = Color(0xFF859A73).copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.CheckCircle, 
                            contentDescription = "Success", 
                            tint = Color(0xFF859A73), 
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "You're All Set!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Your personalized Siddha therapy plan is ready for your journey.",
                    textAlign = TextAlign.Center,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                
                Spacer(modifier = Modifier.height(48.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            plan?.name ?: "Personalized Siddha Protocol", 
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Starts Today • ${plan?.durationDays ?: 21} Days", 
                            color = Color(0xFF859A73),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(), 
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Daily Reminder",
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                            Surface(
                                color = Color(0xFF859A73).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    "07:00 AM", 
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF859A73),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = onStartMyTherapy,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF859A73))
                ) {
                    Text(text = "Start My Therapy", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                TextButton(onClick = { 
                    plan?.let {
                        downloadPlanAsPdf(context, it, userName)
                    } ?: run {
                        Toast.makeText(context, "No plan available to download", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Icon(
                        Icons.Default.ArrowDownward, 
                        contentDescription = "Download",
                        tint = Color(0xFF859A73)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Download Plan", 
                        color = Color(0xFF859A73),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

fun downloadPlanAsPdf(context: Context, plan: com.simats.siddha.myapplication.api.RecommendedPlanDto, userName: String) {
    val fileName = "Siddha_Therapy_Plan_${userName.replace(" ", "_").replace("[^a-zA-Z0-9_]".toRegex(), "")}.pdf"
    
    val pdfDocument = PdfDocument()
    val pageWidth = 595
    val pageHeight = 842
    var pageNum = 1
    var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNum).create()
    var page = pdfDocument.startPage(pageInfo)
    var canvas = page.canvas

    val margin = 50f
    val contentWidth = (pageWidth - 2 * margin).toInt()
    
    val titlePaint = TextPaint().apply {
        isAntiAlias = true
        textSize = 24f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        color = android.graphics.Color.BLACK
    }
    
    val headerPaint = TextPaint().apply {
        isAntiAlias = true
        textSize = 18f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        color = android.graphics.Color.rgb(133, 154, 115) // #859A73
    }
    
    val textPaint = TextPaint().apply {
        isAntiAlias = true
        textSize = 14f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        color = android.graphics.Color.DKGRAY
    }
    
    var currentY = margin

    fun checkPageBreak(requiredHeight: Float) {
        if (currentY + requiredHeight > pageHeight - margin) {
            pdfDocument.finishPage(page)
            pageNum++
            pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNum).create()
            page = pdfDocument.startPage(pageInfo)
            canvas = page.canvas
            currentY = margin
        }
    }

    fun drawSection(title: String?, content: String?, isMainTitle: Boolean = false) {
        if (title != null) {
            val paint = if (isMainTitle) titlePaint else headerPaint
            val layout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StaticLayout.Builder.obtain(title, 0, title.length, paint, contentWidth).build()
            } else {
                @Suppress("DEPRECATION")
                StaticLayout(title, paint, contentWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
            }
            checkPageBreak(layout.height.toFloat() + 20f)
            canvas.save()
            canvas.translate(margin, currentY)
            layout.draw(canvas)
            canvas.restore()
            currentY += layout.height + 10f
        }
        
        if (content != null) {
            val layout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StaticLayout.Builder.obtain(content, 0, content.length, textPaint, contentWidth).build()
            } else {
                @Suppress("DEPRECATION")
                StaticLayout(content, textPaint, contentWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
            }
            checkPageBreak(layout.height.toFloat() + 20f)
            canvas.save()
            canvas.translate(margin, currentY)
            layout.draw(canvas)
            canvas.restore()
            currentY += layout.height + 25f
        }
    }

    drawSection("Siddha Therapy Plan for $userName", null, true)
    currentY += 10f
    
    drawSection("Plan Summary", "Name: ${plan.name}\nDuration: ${plan.durationDays} Days\n\n${plan.description}")
    drawSection("Diet Plan", plan.dietPlan)
    drawSection("Herbs Plan", plan.herbsPlan)
    drawSection("Lifestyle Plan", plan.lifestylePlan)

    pdfDocument.finishPage(page)

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            
            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    pdfDocument.writeTo(outputStream)
                }
                Toast.makeText(context, "PDF downloaded to Downloads folder", Toast.LENGTH_LONG).show()
            } ?: run {
                Toast.makeText(context, "Failed to create file", Toast.LENGTH_SHORT).show()
            }
        } else {
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = java.io.File(downloadsDir, fileName)
            file.outputStream().use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            Toast.makeText(context, "PDF downloaded to Downloads folder", Toast.LENGTH_LONG).show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error saving PDF: ${e.message}", Toast.LENGTH_SHORT).show()
    } finally {
        pdfDocument.close()
    }
}

// Removing preview since it requires passing a real ViewModel instance which is complex to mock here.
