package com.lingualearn.app.data

import android.content.Context
import android.util.Log
import com.lingualearn.app.model.LanguageProgress
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class UserFirestoreData(
    val uid: String = "",
    val email: String = "",
    val fullName: String = "",
    val preferredLanguage: String = "es",
    val dailyGoalMinutes: Int = 15,
    val isEmailVerified: Boolean = false,
    val isGuest: Boolean = false,
    val totalXp: Int = 0,
    val dailyStreak: Int = 0,
    val lastPracticeTime: Long = 0L,
    val quizAccuracy: Int = 0,
    val quizzesTaken: Int = 0,
    val weeklyXp: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0),
    val completedLessons: List<String> = emptyList(),
    val bookmarkedLessons: List<String> = emptyList(),
    val favoriteLessons: List<String> = emptyList(),
    val unlockedAchievements: List<String> = emptyList(),
    val recentQuizScores: List<Map<String, Any>> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val lastActiveAt: Long = System.currentTimeMillis()
)

class FirestoreRepository(private val context: Context) {

    companion object {
        private const val TAG = "FirestoreRepository"
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_LANGUAGES = "languages"
        private const val SUBCOLLECTION_QUIZ_SCORES = "quiz_scores"
    }

    private val firestore: FirebaseFirestore? by lazy {
        try {
            com.lingualearn.app.MainActivity.initFirebaseSafe(context)
            FirebaseFirestore.getInstance()
        } catch (e: Throwable) {
            Log.d(TAG, "Cloud Firestore unavailable: ${e.message}")
            null
        }
    }

    val isRealFirebaseConfigured: Boolean
        get() = try {
            val app = com.google.firebase.FirebaseApp.getInstance()
            val key = app.options.apiKey
            key.isNotBlank() &&
                !key.startsWith("AIzaSyMockKey") &&
                !key.contains("Mock") &&
                !key.contains("Dummy")
        } catch (_: Throwable) {
            false
        }

    /**
     * Create or update the user's primary profile in Cloud Firestore.
     */
    suspend fun saveUserProfile(
        uid: String,
        email: String,
        fullName: String,
        preferredLanguage: String,
        dailyGoalMinutes: Int,
        isEmailVerified: Boolean,
        isGuest: Boolean
    ): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) {
            Log.d(TAG, "Notice: Local session active for UID: $uid")
            return@withContext Result.success(Unit)
        }
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val userRef = db.collection(COLLECTION_USERS).document(uid)
            val snapshot = userRef.get().await()

            val profileMap = hashMapOf<String, Any>(
                "uid" to uid,
                "email" to email,
                "fullName" to fullName,
                "preferredLanguage" to preferredLanguage,
                "dailyGoalMinutes" to dailyGoalMinutes,
                "isEmailVerified" to isEmailVerified,
                "isGuest" to isGuest,
                "lastActiveAt" to System.currentTimeMillis()
            )

            if (!snapshot.exists()) {
                profileMap["createdAt"] = System.currentTimeMillis()
                profileMap["totalXp"] = 0
                profileMap["dailyStreak"] = 0
                profileMap["lastPracticeTime"] = 0L
                profileMap["quizAccuracy"] = 0
                profileMap["quizzesTaken"] = 0
                profileMap["weeklyXp"] = listOf(0, 0, 0, 0, 0, 0, 0)
                profileMap["completedLessons"] = emptyList<String>()
                profileMap["bookmarkedLessons"] = emptyList<String>()
                profileMap["favoriteLessons"] = emptyList<String>()
                profileMap["unlockedAchievements"] = emptyList<String>()
                profileMap["recentQuizScores"] = emptyList<Map<String, Any>>()
            }

            userRef.set(profileMap, SetOptions.merge()).await()
            Log.d(TAG, "User profile saved in Firestore for UID: $uid")
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not save user profile to Firestore: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Fetch the user's entire synchronized learning profile from Firestore.
     */
    suspend fun getUserData(uid: String): Result<UserFirestoreData?> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) {
            return@withContext Result.success(null)
        }
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val doc = db.collection(COLLECTION_USERS).document(uid).get().await()
            if (!doc.exists()) {
                return@withContext Result.success(null)
            }

            val data = parseUserFirestoreData(doc.data ?: emptyMap(), uid)
            Result.success(data)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not fetch user data from Firestore: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Attach a real-time listener to Firestore so changes made on other devices
     * immediately reflect on this device.
     */
    fun attachUserSyncListener(
        uid: String,
        onUpdate: (UserFirestoreData) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ): ListenerRegistration? {
        if (!isRealFirebaseConfigured) return null
        val db = firestore ?: return null
        return try {
            db.collection(COLLECTION_USERS).document(uid)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        Log.d(TAG, "Notice: listen event for user $uid: ${exception.message}")
                        onError?.invoke(exception)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        val data = parseUserFirestoreData(snapshot.data ?: emptyMap(), uid)
                        onUpdate(data)
                    }
                }
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: snapshot listener fallback: ${e.message}")
            null
        }
    }

    /**
     * Push full local progress to Firestore (e.g. initial upload or sync).
     */
    suspend fun syncAllProgress(
        uid: String,
        totalXp: Int,
        dailyStreak: Int,
        quizAccuracy: Int,
        quizzesTaken: Int,
        weeklyXp: List<Int>,
        completedLessons: Set<String>,
        bookmarkedLessons: Set<String>,
        favoriteLessons: Set<String>,
        unlockedAchievements: Set<String>,
        preferredLanguage: String,
        dailyGoalMinutes: Int
    ): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val updates = hashMapOf<String, Any>(
                "totalXp" to totalXp,
                "dailyStreak" to dailyStreak,
                "quizAccuracy" to quizAccuracy,
                "quizzesTaken" to quizzesTaken,
                "weeklyXp" to weeklyXp,
                "completedLessons" to completedLessons.toList(),
                "bookmarkedLessons" to bookmarkedLessons.toList(),
                "favoriteLessons" to favoriteLessons.toList(),
                "unlockedAchievements" to unlockedAchievements.toList(),
                "preferredLanguage" to preferredLanguage,
                "dailyGoalMinutes" to dailyGoalMinutes,
                "lastActiveAt" to System.currentTimeMillis()
            )
            db.collection(COLLECTION_USERS).document(uid)
                .set(updates, SetOptions.merge())
                .await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not sync progress to Firestore: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Update completed lessons and XP reward in Firestore.
     */
    suspend fun updateCompletedLessons(
        uid: String,
        completedLessons: Set<String>,
        newTotalXp: Int,
        weeklyXp: List<Int>,
        achievements: Set<String>
    ): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val updates = hashMapOf<String, Any>(
                "completedLessons" to completedLessons.toList(),
                "totalXp" to newTotalXp,
                "weeklyXp" to weeklyXp,
                "unlockedAchievements" to achievements.toList(),
                "lastActiveAt" to System.currentTimeMillis()
            )
            db.collection(COLLECTION_USERS).document(uid)
                .set(updates, SetOptions.merge())
                .await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not update completed lessons: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Update bookmarks list in Firestore.
     */
    suspend fun updateBookmarks(uid: String, bookmarks: Set<String>): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            db.collection(COLLECTION_USERS).document(uid)
                .update("bookmarkedLessons", bookmarks.toList())
                .await()
            Result.success(Unit)
        } catch (e: Throwable) {
            // If document doesn't have field yet, fallback to merge
            try {
                db.collection(COLLECTION_USERS).document(uid)
                    .set(mapOf("bookmarkedLessons" to bookmarks.toList()), SetOptions.merge())
                    .await()
                Result.success(Unit)
            } catch (fallbackEx: Throwable) {
                Log.d(TAG, "Notice: could not update bookmarks: ${fallbackEx.message}")
                Result.failure(fallbackEx)
            }
        }
    }

    /**
     * Update favorites list in Firestore.
     */
    suspend fun updateFavorites(uid: String, favorites: Set<String>): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            db.collection(COLLECTION_USERS).document(uid)
                .update("favoriteLessons", favorites.toList())
                .await()
            Result.success(Unit)
        } catch (e: Throwable) {
            try {
                db.collection(COLLECTION_USERS).document(uid)
                    .set(mapOf("favoriteLessons" to favorites.toList()), SetOptions.merge())
                    .await()
                Result.success(Unit)
            } catch (fallbackEx: Throwable) {
                Log.d(TAG, "Notice: could not update favorites: ${fallbackEx.message}")
                Result.failure(fallbackEx)
            }
        }
    }

    /**
     * Record a completed quiz score, accuracy, streak, and XP reward.
     * Also saves to the user's `quiz_scores` subcollection for complete history.
     */
    suspend fun recordQuizScore(
        uid: String,
        score: Int,
        totalQuestions: Int,
        newTotalXp: Int,
        newDailyStreak: Int,
        overallAccuracy: Int,
        quizzesTaken: Int,
        weeklyXp: List<Int>,
        languageId: String,
        isDailyPractice: Boolean,
        achievements: Set<String>
    ): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val timestamp = System.currentTimeMillis()
            val scoreEntry = hashMapOf<String, Any>(
                "score" to score,
                "totalQuestions" to totalQuestions,
                "accuracyPercent" to if (totalQuestions > 0) (score * 100) / totalQuestions else 0,
                "languageId" to languageId,
                "isDailyPractice" to isDailyPractice,
                "timestamp" to timestamp
            )

            val userRef = db.collection(COLLECTION_USERS).document(uid)

            // 1. Add to subcollection for full quiz records
            try {
                userRef.collection(SUBCOLLECTION_QUIZ_SCORES)
                    .add(scoreEntry)
                    .await()
            } catch (subEx: Throwable) {
                Log.d(TAG, "Subcollection note: ${subEx.message}")
            }

            // 2. Update parent user document with aggregated stats & recent list
            val updates = hashMapOf<String, Any>(
                "totalXp" to newTotalXp,
                "dailyStreak" to newDailyStreak,
                "quizAccuracy" to overallAccuracy,
                "quizzesTaken" to quizzesTaken,
                "weeklyXp" to weeklyXp,
                "lastPracticeTime" to timestamp,
                "unlockedAchievements" to achievements.toList(),
                "recentQuizScores" to FieldValue.arrayUnion(scoreEntry),
                "lastActiveAt" to timestamp
            )

            userRef.set(updates, SetOptions.merge()).await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not record quiz score in Firestore: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Update preferred target language.
     */
    suspend fun updateSelectedLanguage(uid: String, languageId: String): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            db.collection(COLLECTION_USERS).document(uid)
                .set(mapOf("preferredLanguage" to languageId, "lastActiveAt" to System.currentTimeMillis()), SetOptions.merge())
                .await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not update selected language: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Update daily learning goal.
     */
    suspend fun updateDailyGoal(uid: String, goalMinutes: Int): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            db.collection(COLLECTION_USERS).document(uid)
                .set(mapOf("dailyGoalMinutes" to goalMinutes, "lastActiveAt" to System.currentTimeMillis()), SetOptions.merge())
                .await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not update daily goal: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Reset user learning progress in Firestore.
     */
    suspend fun resetUserProgress(uid: String): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val resetMap = hashMapOf<String, Any>(
                "totalXp" to 0,
                "dailyStreak" to 0,
                "lastPracticeTime" to 0L,
                "quizAccuracy" to 0,
                "quizzesTaken" to 0,
                "weeklyXp" to listOf(0, 0, 0, 0, 0, 0, 0),
                "completedLessons" to emptyList<String>(),
                "bookmarkedLessons" to emptyList<String>(),
                "favoriteLessons" to emptyList<String>(),
                "unlockedAchievements" to emptyList<String>(),
                "recentQuizScores" to emptyList<Map<String, Any>>(),
                "lastActiveAt" to System.currentTimeMillis()
            )
            db.collection(COLLECTION_USERS).document(uid)
                .set(resetMap, SetOptions.merge())
                .await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not reset user progress in Firestore: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Store progress separately for each language in Firestore under:
     * users/{uid}/languages/{languageCode}
     */
     suspend fun saveLanguageProgress(
        uid: String,
        progress: LanguageProgress
    ): Result<Unit> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(Unit)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val langRef = db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_LANGUAGES)
                .document(progress.languageCode)

            val langMap = hashMapOf<String, Any>(
                "languageCode" to progress.languageCode,
                "xp" to progress.xp,
                "currentLevel" to progress.currentLevel,
                "highestUnlockedLevel" to progress.highestUnlockedLevel,
                "assessedLevel" to progress.assessedLevel,
                "placementTestTaken" to progress.placementTestTaken,
                "completedLessons" to progress.completedLessons.toList(),
                "bookmarkedLessons" to progress.bookmarkedLessons.toList(),
                "favoriteLessons" to progress.favoriteLessons.toList(),
                "quizAccuracy" to progress.quizAccuracy,
                "quizzesTaken" to progress.quizzesTaken,
                "streak" to progress.streak,
                "currentLesson" to progress.currentLesson,
                "vocabularyProgress" to progress.vocabularyProgress,
                "lastStudied" to progress.lastStudied
            )

            langRef.set(langMap, SetOptions.merge()).await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not save language progress: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getLanguageProgress(
        uid: String,
        languageCode: String
    ): Result<LanguageProgress?> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(null)
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val doc = db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_LANGUAGES)
                .document(languageCode)
                .get()
                .await()

            if (!doc.exists()) {
                return@withContext Result.success(null)
            }

            val data = doc.data ?: return@withContext Result.success(null)
            val progress = LanguageProgress(
                languageCode = languageCode,
                xp = (data["xp"] as? Number)?.toInt() ?: 0,
                currentLevel = (data["currentLevel"] as? Number)?.toInt() ?: 1,
                highestUnlockedLevel = (data["highestUnlockedLevel"] as? Number)?.toInt() ?: 1,
                assessedLevel = (data["assessedLevel"] as? Number)?.toInt() ?: 1,
                placementTestTaken = (data["placementTestTaken"] as? Boolean) ?: false,
                completedLessons = (data["completedLessons"] as? List<*>)?.mapNotNull { it as? String }?.toSet() ?: emptySet(),
                bookmarkedLessons = (data["bookmarkedLessons"] as? List<*>)?.mapNotNull { it as? String }?.toSet() ?: emptySet(),
                favoriteLessons = (data["favoriteLessons"] as? List<*>)?.mapNotNull { it as? String }?.toSet() ?: emptySet(),
                quizAccuracy = (data["quizAccuracy"] as? Number)?.toInt() ?: 0,
                quizzesTaken = (data["quizzesTaken"] as? Number)?.toInt() ?: 0,
                streak = (data["streak"] as? Number)?.toInt() ?: 0,
                currentLesson = (data["currentLesson"] as? Number)?.toInt() ?: 1,
                vocabularyProgress = (data["vocabularyProgress"] as? Number)?.toInt() ?: 0,
                lastStudied = (data["lastStudied"] as? Number)?.toLong() ?: 0L
            )
            Result.success(progress)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not get language progress: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun getAllLanguageProgress(
        uid: String
    ): Result<Map<String, LanguageProgress>> = withContext(Dispatchers.IO) {
        if (!isRealFirebaseConfigured) return@withContext Result.success(emptyMap())
        val db = firestore ?: return@withContext Result.failure(Exception("Firestore not initialized"))
        try {
            val snapshot = db.collection(COLLECTION_USERS)
                .document(uid)
                .collection(COLLECTION_LANGUAGES)
                .get()
                .await()

            val map = mutableMapOf<String, LanguageProgress>()
            for (doc in snapshot.documents) {
                val data = doc.data ?: continue
                val code = doc.id
                map[code] = LanguageProgress(
                    languageCode = code,
                    xp = (data["xp"] as? Number)?.toInt() ?: 0,
                    currentLevel = (data["currentLevel"] as? Number)?.toInt() ?: 1,
                    highestUnlockedLevel = (data["highestUnlockedLevel"] as? Number)?.toInt() ?: 1,
                    assessedLevel = (data["assessedLevel"] as? Number)?.toInt() ?: 1,
                    placementTestTaken = (data["placementTestTaken"] as? Boolean) ?: false,
                    completedLessons = (data["completedLessons"] as? List<*>)?.mapNotNull { it as? String }?.toSet() ?: emptySet(),
                    bookmarkedLessons = (data["bookmarkedLessons"] as? List<*>)?.mapNotNull { it as? String }?.toSet() ?: emptySet(),
                    favoriteLessons = (data["favoriteLessons"] as? List<*>)?.mapNotNull { it as? String }?.toSet() ?: emptySet(),
                    quizAccuracy = (data["quizAccuracy"] as? Number)?.toInt() ?: 0,
                    quizzesTaken = (data["quizzesTaken"] as? Number)?.toInt() ?: 0,
                    streak = (data["streak"] as? Number)?.toInt() ?: 0,
                    currentLesson = (data["currentLesson"] as? Number)?.toInt() ?: 1,
                    vocabularyProgress = (data["vocabularyProgress"] as? Number)?.toInt() ?: 0,
                    lastStudied = (data["lastStudied"] as? Number)?.toLong() ?: 0L
                )
            }
            Result.success(map)
        } catch (e: Throwable) {
            Log.d(TAG, "Notice: could not fetch all languages: ${e.message}")
            Result.failure(e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseUserFirestoreData(map: Map<String, Any>, defaultUid: String): UserFirestoreData {
        return UserFirestoreData(
            uid = map["uid"] as? String ?: defaultUid,
            email = map["email"] as? String ?: "",
            fullName = map["fullName"] as? String ?: "",
            preferredLanguage = map["preferredLanguage"] as? String ?: "es",
            dailyGoalMinutes = (map["dailyGoalMinutes"] as? Number)?.toInt() ?: 15,
            isEmailVerified = map["isEmailVerified"] as? Boolean ?: false,
            isGuest = map["isGuest"] as? Boolean ?: false,
            totalXp = (map["totalXp"] as? Number)?.toInt() ?: 0,
            dailyStreak = (map["dailyStreak"] as? Number)?.toInt() ?: 0,
            lastPracticeTime = (map["lastPracticeTime"] as? Number)?.toLong() ?: 0L,
            quizAccuracy = (map["quizAccuracy"] as? Number)?.toInt() ?: 0,
            quizzesTaken = (map["quizzesTaken"] as? Number)?.toInt() ?: 0,
            weeklyXp = (map["weeklyXp"] as? List<*>)?.mapNotNull { (it as? Number)?.toInt() }?.ifEmpty { listOf(0, 0, 0, 0, 0, 0, 0) } ?: listOf(0, 0, 0, 0, 0, 0, 0),
            completedLessons = (map["completedLessons"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
            bookmarkedLessons = (map["bookmarkedLessons"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
            favoriteLessons = (map["favoriteLessons"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
            unlockedAchievements = (map["unlockedAchievements"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
            recentQuizScores = (map["recentQuizScores"] as? List<*>)?.mapNotNull { it as? Map<String, Any> } ?: emptyList(),
            createdAt = (map["createdAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
            lastActiveAt = (map["lastActiveAt"] as? Number)?.toLong() ?: System.currentTimeMillis()
        )
    }
}
