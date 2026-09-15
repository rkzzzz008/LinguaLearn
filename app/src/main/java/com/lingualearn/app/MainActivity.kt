package com.lingualearn.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lingualearn.app.data.PreferencesManager
import com.lingualearn.app.ui.MainAppScreen
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initFirebaseSafe(this)
        val prefs = PreferencesManager(this)
        setContent {
            MainAppScreen(prefs = prefs)
        }
    }

    companion object {
        /**
         * Sentinel API key used only when google-services.json is absent (e.g. in CI or
         * open-source environments without real Firebase credentials).
         *
         * [AuthRepository.isRealFirebaseConfigured] and [FirestoreRepository.isRealFirebaseConfigured]
         * detect this prefix and disable all live Firebase calls, keeping the app fully
         * functional in local/offline mode.
         *
         * This key is NOT a real Firebase key. It deliberately starts with "AIzaSyMockKey"
         * so the detection logic in both repositories can identify offline mode reliably.
         */
        private const val OFFLINE_SENTINEL_KEY = "AIzaSyMockKeyForLinguaLearnOfflineFallback"

        fun initFirebaseSafe(context: Context) {
            try {
                if (FirebaseApp.getApps(context).isEmpty()) {
                    val app = try {
                        // Primary path: real google-services.json present → use real Firebase project.
                        FirebaseApp.initializeApp(context)
                    } catch (_: Throwable) {
                        null
                    }
                    if (app == null && FirebaseApp.getApps(context).isEmpty()) {
                        // Fallback path: no google-services.json — create a sentinel app so the
                        // rest of the codebase can run in local/offline mode without crashing.
                        val options = FirebaseOptions.Builder()
                            .setApplicationId(context.packageName)
                            .setProjectId("lingualearn-offline")
                            .setApiKey(OFFLINE_SENTINEL_KEY)
                            .build()
                        FirebaseApp.initializeApp(context, options)
                    }
                }
            } catch (_: Throwable) {
                // Safely handle environments without Google Services at all.
            }
        }
    }
}

