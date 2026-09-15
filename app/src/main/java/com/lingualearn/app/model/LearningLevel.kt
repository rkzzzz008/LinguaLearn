package com.lingualearn.app.model

/**
 * Defines the 10 Language Proficiency Levels in LinguaLearn.
 */
data class LearningLevel(
    val levelNumber: Int,               // 1..10
    val title: String,                  // e.g. "Absolute Beginner"
    val subtitle: String,               // e.g. "Foundations & First Steps"
    val description: String,            // Description of what the learner achieves
    val cefrCode: String,               // e.g. "Pre-A1", "A1", "A2", "B1", "B2", "C1", "C2"
    val minXp: Int,                     // Starting XP threshold for this level
    val nextLevelXp: Int,               // Target XP to unlock the next level
    val vocabRange: String,             // e.g. "50–100 words"
    val difficultyIndicator: String,    // e.g. "Introductory", "Fundamental", "Intermediate", "Mastery"
    val badgeEmoji: String,             // e.g. "🌱", "🌿", "🧭", "⛺", "🚀", "💡", "🎯", "🏆", "🌟", "👑"
    val curriculumTopics: List<String>  // The 6 specific curriculum topics for this level
) {
    val fullTitle: String
        get() = "Level $levelNumber — $title"

    val xpRequiredForThisLevel: Int
        get() = (nextLevelXp - minXp).coerceAtLeast(1)

    fun calculateProgressPercent(currentXp: Int): Int {
        if (currentXp <= minXp) return 0
        if (currentXp >= nextLevelXp) return 100
        val earned = currentXp - minXp
        val totalNeeded = nextLevelXp - minXp
        return ((earned.toFloat() / totalNeeded.toFloat()) * 100).toInt().coerceIn(0, 100)
    }

    fun xpEarnedInLevel(currentXp: Int): Int {
        return (currentXp - minXp).coerceIn(0, xpRequiredForThisLevel)
    }

    companion object {
        val ALL_LEVELS: List<LearningLevel> = listOf(
            LearningLevel(
                levelNumber = 1,
                title = "Absolute Beginner",
                subtitle = "Foundations & First Steps",
                description = "Master essential greetings, self-introductions, basic responses, numbers 1–10, and fundamental pronouns.",
                cefrCode = "Pre-A1",
                minXp = 0,
                nextLevelXp = 150,
                vocabRange = "50–100 words",
                difficultyIndicator = "Introductory",
                badgeEmoji = "🌱",
                curriculumTopics = listOf(
                    "Greetings",
                    "Introducing Yourself",
                    "Yes / No / Basic Responses",
                    "Numbers 1–10",
                    "Basic Pronouns",
                    "Essential Words"
                )
            ),
            LearningLevel(
                levelNumber = 2,
                title = "Beginner",
                subtitle = "Family & Everyday Words",
                description = "Talk about family, identify colors and food, master calendar days and months, ask simple questions, and use basic verbs.",
                cefrCode = "A1",
                minXp = 150,
                nextLevelXp = 350,
                vocabRange = "100–250 words",
                difficultyIndicator = "Fundamental",
                badgeEmoji = "🌿",
                curriculumTopics = listOf(
                    "Family",
                    "Colors",
                    "Food",
                    "Days & Months",
                    "Simple Questions",
                    "Basic Verbs"
                )
            ),
            LearningLevel(
                levelNumber = 3,
                title = "Beginner+",
                subtitle = "Daily Routine & Routines",
                description = "Describe your daily routine, go shopping, tell the time, ask for and give directions, and build simple conversational sentences.",
                cefrCode = "A1+",
                minXp = 350,
                nextLevelXp = 600,
                vocabRange = "250–500 words",
                difficultyIndicator = "Emerging",
                badgeEmoji = "🧭",
                curriculumTopics = listOf(
                    "Daily Routine",
                    "Shopping",
                    "Time",
                    "Directions",
                    "Simple Sentences",
                    "Common Conversations"
                )
            ),
            LearningLevel(
                levelNumber = 4,
                title = "Elementary",
                subtitle = "Stories, Travel & Plans",
                description = "Narrate past events, describe future plans, navigate international travel, order at restaurants, and describe people.",
                cefrCode = "A2",
                minXp = 600,
                nextLevelXp = 950,
                vocabRange = "500–800 words",
                difficultyIndicator = "Elementary",
                badgeEmoji = "⛺",
                curriculumTopics = listOf(
                    "Past Events",
                    "Future Plans",
                    "Travel",
                    "Restaurants",
                    "Describing People",
                    "Everyday Conversations"
                )
            ),
            LearningLevel(
                levelNumber = 5,
                title = "Elementary+",
                subtitle = "Opinions & Experiences",
                description = "Sustain longer dialogues, articulate opinions, share life experiences, make comparisons, and practice listening to natural cadences.",
                cefrCode = "A2+",
                minXp = 950,
                nextLevelXp = 1400,
                vocabRange = "800–1,200 words",
                difficultyIndicator = "Upper Elementary",
                badgeEmoji = "🚀",
                curriculumTopics = listOf(
                    "Longer Conversations",
                    "Opinions",
                    "Experiences",
                    "Comparisons",
                    "More Complex Grammar",
                    "Listening Practice"
                )
            ),
            LearningLevel(
                levelNumber = 6,
                title = "Intermediate",
                subtitle = "Workplace & Society",
                description = "Engage in vivid storytelling, participate in workplace conversations, navigate social contexts, interpret news, and use common idioms.",
                cefrCode = "B1",
                minXp = 1400,
                nextLevelXp = 1950,
                vocabRange = "1,200–1,800 words",
                difficultyIndicator = "Independent",
                badgeEmoji = "💡",
                curriculumTopics = listOf(
                    "Storytelling",
                    "Workplace Conversations",
                    "Social Situations",
                    "News & Media",
                    "Idiomatic Expressions",
                    "Intermediate Grammar"
                )
            ),
            LearningLevel(
                levelNumber = 7,
                title = "Intermediate+",
                subtitle = "Debate & Abstract Topics",
                description = "Engage in complex debates, discuss abstract topics, express ideas with natural phrasing, and sharpen comprehensive listening.",
                cefrCode = "B1+",
                minXp = 1950,
                nextLevelXp = 2600,
                vocabRange = "1,800–2,500 words",
                difficultyIndicator = "Upper Intermediate",
                badgeEmoji = "🎯",
                curriculumTopics = listOf(
                    "Complex Conversations",
                    "Debate & Opinions",
                    "Abstract Topics",
                    "Natural Expressions",
                    "Listening Comprehension",
                    "Writing Practice"
                )
            ),
            LearningLevel(
                levelNumber = 8,
                title = "Upper Intermediate",
                subtitle = "Cultural Nuance & Depth",
                description = "Handle advanced conversations with deep cultural context, understand nuanced vocabulary, distinguish formal vs informal speech, and master complex syntax.",
                cefrCode = "B2",
                minXp = 2600,
                nextLevelXp = 3400,
                vocabRange = "2,500–3,500 words",
                difficultyIndicator = "Competent",
                badgeEmoji = "🏆",
                curriculumTopics = listOf(
                    "Advanced Conversations",
                    "Cultural Context",
                    "Nuanced Vocabulary",
                    "Advanced Listening",
                    "Formal vs Informal Speech",
                    "Complex Grammar"
                )
            ),
            LearningLevel(
                levelNumber = 9,
                title = "Advanced",
                subtitle = "Professional & Literature",
                description = "Excel in professional communication, advanced reading, idioms, nuanced writing, and effortlessly participate in native-level discussions.",
                cefrCode = "C1",
                minXp = 3400,
                nextLevelXp = 4500,
                vocabRange = "3,500–5,000 words",
                difficultyIndicator = "Proficient",
                badgeEmoji = "🌟",
                curriculumTopics = listOf(
                    "Professional Communication",
                    "Advanced Reading",
                    "Idioms & Expressions",
                    "Advanced Writing",
                    "Difficult Listening",
                    "Native-Level Conversations"
                )
            ),
            LearningLevel(
                levelNumber = 10,
                title = "Fluent",
                subtitle = "Mastery & Real-World Fluency",
                description = "Achieve full conversational ease, humor and cultural references, high-level debate, professional fluency, and complete cultural immersion.",
                cefrCode = "C2",
                minXp = 4500,
                nextLevelXp = 6000,
                vocabRange = "5,000+ words",
                difficultyIndicator = "Mastery",
                badgeEmoji = "👑",
                curriculumTopics = listOf(
                    "Natural Conversation",
                    "Humor & Cultural References",
                    "Advanced Debates",
                    "Professional Fluency",
                    "Native-Speed Listening",
                    "Real-World Simulation"
                )
            )
        )

        fun getLevelByNumber(levelNumber: Int): LearningLevel {
            return ALL_LEVELS.find { it.levelNumber == levelNumber.coerceIn(1, 10) }
                ?: ALL_LEVELS.first()
        }

        fun getLevelForXp(xp: Int): LearningLevel {
            for (level in ALL_LEVELS.reversed()) {
                if (xp >= level.minXp) {
                    return level
                }
            }
            return ALL_LEVELS.first()
        }
    }
}

/**
 * Data needed to render the Level Completion celebration screen.
 */
data class LevelCelebrationData(
    val completedLevel: LearningLevel,
    val nextLevel: LearningLevel?,
    val xpEarned: Int,
    val lessonsCompletedCount: Int,
    val quizAccuracy: Int,
    val vocabularyLearnedCount: Int
)
