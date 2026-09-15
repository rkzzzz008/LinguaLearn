package com.lingualearn.app.model

import androidx.compose.ui.graphics.Color

data class Language(
    val id: String,
    val name: String,
    val nativeName: String,
    val flagEmoji: String,
    val difficulty: String,
    val totalLessons: Int,
    val shortDescription: String = "",
    val levelIndicator: String = "Beginner",
    val category: String = "Popular",
    val startGradientColor: Color,
    val endGradientColor: Color,
    val greeting: String
)
