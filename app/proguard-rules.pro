# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# ============================================================
# LinguaLearn — Production ProGuard / R8 Rules
# ============================================================

# Preserve line numbers in stack traces for crash reporting
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ── Firebase Auth ────────────────────────────────────────────
-keep class com.google.firebase.auth.** { *; }
-keep class com.google.firebase.FirebaseApp { *; }
-keep class com.google.firebase.FirebaseOptions { *; }
-keep class com.google.firebase.auth.internal.** { *; }
-dontwarn com.google.firebase.auth.**

# ── Firebase Firestore ───────────────────────────────────────
-keep class com.google.firebase.firestore.** { *; }
-keep class com.google.firebase.firestore.model.** { *; }
-dontwarn com.google.firebase.firestore.**

# ── Google Identity / Credential Manager ─────────────────────
# Credential Manager uses reflection to call GoogleIdTokenCredential.createFrom()
-keep class com.google.android.libraries.identity.googleid.** { *; }
-keep class androidx.credentials.** { *; }
-keep class com.google.android.gms.auth.** { *; }
-dontwarn com.google.android.libraries.identity.googleid.**
-dontwarn androidx.credentials.**

# Keep data classes used for Firestore serialization
# (Firestore SDK accesses field names via reflection)
-keep class com.lingualearn.app.data.UserFirestoreData { *; }
-keepclassmembers class com.lingualearn.app.data.UserFirestoreData {
    public <init>(...);
    public *;
}

# ── Kotlin Coroutines ─────────────────────────────────────────
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-dontwarn kotlinx.coroutines.**

# ── Coil (profile image loading) ─────────────────────────────
-keep class coil.** { *; }
-dontwarn coil.**

# ── Moshi (JSON serialization) ────────────────────────────────
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier @interface *
-dontwarn com.squareup.moshi.**

# ── OkHttp / Retrofit ─────────────────────────────────────────
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# ── Jetpack Compose tooling (not needed in release) ──────────
-dontwarn androidx.compose.ui.tooling.**

# ── Android Room ──────────────────────────────────────────────
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.**
