package com.lingualearn.app

import com.lingualearn.app.data.LanguageRepository
import com.lingualearn.app.data.content.CurriculumCatalog
import com.lingualearn.app.data.content.LanguageDataCatalog
import com.lingualearn.app.model.LearningLevel
import com.lingualearn.app.util.AudioHelper
import org.junit.Assert.*
import org.junit.Test

class CurriculumValidationTest {

    private val allLanguages = LanguageDataCatalog.allLanguages

    @Test
    fun testAllEighteenLanguagesExist() {
        assertEquals("Expected 18 supported languages in catalog", 18, allLanguages.size)
        val expectedCodes = setOf(
            "es", "fr", "de", "ja", "ko", "en", "it", "pt", "ru", "zh",
            "hi", "ta", "te", "ml", "tr", "nl", "sv", "ar"
        )
        val actualCodes = allLanguages.map { it.id }.toSet()
        assertEquals("Language IDs must match expected 18 codes", expectedCodes, actualCodes)
    }

    @Test
    fun testAllLanguagesHaveSixtyCurriculumLessons() {
        for (lang in allLanguages) {
            val allLessons = CurriculumCatalog.getAllLessonsForLanguage(lang.id)
            assertEquals(
                "Language ${lang.name} (${lang.id}) must have exactly 60 curriculum lessons (10 levels * 6 topics)",
                60,
                allLessons.size
            )

            val uniqueIds = allLessons.map { it.id }.toSet()
            assertEquals(
                "Language ${lang.name} (${lang.id}) must have 60 unique lesson IDs",
                60,
                uniqueIds.size
            )
        }
    }

    @Test
    fun testNoPlaceholderContentInAnyLanguage() {
        for (lang in allLanguages) {
            for (lvl in 1..10) {
                val lessons = CurriculumCatalog.getLessonsForLevel(lang.id, lvl)
                assertEquals(
                    "Language ${lang.id} at level $lvl must have 6 lessons",
                    6,
                    lessons.size
                )

                for (lesson in lessons) {
                    assertFalse(
                        "Lesson ${lesson.id} contains default placeholder 'expands your proficiency'",
                        lesson.exampleSentence.contains("expands your proficiency", ignoreCase = true)
                    )
                    assertFalse(
                        "Lesson ${lesson.id} contains default placeholder 'Core study of'",
                        lesson.meaning.contains("Core study of", ignoreCase = true)
                    )

                    assertTrue("Lesson ${lesson.id} word must not be blank", lesson.word.isNotBlank())
                    assertTrue("Lesson ${lesson.id} meaning must not be blank", lesson.meaning.isNotBlank())
                    assertTrue("Lesson ${lesson.id} pronunciation must not be blank", lesson.pronunciation.isNotBlank())
                    assertTrue("Lesson ${lesson.id} exampleSentence must not be blank", lesson.exampleSentence.isNotBlank())
                    assertTrue("Lesson ${lesson.id} exampleTranslation must not be blank", lesson.exampleTranslation.isNotBlank())
                    assertTrue("Lesson ${lesson.id} tip must not be blank", lesson.tip.isNotBlank())
                }
            }
        }
    }

    @Test
    fun testDistinctContentAcrossTopicsPerLevel() {
        // Ensures that within each level, each of the 6 topics has distinct vocabulary / phrase content
        for (lang in allLanguages) {
            for (lvl in 1..10) {
                val lessons = CurriculumCatalog.getLessonsForLevel(lang.id, lvl)
                val words = lessons.map { it.word.trim().lowercase() }.toSet()
                assertEquals(
                    "Language ${lang.id} level $lvl must have 6 distinct words across the 6 topics (found ${words.size})",
                    6,
                    words.size
                )
            }
        }
    }

    @Test
    fun testAudioHelperTtsCoverageForAllLanguages() {
        for (lang in allLanguages) {
            assertTrue(
                "Language ${lang.id} (${lang.name}) must have TTS mapping in AudioHelper",
                AudioHelper.isTtsMapped(lang.id)
            )
            assertNotNull(
                "Language ${lang.id} (${lang.name}) must have a non-null Locale in AudioHelper",
                AudioHelper.localeFor(lang.id)
            )
        }
    }

    @Test
    fun testLanguageRepositoryIntegration() {
        for (lang in allLanguages) {
            val lvl1Lessons = LanguageRepository.getLessonsForLevel(lang.id, 1)
            assertEquals("LanguageRepository should provide 6 lessons for level 1", 6, lvl1Lessons.size)

            val questions = LanguageRepository.getQuestionsForLanguage(lang.id, count = 5, levelNumber = 1)
            assertTrue("Questions should be generated for ${lang.id}", questions.isNotEmpty())
            for (q in questions) {
                assertTrue("Question text should not be blank", q.question.isNotBlank())
                assertEquals("Question should have 4 options", 4, q.options.size)
                assertTrue("Correct option index should be in bounds", q.correctIndex in 0..3)
            }
        }
    }
}
