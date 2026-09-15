package com.lingualearn.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lingualearn.app.model.Language
import com.lingualearn.app.model.QuizQuestion
import com.lingualearn.app.ui.components.EmptyStateView
import com.lingualearn.app.ui.theme.BrandAccent
import com.lingualearn.app.ui.theme.BrandError
import com.lingualearn.app.ui.theme.BrandPrimary
import com.lingualearn.app.ui.theme.BrandSecondary
import com.lingualearn.app.ui.theme.BrandStreak
import com.lingualearn.app.ui.theme.BrandSuccess

@Composable
fun QuizScreen(
    currentLanguage: Language,
    questions: List<QuizQuestion>,
    currentIndex: Int,
    selectedIndex: Int?,
    score: Int,
    isFinished: Boolean,
    isDailyPractice: Boolean,
    onAnswer: (Int) -> Unit,
    onNext: () -> Unit,
    onRestart: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (questions.isEmpty()) {
        EmptyStateView(
            emoji = "📝",
            title = "Loading Quiz Challenge",
            description = "Get ready to test your knowledge of ${currentLanguage.name}.",
            actionText = "Start Quiz Now",
            onAction = onRestart,
            modifier = modifier
        )
        return
    }

    if (isFinished) {
        QuizResultView(
            totalQuestions = questions.size,
            score = score,
            isDailyPractice = isDailyPractice,
            onRestart = onRestart,
            onBackToHome = onBackToHome,
            modifier = modifier
        )
        return
    }

    val currentQ = questions.getOrNull(currentIndex) ?: questions.first()
    val total = questions.size
    val progress = (currentIndex + 1).toFloat() / total.toFloat()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Top Bar: Quiz Title & Score counter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isDailyPractice) "⚡ Daily Practice Sprint" else "📝 Quick Quiz",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "Question ${currentIndex + 1} of $total • ${currentLanguage.flagEmoji} ${currentLanguage.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = BrandAccent.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, BrandAccent.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Score",
                        tint = BrandAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$score pts",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFFB45309)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Animated Progress Bar
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = BrandPrimary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Round
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = BrandPrimary.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "QUESTION ${currentIndex + 1}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = BrandPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = currentQ.question,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 28.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4 Multiple Choice Options with spring bounce on selection
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            currentQ.options.forEachIndexed { optIndex, optionText ->
                val isSelected = selectedIndex == optIndex
                val isCorrect = optIndex == currentQ.correctIndex
                val isAnswered = selectedIndex != null

                val optionScale by animateFloatAsState(
                    targetValue = if (isSelected) 1.02f else 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "optionScale"
                )

                val backgroundColor by animateColorAsState(
                    targetValue = when {
                        !isAnswered -> MaterialTheme.colorScheme.surface
                        isSelected && isCorrect -> Color(0xFFDCFCE7) // green 100
                        isSelected && !isCorrect -> Color(0xFFFEE2E2) // red 100
                        !isSelected && isCorrect -> Color(0xFFDCFCE7) // show correct
                        else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                    },
                    animationSpec = tween(300),
                    label = "optionBg"
                )

                val borderColor by animateColorAsState(
                    targetValue = when {
                        !isAnswered -> MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                        isSelected && isCorrect -> BrandSuccess
                        isSelected && !isCorrect -> BrandError
                        !isSelected && isCorrect -> BrandSuccess
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                    },
                    animationSpec = tween(300),
                    label = "optionBorder"
                )

                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = backgroundColor,
                    border = androidx.compose.foundation.BorderStroke(2.dp, borderColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(optionScale)
                        .clip(RoundedCornerShape(18.dp))
                        .clickable(enabled = !isAnswered) { onAnswer(optIndex) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            // Letter bubble (A, B, C, D)
                            Surface(
                                shape = CircleShape,
                                color = when {
                                    !isAnswered -> MaterialTheme.colorScheme.surfaceVariant
                                    isCorrect -> BrandSuccess
                                    isSelected -> BrandError
                                    else -> MaterialTheme.colorScheme.surfaceVariant
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    val letter = when (optIndex) {
                                        0 -> "A"
                                        1 -> "B"
                                        2 -> "C"
                                        else -> "D"
                                    }
                                    Text(
                                        text = letter,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = if (isAnswered && (isCorrect || isSelected)) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Correct / Incorrect Status Icon
                        if (isAnswered) {
                            if (isCorrect) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Correct",
                                    tint = BrandSuccess,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Wrong",
                                    tint = BrandError,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Explanation Card after answering
            if (selectedIndex != null) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "💡 ${currentQ.explanation}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(14.dp),
                        lineHeight = 20.sp
                    )
                }
            }
        }

        // Next Button
        Button(
            onClick = onNext,
            enabled = selectedIndex != null,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = if (currentIndex + 1 < total) "Next Question" else "See Results 🏆",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun QuizResultView(
    totalQuestions: Int,
    score: Int,
    isDailyPractice: Boolean,
    onRestart: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accuracy = if (totalQuestions > 0) (score * 100) / totalQuestions else 0
    val earnedXp = if (isDailyPractice) score * 10 + 20 else score * 10

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Trophy Badge
        Surface(
            shape = CircleShape,
            color = Color(0xFFFEF3C7),
            shadowElevation = 8.dp,
            modifier = Modifier.size(108.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = if (accuracy >= 80) "🏆" else if (accuracy >= 50) "⭐" else "💪",
                    fontSize = 54.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = if (accuracy >= 80) "Outstanding Mastery!" else if (accuracy >= 50) "Great Practice!" else "Good Effort!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (isDailyPractice) "Daily Sprint Complete! Streak boosted 🔥" else "You've successfully finished this language test.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Result Score Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$score / $totalQuestions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = BrandPrimary
                    )
                    Text(
                        text = "Score",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$accuracy%",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = BrandSuccess
                    )
                    Text(
                        text = "Accuracy",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "+$earnedXp",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = BrandAccent
                    )
                    Text(
                        text = "XP Earned",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Button(
            onClick = onRestart,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Replay,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Retake Quiz",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBackToHome,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "Back to Dashboard",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
    }
}
