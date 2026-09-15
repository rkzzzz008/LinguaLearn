package com.lingualearn.app.data.content

/**
 * Encapsulates the localized lesson content for curriculum modules across all supported languages.
 */
data class CurriculumLessonContent(
    val word: String,
    val meaning: String,
    val pronunciation: String,
    val exampleSentence: String,
    val exampleTranslation: String,
    val tip: String
)
