package com.lingualearn.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lingualearn.app.data.LanguageRepository
import com.lingualearn.app.model.Language
import com.lingualearn.app.model.LearningLevel
import com.lingualearn.app.model.Lesson
import com.lingualearn.app.ui.theme.BrandPrimary
import com.lingualearn.app.ui.theme.BrandSecondary
import com.lingualearn.app.ui.theme.GoldAccent

enum class LevelNodeState {
    COMPLETED,
    CURRENT,
    UNLOCKED,
    LOCKED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningJourneyScreen(
    currentLanguage: Language,
    currentLevelNumber: Int,
    highestUnlockedLevel: Int,
    languageXp: Int,
    completedLessons: Set<String>,
    onBack: () -> Unit,
    onOpenLesson: (Lesson) -> Unit,
    onStartLevelQuiz: (levelNumber: Int) -> Unit,
    onOpenPlacementTest: () -> Unit
) {
    val allLevels = LearningLevel.ALL_LEVELS
    var expandedLevelNumber by remember { mutableStateOf<Int?>(currentLevelNumber) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = currentLanguage.flagEmoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "${currentLanguage.name} Learning Journey",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "10-Level Proficiency Roadmap",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = BrandPrimary.copy(alpha = 0.12f),
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onOpenPlacementTest() }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = null,
                                tint = BrandPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Placement Test",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = BrandPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Overview Card
            item {
                val activeLvl = LearningLevel.getLevelByNumber(currentLevelNumber)
                val currentLevelPercent = activeLvl.calculateProgressPercent(languageXp)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    border = BorderStroke(1.dp, BrandPrimary.copy(alpha = 0.25f))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = BrandPrimary.copy(alpha = 0.12f)
                                ) {
                                    Text(
                                        text = "CURRENT LEVEL ${activeLvl.levelNumber} OF 10",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BrandPrimary,
                                        letterSpacing = 1.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = activeLvl.title,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${activeLvl.difficultyIndicator} • CEFR ${activeLvl.cefrCode} • ${activeLvl.vocabRange}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                shape = CircleShape,
                                color = GoldAccent.copy(alpha = 0.15f),
                                modifier = Modifier.size(54.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = activeLvl.badgeEmoji, fontSize = 28.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "⭐ $languageXp Language XP",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (currentLevelNumber < 10) "$currentLevelPercent% toward Level ${currentLevelNumber + 1}" else "Mastery Reached",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BrandPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        LinearProgressIndicator(
                            progress = currentLevelPercent / 100f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = BrandPrimary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }

            // 10 Levels Journey Map
            items(allLevels) { level ->
                val state = when {
                    level.levelNumber < currentLevelNumber -> LevelNodeState.COMPLETED
                    level.levelNumber == currentLevelNumber -> LevelNodeState.CURRENT
                    level.levelNumber <= highestUnlockedLevel -> LevelNodeState.UNLOCKED
                    else -> LevelNodeState.LOCKED
                }

                val isExpanded = expandedLevelNumber == level.levelNumber

                LevelJourneyCard(
                    languageId = currentLanguage.id,
                    level = level,
                    nodeState = state,
                    currentXp = languageXp,
                    completedLessons = completedLessons,
                    isExpanded = isExpanded,
                    onToggleExpand = {
                        expandedLevelNumber = if (isExpanded) null else level.levelNumber
                    },
                    onOpenLesson = onOpenLesson,
                    onStartQuiz = { onStartLevelQuiz(level.levelNumber) }
                )
            }
        }
    }
}

@Composable
private fun LevelJourneyCard(
    languageId: String,
    level: LearningLevel,
    nodeState: LevelNodeState,
    currentXp: Int,
    completedLessons: Set<String>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onOpenLesson: (Lesson) -> Unit,
    onStartQuiz: () -> Unit
) {
    val levelLessons = remember(languageId, level.levelNumber) {
        LanguageRepository.getLessonsForLevel(languageId, level.levelNumber)
    }

    val progressPercent = level.calculateProgressPercent(currentXp)
    val isCompleted = nodeState == LevelNodeState.COMPLETED || progressPercent == 100
    val isCurrent = nodeState == LevelNodeState.CURRENT
    val isLocked = nodeState == LevelNodeState.LOCKED

    val cardBorder = when {
        isCurrent -> BorderStroke(2.dp, BrandPrimary)
        isCompleted -> BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.5f))
        else -> null
    }

    val containerAlpha = if (isLocked) 0.6f else 1f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onToggleExpand() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) {
                MaterialTheme.colorScheme.surface
            } else if (isCompleted) {
                Color(0xFF10B981).copy(alpha = 0.05f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = cardBorder,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrent) 4.dp else 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status Badge Icon
                Surface(
                    shape = CircleShape,
                    color = when {
                        isCompleted -> Color(0xFF10B981).copy(alpha = 0.15f)
                        isCurrent -> BrandPrimary.copy(alpha = 0.15f)
                        isLocked -> MaterialTheme.colorScheme.surfaceVariant
                        else -> BrandSecondary.copy(alpha = 0.15f)
                    },
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        when {
                            isCompleted -> Text(text = "⭐", fontSize = 22.sp)
                            isCurrent -> Text(text = "🔓", fontSize = 22.sp)
                            isLocked -> Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                modifier = Modifier.size(20.dp)
                            )
                            else -> Icon(
                                imageVector = Icons.Default.LockOpen,
                                contentDescription = "Unlocked",
                                tint = BrandSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Level ${level.levelNumber} • ${level.title}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (isLocked) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
                        )

                        // Status pill
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = when {
                                isCompleted -> Color(0xFF10B981).copy(alpha = 0.15f)
                                isCurrent -> BrandPrimary.copy(alpha = 0.15f)
                                isLocked -> MaterialTheme.colorScheme.surfaceVariant
                                else -> BrandSecondary.copy(alpha = 0.15f)
                            }
                        ) {
                            Text(
                                text = when {
                                    isCompleted -> "✓ Completed"
                                    isCurrent -> "$progressPercent% Complete"
                                    isLocked -> "Locked (${level.minXp} XP)"
                                    else -> "Unlocked"
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    isCompleted -> Color(0xFF10B981)
                                    isCurrent -> BrandPrimary
                                    isLocked -> MaterialTheme.colorScheme.onSurfaceVariant
                                    else -> BrandSecondary
                                },
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${level.subtitle} • CEFR ${level.cefrCode} • ${level.vocabRange}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Progress bar for current or completed level
            if (!isLocked) {
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = progressPercent / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = if (isCompleted) Color(0xFF10B981) else BrandPrimary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            // Expandable content: Curriculum topics & lessons
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    Text(
                        text = level.description,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Curriculum Modules (${levelLessons.size} lessons)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    levelLessons.forEachIndexed { idx, lesson ->
                        val isLessonDone = completedLessons.contains(lesson.id)
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable(enabled = !isLocked) {
                                    onOpenLesson(lesson)
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = if (isLessonDone) Color(0xFF10B981).copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isLessonDone) Color(0xFF10B981).copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        if (isLessonDone) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "Done",
                                                tint = Color(0xFF10B981),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        } else {
                                            Text(
                                                text = "${idx + 1}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = lesson.title,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${lesson.word} • ${lesson.meaning}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                if (!isLocked) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Open",
                                        tint = BrandPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Action buttons
                    if (!isLocked) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    val firstIncomplete = levelLessons.firstOrNull { !completedLessons.contains(it.id) }
                                        ?: levelLessons.firstOrNull()
                                    if (firstIncomplete != null) {
                                        onOpenLesson(firstIncomplete)
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isCompleted) "Review Lessons" else "Practice Level",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            OutlinedButton(
                                onClick = onStartQuiz,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = null,
                                    tint = BrandPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Level Quiz",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = BrandPrimary
                                )
                            }
                        }
                    } else {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Reach ${level.minXp} XP in Level ${level.levelNumber - 1} or complete diagnostic test to unlock.",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
