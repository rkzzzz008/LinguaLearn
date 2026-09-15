package com.lingualearn.app.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random

private data class ConfettiParticle(
    val x: Float,
    val initialY: Float,
    val speedY: Float,
    val size: Float,
    val color: Color,
    val rotationSpeed: Float,
    val amplitude: Float
)

@Composable
fun ConfettiOverlay(
    modifier: Modifier = Modifier,
    particleCount: Int = 60
) {
    val colors = listOf(
        Color(0xFF4F46E5),
        Color(0xFF06B6D4),
        Color(0xFFF59E0B),
        Color(0xFF22C55E),
        Color(0xFFEC4899),
        Color(0xFFFF6B00)
    )

    val particles = remember {
        List(particleCount) {
            ConfettiParticle(
                x = Random.nextFloat(),
                initialY = Random.nextFloat() * -0.5f,
                speedY = 0.4f + Random.nextFloat() * 0.6f,
                size = 14f + Random.nextFloat() * 18f,
                color = colors.random(),
                rotationSpeed = 180f + Random.nextFloat() * 360f,
                amplitude = 20f + Random.nextFloat() * 40f
            )
        }
    }

    val transition = rememberInfiniteTransition(label = "confetti")
    val progress = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confetti_progress"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        particles.forEachIndexed { index, p ->
            val curProgress = (progress.value + (index * 0.03f)) % 1f
            val y = (p.initialY + curProgress * 1.5f) * height
            val x = (p.x * width) + kotlin.math.sin(curProgress * 6.28f * 2) * p.amplitude

            if (y in -20f..height + 20f) {
                rotate(degrees = curProgress * p.rotationSpeed, pivot = Offset(x, y)) {
                    drawRect(
                        color = p.color,
                        topLeft = Offset(x - p.size / 2, y - p.size / 2),
                        size = Size(p.size, p.size * 0.6f)
                    )
                }
            }
        }
    }
}
