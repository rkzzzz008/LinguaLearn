package com.lingualearn.app.model

data class QuizQuestion(
    val id: String,
    val languageId: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)
