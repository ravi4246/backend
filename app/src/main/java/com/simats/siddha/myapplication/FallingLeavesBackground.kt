package com.simats.siddha.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import com.simats.siddha.myapplication.R
import kotlin.math.sin
import kotlin.random.Random





private data class Leaf(
    var x: Float,
    var y: Float,
    val size: Float,
    var rotation: Float,
    val rotationSpeed: Float,
    val horizontalSpeed: Float,
    val verticalSpeed: Float,
    val swingAmplitude: Float,
    val swingFrequency: Float,
    var swingPhase: Float
)

@Composable
fun FallingLeavesBackground(modifier: Modifier = Modifier) {
    val leafBitmap = ImageBitmap.imageResource(id = R.drawable.img_1)
    val leaves = remember { mutableStateListOf<Leaf>() }
    var screenWidthPx by remember { mutableStateOf(0f) }
    var screenHeightPx by remember { mutableStateOf(0f) }
    val density = LocalDensity.current.density

    // Initialize leaves
    LaunchedEffect(screenWidthPx, screenHeightPx, density) {
        if (screenWidthPx > 0 && screenHeightPx > 0 && leaves.isEmpty()) {
            leaves.addAll(List(25) {
                Leaf(
                    x = Random.nextFloat() * screenWidthPx,
                    y = Random.nextFloat() * screenHeightPx,
                    size = (Random.nextFloat() * 20 + 25) * density,
                    rotation = Random.nextFloat() * 360,
                    rotationSpeed = (Random.nextFloat() - 0.5f) * 2,
                    verticalSpeed = (Random.nextFloat() * 0.5f + 0.5f) * density,
                    horizontalSpeed = (Random.nextFloat() - 0.5f) * 0.5f * density,
                    swingAmplitude = Random.nextFloat() * 10f + 5f,
                    swingFrequency = Random.nextFloat() * 0.01f + 0.005f,
                    swingPhase = Random.nextFloat() * 2 * Math.PI.toFloat()
                )
            })
        }
    }

    // Animation loop
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { 
                leaves.forEachIndexed { index, leaf ->
                    leaf.y += leaf.verticalSpeed
                    leaf.swingPhase += leaf.swingFrequency
                    leaf.x += leaf.horizontalSpeed + sin(leaf.swingPhase) * leaf.swingAmplitude
                    leaf.rotation += leaf.rotationSpeed

                    // Reset leaf when it goes off-screen
                    if (leaf.y > screenHeightPx + leaf.size) {
                        leaves[index] = leaf.copy(
                            x = Random.nextFloat() * screenWidthPx,
                            y = -leaf.size
                        )
                    }
                }
            }
        }
    }

    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    Canvas(modifier = modifier.fillMaxSize()) {
        if (screenWidthPx == 0f) screenWidthPx = size.width
        if (screenHeightPx == 0f) screenHeightPx = size.height

        leaves.forEach { leaf ->
            withTransform({
                translate(left = leaf.x, top = leaf.y)
                rotate(degrees = leaf.rotation, pivot = Offset(leaf.size / 2f, leaf.size / 2f))
            }) {
                drawImage(
                    image = leafBitmap,
                    dstSize = androidx.compose.ui.unit.IntSize(leaf.size.toInt(), leaf.size.toInt()),
                    alpha = if (isDark) 0.3f else 0.7f
                )
            }
        }
    }
}
