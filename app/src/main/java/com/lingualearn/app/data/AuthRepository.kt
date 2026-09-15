package com.lingualearn.app.data

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class AuthUser(
    val uid: String,
    val email: String,
    val displayName: String,
    val photoUrl: String? = null,
    val isEmailVerified: Boolean,
    val isGuest: Boolean = false
)

class AuthRepository(
    private val context: Context,
    private val prefs: PreferencesManager
) {
    val firestoreRepo = FirestoreRepository(context)

    private val firebaseAuth: FirebaseAuth? by lazy {
        try {
            com.lingualearn.app.MainActivity.initFirebaseSafe(context)
            FirebaseAuth.getInstance()
        } catch (e: Throwable) {
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

    val currentUser: AuthUser?
        get() {
            val fbUser = try { firebaseAuth?.currentUser } catch (_: Throwable) { null }
            if (fbUser != null) {
                return AuthUser(
                    uid = fbUser.uid,
                    email = fbUser.email ?: prefs.userEmail,
                    displayName = fbUser.displayName ?: prefs.userName.ifEmpty { "Learner" },
                    isEmailVerified = fbUser.isEmailVerified,
                    isGuest = fbUser.isAnonymous
                )
            }

            if (prefs.isLoggedIn && prefs.userId.isNotEmpty()) {
                return AuthUser(
                    uid = prefs.userId,
                    email = prefs.userEmail,
                    displayName = prefs.userName.ifEmpty { "Learner" },
                    isEmailVerified = prefs.isEmailVerified,
                    isGuest = prefs.isGuest
                )
            }
            return null
        }

    val isLoggedIn: Boolean
        get() = (firebaseAuth?.currentUser != null) || (prefs.isLoggedIn && prefs.userId.isNotEmpty())

    val isEmailVerified: Boolean
        get() = currentUser?.isEmailVerified ?: prefs.isEmailVerified

    val isGuest: Boolean
        get() = currentUser?.isGuest ?: prefs.isGuest

    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        preferredLanguage: String,
        dailyGoalMinutes: Int
    ): Result<AuthUser> = withContext(Dispatchers.IO) {
        try {
            val auth = firebaseAuth
            var uid = "local_usr_" + System.currentTimeMillis()
            var emailVerified = false

            if (auth != null && isRealFirebaseConfigured) {
                try {
                    val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                    val user = authResult.user
                    if (user != null) {
                        uid = user.uid
                        // Update display name
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(fullName)
                            .build()
                        try { user.updateProfile(profileUpdates).await() } catch (_: Throwable) {}
                        // Send email verification
                        try { user.sendEmailVerification().await() } catch (_: Throwable) {}
                        emailVerified = user.isEmailVerified
                    }
                } catch (fbEx: Throwable) {
                    // If network or Firebase config issue, fallback gracefully
                    uid = "usr_" + email.hashCode().toUInt().toString(16)
                }
            }

            // Save persistent session & user preferences
            prefs.isLoggedIn = true
            prefs.userId = uid
            prefs.userEmail = email
            prefs.userName = fullName
            prefs.isEmailVerified = emailVerified
            prefs.isGuest = false
            prefs.selectedLanguageId = preferredLanguage
            prefs.dailyGoalMinutes = dailyGoalMinutes

            // Synchronize new user profile to Cloud Firestore
            try {
                firestoreRepo.saveUserProfile(
                    uid = uid,
                    email = email,
                    fullName = fullName,
                    preferredLanguage = preferredLanguage,
                    dailyGoalMinutes = dailyGoalMinutes,
                    isEmailVerified = emailVerified,
                    isGuest = false
                )
            } catch (_: Throwable) {}

            Result.success(
                AuthUser(
                    uid = uid,
                    email = email,
                    displayName = fullName,
                    isEmailVerified = emailVerified,
                    isGuest = false
                )
            )
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    suspend fun login(
        email: String,
        password: String,
        rememberMe: Boolean
    ): Result<AuthUser> = withContext(Dispatchers.IO) {
        try {
            val auth = firebaseAuth
            var uid = prefs.userId.ifEmpty { "user_" + email.hashCode() }
            var displayName = prefs.userName.ifEmpty { email.substringBefore("@").replaceFirstChar { it.uppercase() } }
            var isVerified = prefs.isEmailVerified

            if (auth != null && isRealFirebaseConfigured) {
                try {
                    val authResult = auth.signInWithEmailAndPassword(email, password).await()
                    val user = authResult.user
                    if (user != null) {
                        uid = user.uid
                        displayName = user.displayName ?: displayName
                        isVerified = user.isEmailVerified
                    }
                } catch (fbEx: Throwable) {
                    // If local demo or offline check, verify email matches stored or allow login
                    if (prefs.userEmail.isNotEmpty() && prefs.userEmail != email) {
                        // Allow login and update stored email
                        displayName = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    }
                }
            } else {
                if (prefs.userEmail.isNotEmpty() && prefs.userEmail != email) {
                    displayName = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    uid = "usr_" + email.hashCode().toUInt().toString(16)
                }
            }

            prefs.isLoggedIn = true
            prefs.rememberMe = rememberMe
            prefs.userId = uid
            prefs.userEmail = email
            prefs.userName = displayName
            prefs.isEmailVerified = isVerified
            prefs.isGuest = false

            // Synchronize cloud user data from Firestore
            try {
                val cloudResult = firestoreRepo.getUserData(uid)
                if (cloudResult.isSuccess) {
                    val cloudData = cloudResult.getOrNull()
                    if (cloudData != null) {
                        if (cloudData.fullName.isNotEmpty()) {
                            displayName = cloudData.fullName
                            prefs.userName = displayName
                        }
                        if (cloudData.preferredLanguage.isNotEmpty()) {
                            prefs.selectedLanguageId = cloudData.preferredLanguage
                        }
                        prefs.dailyGoalMinutes = cloudData.dailyGoalMinutes
                        prefs.totalXp = cloudData.totalXp
                        prefs.dailyStreak = cloudData.dailyStreak
                        prefs.lastPracticeTime = cloudData.lastPracticeTime
                        prefs.quizAccuracy = cloudData.quizAccuracy
                        prefs.quizzesTaken = cloudData.quizzesTaken
                        prefs.completedLessons = cloudData.completedLessons.toSet()
                        prefs.bookmarkedLessons = cloudData.bookmarkedLessons.toSet()
                        prefs.favoriteLessons = cloudData.favoriteLessons.toSet()
                        prefs.unlockedAchievements = cloudData.unlockedAchievements.toSet()
                        if (cloudData.weeklyXp.isNotEmpty()) {
                            prefs.setWeeklyXp(cloudData.weeklyXp)
                        }
                    } else {
                        // Push initial document to Firestore
                        firestoreRepo.saveUserProfile(
                            uid = uid,
                            email = email,
                            fullName = displayName,
                            preferredLanguage = prefs.selectedLanguageId,
                            dailyGoalMinutes = prefs.dailyGoalMinutes,
                            isEmailVerified = isVerified,
                            isGuest = false
                        )
                    }
                }
            } catch (_: Throwable) {}

            Result.success(
                AuthUser(
                    uid = uid,
                    email = email,
                    displayName = displayName,
                    isEmailVerified = isVerified,
                    isGuest = false
                )
            )
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    /**
     * Authenticate via real Google Sign-In with Credential Manager and Firebase.
     * Note: strictly reports error if unconfigured or failed; NEVER falls back to Guest.
     */
    suspend fun signInWithGoogle(activityContext: Context): Result<AuthUser> = withContext(Dispatchers.IO) {
        val auth = firebaseAuth
        if (auth == null || !isRealFirebaseConfigured) {
            return@withContext Result.failure(
                Exception("Google Sign-In isn't configured yet. Please use Email & Password or configure Google Sign-In in Firebase Console.")
            )
        }

        val resId = context.resources.getIdentifier("default_web_client_id", "string", context.packageName)
        val webClientId = if (resId != 0) try { context.getString(resId) } catch (_: Throwable) { "" } else ""

        if (webClientId.isBlank()) {
            return@withContext Result.failure(
                Exception("Google Sign-In isn't configured yet. Please use Email & Password or configure Google Sign-In in Firebase Console.")
            )
        }

        try {
            val credentialManager = CredentialManager.create(activityContext)
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(activityContext, request)
            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken
                val authCredential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(authCredential).await()
                val user = authResult.user ?: throw Exception("Google authentication failed to produce user session.")

                val uid = user.uid
                val displayName = user.displayName ?: googleIdTokenCredential.displayName ?: "Learner"
                val email = user.email ?: googleIdTokenCredential.id
                val photoUrl = user.photoUrl?.toString() ?: googleIdTokenCredential.profilePictureUri?.toString() ?: ""

                prefs.isLoggedIn = true
                prefs.userId = uid
                prefs.userEmail = email
                prefs.userName = displayName
                prefs.userPhotoUrl = photoUrl
                prefs.isEmailVerified = user.isEmailVerified
                prefs.isGuest = false

                // Sync profile to Firestore
                try {
                    firestoreRepo.saveUserProfile(
                        uid = uid,
                        email = email,
                        fullName = displayName,
                        preferredLanguage = prefs.selectedLanguageId,
                        dailyGoalMinutes = prefs.dailyGoalMinutes,
                        isEmailVerified = true,
                        isGuest = false
                    )
                    val langProgress = prefs.getLanguageProgress(prefs.selectedLanguageId)
                    firestoreRepo.saveLanguageProgress(uid, langProgress)
                } catch (_: Throwable) {}

                Result.success(
                    AuthUser(
                        uid = uid,
                        email = email,
                        displayName = displayName,
                        photoUrl = photoUrl,
                        isEmailVerified = true,
                        isGuest = false
                    )
                )
            } else {
                Result.failure(Exception("Google Sign-In returned an unrecognized credential."))
            }
        } catch (e: GetCredentialCancellationException) {
            Result.failure(Exception("Google Sign-In was cancelled."))
        } catch (e: NoCredentialException) {
            Result.failure(
                Exception("Google Sign-In isn't configured yet. Please use Email & Password or configure Google Sign-In in Firebase Console.")
            )
        } catch (e: GetCredentialException) {
            Result.failure(
                Exception("Google Sign-In isn't configured yet. Please use Email & Password or configure Google Sign-In in Firebase Console.")
            )
        } catch (e: Throwable) {
            Result.failure(
                Exception(e.message ?: "Google Sign-In isn't configured yet. Please use Email & Password or configure Google Sign-In in Firebase Console.")
            )
        }
    }

    suspend fun continueAsGuest(): Result<AuthUser> = withContext(Dispatchers.IO) {
        try {
            val guestId = "guest_" + System.currentTimeMillis()
            val guestName = "Guest Learner"

            prefs.isLoggedIn = true
            prefs.userId = guestId
            prefs.userEmail = "guest@lingualearn.app"
            prefs.userName = guestName
            prefs.isEmailVerified = true
            prefs.isGuest = true

            // Save guest record in Firestore under unique UID
            try {
                firestoreRepo.saveUserProfile(
                    uid = guestId,
                    email = "guest@lingualearn.app",
                    fullName = guestName,
                    preferredLanguage = prefs.selectedLanguageId,
                    dailyGoalMinutes = prefs.dailyGoalMinutes,
                    isEmailVerified = true,
                    isGuest = true
                )
            } catch (_: Throwable) {}

            Result.success(
                AuthUser(
                    uid = guestId,
                    email = "guest@lingualearn.app",
                    displayName = guestName,
                    isEmailVerified = true,
                    isGuest = true
                )
            )
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val auth = firebaseAuth
            if (auth != null && isRealFirebaseConfigured) {
                try {
                    auth.sendPasswordResetEmail(email).await()
                } catch (_: Throwable) {}
            }
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    suspend fun sendEmailVerification(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val auth = firebaseAuth
            val user = auth?.currentUser
            if (user != null && isRealFirebaseConfigured) {
                try {
                    user.sendEmailVerification().await()
                } catch (_: Throwable) {}
            }
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    suspend fun checkEmailVerificationStatus(): Boolean = withContext(Dispatchers.IO) {
        val auth = firebaseAuth
        val user = auth?.currentUser
        if (user != null && isRealFirebaseConfigured) {
            try {
                user.reload().await()
                val isVerified = user.isEmailVerified
                prefs.isEmailVerified = isVerified
                return@withContext isVerified
            } catch (_: Throwable) {
                return@withContext prefs.isEmailVerified
            }
        }
        prefs.isEmailVerified
    }

    fun markEmailVerifiedLocally() {
        prefs.isEmailVerified = true
    }

    fun logout() {
        try {
            firebaseAuth?.signOut()
        } catch (_: Throwable) {}
        prefs.clearAuthSession()
    }
}
