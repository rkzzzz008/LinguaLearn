package com.lingualearn.app.data

import com.lingualearn.app.data.content.AsianIndianLanguagesContent
import com.lingualearn.app.data.content.CurriculumCatalog
import com.lingualearn.app.data.content.EuropeanLanguagesContent
import com.lingualearn.app.data.content.LanguageDailyWords
import com.lingualearn.app.data.content.LanguageDataCatalog
import com.lingualearn.app.data.content.LanguageQuizCatalog
import com.lingualearn.app.model.Language
import com.lingualearn.app.model.LearningLevel
import com.lingualearn.app.model.Lesson
import com.lingualearn.app.model.QuizQuestion

object LanguageRepository {

    val supportedLanguages: List<Language>
        get() = LanguageDataCatalog.allLanguages

    fun getLanguage(id: String): Language {
        return LanguageDataCatalog.getLanguage(id)
    }

    val baseLegacyLessons: List<Lesson> by lazy {
        EuropeanLanguagesContent.lessons + AsianIndianLanguagesContent.lessons
    }

    val quizPool: List<QuizQuestion>
        get() = LanguageQuizCatalog.quizPool

    fun getLessonsForLanguage(langId: String): List<Lesson> {
        val curriculum = CurriculumCatalog.getAllLessonsForLanguage(langId)
        val legacy = baseLegacyLessons.filter { it.languageId == langId }
        return curriculum + legacy
    }

    fun getLessonsForLevel(langId: String, levelNumber: Int): List<Lesson> {
        return CurriculumCatalog.getLessonsForLevel(langId, levelNumber)
    }

    fun getLessonsForLanguageAndLevel(langId: String, level: String?): List<Lesson> {
        val langLessons = getLessonsForLanguage(langId)
        if (level.isNullOrBlank() || level.equals("All", ignoreCase = true)) {
            return langLessons
        }
        val levelFiltered = langLessons.filter { it.level.equals(level, ignoreCase = true) }
        return if (levelFiltered.isNotEmpty()) levelFiltered else langLessons
    }

    fun getQuestionsForLanguage(langId: String, count: Int = 5, levelNumber: Int = 1): List<QuizQuestion> {
        // First try to generate questions from curriculum lessons of this level
        val levelLessons = getLessonsForLevel(langId, levelNumber)
        val curriculumQuestions = levelLessons.mapIndexed { idx, lesson ->
            val otherLessons = (levelLessons - lesson).shuffled()
            val wrongOptions = otherLessons.take(3).map { it.meaning }
            val allOptions = (wrongOptions + lesson.meaning).shuffled()
            val correctIdx = allOptions.indexOf(lesson.meaning)
            QuizQuestion(
                id = "lvl_q_${langId}_${levelNumber}_$idx",
                languageId = langId,
                question = "What is the meaning of '${lesson.word}' in $langId?",
                options = allOptions,
                correctIndex = if (correctIdx >= 0) correctIdx else 0,
                explanation = "'${lesson.word}' translates to '${lesson.meaning}' (${lesson.pronunciation})."
            )
        }

        val poolFromCatalog = quizPool.filter { it.languageId == langId }
        val combined = (curriculumQuestions + poolFromCatalog).shuffled()
        return if (combined.isNotEmpty()) combined.take(count) else quizPool.shuffled().take(count)
    }

    fun getDailyPracticeQuestions(count: Int = 10, langId: String? = null): List<QuizQuestion> {
        val list = mutableListOf<QuizQuestion>()
        if (langId != null) {
            list.addAll(getQuestionsForLanguage(langId, count))
        }
        // If not enough for the 10-question challenge, add questions from pool
        if (list.size < count) {
            val remaining = quizPool.filterNot { list.contains(it) }.shuffled()
            list.addAll(remaining)
        }
        while (list.size < count && quizPool.isNotEmpty()) {
            list.addAll(quizPool.shuffled())
        }
        return list.take(count)
    }

    fun getDailyWord(langId: String): Lesson {
        return LanguageDailyWords.getDailyWord(langId)
    }
}
