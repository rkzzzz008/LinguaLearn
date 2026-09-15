package com.lingualearn.app.model

/**
 * Encapsulates the user's isolated learning progress for a specific language.
 * Stored in Firestore under: users/{uid}/languages/{languageCode}
 */
data class LanguageProgress(
    val languageCode: String,
    val xp: Int = 0,
    val currentLevel: Int = 1,
    val highestUnlockedLevel: Int = 1,
    val assessedLevel: Int = 1,
    val placementTestTaken: Boolean = false,
    val completedLessons: Set<String> = emptySet(),
    val bookmarkedLessons: Set<String> = emptySet(),
    val favoriteLessons: Set<String> = emptySet(),
    val quizScores: List<Map<String, Any>> = emptyList(),
    val quizAccuracy: Int = 0,
    val quizzesTaken: Int = 0,
    val streak: Int = 0,
    val vocabularyProgress: Int = 0,
    val currentLesson: Int = 1,
    val lastStudied: Long = System.currentTimeMillis()
)
