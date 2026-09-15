package com.lingualearn.app.model

import androidx.compose.ui.graphics.Color

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val currentProgress: Int,
    val requiredProgress: Int,
    val isUnlocked: Boolean,
    val xpReward: Int,
    val badgeColor: Color
)
