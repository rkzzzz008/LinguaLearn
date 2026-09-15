package com.lingualearn.app.model

data class Lesson(
    val id: String,
    val languageId: String,
    val title: String,
    val category: String, // "Vocabulary", "Grammar", "Phrases"
    val level: String,    // "Beginner", "Intermediate", "Advanced"
    val word: String,
    val meaning: String,
    val pronunciation: String,
    val exampleSentence: String,
    val exampleTranslation: String,
    val tip: String = "",
    val xpReward: Int = 20,
    val levelNumber: Int = 1
)
