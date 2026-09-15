package com.lingualearn.app.ui.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lingualearn.app.data.FirestoreRepository
import com.lingualearn.app.data.LanguageRepository
import com.lingualearn.app.data.PreferencesManager
import com.lingualearn.app.data.UserFirestoreData
import com.lingualearn.app.model.Achievement
import com.lingualearn.app.model.Language
import com.lingualearn.app.model.LearningLevel
import com.lingualearn.app.model.Lesson
import com.lingualearn.app.model.LevelCelebrationData
import com.lingualearn.app.model.QuizQuestion
import com.lingualearn.app.util.AudioHelper
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LinguaViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = PreferencesManager(application)
    private val audioHelper = AudioHelper(application)
    private val firestoreRepo = FirestoreRepository(application)
    private var firestoreListener: ListenerRegistration? = null

    private val _isCloudSyncing = MutableStateFlow(false)
    val isCloudSyncing: StateFlow<Boolean> = _isCloudSyncing.asStateFlow()

    // Language & Core State
    private val _currentLanguage = MutableStateFlow(LanguageRepository.getLanguage(prefs.selectedLanguageId))
    val currentLanguage: StateFlow<Language> = _currentLanguage.asStateFlow()

    val allLanguages: List<Language> = LanguageRepository.supportedLanguages

    // 10-Level Progression System State
    private val _currentLevel = MutableStateFlow(prefs.getLevel(prefs.selectedLanguageId))
    val currentLevel: StateFlow<Int> = _currentLevel.asStateFlow()

    private val _highestUnlockedLevel = MutableStateFlow(prefs.getHighestUnlockedLevel(prefs.selectedLanguageId))
    val highestUnlockedLevel: StateFlow<Int> = _highestUnlockedLevel.asStateFlow()

    private val _currentLevelInfo = MutableStateFlow(LearningLevel.getLevelByNumber(prefs.getLevel(prefs.selectedLanguageId)))
    val currentLevelInfo: StateFlow<LearningLevel> = _currentLevelInfo.asStateFlow()

    private val _levelProgressPercent = MutableStateFlow(
        LearningLevel.getLevelByNumber(prefs.getLevel(prefs.selectedLanguageId))
            .calculateProgressPercent(prefs.getLanguageProgress(prefs.selectedLanguageId).xp)
    )
    val levelProgressPercent: StateFlow<Int> = _levelProgressPercent.asStateFlow()

    private val _xpToNextLevel = MutableStateFlow(
        (LearningLevel.getLevelByNumber(prefs.getLevel(prefs.selectedLanguageId)).nextLevelXp -
                prefs.getLanguageProgress(prefs.selectedLanguageId).xp).coerceAtLeast(0)
    )
    val xpToNextLevel: StateFlow<Int> = _xpToNextLevel.asStateFlow()

    private val _levelCelebration = MutableStateFlow<LevelCelebrationData?>(null)
    val levelCelebration: StateFlow<LevelCelebrationData?> = _levelCelebration.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _lessons = MutableStateFlow(LanguageRepository.getLessonsForLanguage(prefs.selectedLanguageId))
    val lessons: StateFlow<List<Lesson>> = _lessons.asStateFlow()

    private val _activeLesson = MutableStateFlow<Lesson?>(null)
    val activeLesson: StateFlow<Lesson?> = _activeLesson.asStateFlow()

    private val _completedLessons = MutableStateFlow(prefs.completedLessons)
    val completedLessons: StateFlow<Set<String>> = _completedLessons.asStateFlow()

    private val _bookmarkedLessons = MutableStateFlow(prefs.bookmarkedLessons)
    val bookmarkedLessons: StateFlow<Set<String>> = _bookmarkedLessons.asStateFlow()

    private val _favoriteLessons = MutableStateFlow(prefs.favoriteLessons)
    val favoriteLessons: StateFlow<Set<String>> = _favoriteLessons.asStateFlow()

    // Streak & XP
    private val _dailyStreak = MutableStateFlow(prefs.dailyStreak)
    val dailyStreak: StateFlow<Int> = _dailyStreak.asStateFlow()

    private val _totalXp = MutableStateFlow(prefs.totalXp)
    val totalXp: StateFlow<Int> = _totalXp.asStateFlow()

    private val _learnerLevel = MutableStateFlow(calculateLearnerLevel(prefs.totalXp))
    val learnerLevel: StateFlow<Int> = _learnerLevel.asStateFlow()

    private val _learnerLevelTitle = MutableStateFlow(calculateLevelTitle(prefs.totalXp))
    val learnerLevelTitle: StateFlow<String> = _learnerLevelTitle.asStateFlow()

    private val _learningLevel = MutableStateFlow(prefs.learningLevel)
    val learningLevel: StateFlow<String> = _learningLevel.asStateFlow()

    private val _monthlyActiveDays = MutableStateFlow(prefs.monthlyActiveDates)
    val monthlyActiveDays: StateFlow<Set<String>> = _monthlyActiveDays.asStateFlow()

    private val _dailyWord = MutableStateFlow(LanguageRepository.getDailyWord(prefs.selectedLanguageId))
    val dailyWord: StateFlow<Lesson> = _dailyWord.asStateFlow()

    // Flashcard State
    private val _flashcardIndex = MutableStateFlow(0)
    val flashcardIndex: StateFlow<Int> = _flashcardIndex.asStateFlow()

    private val _isCardFlipped = MutableStateFlow(false)
    val isCardFlipped: StateFlow<Boolean> = _isCardFlipped.asStateFlow()

    private val _flashcardDeck = MutableStateFlow(LanguageRepository.getLessonsForLanguage(prefs.selectedLanguageId))
    val flashcardDeck: StateFlow<List<Lesson>> = _flashcardDeck.asStateFlow()

    // Quiz State
    private val _quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedAnswerIndex: StateFlow<Int?> = _selectedAnswerIndex.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> = _isQuizFinished.asStateFlow()

    private val _isDailyPractice = MutableStateFlow(false)
    val isDailyPractice: StateFlow<Boolean> = _isDailyPractice.asStateFlow()

    private val _showConfetti = MutableStateFlow(false)
    val showConfetti: StateFlow<Boolean> = _showConfetti.asStateFlow()

    // Progress & Analytics
    private val _weeklyXp = MutableStateFlow(prefs.getWeeklyXp())
    val weeklyXp: StateFlow<List<Int>> = _weeklyXp.asStateFlow()

    private val _quizAccuracy = MutableStateFlow(prefs.quizAccuracy)
    val quizAccuracy: StateFlow<Int> = _quizAccuracy.asStateFlow()

    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    // Settings
    private val _darkModePreference = MutableStateFlow(prefs.darkModePreference)
    val darkModePreference: StateFlow<Int> = _darkModePreference.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(prefs.notificationsEnabled)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    init {
        refreshLanguageData(prefs.selectedLanguageId)
        _achievements.value = getAchievements()
        if (prefs.isLoggedIn && prefs.userId.isNotEmpty()) {
            attachCloudSync(prefs.userId)
        }
    }

    fun attachCloudSync(userId: String = prefs.userId) {
        if (userId.isEmpty()) return
        firestoreListener?.remove()

        viewModelScope.launch {
            _isCloudSyncing.value = true
            try {
                val cloudResult = firestoreRepo.getUserData(userId)
                if (cloudResult.isSuccess) {
                    val data = cloudResult.getOrNull()
                    if (data != null) {
                        applyCloudData(data)
                    } else {
                        // Push initial local cache to Firestore if document does not exist yet
                        val achSet = _achievements.value.filter { it.isUnlocked }.map { it.id }.toSet()
                        firestoreRepo.syncAllProgress(
                            uid = userId,
                            totalXp = _totalXp.value,
                            dailyStreak = _dailyStreak.value,
                            quizAccuracy = _quizAccuracy.value,
                            quizzesTaken = prefs.quizzesTaken,
                            weeklyXp = _weeklyXp.value,
                            completedLessons = _completedLessons.value,
                            bookmarkedLessons = _bookmarkedLessons.value,
                            favoriteLessons = _favoriteLessons.value,
                            unlockedAchievements = achSet,
                            preferredLanguage = _currentLanguage.value.id,
                            dailyGoalMinutes = prefs.dailyGoalMinutes
                        )
                    }
                }
            } catch (_: Throwable) {
            } finally {
                _isCloudSyncing.value = false
            }
        }

        firestoreListener = firestoreRepo.attachUserSyncListener(
            uid = userId,
            onUpdate = { cloudData ->
                applyCloudData(cloudData)
            }
        )
    }

    private fun applyCloudData(data: UserFirestoreData) {
        if (data.totalXp != _totalXp.value) {
            _totalXp.value = data.totalXp
            prefs.totalXp = data.totalXp
        }
        if (data.dailyStreak != _dailyStreak.value) {
            _dailyStreak.value = data.dailyStreak
            prefs.dailyStreak = data.dailyStreak
        }
        val cloudCompleted = data.completedLessons.toSet()
        if (cloudCompleted != _completedLessons.value) {
            _completedLessons.value = cloudCompleted
            prefs.completedLessons = cloudCompleted
        }
        val cloudBookmarks = data.bookmarkedLessons.toSet()
        if (cloudBookmarks != _bookmarkedLessons.value) {
            _bookmarkedLessons.value = cloudBookmarks
            prefs.bookmarkedLessons = cloudBookmarks
        }
        val cloudFavorites = data.favoriteLessons.toSet()
        if (cloudFavorites != _favoriteLessons.value) {
            _favoriteLessons.value = cloudFavorites
            prefs.favoriteLessons = cloudFavorites
        }
        if (data.quizAccuracy != _quizAccuracy.value) {
            _quizAccuracy.value = data.quizAccuracy
            prefs.quizAccuracy = data.quizAccuracy
        }
        if (data.quizzesTaken != prefs.quizzesTaken) {
            prefs.quizzesTaken = data.quizzesTaken
        }
        if (data.weeklyXp.isNotEmpty() && data.weeklyXp != _weeklyXp.value) {
            _weeklyXp.value = data.weeklyXp
            prefs.setWeeklyXp(data.weeklyXp)
        }
        if (data.preferredLanguage.isNotEmpty() && data.preferredLanguage != _currentLanguage.value.id) {
            prefs.selectedLanguageId = data.preferredLanguage
            refreshLanguageData(data.preferredLanguage)
        }
        if (data.dailyGoalMinutes != prefs.dailyGoalMinutes) {
            prefs.dailyGoalMinutes = data.dailyGoalMinutes
        }
        _achievements.value = getAchievements()
    }

    fun onUserLogin(userId: String) {
        attachCloudSync(userId)
    }

    fun onUserLogout() {
        firestoreListener?.remove()
        firestoreListener = null
        resetAllProgress()
    }

    private fun refreshLanguageData(langId: String) {
        val lang = LanguageRepository.getLanguage(langId)
        _currentLanguage.value = lang
        val langProgress = prefs.getLanguageProgress(langId)
        _completedLessons.value = langProgress.completedLessons
        _bookmarkedLessons.value = langProgress.bookmarkedLessons
        _favoriteLessons.value = langProgress.favoriteLessons
        _totalXp.value = langProgress.xp
        _quizAccuracy.value = langProgress.quizAccuracy

        val levelNum = langProgress.currentLevel.coerceIn(1, 10)
        _currentLevel.value = levelNum
        val highest = langProgress.highestUnlockedLevel.coerceAtLeast(levelNum)
        _highestUnlockedLevel.value = highest

        val levelInfo = LearningLevel.getLevelByNumber(levelNum)
        _currentLevelInfo.value = levelInfo
        _levelProgressPercent.value = levelInfo.calculateProgressPercent(langProgress.xp)
        _xpToNextLevel.value = (levelInfo.nextLevelXp - langProgress.xp).coerceAtLeast(0)

        // Load level-specific curriculum modules
        val levelLessons = LanguageRepository.getLessonsForLevel(langId, levelNum)
        val allLangLessons = LanguageRepository.getLessonsForLanguage(langId)
        _lessons.value = allLangLessons
        _flashcardDeck.value = if (levelLessons.isNotEmpty()) levelLessons else allLangLessons
        _dailyWord.value = LanguageRepository.getDailyWord(langId)
        _flashcardIndex.value = 0
        _isCardFlipped.value = false
        audioHelper.setLanguage(langId)
    }

    fun selectLanguage(langId: String) {
        val currentLangId = _currentLanguage.value.id
        // Persist current language progress before switching
        val currentProg = com.lingualearn.app.model.LanguageProgress(
            languageCode = currentLangId,
            xp = _totalXp.value,
            currentLevel = _currentLevel.value,
            highestUnlockedLevel = _highestUnlockedLevel.value,
            placementTestTaken = prefs.isPlacementTestTaken(currentLangId),
            completedLessons = _completedLessons.value,
            bookmarkedLessons = _bookmarkedLessons.value,
            favoriteLessons = _favoriteLessons.value,
            quizAccuracy = _quizAccuracy.value,
            quizzesTaken = prefs.quizzesTaken,
            streak = _dailyStreak.value,
            lastStudied = System.currentTimeMillis()
        )
        prefs.saveLanguageProgress(currentProg)
        val uid = prefs.userId
        if (uid.isNotEmpty()) {
            viewModelScope.launch {
                firestoreRepo.saveLanguageProgress(uid, currentProg)
                firestoreRepo.updateSelectedLanguage(uid, langId)
            }
        }

        prefs.selectedLanguageId = langId
        refreshLanguageData(langId)

        // Sync cloud language progress if available
        if (uid.isNotEmpty()) {
            viewModelScope.launch {
                val cloudProg = firestoreRepo.getLanguageProgress(uid, langId).getOrNull()
                if (cloudProg != null) {
                    prefs.saveLanguageProgress(cloudProg)
                    _completedLessons.value = cloudProg.completedLessons
                    _bookmarkedLessons.value = cloudProg.bookmarkedLessons
                    _favoriteLessons.value = cloudProg.favoriteLessons
                    _currentLevel.value = cloudProg.currentLevel.coerceIn(1, 10)
                    _highestUnlockedLevel.value = cloudProg.highestUnlockedLevel.coerceAtLeast(cloudProg.currentLevel)
                    _currentLevelInfo.value = LearningLevel.getLevelByNumber(cloudProg.currentLevel)
                    _levelProgressPercent.value = _currentLevelInfo.value.calculateProgressPercent(cloudProg.xp)
                    _xpToNextLevel.value = (_currentLevelInfo.value.nextLevelXp - cloudProg.xp).coerceAtLeast(0)
                }
            }
        }
    }

    private fun saveCurrentLanguageState() {
        val currentLangId = _currentLanguage.value.id
        val currentProg = com.lingualearn.app.model.LanguageProgress(
            languageCode = currentLangId,
            xp = _totalXp.value,
            currentLevel = _currentLevel.value,
            highestUnlockedLevel = _highestUnlockedLevel.value,
            placementTestTaken = prefs.isPlacementTestTaken(currentLangId),
            completedLessons = _completedLessons.value,
            bookmarkedLessons = _bookmarkedLessons.value,
            favoriteLessons = _favoriteLessons.value,
            quizAccuracy = _quizAccuracy.value,
            quizzesTaken = prefs.quizzesTaken,
            streak = _dailyStreak.value,
            lastStudied = System.currentTimeMillis()
        )
        prefs.saveLanguageProgress(currentProg)
        val uid = prefs.userId
        if (uid.isNotEmpty()) {
            viewModelScope.launch {
                firestoreRepo.saveLanguageProgress(uid, currentProg)
            }
        }
    }

    fun applyPlacementLevel(levelNumber: Int) {
        val targetLevel = levelNumber.coerceIn(1, 10)
        val targetLevelInfo = LearningLevel.getLevelByNumber(targetLevel)
        val langId = _currentLanguage.value.id

        _currentLevel.value = targetLevel
        _currentLevelInfo.value = targetLevelInfo
        val newHighest = _highestUnlockedLevel.value.coerceAtLeast(targetLevel)
        _highestUnlockedLevel.value = newHighest

        // Ensure user has at least the minimum XP for the recommended level
        if (_totalXp.value < targetLevelInfo.minXp) {
            _totalXp.value = targetLevelInfo.minXp
            prefs.totalXp = targetLevelInfo.minXp
        }

        prefs.setLevel(langId, targetLevel)
        prefs.setPlacementTestTaken(langId, true, targetLevel)

        _levelProgressPercent.value = targetLevelInfo.calculateProgressPercent(_totalXp.value)
        _xpToNextLevel.value = (targetLevelInfo.nextLevelXp - _totalXp.value).coerceAtLeast(0)

        // Refresh lessons and flashcards to target level
        val levelLessons = LanguageRepository.getLessonsForLevel(langId, targetLevel)
        _flashcardDeck.value = if (levelLessons.isNotEmpty()) levelLessons else _lessons.value
        _flashcardIndex.value = 0

        saveCurrentLanguageState()
        triggerCelebration()
    }

    fun dismissLevelCelebration() {
        _levelCelebration.value = null
    }

    fun startLevelQuiz(levelNumber: Int) {
        _isDailyPractice.value = false
        val questions = LanguageRepository.getQuestionsForLanguage(_currentLanguage.value.id, 5, levelNumber)
        _quizQuestions.value = questions
        _currentQuestionIndex.value = 0
        _selectedAnswerIndex.value = null
        _quizScore.value = 0
        _isQuizFinished.value = false
    }

    fun getSuggestedNextLesson(): Lesson {
        val langId = _currentLanguage.value.id
        val levelLessons = LanguageRepository.getLessonsForLevel(langId, _currentLevel.value)
        val uncompleted = levelLessons.firstOrNull { !_completedLessons.value.contains(it.id) }
        return uncompleted ?: levelLessons.firstOrNull() ?: _dailyWord.value
    }

    fun setLearningLevel(level: String) {
        _learningLevel.value = level
        prefs.learningLevel = level
        val langId = _currentLanguage.value.id
        _lessons.value = LanguageRepository.getLessonsForLanguageAndLevel(langId, level)
    }

    private fun recordTodayActive() {
        val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(java.util.Date())
        val currentDays = _monthlyActiveDays.value.toMutableSet()
        if (currentDays.add(today)) {
            _monthlyActiveDays.value = currentDays
            prefs.monthlyActiveDates = currentDays
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun openLesson(lesson: Lesson) {
        _activeLesson.value = lesson
    }

    fun closeLesson() {
        _activeLesson.value = null
    }

    fun nextLesson() {
        val list = filteredLessons()
        val curr = _activeLesson.value ?: return
        val idx = list.indexOfFirst { it.id == curr.id }
        if (idx >= 0 && idx < list.size - 1) {
            _activeLesson.value = list[idx + 1]
        }
    }

    fun prevLesson() {
        val list = filteredLessons()
        val curr = _activeLesson.value ?: return
        val idx = list.indexOfFirst { it.id == curr.id }
        if (idx > 0) {
            _activeLesson.value = list[idx - 1]
        }
    }

    fun filteredLessons(): List<Lesson> {
        val cat = _selectedCategory.value
        val all = _lessons.value
        return if (cat == "All") all else all.filter { it.category.equals(cat, ignoreCase = true) }
    }

    fun toggleBookmark(lessonId: String) {
        val current = _bookmarkedLessons.value.toMutableSet()
        if (current.contains(lessonId)) current.remove(lessonId) else current.add(lessonId)
        _bookmarkedLessons.value = current
        prefs.bookmarkedLessons = current

        val uid = prefs.userId
        if (uid.isNotEmpty()) {
            viewModelScope.launch {
                firestoreRepo.updateBookmarks(uid, current)
            }
        }
    }

    fun toggleFavorite(lessonId: String) {
        val current = _favoriteLessons.value.toMutableSet()
        if (current.contains(lessonId)) current.remove(lessonId) else current.add(lessonId)
        _favoriteLessons.value = current
        prefs.favoriteLessons = current

        val uid = prefs.userId
        if (uid.isNotEmpty()) {
            viewModelScope.launch {
                firestoreRepo.updateFavorites(uid, current)
            }
        }
    }

    fun completeLesson(lessonId: String) {
        val current = _completedLessons.value.toMutableSet()
        if (!current.contains(lessonId)) {
            current.add(lessonId)
            _completedLessons.value = current
            prefs.completedLessons = current

            addXp(20)
            _achievements.value = getAchievements()
            triggerCelebration()

            val uid = prefs.userId
            if (uid.isNotEmpty()) {
                val achSet = _achievements.value.filter { it.isUnlocked }.map { it.id }.toSet()
                viewModelScope.launch {
                    firestoreRepo.updateCompletedLessons(
                        uid = uid,
                        completedLessons = current,
                        newTotalXp = _totalXp.value,
                        weeklyXp = _weeklyXp.value,
                        achievements = achSet
                    )
                }
            }
        }
    }

    fun playPronunciation(word: String) {
        audioHelper.speak(word, _currentLanguage.value.id)
    }

    // Flashcard Actions
    fun flipCard() {
        _isCardFlipped.value = !_isCardFlipped.value
    }

    fun nextCard() {
        _isCardFlipped.value = false
        val size = _flashcardDeck.value.size
        if (size > 0) {
            _flashcardIndex.value = (_flashcardIndex.value + 1) % size
        }
    }

    fun prevCard() {
        _isCardFlipped.value = false
        val size = _flashcardDeck.value.size
        if (size > 0) {
            _flashcardIndex.value = if (_flashcardIndex.value - 1 < 0) size - 1 else _flashcardIndex.value - 1
        }
    }

    fun shuffleDeck() {
        _isCardFlipped.value = false
        _flashcardDeck.value = _flashcardDeck.value.shuffled()
        _flashcardIndex.value = 0
    }

    // Quiz Actions
    fun startQuiz(isDaily: Boolean = false) {
        _isDailyPractice.value = isDaily
        val questions = if (isDaily) {
            LanguageRepository.getDailyPracticeQuestions(10, _currentLanguage.value.id)
        } else {
            LanguageRepository.getQuestionsForLanguage(_currentLanguage.value.id, 5)
        }
        _quizQuestions.value = questions
        _currentQuestionIndex.value = 0
        _selectedAnswerIndex.value = null
        _quizScore.value = 0
        _isQuizFinished.value = false
    }

    fun startDailyChallenge() {
        startQuiz(isDaily = true)
    }

    fun answerQuestion(index: Int) {
        if (_selectedAnswerIndex.value != null) return // already answered
        _selectedAnswerIndex.value = index
        val currentQ = _quizQuestions.value.getOrNull(_currentQuestionIndex.value) ?: return

        if (index == currentQ.correctIndex) {
            _quizScore.value += 1
        }
    }

    fun nextQuestion() {
        val total = _quizQuestions.value.size
        if (_currentQuestionIndex.value + 1 < total) {
            _currentQuestionIndex.value += 1
            _selectedAnswerIndex.value = null
        } else {
            // Quiz completed!
            _isQuizFinished.value = true
            val score = _quizScore.value
            val earnedXp = if (_isDailyPractice.value) score * 10 + 20 else score * 10
            addXp(earnedXp)

            if (_isDailyPractice.value) {
                // bump streak
                _dailyStreak.value += 1
                prefs.dailyStreak = _dailyStreak.value
            }

            val accuracy = if (total > 0) (score * 100) / total else 0
            _quizAccuracy.value = ((_quizAccuracy.value + accuracy) / 2).coerceIn(10, 100)
            prefs.quizAccuracy = _quizAccuracy.value

            val uid = prefs.userId
            if (uid.isNotEmpty()) {
                val achSet = _achievements.value.filter { it.isUnlocked }.map { it.id }.toSet()
                viewModelScope.launch {
                    firestoreRepo.recordQuizScore(
                        uid = uid,
                        score = score,
                        totalQuestions = total,
                        newTotalXp = _totalXp.value,
                        newDailyStreak = _dailyStreak.value,
                        overallAccuracy = _quizAccuracy.value,
                        quizzesTaken = prefs.quizzesTaken,
                        weeklyXp = _weeklyXp.value,
                        languageId = _currentLanguage.value.id,
                        isDailyPractice = _isDailyPractice.value,
                        achievements = achSet
                    )
                }
            }

            triggerCelebration()
        }
    }

    fun addXp(amount: Int) {
        val newXp = _totalXp.value + amount
        _totalXp.value = newXp
        prefs.totalXp = newXp
        prefs.addXpToToday(amount)
        _weeklyXp.value = prefs.getWeeklyXp()
        _learnerLevel.value = calculateLearnerLevel(newXp)
        _learnerLevelTitle.value = calculateLevelTitle(newXp)

        val langId = _currentLanguage.value.id
        val curLevelNum = _currentLevel.value
        val curLevelInfo = _currentLevelInfo.value
        val currentPercent = curLevelInfo.calculateProgressPercent(newXp)
        _levelProgressPercent.value = currentPercent
        _xpToNextLevel.value = (curLevelInfo.nextLevelXp - newXp).coerceAtLeast(0)

        // Check if level up threshold reached
        if (newXp >= curLevelInfo.nextLevelXp && curLevelNum < 10) {
            val nextLevelNum = curLevelNum + 1
            val nextLevelInfo = LearningLevel.getLevelByNumber(nextLevelNum)
            _currentLevel.value = nextLevelNum
            _currentLevelInfo.value = nextLevelInfo
            val newHighest = _highestUnlockedLevel.value.coerceAtLeast(nextLevelNum)
            _highestUnlockedLevel.value = newHighest
            prefs.setLevel(langId, nextLevelNum)

            val levelLessons = LanguageRepository.getLessonsForLevel(langId, curLevelNum)
            val completedInLevel = levelLessons.count { _completedLessons.value.contains(it.id) }

            // Trigger Level Celebration
            _levelCelebration.value = LevelCelebrationData(
                completedLevel = curLevelInfo,
                nextLevel = nextLevelInfo,
                xpEarned = curLevelInfo.xpRequiredForThisLevel,
                lessonsCompletedCount = completedInLevel,
                quizAccuracy = _quizAccuracy.value,
                vocabularyLearnedCount = (curLevelNum * 25)
            )
            triggerCelebration()
        }

        saveCurrentLanguageState()
        recordTodayActive()
        _achievements.value = getAchievements()
    }

    private fun triggerCelebration() {
        _showConfetti.value = true
        viewModelScope.launch {
            delay(3500)
            _showConfetti.value = false
        }
    }

    fun dismissConfetti() {
        _showConfetti.value = false
    }

    // Achievements calculation
    fun getAchievements(): List<Achievement> {
        val completedCount = _completedLessons.value.size
        val streak = _dailyStreak.value
        val xp = _totalXp.value
        val accuracy = _quizAccuracy.value

        return listOf(
            Achievement(
                id = "ach_first_lesson",
                title = "First Step",
                description = "Complete your very first lesson",
                iconEmoji = "🌟",
                currentProgress = completedCount.coerceAtMost(1),
                requiredProgress = 1,
                isUnlocked = completedCount >= 1,
                xpReward = 50,
                badgeColor = Color(0xFF3B82F6)
            ),
            Achievement(
                id = "ach_7_streak",
                title = "7-Day Streak",
                description = "Practice 7 consecutive days",
                iconEmoji = "🔥",
                currentProgress = streak.coerceAtMost(7),
                requiredProgress = 7,
                isUnlocked = streak >= 7,
                xpReward = 100,
                badgeColor = Color(0xFFFF6B00)
            ),
            Achievement(
                id = "ach_30_streak",
                title = "Monthly Master",
                description = "Build a 30-day learning habit",
                iconEmoji = "🏆",
                currentProgress = streak.coerceAtMost(30),
                requiredProgress = 30,
                isUnlocked = streak >= 30,
                xpReward = 300,
                badgeColor = Color(0xFFF59E0B)
            ),
            Achievement(
                id = "ach_10_words",
                title = "Vocabulary Scout",
                description = "Learn 10 new vocabulary words",
                iconEmoji = "📖",
                currentProgress = (completedCount * 3).coerceAtMost(10),
                requiredProgress = 10,
                isUnlocked = completedCount * 3 >= 10,
                xpReward = 75,
                badgeColor = Color(0xFF10B981)
            ),
            Achievement(
                id = "ach_quiz_master",
                title = "Quiz Master",
                description = "Achieve 85%+ overall accuracy in quizzes",
                iconEmoji = "🎯",
                currentProgress = accuracy.coerceAtMost(85),
                requiredProgress = 85,
                isUnlocked = accuracy >= 85,
                xpReward = 150,
                badgeColor = Color(0xFF8B5CF6)
            ),
            Achievement(
                id = "ach_level_up",
                title = "Ascension",
                description = "Reach Level 5 (500+ XP)",
                iconEmoji = "🚀",
                currentProgress = xp.coerceAtMost(500),
                requiredProgress = 500,
                isUnlocked = xp >= 500,
                xpReward = 200,
                badgeColor = Color(0xFFEC4899)
            )
        )
    }

    fun setDarkModePreference(mode: Int) {
        _darkModePreference.value = mode
        prefs.darkModePreference = mode
    }

    fun setNotifications(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        prefs.notificationsEnabled = enabled
    }

    fun resetAllProgress() {
        prefs.resetAllProgress()
        _dailyStreak.value = 0
        _totalXp.value = 0
        _completedLessons.value = emptySet()
        _bookmarkedLessons.value = emptySet()
        _favoriteLessons.value = emptySet()
        _weeklyXp.value = listOf(0, 0, 0, 0, 0, 0, 0)
        _quizAccuracy.value = 0
        refreshLanguageData(prefs.selectedLanguageId)
        _achievements.value = getAchievements()

        val uid = prefs.userId
        if (uid.isNotEmpty()) {
            viewModelScope.launch {
                firestoreRepo.resetUserProgress(uid)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        firestoreListener?.remove()
        audioHelper.shutdown()
    }

    companion object {
        fun calculateLearnerLevel(xp: Int): Int {
            return when {
                xp >= 1500 -> 5
                xp >= 800 -> 4
                xp >= 400 -> 3
                xp >= 150 -> 2
                else -> 1
            }
        }

        fun calculateLevelTitle(xp: Int): String {
            return when {
                xp >= 1500 -> "Master Polyglot (Level 5)"
                xp >= 800 -> "Fluent Scholar (Level 4)"
                xp >= 400 -> "Conversationalist (Level 3)"
                xp >= 150 -> "Apprentice Speaker (Level 2)"
                else -> "Novice Explorer (Level 1)"
            }
        }
    }
}
