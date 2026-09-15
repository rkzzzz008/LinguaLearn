package com.lingualearn.app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lingualearn.app.data.PlacementQuestion
import com.lingualearn.app.data.PlacementTestCatalog
import com.lingualearn.app.model.Language
import com.lingualearn.app.model.LearningLevel
import com.lingualearn.app.ui.theme.BrandPrimary
import com.lingualearn.app.ui.theme.BrandSecondary
import com.lingualearn.app.ui.theme.GoldAccent

enum class AssessmentStep {
    SELF_EVALUATION,
    DIAGNOSTIC_QUIZ,
    RECOMMENDATION_RESULT
}

data class SelfEvaluationOption(
    val tier: Int,
    val emoji: String,
    val title: String,
    val subtitle: String,
    val targetLevel: Int
)

@Composable
fun LevelAssessmentDialog(
    language: Language,
    onDismiss: () -> Unit,
    onApplyLevel: (levelNumber: Int) -> Unit
) {
    var step by remember { mutableStateOf(AssessmentStep.SELF_EVALUATION) }
    var selectedTier by remember { mutableStateOf(1) }

    // Quiz state
    val questions = remember(language.id) {
        PlacementTestCatalog.getPlacementTestForLanguage(language.id)
    }
    var currentQuestionIdx by remember { mutableStateOf(0) }
    var selectedAnswerIdx by remember { mutableStateOf<Int?>(null) }
    var quizScore by remember { mutableStateOf(0) }
    var recommendedLevel by remember { mutableStateOf(1) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .padding(vertical = 24.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Header Bar with Close / Back
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (step == AssessmentStep.DIAGNOSTIC_QUIZ) {
                            IconButton(onClick = { step = AssessmentStep.SELF_EVALUATION }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = language.flagEmoji, fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${language.name} Placement",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedContent(
                        targetState = step,
                        transitionSpec = {
                            fadeIn() togetherWith fadeOut()
                        },
                        label = "assessment_step"
                    ) { currentStep ->
                        when (currentStep) {
                            AssessmentStep.SELF_EVALUATION -> {
                                SelfEvaluationView(
                                    language = language,
                                    selectedTier = selectedTier,
                                    onSelectTier = { selectedTier = it },
                                    onTakeDiagnosticTest = {
                                        step = AssessmentStep.DIAGNOSTIC_QUIZ
                                        currentQuestionIdx = 0
                                        quizScore = 0
                                        selectedAnswerIdx = null
                                    },
                                    onConfirmSelfEvaluation = {
                                        recommendedLevel = PlacementTestCatalog.calculateRecommendedLevel(
                                            selfAssessmentTier = selectedTier,
                                            testScore = 0,
                                            totalQuestions = 0
                                        )
                                        step = AssessmentStep.RECOMMENDATION_RESULT
                                    }
                                )
                            }

                            AssessmentStep.DIAGNOSTIC_QUIZ -> {
                                DiagnosticQuizView(
                                    questions = questions,
                                    currentIndex = currentQuestionIdx,
                                    selectedAnswerIndex = selectedAnswerIdx,
                                    onSelectAnswer = { idx ->
                                        selectedAnswerIdx = idx
                                        val q = questions[currentQuestionIdx]
                                        if (idx == q.correctIndex) {
                                            quizScore += 1
                                        }
                                    },
                                    onNextQuestion = {
                                        if (currentQuestionIdx + 1 < questions.size) {
                                            currentQuestionIdx += 1
                                            selectedAnswerIdx = null
                                        } else {
                                            // Quiz finished! Calculate recommended level
                                            recommendedLevel = PlacementTestCatalog.calculateRecommendedLevel(
                                                selfAssessmentTier = selectedTier,
                                                testScore = quizScore,
                                                totalQuestions = questions.size
                                            )
                                            step = AssessmentStep.RECOMMENDATION_RESULT
                                        }
                                    }
                                )
                            }

                            AssessmentStep.RECOMMENDATION_RESULT -> {
                                RecommendationResultView(
                                    language = language,
                                    recommendedLevel = recommendedLevel,
                                    quizScore = quizScore,
                                    totalQuestions = questions.size,
                                    onAcceptRecommended = {
                                        onApplyLevel(recommendedLevel)
                                        onDismiss()
                                    },
                                    onStartAtLevel1 = {
                                        onApplyLevel(1)
                                        onDismiss()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SelfEvaluationView(
    language: Language,
    selectedTier: Int,
    onSelectTier: (Int) -> Unit,
    onTakeDiagnosticTest: () -> Unit,
    onConfirmSelfEvaluation: () -> Unit
) {
    val scrollState = rememberScrollState()

    val options = listOf(
        SelfEvaluationOption(1, "🌱", "I'm completely new", "Starting from absolute zero (greetings & alphabet)", 1),
        SelfEvaluationOption(2, "🌿", "I know a few basic words", "Familiar with food, numbers, and basic courtesy", 2),
        SelfEvaluationOption(3, "🧭", "I understand simple sentences", "Can order meals, read signs, and ask basic directions", 3),
        SelfEvaluationOption(4, "⛺", "I can have basic conversations", "Can talk about hobbies, past trips, and future plans", 5),
        SelfEvaluationOption(5, "🎯", "I know the language fairly well", "Comfortable with complex discussions and news", 7)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Select your starting level in ${language.name}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "We will place you into lessons tailored to your actual skill level.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Prominent Placement Test Recommendation Banner
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { onTakeDiagnosticTest() },
            shape = RoundedCornerShape(16.dp),
            color = BrandPrimary.copy(alpha = 0.12f),
            border = BorderStroke(1.dp, BrandPrimary.copy(alpha = 0.4f))
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = BrandPrimary.copy(alpha = 0.2f),
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = "Diagnostic Quiz",
                            tint = BrandPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Take 2-Min Placement Test",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = BrandPrimary
                    )
                    Text(
                        text = "Questions on vocabulary, grammar, reading & phrases for highest accuracy.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Or choose where you feel comfortable:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 5 Self-evaluation options
        options.forEach { opt ->
            val isSelected = selectedTier == opt.tier
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onSelectTier(opt.tier) },
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) BrandPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                border = if (isSelected) BorderStroke(1.5.dp, BrandPrimary) else null
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = opt.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = opt.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = opt.subtitle,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = BrandPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onConfirmSelfEvaluation,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
        ) {
            Text("Recommend My Level", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun DiagnosticQuizView(
    questions: List<PlacementQuestion>,
    currentIndex: Int,
    selectedAnswerIndex: Int?,
    onSelectAnswer: (Int) -> Unit,
    onNextQuestion: () -> Unit
) {
    val question = questions.getOrNull(currentIndex) ?: return
    val progress = (currentIndex + 1).toFloat() / questions.size.toFloat()

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = BrandSecondary.copy(alpha = 0.15f)
            ) {
                Text(
                    text = question.category.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandSecondary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }

            Text(
                text = "Question ${currentIndex + 1} of ${questions.size}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = BrandPrimary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = question.question,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        question.options.forEachIndexed { idx, option ->
            val isSelected = selectedAnswerIndex == idx
            val isAnswered = selectedAnswerIndex != null
            val isCorrect = idx == question.correctIndex

            val backgroundColor = when {
                !isAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                isSelected && isCorrect -> Color(0xFF10B981).copy(alpha = 0.15f)
                isSelected && !isCorrect -> Color(0xFFEF4444).copy(alpha = 0.15f)
                isCorrect -> Color(0xFF10B981).copy(alpha = 0.12f)
                else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            }

            val borderColor = when {
                !isAnswered -> if (isSelected) BrandPrimary else Color.Transparent
                isSelected && isCorrect -> Color(0xFF10B981)
                isSelected && !isCorrect -> Color(0xFFEF4444)
                isCorrect -> Color(0xFF10B981).copy(alpha = 0.6f)
                else -> Color.Transparent
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(enabled = selectedAnswerIndex == null) {
                        onSelectAnswer(idx)
                    },
                shape = RoundedCornerShape(12.dp),
                color = backgroundColor,
                border = BorderStroke(1.5.dp, borderColor)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.size(26.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = ('A' + idx).toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = option,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = onNextQuestion,
            enabled = selectedAnswerIndex != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
        ) {
            Text(
                text = if (currentIndex + 1 < questions.size) "Next Question" else "Complete Diagnostic",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun RecommendationResultView(
    language: Language,
    recommendedLevel: Int,
    quizScore: Int,
    totalQuestions: Int,
    onAcceptRecommended: () -> Unit,
    onStartAtLevel1: () -> Unit
) {
    val level = LearningLevel.getLevelByNumber(recommendedLevel)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = GoldAccent.copy(alpha = 0.18f),
            modifier = Modifier.size(68.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = level.badgeEmoji, fontSize = 36.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "ASSESSMENT COMPLETE",
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            color = GoldAccent,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Recommended Starting Level",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = level.fullTitle,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = "${level.difficultyIndicator} (${level.cefrCode}) • ${level.vocabRange}",
            style = MaterialTheme.typography.bodySmall,
            color = BrandPrimary,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Diagnostic Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                if (totalQuestions > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Diagnostic Score",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$quizScore / $totalQuestions correct (${((quizScore.toFloat() / totalQuestions) * 100).toInt()}%)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Text(
                    text = level.description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Two explicit choices per instructions
        Button(
            onClick = onAcceptRecommended,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
        ) {
            Text(
                text = "Start at ${level.title} (Level ${level.levelNumber})",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onStartAtLevel1,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Start from Level 1 (Absolute Beginner)",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
        }
    }
}
