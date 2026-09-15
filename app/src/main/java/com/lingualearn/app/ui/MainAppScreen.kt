package com.lingualearn.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lingualearn.app.data.AuthRepository
import com.lingualearn.app.data.PreferencesManager
import com.lingualearn.app.ui.components.ConfettiOverlay
import com.lingualearn.app.ui.screens.AchievementsScreen
import com.lingualearn.app.ui.screens.EmailVerificationScreen
import com.lingualearn.app.ui.screens.FlashcardScreen
import com.lingualearn.app.ui.screens.HomeScreen
import com.lingualearn.app.ui.screens.LanguageSelectionScreen
import com.lingualearn.app.ui.screens.LearningJourneyScreen
import com.lingualearn.app.ui.screens.LessonsScreen
import com.lingualearn.app.ui.screens.LevelAssessmentDialog
import com.lingualearn.app.ui.screens.LevelCelebrationDialog
import com.lingualearn.app.ui.screens.LoginScreen
import com.lingualearn.app.ui.screens.OnboardingScreen
import com.lingualearn.app.ui.screens.ProfileScreen
import com.lingualearn.app.ui.screens.ProgressScreen
import com.lingualearn.app.ui.screens.QuizScreen
import com.lingualearn.app.ui.screens.RegisterScreen
import com.lingualearn.app.ui.screens.SplashScreen
import com.lingualearn.app.ui.theme.BrandPrimary
import com.lingualearn.app.ui.theme.LinguaLearnTheme
import com.lingualearn.app.ui.viewmodel.LinguaViewModel

enum class MainTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    LEVELS("Levels", Icons.Default.Map),
    LESSONS("Lessons", Icons.Default.MenuBook),
    FLASHCARDS("Cards", Icons.Default.Style),
    QUIZ("Quiz", Icons.Default.Quiz),
    PROGRESS("Progress", Icons.Default.BarChart),
    PROFILE("Profile", Icons.Default.Person)
}

enum class AppFlowState {
    SPLASH,
    ONBOARDING,
    LOGIN,
    REGISTER,
    EMAIL_VERIFICATION,
    CHOOSE_INITIAL_LANGUAGE,
    MAIN_APP
}

@Composable
fun MainAppScreen(
    prefs: PreferencesManager,
    viewModel: LinguaViewModel = viewModel()
) {
    val context = LocalContext.current
    val authRepo = remember { AuthRepository(context, prefs) }
    var flowState by remember { mutableStateOf(AppFlowState.SPLASH) }
    var userEmailForVerification by remember { mutableStateOf("") }

    var showLanguagePicker by remember { mutableStateOf(false) }
    var showAchievements by remember { mutableStateOf(false) }
    var showPlacementTest by remember { mutableStateOf(false) }
    var currentTab by remember { mutableStateOf(MainTab.HOME) }

    val darkModePref by viewModel.darkModePreference.collectAsState()
    val isDarkTheme = when (darkModePref) {
        1 -> true
        0 -> false
        else -> isSystemInDarkTheme()
    }

    LinguaLearnTheme(darkTheme = isDarkTheme) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (flowState) {
                AppFlowState.SPLASH -> {
                    SplashScreen(
                        onSplashComplete = {
                            if (!prefs.isOnboardingDone) {
                                flowState = AppFlowState.ONBOARDING
                            } else if (!authRepo.isLoggedIn) {
                                flowState = AppFlowState.LOGIN
                            } else if (!authRepo.isEmailVerified && !authRepo.isGuest) {
                                userEmailForVerification = authRepo.currentUser?.email ?: prefs.userEmail
                                flowState = AppFlowState.EMAIL_VERIFICATION
                            } else {
                                flowState = AppFlowState.MAIN_APP
                            }
                        }
                    )
                }

                AppFlowState.ONBOARDING -> {
                    OnboardingScreen(
                        onFinishOnboarding = {
                            prefs.isOnboardingDone = true
                            if (!authRepo.isLoggedIn) {
                                flowState = AppFlowState.LOGIN
                            } else {
                                flowState = AppFlowState.MAIN_APP
                            }
                        }
                    )
                }

                AppFlowState.LOGIN -> {
                    LoginScreen(
                        authRepo = authRepo,
                        onLoginSuccess = {
                            viewModel.onUserLogin(authRepo.currentUser?.uid ?: prefs.userId)
                            if (!authRepo.isEmailVerified && !authRepo.isGuest) {
                                userEmailForVerification = authRepo.currentUser?.email ?: prefs.userEmail
                                flowState = AppFlowState.EMAIL_VERIFICATION
                            } else {
                                flowState = AppFlowState.MAIN_APP
                            }
                        },
                        onNavigateToRegister = {
                            flowState = AppFlowState.REGISTER
                        },
                        onContinueAsGuest = {
                            viewModel.onUserLogin(authRepo.currentUser?.uid ?: prefs.userId)
                            flowState = AppFlowState.MAIN_APP
                        }
                    )
                }

                AppFlowState.REGISTER -> {
                    RegisterScreen(
                        authRepo = authRepo,
                        onRegisterSuccess = { email ->
                            userEmailForVerification = email
                            viewModel.selectLanguage(prefs.selectedLanguageId)
                            flowState = AppFlowState.EMAIL_VERIFICATION
                        },
                        onNavigateToLogin = {
                            flowState = AppFlowState.LOGIN
                        }
                    )
                }

                AppFlowState.EMAIL_VERIFICATION -> {
                    EmailVerificationScreen(
                        userEmail = userEmailForVerification.ifEmpty { prefs.userEmail },
                        authRepo = authRepo,
                        onVerifiedSuccess = {
                            flowState = AppFlowState.CHOOSE_INITIAL_LANGUAGE
                        },
                        onLogout = {
                            authRepo.logout()
                            flowState = AppFlowState.LOGIN
                        }
                    )
                }

                AppFlowState.CHOOSE_INITIAL_LANGUAGE -> {
                    val currentLang by viewModel.currentLanguage.collectAsState()
                    LanguageSelectionScreen(
                        languages = viewModel.allLanguages,
                        selectedLanguageId = currentLang.id,
                        onLanguageSelected = { langId ->
                            viewModel.selectLanguage(langId)
                            flowState = AppFlowState.MAIN_APP
                        },
                        onBack = {
                            flowState = AppFlowState.MAIN_APP
                        }
                    )
                }

                AppFlowState.MAIN_APP -> {
                    if (showLanguagePicker) {
                        val currentLang by viewModel.currentLanguage.collectAsState()
                        LanguageSelectionScreen(
                            languages = viewModel.allLanguages,
                            selectedLanguageId = currentLang.id,
                            onLanguageSelected = { langId ->
                                viewModel.selectLanguage(langId)
                                showLanguagePicker = false
                            },
                            onBack = {
                                showLanguagePicker = false
                            }
                        )
                    } else if (showAchievements) {
                        val achievements by viewModel.achievements.collectAsState()
                        AchievementsScreen(
                            achievements = achievements,
                            onBack = { showAchievements = false }
                        )
                    } else {
                        // Main Experience with Bottom Navigation
                        val currentLanguage by viewModel.currentLanguage.collectAsState()
                        val lessons by viewModel.lessons.collectAsState()
                        val selectedCategory by viewModel.selectedCategory.collectAsState()
                        val activeLesson by viewModel.activeLesson.collectAsState()
                        val completedLessons by viewModel.completedLessons.collectAsState()
                        val bookmarkedLessons by viewModel.bookmarkedLessons.collectAsState()
                        val favoriteLessons by viewModel.favoriteLessons.collectAsState()
                        val dailyStreak by viewModel.dailyStreak.collectAsState()
                        val totalXp by viewModel.totalXp.collectAsState()
                        val dailyWord by viewModel.dailyWord.collectAsState()
                        val flashcardDeck by viewModel.flashcardDeck.collectAsState()
                        val flashcardIndex by viewModel.flashcardIndex.collectAsState()
                        val isCardFlipped by viewModel.isCardFlipped.collectAsState()
                        val quizQuestions by viewModel.quizQuestions.collectAsState()
                        val currentQIndex by viewModel.currentQuestionIndex.collectAsState()
                        val selectedAnswerIndex by viewModel.selectedAnswerIndex.collectAsState()
                        val quizScore by viewModel.quizScore.collectAsState()
                        val isQuizFinished by viewModel.isQuizFinished.collectAsState()
                        val isDailyPractice by viewModel.isDailyPractice.collectAsState()
                        val showConfetti by viewModel.showConfetti.collectAsState()
                        val weeklyXp by viewModel.weeklyXp.collectAsState()
                        val quizAccuracy by viewModel.quizAccuracy.collectAsState()
                        val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
                        val learnerLevel by viewModel.learnerLevel.collectAsState()
                        val learnerLevelTitle by viewModel.learnerLevelTitle.collectAsState()
                        val learningLevel by viewModel.learningLevel.collectAsState()

                        // 10-Level System StateFlows
                        val currentLevel by viewModel.currentLevel.collectAsState()
                        val highestUnlockedLevel by viewModel.highestUnlockedLevel.collectAsState()
                        val currentLevelInfo by viewModel.currentLevelInfo.collectAsState()
                        val levelProgressPercent by viewModel.levelProgressPercent.collectAsState()
                        val xpToNextLevel by viewModel.xpToNextLevel.collectAsState()
                        val levelCelebration by viewModel.levelCelebration.collectAsState()

                        Scaffold(
                            bottomBar = {
                                // Hide bottom bar when inside full active lesson view
                                if (activeLesson == null) {
                                    NavigationBar(
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        tonalElevation = 6.dp
                                    ) {
                                        MainTab.values().forEach { tab ->
                                            val isSelected = currentTab == tab
                                            NavigationBarItem(
                                                selected = isSelected,
                                                onClick = {
                                                    if (tab == MainTab.QUIZ && currentTab != MainTab.QUIZ) {
                                                        viewModel.startQuiz(false)
                                                    }
                                                    currentTab = tab
                                                },
                                                icon = {
                                                    Icon(
                                                        imageVector = tab.icon,
                                                        contentDescription = tab.label
                                                    )
                                                },
                                                label = {
                                                    Text(
                                                        text = tab.label,
                                                        fontSize = 11.sp,
                                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                                    )
                                                },
                                                colors = NavigationBarItemDefaults.colors(
                                                    selectedIconColor = BrandPrimary,
                                                    selectedTextColor = BrandPrimary,
                                                    indicatorColor = BrandPrimary.copy(alpha = 0.12f)
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                when (currentTab) {
                                    MainTab.HOME -> {
                                        HomeScreen(
                                            currentLanguage = currentLanguage,
                                            dailyStreak = dailyStreak,
                                            totalXp = totalXp,
                                            dailyWord = dailyWord,
                                            lessons = lessons,
                                            completedLessons = completedLessons,
                                            bookmarkedLessons = bookmarkedLessons,
                                            userName = prefs.userName.ifEmpty { "Learner" },
                                            dailyGoalMinutes = prefs.dailyGoalMinutes,
                                            learnerLevel = currentLevel,
                                            learnerLevelTitle = "${currentLevelInfo.title} (Level $currentLevel)",
                                            learningLevel = learningLevel,
                                            currentLevelNumber = currentLevel,
                                            highestUnlockedLevel = highestUnlockedLevel,
                                            levelProgressPercent = levelProgressPercent,
                                            xpToNextLevel = xpToNextLevel,
                                            suggestedLesson = viewModel.getSuggestedNextLesson(),
                                            onSelectLearningLevel = { lvl -> viewModel.setLearningLevel(lvl) },
                                            onOpenLanguageSelection = { showLanguagePicker = true },
                                            onOpenLesson = { lesson ->
                                                viewModel.openLesson(lesson)
                                                currentTab = MainTab.LESSONS
                                            },
                                            onPlayAudio = { word -> viewModel.playPronunciation(word) },
                                            onToggleBookmark = { id -> viewModel.toggleBookmark(id) },
                                            onStartDailyPractice = {
                                                viewModel.startDailyChallenge()
                                                currentTab = MainTab.QUIZ
                                            },
                                            onNavigateToFlashcards = {
                                                currentTab = MainTab.FLASHCARDS
                                            },
                                            onOpenLearningJourney = {
                                                currentTab = MainTab.LEVELS
                                            },
                                            onOpenPlacementTest = {
                                                showPlacementTest = true
                                            }
                                        )
                                    }

                                    MainTab.LEVELS -> {
                                        LearningJourneyScreen(
                                            currentLanguage = currentLanguage,
                                            currentLevelNumber = currentLevel,
                                            highestUnlockedLevel = highestUnlockedLevel,
                                            languageXp = totalXp,
                                            completedLessons = completedLessons,
                                            onBack = { currentTab = MainTab.HOME },
                                            onOpenLesson = { lesson ->
                                                viewModel.openLesson(lesson)
                                                currentTab = MainTab.LESSONS
                                            },
                                            onStartLevelQuiz = { levelNumber ->
                                                viewModel.startLevelQuiz(levelNumber)
                                                currentTab = MainTab.QUIZ
                                            },
                                            onOpenPlacementTest = {
                                                showPlacementTest = true
                                            }
                                        )
                                    }

                                    MainTab.LESSONS -> {
                                        LessonsScreen(
                                            currentLanguage = currentLanguage,
                                            selectedCategory = selectedCategory,
                                            lessons = viewModel.filteredLessons(),
                                            activeLesson = activeLesson,
                                            completedLessons = completedLessons,
                                            bookmarkedLessons = bookmarkedLessons,
                                            favoriteLessons = favoriteLessons,
                                            onCategorySelected = { cat -> viewModel.selectCategory(cat) },
                                            onOpenLesson = { lesson -> viewModel.openLesson(lesson) },
                                            onCloseLesson = { viewModel.closeLesson() },
                                            onNextLesson = { viewModel.nextLesson() },
                                            onPrevLesson = { viewModel.prevLesson() },
                                            onToggleBookmark = { id -> viewModel.toggleBookmark(id) },
                                            onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
                                            onCompleteLesson = { id -> viewModel.completeLesson(id) },
                                            onPlayAudio = { word -> viewModel.playPronunciation(word) }
                                        )
                                    }

                                    MainTab.FLASHCARDS -> {
                                        FlashcardScreen(
                                            currentLanguage = currentLanguage,
                                            deck = flashcardDeck,
                                            currentIndex = flashcardIndex,
                                            isFlipped = isCardFlipped,
                                            favoriteLessons = favoriteLessons,
                                            onFlip = { viewModel.flipCard() },
                                            onNext = { viewModel.nextCard() },
                                            onPrev = { viewModel.prevCard() },
                                            onShuffle = { viewModel.shuffleDeck() },
                                            onToggleFavorite = { id -> viewModel.toggleFavorite(id) },
                                            onPlayAudio = { word -> viewModel.playPronunciation(word) }
                                        )
                                    }

                                    MainTab.QUIZ -> {
                                        QuizScreen(
                                            currentLanguage = currentLanguage,
                                            questions = quizQuestions,
                                            currentIndex = currentQIndex,
                                            selectedIndex = selectedAnswerIndex,
                                            score = quizScore,
                                            isFinished = isQuizFinished,
                                            isDailyPractice = isDailyPractice,
                                            onAnswer = { idx -> viewModel.answerQuestion(idx) },
                                            onNext = { viewModel.nextQuestion() },
                                            onRestart = { viewModel.startQuiz(isDailyPractice) },
                                            onBackToHome = { currentTab = MainTab.HOME }
                                        )
                                    }

                                    MainTab.PROGRESS -> {
                                        ProgressScreen(
                                            dailyStreak = dailyStreak,
                                            totalXp = totalXp,
                                            completedLessonsCount = completedLessons.size,
                                            quizAccuracy = quizAccuracy,
                                            weeklyXp = weeklyXp,
                                            currentLanguage = currentLanguage,
                                            learnerLevelTitle = "${currentLevelInfo.title} (Level $currentLevel)",
                                            currentLevelNumber = currentLevel,
                                            highestUnlockedLevel = highestUnlockedLevel,
                                            onNavigateToLevels = { currentTab = MainTab.LEVELS }
                                        )
                                    }

                                    MainTab.PROFILE -> {
                                        ProfileScreen(
                                            currentLanguage = currentLanguage,
                                            dailyStreak = dailyStreak,
                                            totalXp = totalXp,
                                            completedLessonsCount = completedLessons.size,
                                            darkModePref = darkModePref,
                                            notificationsEnabled = notificationsEnabled,
                                            userName = prefs.userName.ifEmpty { "Learner" },
                                            userEmail = prefs.userEmail,
                                            userUid = authRepo.currentUser?.uid ?: prefs.userId,
                                            userPhotoUrl = authRepo.currentUser?.photoUrl?.toString(),
                                            learnerLevelTitle = learnerLevelTitle,
                                            allLanguages = viewModel.allLanguages,
                                            isGuest = authRepo.isGuest,
                                            dailyGoalMinutes = prefs.dailyGoalMinutes,
                                            onOpenLanguageSelection = { showLanguagePicker = true },
                                            onSelectLanguage = { langId -> viewModel.selectLanguage(langId) },
                                            onOpenAchievements = { showAchievements = true },
                                            onToggleDarkMode = { mode -> viewModel.setDarkModePreference(mode) },
                                            onToggleNotifications = { enabled -> viewModel.setNotifications(enabled) },
                                            onResetProgress = { viewModel.resetAllProgress() },
                                            onLogout = {
                                                viewModel.onUserLogout()
                                                authRepo.logout()
                                                flowState = AppFlowState.LOGIN
                                            }
                                        )
                                    }
                                }

                                // 10-Level Placement Assessment Dialog
                                if (showPlacementTest) {
                                    LevelAssessmentDialog(
                                        language = currentLanguage,
                                        onDismiss = { showPlacementTest = false },
                                        onApplyLevel = { targetLevel ->
                                            viewModel.applyPlacementLevel(targetLevel)
                                            showPlacementTest = false
                                        }
                                    )
                                }

                                // Level Progression Celebration Dialog
                                levelCelebration?.let { celebData ->
                                    LevelCelebrationDialog(
                                        celebrationData = celebData,
                                        onContinue = {
                                            viewModel.dismissLevelCelebration()
                                            currentTab = MainTab.LEVELS
                                        }
                                    )
                                }

                                // Celebratory Confetti Overlay
                                AnimatedVisibility(
                                    visible = showConfetti,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    ConfettiOverlay()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
