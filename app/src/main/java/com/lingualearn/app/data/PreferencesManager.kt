package com.lingualearn.app.data

import android.content.Context
import android.content.SharedPreferences
import com.lingualearn.app.model.LanguageProgress
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("lingualearn_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_ONBOARDING_DONE = "onboarding_done"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_PHOTO_URL = "user_photo_url"
        private const val KEY_IS_EMAIL_VERIFIED = "is_email_verified"
        private const val KEY_IS_GUEST = "is_guest"
        private const val KEY_REMEMBER_ME = "remember_me"
        private const val KEY_DAILY_GOAL = "daily_goal_minutes"
        private const val KEY_LEARNING_LEVEL = "learning_level" // Beginner, Intermediate, Advanced

        private const val KEY_SELECTED_LANGUAGE = "selected_language"
        private const val KEY_COMPLETED_LESSONS = "completed_lessons"
        private const val KEY_BOOKMARKED_LESSONS = "bookmarked_lessons"
        private const val KEY_FAVORITE_LESSONS = "favorite_lessons"
        private const val KEY_DAILY_STREAK = "daily_streak"
        private const val KEY_LONGEST_STREAK = "longest_streak"
        private const val KEY_LAST_ACTIVE_DATE = "last_active_date"
        private const val KEY_MONTHLY_ACTIVE_DATES = "monthly_active_dates"
        private const val KEY_LAST_PRACTICE_TIME = "last_practice_time"
        private const val KEY_TOTAL_XP = "total_xp"
        private const val KEY_QUIZ_ACCURACY = "quiz_accuracy"
        private const val KEY_QUIZZES_TAKEN = "quizzes_taken"
        private const val KEY_ACHIEVEMENTS = "achievements"
        private const val KEY_DARK_MODE = "dark_mode" // -1 system, 0 light, 1 dark
        private const val KEY_NOTIFICATIONS = "notifications"
        private const val KEY_WEEKLY_XP = "weekly_xp" // comma-separated
    }

    // Authentication Session
    var isOnboardingDone: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_DONE, false)
        set(value) = prefs.edit().putBoolean(KEY_ONBOARDING_DONE, value).apply()

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var userId: String
        get() = prefs.getString(KEY_USER_ID, "") ?: ""
        set(value) = prefs.edit().putString(KEY_USER_ID, value).apply()

    var userEmail: String
        get() = prefs.getString(KEY_USER_EMAIL, "") ?: ""
        set(value) = prefs.edit().putString(KEY_USER_EMAIL, value).apply()

    var userName: String
        get() = prefs.getString(KEY_USER_NAME, "") ?: ""
        set(value) = prefs.edit().putString(KEY_USER_NAME, value).apply()

    var userPhotoUrl: String
        get() = prefs.getString(KEY_USER_PHOTO_URL, "") ?: ""
        set(value) = prefs.edit().putString(KEY_USER_PHOTO_URL, value).apply()

    var isEmailVerified: Boolean
        get() = prefs.getBoolean(KEY_IS_EMAIL_VERIFIED, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_EMAIL_VERIFIED, value).apply()

    var isGuest: Boolean
        get() = prefs.getBoolean(KEY_IS_GUEST, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_GUEST, value).apply()

    var rememberMe: Boolean
        get() = prefs.getBoolean(KEY_REMEMBER_ME, true)
        set(value) = prefs.edit().putBoolean(KEY_REMEMBER_ME, value).apply()

    var dailyGoalMinutes: Int
        get() = prefs.getInt(KEY_DAILY_GOAL, 15)
        set(value) = prefs.edit().putInt(KEY_DAILY_GOAL, value).apply()

    var learningLevel: String
        get() = prefs.getString(KEY_LEARNING_LEVEL, "Beginner") ?: "Beginner"
        set(value) = prefs.edit().putString(KEY_LEARNING_LEVEL, value).apply()

    // Learning Preferences & Language
    var selectedLanguageId: String
        get() = prefs.getString(KEY_SELECTED_LANGUAGE, "es") ?: "es"
        set(value) = prefs.edit().putString(KEY_SELECTED_LANGUAGE, value).apply()

    // Per-Language Dynamic Accessors
    private fun langKey(baseKey: String, lang: String = selectedLanguageId): String = "${baseKey}_$lang"

    var completedLessons: Set<String>
        get() = prefs.getStringSet(langKey(KEY_COMPLETED_LESSONS), null)
            ?: prefs.getStringSet(KEY_COMPLETED_LESSONS, emptySet()) ?: emptySet()
        set(value) {
            prefs.edit().putStringSet(langKey(KEY_COMPLETED_LESSONS), value).apply()
            prefs.edit().putStringSet(KEY_COMPLETED_LESSONS, value).apply()
        }

    var bookmarkedLessons: Set<String>
        get() = prefs.getStringSet(langKey(KEY_BOOKMARKED_LESSONS), null)
            ?: prefs.getStringSet(KEY_BOOKMARKED_LESSONS, emptySet()) ?: emptySet()
        set(value) {
            prefs.edit().putStringSet(langKey(KEY_BOOKMARKED_LESSONS), value).apply()
            prefs.edit().putStringSet(KEY_BOOKMARKED_LESSONS, value).apply()
        }

    var favoriteLessons: Set<String>
        get() = prefs.getStringSet(langKey(KEY_FAVORITE_LESSONS), null)
            ?: prefs.getStringSet(KEY_FAVORITE_LESSONS, emptySet()) ?: emptySet()
        set(value) {
            prefs.edit().putStringSet(langKey(KEY_FAVORITE_LESSONS), value).apply()
            prefs.edit().putStringSet(KEY_FAVORITE_LESSONS, value).apply()
        }

    var dailyStreak: Int
        get() = prefs.getInt(KEY_DAILY_STREAK, 0)
        set(value) {
            prefs.edit().putInt(KEY_DAILY_STREAK, value).apply()
            if (value > longestStreak) {
                longestStreak = value
            }
        }

    var longestStreak: Int
        get() = prefs.getInt(KEY_LONGEST_STREAK, dailyStreak.coerceAtLeast(1))
        set(value) = prefs.edit().putInt(KEY_LONGEST_STREAK, value).apply()

    var lastActiveDate: String
        get() = prefs.getString(KEY_LAST_ACTIVE_DATE, "") ?: ""
        set(value) = prefs.edit().putString(KEY_LAST_ACTIVE_DATE, value).apply()

    var monthlyActiveDates: Set<String>
        get() = prefs.getStringSet(KEY_MONTHLY_ACTIVE_DATES, emptySet()) ?: emptySet()
        set(value) = prefs.edit().putStringSet(KEY_MONTHLY_ACTIVE_DATES, value).apply()

    var lastPracticeTime: Long
        get() = prefs.getLong(KEY_LAST_PRACTICE_TIME, 0L)
        set(value) = prefs.edit().putLong(KEY_LAST_PRACTICE_TIME, value).apply()

    var totalXp: Int
        get() = prefs.getInt(langKey(KEY_TOTAL_XP), -1).let {
            if (it >= 0) it else prefs.getInt(KEY_TOTAL_XP, 0)
        }
        set(value) {
            prefs.edit().putInt(langKey(KEY_TOTAL_XP), value).apply()
            prefs.edit().putInt(KEY_TOTAL_XP, value).apply()
        }

    var quizAccuracy: Int
        get() = prefs.getInt(langKey(KEY_QUIZ_ACCURACY), -1).let {
            if (it >= 0) it else prefs.getInt(KEY_QUIZ_ACCURACY, 0)
        }
        set(value) {
            prefs.edit().putInt(langKey(KEY_QUIZ_ACCURACY), value).apply()
            prefs.edit().putInt(KEY_QUIZ_ACCURACY, value).apply()
        }

    var quizzesTaken: Int
        get() = prefs.getInt(langKey(KEY_QUIZZES_TAKEN), -1).let {
            if (it >= 0) it else prefs.getInt(KEY_QUIZZES_TAKEN, 0)
        }
        set(value) {
            prefs.edit().putInt(langKey(KEY_QUIZZES_TAKEN), value).apply()
            prefs.edit().putInt(KEY_QUIZZES_TAKEN, value).apply()
        }

    var unlockedAchievements: Set<String>
        get() = prefs.getStringSet(KEY_ACHIEVEMENTS, emptySet()) ?: emptySet()
        set(value) = prefs.edit().putStringSet(KEY_ACHIEVEMENTS, value).apply()

    var darkModePreference: Int
        get() = prefs.getInt(KEY_DARK_MODE, -1)
        set(value) = prefs.edit().putInt(KEY_DARK_MODE, value).apply()

    var notificationsEnabled: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATIONS, true)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATIONS, value).apply()

    fun recordDailyActivity() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val updatedDates = monthlyActiveDates.toMutableSet()
        updatedDates.add(today)
        monthlyActiveDates = updatedDates
        lastActiveDate = today
        lastPracticeTime = System.currentTimeMillis()
    }

    /**
     * Retrieve isolated language progress.
     */
    fun getLanguageProgress(langCode: String): LanguageProgress {
        val completed = prefs.getStringSet(langKey(KEY_COMPLETED_LESSONS, langCode), emptySet()) ?: emptySet()
        val bookmarked = prefs.getStringSet(langKey(KEY_BOOKMARKED_LESSONS, langCode), emptySet()) ?: emptySet()
        val favorites = prefs.getStringSet(langKey(KEY_FAVORITE_LESSONS, langCode), emptySet()) ?: emptySet()
        val xp = prefs.getInt(langKey(KEY_TOTAL_XP, langCode), 0)
        val accuracy = prefs.getInt(langKey(KEY_QUIZ_ACCURACY, langCode), 0)
        val quizzes = prefs.getInt(langKey(KEY_QUIZZES_TAKEN, langCode), 0)
        val streakVal = prefs.getInt(langKey(KEY_DAILY_STREAK, langCode), dailyStreak)
        val currentLess = prefs.getInt("current_lesson_$langCode", completed.size + 1)
        val lastTime = prefs.getLong("last_studied_$langCode", 0L)
        val level = prefs.getInt("current_level_$langCode", 1).coerceIn(1, 10)
        val maxUnlocked = prefs.getInt("highest_unlocked_level_$langCode", level).coerceIn(1, 10)
        val assessed = prefs.getInt("assessed_level_$langCode", 1).coerceIn(1, 10)
        val placementTaken = prefs.getBoolean("placement_test_taken_$langCode", false)

        return LanguageProgress(
            languageCode = langCode,
            xp = xp,
            currentLevel = level,
            highestUnlockedLevel = maxUnlocked,
            assessedLevel = assessed,
            placementTestTaken = placementTaken,
            completedLessons = completed,
            bookmarkedLessons = bookmarked,
            favoriteLessons = favorites,
            quizScores = emptyList(),
            quizAccuracy = accuracy,
            quizzesTaken = quizzes,
            streak = streakVal,
            vocabularyProgress = completed.size * 5,
            currentLesson = currentLess,
            lastStudied = lastTime
        )
    }

    /**
     * Save isolated language progress without affecting other languages.
     */
    fun saveLanguageProgress(progress: LanguageProgress) {
        val code = progress.languageCode
        prefs.edit()
            .putStringSet(langKey(KEY_COMPLETED_LESSONS, code), progress.completedLessons)
            .putStringSet(langKey(KEY_BOOKMARKED_LESSONS, code), progress.bookmarkedLessons)
            .putStringSet(langKey(KEY_FAVORITE_LESSONS, code), progress.favoriteLessons)
            .putInt(langKey(KEY_TOTAL_XP, code), progress.xp)
            .putInt(langKey(KEY_QUIZ_ACCURACY, code), progress.quizAccuracy)
            .putInt(langKey(KEY_QUIZZES_TAKEN, code), progress.quizzesTaken)
            .putInt(langKey(KEY_DAILY_STREAK, code), progress.streak)
            .putInt("current_lesson_$code", progress.currentLesson)
            .putLong("last_studied_$code", progress.lastStudied)
            .putInt("current_level_$code", progress.currentLevel)
            .putInt("highest_unlocked_level_$code", progress.highestUnlockedLevel)
            .putInt("assessed_level_$code", progress.assessedLevel)
            .putBoolean("placement_test_taken_$code", progress.placementTestTaken)
            .apply()
    }

    fun getLevel(langCode: String): Int {
        return prefs.getInt("current_level_$langCode", 1).coerceIn(1, 10)
    }

    fun setLevel(langCode: String, level: Int) {
        val currentMax = getHighestUnlockedLevel(langCode)
        val newMax = if (level > currentMax) level else currentMax
        prefs.edit()
            .putInt("current_level_$langCode", level.coerceIn(1, 10))
            .putInt("highest_unlocked_level_$langCode", newMax.coerceIn(1, 10))
            .apply()
    }

    fun getHighestUnlockedLevel(langCode: String): Int {
        return prefs.getInt("highest_unlocked_level_$langCode", 1).coerceIn(1, 10)
    }

    fun setHighestUnlockedLevel(langCode: String, level: Int) {
        prefs.edit().putInt("highest_unlocked_level_$langCode", level.coerceIn(1, 10)).apply()
    }

    fun isPlacementTestTaken(langCode: String): Boolean {
        return prefs.getBoolean("placement_test_taken_$langCode", false)
    }

    fun setPlacementTestTaken(langCode: String, taken: Boolean, level: Int = 1) {
        prefs.edit()
            .putBoolean("placement_test_taken_$langCode", taken)
            .putInt("assessed_level_$langCode", level.coerceIn(1, 10))
            .apply()
    }

    fun getWeeklyXp(): List<Int> {
        val raw = prefs.getString(KEY_WEEKLY_XP, "0,0,0,0,0,0,0") ?: "0,0,0,0,0,0,0"
        return raw.split(",").mapNotNull { it.toIntOrNull() }.ifEmpty { listOf(0, 0, 0, 0, 0, 0, 0) }
    }

    fun setWeeklyXp(list: List<Int>) {
        prefs.edit().putString(KEY_WEEKLY_XP, list.joinToString(",")).apply()
    }

    fun addXpToToday(addedXp: Int) {
        val currentList = getWeeklyXp().toMutableList()
        if (currentList.isNotEmpty()) {
            val last = currentList.last()
            currentList[currentList.size - 1] = last + addedXp
            prefs.edit().putString(KEY_WEEKLY_XP, currentList.joinToString(",")).apply()
        }
    }

    fun clearAuthSession() {
        // Keep learning progress, only remove login session
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .putString(KEY_USER_ID, "")
            .putString(KEY_USER_EMAIL, "")
            .putString(KEY_USER_PHOTO_URL, "")
            .putBoolean(KEY_IS_EMAIL_VERIFIED, false)
            .putBoolean(KEY_IS_GUEST, false)
            .apply()
    }

    fun resetAllProgress() {
        completedLessons = emptySet()
        bookmarkedLessons = emptySet()
        favoriteLessons = emptySet()
        dailyStreak = 0
        totalXp = 0
        quizAccuracy = 0
        quizzesTaken = 0
        unlockedAchievements = emptySet()
        prefs.edit().putString(KEY_WEEKLY_XP, "0,0,0,0,0,0,0").apply()
    }
}

