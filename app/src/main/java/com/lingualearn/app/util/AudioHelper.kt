package com.lingualearn.app.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

/**
 * Wraps Android TextToSpeech with locale mapping for all 18 supported languages.
 * Gracefully falls back to English when a device TTS engine does not have the
 * requested language pack installed, without crashing.
 */
class AudioHelper(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var currentLangId = "es"

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.d(TAG, "TTS initialization notice: ${e.message}")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            setLanguage(currentLangId)
        } else {
            Log.w(TAG, "TTS engine initialization failed with status=$status")
        }
    }

    /**
     * Sets the TTS locale for the given language ID (BCP-47).
     * Falls back to English if the locale is unavailable on this device.
     */
    fun setLanguage(langId: String) {
        currentLangId = langId
        if (!isInitialized || tts == null) return
        val locale = localeFor(langId)
        try {
            val availability = tts?.isLanguageAvailable(locale) ?: TextToSpeech.LANG_NOT_SUPPORTED
            when (availability) {
                TextToSpeech.LANG_AVAILABLE,
                TextToSpeech.LANG_COUNTRY_AVAILABLE,
                TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE -> {
                    tts?.language = locale
                    Log.d(TAG, "TTS locale set to ${locale.toLanguageTag()} for '$langId'")
                }
                TextToSpeech.LANG_MISSING_DATA -> {
                    Log.w(TAG, "TTS locale ${locale.toLanguageTag()} has missing data for '$langId'. Falling back to English.")
                    tts?.language = Locale.ENGLISH
                }
                else -> {
                    Log.w(TAG, "TTS locale ${locale.toLanguageTag()} not supported for '$langId'. Falling back to English.")
                    tts?.language = Locale.ENGLISH
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Locale setting notice for '$langId': ${e.message}")
        }
    }

    fun speak(text: String, langId: String = currentLangId) {
        setLanguage(langId)
        if (isInitialized && tts != null) {
            val params = Bundle()
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, "UtteranceId_${langId}_${text.hashCode()}")
        } else {
            // Fallback: subtle audio tone feedback when TTS is unavailable
            try {
                val tone = ToneGenerator(AudioManager.STREAM_MUSIC, 70)
                tone.startTone(ToneGenerator.TONE_PROP_BEEP, 120)
            } catch (_: Exception) {}
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }

    companion object {
        private const val TAG = "AudioHelper"

        /**
         * Returns the best-effort BCP-47 locale for the given LinguaLearn language ID.
         * All 18 supported language IDs are explicitly mapped.
         */
        fun localeFor(langId: String): Locale = when (langId) {
            "es" -> Locale("es", "ES")       // Spanish (Spain)
            "fr" -> Locale.FRENCH            // French
            "de" -> Locale.GERMAN            // German
            "ja" -> Locale.JAPANESE          // Japanese
            "ko" -> Locale.KOREAN            // Korean
            "en" -> Locale.ENGLISH           // English
            "it" -> Locale.ITALIAN           // Italian
            "pt" -> Locale("pt", "BR")       // Portuguese (Brazil — broader TTS coverage)
            "ru" -> Locale("ru", "RU")       // Russian
            "zh" -> Locale.SIMPLIFIED_CHINESE // Mandarin Chinese (Simplified)
            "hi" -> Locale("hi", "IN")       // Hindi
            "ta" -> Locale("ta", "IN")       // Tamil
            "te" -> Locale("te", "IN")       // Telugu
            "ml" -> Locale("ml", "IN")       // Malayalam
            "ar" -> Locale("ar", "SA")       // Arabic (Saudi Arabia — MSA)
            "tr" -> Locale("tr", "TR")       // Turkish
            "nl" -> Locale("nl", "NL")       // Dutch
            "sv" -> Locale("sv", "SE")       // Swedish
            else -> Locale.ENGLISH           // Safe fallback
        }

        /** Returns true if a given language ID has a known TTS locale mapping. */
        fun isTtsMapped(langId: String): Boolean =
            setOf("es","fr","de","ja","ko","en","it","pt","ru","zh","hi","ta","te","ml","ar","tr","nl","sv").contains(langId)
    }
}
