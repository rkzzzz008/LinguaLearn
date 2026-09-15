package com.lingualearn.app.data

import com.lingualearn.app.model.QuizQuestion

data class PlacementQuestion(
    val id: String,
    val category: String, // "Vocabulary", "Grammar", "Reading", "Common Phrases", "Sentence Understanding"
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val levelWeight: Int // level difficulty 1..10
)

object PlacementTestCatalog {

    fun getPlacementTestForLanguage(langId: String): List<PlacementQuestion> {
        return when (langId) {
            "es" -> spanishPlacementQuestions
            "fr" -> frenchPlacementQuestions
            "de" -> germanPlacementQuestions
            "ja" -> japanesePlacementQuestions
            else -> getGeneralPlacementQuestions(langId)
        }
    }

    private val spanishPlacementQuestions = listOf(
        PlacementQuestion(
            id = "pt_es_1",
            category = "Vocabulary",
            question = "¿Qué significa 'Buenos días' en inglés?",
            options = listOf("Good night", "Good morning", "Goodbye", "Please"),
            correctIndex = 1,
            levelWeight = 1
        ),
        PlacementQuestion(
            id = "pt_es_2",
            category = "Common Phrases",
            question = "¿Cómo se responde cortésmente a 'Muchas gracias'?",
            options = listOf("Hasta luego", "De nada", "Por supuesto", "Lo siento"),
            correctIndex = 1,
            levelWeight = 2
        ),
        PlacementQuestion(
            id = "pt_es_3",
            category = "Grammar",
            question = "Elige la forma correcta: 'Nosotros ______ español todos los días.'",
            options = listOf("estudia", "estudiamos", "estudian", "estudio"),
            correctIndex = 1,
            levelWeight = 3
        ),
        PlacementQuestion(
            id = "pt_es_4",
            category = "Reading",
            question = "Lee el texto: 'Ayer Juan fue al mercado y compró fruta fresca.' ¿Qué hizo Juan ayer?",
            options = listOf("Fue a la escuela", "Compró fruta en el mercado", "Cocinó en su casa", "Viajó en tren"),
            correctIndex = 1,
            levelWeight = 4
        ),
        PlacementQuestion(
            id = "pt_es_5",
            category = "Sentence Understanding",
            question = "¿Qué expresa la frase: 'Si tuviera más tiempo libre, viajaría por todo el mundo'?",
            options = listOf("Un hecho que ocurrió ayer", "Una condición hipotética en el presente", "Una orden directa", "Un plan garantizado"),
            correctIndex = 1,
            levelWeight = 6
        ),
        PlacementQuestion(
            id = "pt_es_6",
            category = "Grammar",
            question = "Completa con el subjuntivo: 'Dudo que ellos ______ a tiempo para la reunión.'",
            options = listOf("llegan", "lleguen", "llegarán", "llegaron"),
            correctIndex = 1,
            levelWeight = 7
        ),
        PlacementQuestion(
            id = "pt_es_7",
            category = "Reading",
            question = "¿Qué significa la expresión: 'Ese coche me costó un ojo de la cara'?",
            options = listOf("Fue un regalo", "Fue extremadamente caro", "Era defectuoso", "Lo compró en cuotas"),
            correctIndex = 1,
            levelWeight = 8
        )
    )

    private val frenchPlacementQuestions = listOf(
        PlacementQuestion(
            id = "pt_fr_1",
            category = "Vocabulary",
            question = "Que signifie 'Merci beaucoup' en anglais ?",
            options = listOf("Please", "Thank you very much", "Excuse me", "Good evening"),
            correctIndex = 1,
            levelWeight = 1
        ),
        PlacementQuestion(
            id = "pt_fr_2",
            category = "Common Phrases",
            question = "Comment demande-t-on l'addition au restaurant ?",
            options = listOf("L'addition, s'il vous plaît", "Où sont les toilettes ?", "Je suis prêt", "À demain"),
            correctIndex = 0,
            levelWeight = 2
        ),
        PlacementQuestion(
            id = "pt_fr_3",
            category = "Grammar",
            question = "Complétez : 'Demain, nous ______ visiter le château de Versailles.'",
            options = listOf("allons", "vais", "vont", "allez"),
            correctIndex = 0,
            levelWeight = 3
        ),
        PlacementQuestion(
            id = "pt_fr_4",
            category = "Reading",
            question = "'Hier soir, Sophie a préparé une délicieuse tarte aux pommes.' Qu'a fait Sophie ?",
            options = listOf("Elle a acheté du pain", "Elle a cuisiné une tarte aux pommes", "Elle est allée au restaurant", "Elle a lu un livre"),
            correctIndex = 1,
            levelWeight = 4
        ),
        PlacementQuestion(
            id = "pt_fr_5",
            category = "Sentence Understanding",
            question = "Que signifie : 'Il faut absolument que nous partions avant le coucher du soleil' ?",
            options = listOf("Nous pouvons rester la nuit", "Il est impératif de partir avant le crépuscule", "Le soleil ne se couche pas", "Nous partirons demain"),
            correctIndex = 1,
            levelWeight = 6
        ),
        PlacementQuestion(
            id = "pt_fr_6",
            category = "Grammar",
            question = "Choisissez la forme correcte au subjonctif : 'Bien qu'il ______ fatigué, il a continué.'",
            options = listOf("est", "soit", "sera", "était"),
            correctIndex = 1,
            levelWeight = 7
        )
    )

    private val germanPlacementQuestions = listOf(
        PlacementQuestion(
            id = "pt_de_1",
            category = "Vocabulary",
            question = "Was bedeutet 'Guten Tag'?",
            options = listOf("Good night", "Good day / Hello", "Goodbye", "Please"),
            correctIndex = 1,
            levelWeight = 1
        ),
        PlacementQuestion(
            id = "pt_de_2",
            category = "Common Phrases",
            question = "Wie fragt man nach dem Preis?",
            options = listOf("Wie spät ist es?", "Wie viel kostet das?", "Wo wohnen Sie?", "Wer ist das?"),
            correctIndex = 1,
            levelWeight = 2
        ),
        PlacementQuestion(
            id = "pt_de_3",
            category = "Grammar",
            question = "Welcher Artikel passt: '______ Buch liegt auf dem Tisch.'",
            options = listOf("Der", "Die", "Das", "Den"),
            correctIndex = 2,
            levelWeight = 3
        ),
        PlacementQuestion(
            id = "pt_de_4",
            category = "Reading",
            question = "Satzverständnis: 'Der ICE-Zug fährt von Gleis 4 ab.' Was bedeutet das?",
            options = listOf("Der Zug hat Verspätung", "Der Zug fährt von Bahnsteig 4 ab", "Gleis 4 ist gesperrt", "Der Zug fällt aus"),
            correctIndex = 1,
            levelWeight = 4
        ),
        PlacementQuestion(
            id = "pt_de_5",
            category = "Sentence Understanding",
            question = "Was drückt aus: 'Wenn ich Zeit hätte, würde ich nach Wien reisen'?",
            options = listOf("Er war gestern in Wien", "Eine hypothetische Möglichkeit", "Eine Pflicht", "Er hasst Reisen"),
            correctIndex = 1,
            levelWeight = 6
        )
    )

    private val japanesePlacementQuestions = listOf(
        PlacementQuestion(
            id = "pt_ja_1",
            category = "Vocabulary",
            question = "「ありがとう」の意味は何ですか？",
            options = listOf("Hello", "Thank you", "Goodbye", "Excuse me"),
            correctIndex = 1,
            levelWeight = 1
        ),
        PlacementQuestion(
            id = "pt_ja_2",
            category = "Common Phrases",
            question = "食事の前に言う挨拶は何ですか？",
            options = listOf("ごちそうさまでした", "いただきます", "さようなら", "おやすみなさい"),
            correctIndex = 1,
            levelWeight = 2
        ),
        PlacementQuestion(
            id = "pt_ja_3",
            category = "Grammar",
            question = "正しい助詞を選んでください：「駅_____行きます」",
            options = listOf("へ (e)", "は (wa)", "の (no)", "も (mo)"),
            correctIndex = 0,
            levelWeight = 3
        ),
        PlacementQuestion(
            id = "pt_ja_4",
            category = "Reading",
            question = "「昨日、新幹線で京都に行きました。」誰が何をしましたか？",
            options = listOf("飛行機で大阪に行った", "新幹線で京都に行った", "車で東京を走った", "歩いて神社に行った"),
            correctIndex = 1,
            levelWeight = 4
        ),
        PlacementQuestion(
            id = "pt_ja_5",
            category = "Sentence Understanding",
            question = "ビジネス敬語「お世話になっております」が使われる場面は？",
            options = listOf("友達と喧嘩した時", "取引先や顧客との挨拶", "就寝前の挨拶", "謝罪する時のみ"),
            correctIndex = 1,
            levelWeight = 6
        )
    )

    private fun getGeneralPlacementQuestions(langId: String): List<PlacementQuestion> = listOf(
        PlacementQuestion(
            id = "pt_gen_1",
            category = "Vocabulary",
            question = "Which option represents basic polite greetings in this language?",
            options = listOf("Hello & Thank you", "Run & Walk", "Yesterday & Tomorrow", "Red & Yellow"),
            correctIndex = 0,
            levelWeight = 1
        ),
        PlacementQuestion(
            id = "pt_gen_2",
            category = "Common Phrases",
            question = "How confident are you with daily routine interactions (ordering, asking directions)?",
            options = listOf("I don't know any yet", "I can use essential polite phrases", "I get confused", "Only numbers"),
            correctIndex = 1,
            levelWeight = 2
        ),
        PlacementQuestion(
            id = "pt_gen_3",
            category = "Grammar",
            question = "Can you distinguish past, present, and future verb forms in this language?",
            options = listOf("Not yet", "I understand basic present and past", "No idea", "Only nouns"),
            correctIndex = 1,
            levelWeight = 4
        ),
        PlacementQuestion(
            id = "pt_gen_4",
            category = "Reading",
            question = "Can you comprehend short paragraphs about work, travel, or everyday stories?",
            options = listOf("Too difficult", "Yes, with good general comprehension", "Only individual words", "Never tried"),
            correctIndex = 1,
            levelWeight = 6
        ),
        PlacementQuestion(
            id = "pt_gen_5",
            category = "Sentence Understanding",
            question = "Can you follow fast native speakers discussing opinions or cultural topics?",
            options = listOf("Not yet", "I understand abstract concepts well", "Only simple questions", "Never"),
            correctIndex = 1,
            levelWeight = 8
        )
    )

    /**
     * Calculates recommended starting level based on self-choice and quiz results.
     */
    fun calculateRecommendedLevel(
        selfAssessmentTier: Int, // 1 to 5
        testScore: Int,          // correct answers count
        totalQuestions: Int
    ): Int {
        if (totalQuestions == 0) {
            return when (selfAssessmentTier) {
                1 -> 1 // Completely new
                2 -> 2 // Basic words
                3 -> 3 // Simple sentences
                4 -> 5 // Basic conversations
                5 -> 7 // Know fairly well
                else -> 1
            }
        }

        val accuracy = (testScore.toFloat() / totalQuestions.toFloat())
        val computedFromTest = when {
            accuracy >= 0.90f -> 8
            accuracy >= 0.75f -> 6
            accuracy >= 0.60f -> 4
            accuracy >= 0.40f -> 3
            accuracy >= 0.20f -> 2
            else -> 1
        }

        // Balance self-assessment with test performance
        val selfLevel = when (selfAssessmentTier) {
            1 -> 1
            2 -> 2
            3 -> 3
            4 -> 5
            5 -> 7
            else -> 1
        }

        val blended = ((computedFromTest * 0.7f) + (selfLevel * 0.3f)).toInt()
        return blended.coerceIn(1, 8) // Capped at Level 8 for starting placement
    }
}
