# LinguaLearn 🌍

A modern Android language-learning application designed to help users learn multiple languages through structured lessons, pronunciation support, translations, and interactive learning content.

## 📱 Overview

**LinguaLearn** is a multilingual language-learning app built with Kotlin and Jetpack Compose. It provides organized learning content across multiple languages, with beginner-to-intermediate lessons covering essential vocabulary, phrases, pronunciation, and cultural information.

The application focuses on making language learning accessible, structured, and engaging through a clean Android interface.

## ✨ Features

* 🌐 Support for **18 languages**
* 📚 Structured curriculum with topic-based lessons
* 🎯 Multiple learning levels
* 🔊 Text-to-speech pronunciation support
* 📝 Native scripts, romanization, and translations
* 💬 Example sentences and practical phrases
* 🌍 Cultural tips and language-specific information
* 🎨 Modern Jetpack Compose user interface
* 📈 Organized learning progression
* 📱 Android-native experience

## 🌎 Supported Languages

LinguaLearn currently includes curriculum content for:

* Spanish
* French
* German
* Italian
* Japanese
* Korean
* English
* Portuguese
* Russian
* Mandarin Chinese
* Hindi
* Tamil
* Telugu
* Malayalam
* Turkish
* Dutch
* Swedish
* Arabic

## 🧩 Curriculum Structure

The curriculum is organized into:

* **6 major topics**
* **10 levels per language**
* **60 lessons per language**
* **18 supported languages**

This provides a structured learning experience with progressively organized content.

## 🛠️ Tech Stack

| Technology      | Purpose                 |
| --------------- | ----------------------- |
| Kotlin          | Application development |
| Jetpack Compose | Modern Android UI       |
| Android SDK     | Mobile platform         |
| Gradle          | Build automation        |
| Text-to-Speech  | Pronunciation support   |
| Git & GitHub    | Version control         |

## 🏗️ Project Structure

```text
lingualearn/
├── app/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── README.md
```

The curriculum is modularized into separate language groups to make the project easier to maintain and expand.

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

* Android Studio
* Android SDK
* JDK compatible with the project
* Git

### Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/lingualearn.git
cd lingualearn
```

### Open the Project

1. Open Android Studio.
2. Select **Open**.
3. Choose the cloned `lingualearn` directory.
4. Allow Gradle synchronization to complete.
5. Connect an Android device or start an emulator.
6. Run the application.

## 🔊 Text-to-Speech Support

LinguaLearn uses Android Text-to-Speech functionality to provide pronunciation support for supported languages.

Availability and pronunciation quality may depend on:

* The Android device
* Installed text-to-speech engines
* Available language voice data
* Device language settings

If a voice is unavailable, install the required language voice data through the device’s Text-to-Speech settings.

## 🧪 Testing

The project includes curriculum validation tests to verify:

* Language coverage
* Lesson availability
* Curriculum structure
* Content uniqueness
* Required lesson fields

Run the tests using:

```bash
./gradlew test
```

On Windows:

```powershell
.\gradlew.bat test
```

## 🔨 Build the Application

To build the debug version:

```bash
./gradlew assembleDebug
```

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

## 🔐 Configuration

Some Android or backend-related features may require additional configuration files or environment settings.

Do not commit private credentials, API keys, signing keys, or machine-specific configuration files to GitHub.

## 🗺️ Future Improvements

* User accounts and cloud synchronization
* Learning progress tracking
* Spaced-repetition flashcards
* Interactive quizzes
* Daily learning streaks
* Personalized learning recommendations
* Offline lesson access
* Additional languages and advanced levels
* Improved pronunciation evaluation

## 🤝 Contributing

Contributions are welcome.

To contribute:

1. Fork the repository.
2. Create a new branch.

```bash
git checkout -b feature/your-feature-name
```

3. Make your changes.
4. Commit your changes.

```bash
git commit -m "Add your feature"
```

5. Push the branch.

```bash
git push origin feature/your-feature-name
```

6. Open a Pull Request.

## 📄 License

This project is currently available for educational and development purposes.

A formal open-source license may be added in a future release.

## 👨‍💻 Author

Developed by **GLR RAGHUL**

Built with Kotlin, Jetpack Compose, and a passion for accessible language learning.
