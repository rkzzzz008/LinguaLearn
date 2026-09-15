package com.lingualearn.app.data.content

import com.lingualearn.app.model.LearningLevel
import com.lingualearn.app.model.Lesson

internal typealias LessonContent = CurriculumLessonContent

/**
 * Provides comprehensive, level-specific curriculum modules for all 10 proficiency levels
 * across supported languages.
 */
object CurriculumCatalog {

    fun getLessonsForLevel(languageId: String, levelNumber: Int): List<Lesson> {
        val level = LearningLevel.getLevelByNumber(levelNumber)
        val topics = level.curriculumTopics

        return topics.mapIndexed { index, topic ->
            createLessonForTopic(languageId, levelNumber, topic, index + 1)
        }
    }

    fun getAllLessonsForLanguage(languageId: String): List<Lesson> {
        val list = mutableListOf<Lesson>()
        for (lvl in 1..10) {
            list.addAll(getLessonsForLevel(languageId, lvl))
        }
        return list
    }

    private fun createLessonForTopic(
        langId: String,
        levelNumber: Int,
        topic: String,
        topicIndex: Int
    ): Lesson {
        val lessonId = "cur_${langId}_lvl${levelNumber}_$topicIndex"
        val xp = 15 + (levelNumber * 5)
        val cefrCategory = when {
            levelNumber <= 2 -> "Beginner"
            levelNumber <= 5 -> "Elementary"
            levelNumber <= 8 -> "Intermediate"
            else -> "Advanced"
        }

        // Language-specific targeted vocabulary & examples
        val (word, meaning, pronunciation, exampleSentence, exampleTranslation, tip) =
            getLocalizedCurriculumContent(langId, levelNumber, topic)

        return Lesson(
            id = lessonId,
            languageId = langId,
            title = topic,
            category = determineCategory(topic),
            level = cefrCategory,
            word = word,
            meaning = meaning,
            pronunciation = pronunciation,
            exampleSentence = exampleSentence,
            exampleTranslation = exampleTranslation,
            tip = tip,
            xpReward = xp,
            levelNumber = levelNumber
        )
    }

    private fun determineCategory(topic: String): String {
        return when {
            topic.contains("Grammar", ignoreCase = true) || topic.contains("Verbs", ignoreCase = true) || topic.contains("Pronouns", ignoreCase = true) -> "Grammar"
            topic.contains("Conversation", ignoreCase = true) || topic.contains("Dialogue", ignoreCase = true) || topic.contains("Debate", ignoreCase = true) -> "Phrases"
            topic.contains("Travel", ignoreCase = true) || topic.contains("Directions", ignoreCase = true) || topic.contains("Restaurant", ignoreCase = true) -> "Travel"
            topic.contains("Culture", ignoreCase = true) || topic.contains("Humor", ignoreCase = true) || topic.contains("Simulation", ignoreCase = true) -> "Culture Tips"
            else -> "Vocabulary"
        }
    }

    private fun getLocalizedCurriculumContent(
        langId: String,
        levelNumber: Int,
        topic: String
    ): LessonContent {
        return when (langId) {
            "es" -> getSpanishContent(levelNumber, topic)
            "fr" -> getFrenchContent(levelNumber, topic)
            "de" -> GermanCurriculum.getContent(levelNumber, topic)
            "ja" -> JapaneseCurriculum.getContent(levelNumber, topic)
            "ko" -> KoreanCurriculum.getContent(levelNumber, topic)
            "it" -> ItalianCurriculum.getContent(levelNumber, topic)
            "hi" -> HindiCurriculum.getContent(levelNumber, topic)
            "ta" -> TamilCurriculum.getContent(levelNumber, topic)
            "te" -> TeluguCurriculum.getContent(levelNumber, topic)
            "en" -> EnglishCurriculum.getContent(levelNumber, topic)
            "pt" -> PortugueseCurriculum.getContent(levelNumber, topic)
            "ar" -> ArabicCurriculum.getContent(levelNumber, topic)
            "tr" -> TurkishCurriculum.getContent(levelNumber, topic)
            "ru" -> RussianCurriculum.getContent(levelNumber, topic)
            "zh" -> MandarinCurriculum.getContent(levelNumber, topic)
            "ml" -> MalayalamCurriculum.getContent(levelNumber, topic)
            "nl" -> DutchCurriculum.getContent(levelNumber, topic)
            "sv" -> SwedishCurriculum.getContent(levelNumber, topic)
            else -> getDefaultContent(langId, levelNumber, topic)
        }
    }

    // ================= SPANISH CURRICULUM =================
    private fun getSpanishContent(level: Int, topic: String): LessonContent {
        return when (level) {
            1 -> when (topic) {
                "Greetings" -> LessonContent("¡Hola! ¿Cómo estás?", "Hello! How are you?", "OH-lah, KOH-moh ehs-TAHS", "¡Hola! ¿Cómo estás hoy?", "Hello! How are you today?", "Use inverted exclamation and question marks ¡ !")
                "Introducing Yourself" -> LessonContent("Me llamo...", "My name is...", "meh YAH-moh", "Me llamo Sofia, mucho gusto.", "My name is Sofia, nice to meet you.", "Literally translates as 'I call myself'.")
                "Yes / No / Basic Responses" -> LessonContent("Sí / No / Por favor", "Yes / No / Please", "see / noh / pohr fah-VOHR", "Sí, por favor, muchas gracias.", "Yes, please, thank you very much.", "Courteous polite expressions for any conversation.")
                "Numbers 1–10" -> LessonContent("Uno, Dos, Tres...", "1 to 10 numerals", "OO-noh, dohs, trehs", "Tengo dos boletos para el tren.", "I have two tickets for the train.", "Numbers 1-10 form the base for counting and prices.")
                "Basic Pronouns" -> LessonContent("Yo, Tú, Él, Ella, Nosotros", "I, You, He, She, We", "yoh, too, ehl, EH-yah, noh-SOH-trohs", "Nosotros aprendemos español.", "We are learning Spanish.", "Subject pronouns are often omitted because verbs reveal the subject.")
                else -> LessonContent("Palabras esenciales", "Essential words", "pah-LAH-brahs", "Buenos días, adiós y gracias.", "Good morning, goodbye, and thanks.", "Daily communicative essentials.")
            }
            2 -> when (topic) {
                "Family" -> LessonContent("La familia", "The family", "lah fah-MEE-lyah", "Mi hermana vive en Barcelona.", "My sister lives in Barcelona.", "Words ending in -a are typically feminine.")
                "Colors" -> LessonContent("Rojo, Azul, Verde", "Red, Blue, Green", "ROH-hoh, ah-ZOOL, VEHR-deh", "La camisa azul es muy bonita.", "The blue shirt is very pretty.", "Adjectives generally come after nouns in Spanish.")
                "Food" -> LessonContent("La comida", "The food / meal", "lah koh-MEE-dah", "Me encanta la paella española.", "I love Spanish paella.", "Me encanta conveys deep affection for things.")
                "Days & Months" -> LessonContent("Lunes a Domingo", "Monday to Sunday", "LOO-nehs ah doh-MEEN-goh", "El sábado vamos a la playa.", "On Saturday we are going to the beach.", "Days of the week are not capitalized in Spanish.")
                "Simple Questions" -> LessonContent("¿Dónde? ¿Cuándo? ¿Por qué?", "Where? When? Why?", "DOHN-deh, KWAHN-doh, pohr-KAY", "¿Dónde está la estación de metro?", "Where is the subway station?", "All interrogatives carry written accents in Spanish.")
                else -> LessonContent("Verbos básicos", "Basic verbs (comer, vivir)", "KOHN-yoo-gah-SYOHN", "Yo como fruta y vivo en Madrid.", "I eat fruit and live in Madrid.", "Verbs belong to -ar, -er, or -ir conjugations.")
            }
            3 -> when (topic) {
                "Daily Routine" -> LessonContent("Me despierto temprano", "I wake up early", "meh dehs-PYEHR-toh", "Todos los días me despierto a las siete.", "Every day I wake up at seven.", "Reflexive verbs use 'me', 'te', 'se' pronouns.")
                "Shopping" -> LessonContent("¿Cuánto cuesta esto?", "How much does this cost?", "KWAHN-toh KWEHS-tah", "¿Cuánto cuesta este sombrero de paja?", "How much is this straw hat?", "Great for artisan markets and boutiques.")
                "Time" -> LessonContent("¿Qué hora es?", "What time is it?", "kay OH-rah ehs", "Son las tres y media de la tarde.", "It is 3:30 in the afternoon.", "Use 'Es la una' for 1:00, and 'Son las...' for all other hours.")
                "Directions" -> LessonContent("Gira a la derecha", "Turn right", "HEE-rah ah lah deh-REH-chah", "Sigue recto y gira a la derecha.", "Go straight and turn right.", "'Izquierda' is left, 'derecha' is right.")
                "Simple Sentences" -> LessonContent("Quiero tomar un café", "I want to have a coffee", "KYEH-roh toh-MAHR", "Quiero tomar un café con leche.", "I want to have a coffee with milk.", "Pair 'quiero' directly with any infinitive verb.")
                else -> LessonContent("Conversación cotidiana", "Daily chatting", "kohn-vehr-sah-SYOHN", "¿Qué planes tienes para esta tarde?", "What plans do you have for this afternoon?", "Natural icebreaker with locals.")
            }
            4 -> when (topic) {
                "Past Events" -> LessonContent("Ayer visité el museo", "Yesterday I visited the museum", "ah-YEHR vee-see-TAY", "Ayer comí tapas en la plaza mayor.", "Yesterday I ate tapas in the main square.", "The preterite tense describes completed past actions.")
                "Future Plans" -> LessonContent("Voy a viajar el próximo mes", "I am going to travel next month", "voy ah vyah-HAHR", "Voy a viajar a Granada el próximo mes.", "I am going to travel to Granada next month.", "The near future is formed with 'ir + a + infinitive'.")
                "Travel" -> LessonContent("El billete de tren", "The train ticket", "ehl bee-YEH-teh", "¿Tiene un billete de ida y vuelta?", "Do you have a round-trip ticket?", "'Ida y vuelta' means round-trip.")
                "Restaurants" -> LessonContent("La cuenta, por favor", "The check, please", "lah KWEHN-tah", "Estuvo delicioso todo, la cuenta por favor.", "Everything was delicious, the check please.", "Polite way to wrap up a meal in Spain and Latin America.")
                "Describing People" -> LessonContent("Es alto y muy simpático", "He is tall and very friendly", "sehm-PAH-tee-koh", "Mi amigo es alto, alegre y muy simpático.", "My friend is tall, cheerful and very friendly.", "'Ser' is used for inherent traits and character.")
                else -> LessonContent("Pláticas habituales", "Everyday dialogues", "ah-bee-TWAH-lehs", "Hace buen tiempo hoy, ¿no te parece?", "Nice weather today, don't you think?", "Everyday small talk phrase.")
            }
            5 -> when (topic) {
                "Longer Conversations" -> LessonContent("Me gustaría comentarte", "I would like to tell you", "meh goos-tah-REE-ah", "Me gustaría comentarte mi experiencia laboral.", "I would like to share my work experience with you.", "Conditional tense softens requests and statements.")
                "Opinions" -> LessonContent("En mi opinión personal", "In my personal opinion", "ehn mee oh-pee-NYOHN", "En mi opinión, la educación es fundamental.", "In my opinion, education is fundamental.", "Essential for articulating viewpoints constructively.")
                "Experiences" -> LessonContent("He visitado muchos países", "I have visited many countries", "ay vee-see-TAH-doh", "He aprendido mucho durante este viaje.", "I have learned a lot during this journey.", "Present perfect connects past experiences to the present.")
                "Comparisons" -> LessonContent("Más interesante que...", "More interesting than...", "mahs een-teh-reh-SAHN-teh", "Este libro es más interesante que la película.", "This book is more interesting than the movie.", "Use 'más + adjective + que' for comparative degrees.")
                "More Complex Grammar" -> LessonContent("Si tuviera tiempo, iría", "If I had time, I would go", "see too-VYEH-rah", "Si tuviera más tiempo libre, estudiaría arte.", "If I had more free time, I would study art.", "Imperfect subjunctive pairs naturally with the conditional.")
                else -> LessonContent("Comprensión auditiva", "Listening comprehension", "ah-oo-dee-TEE-vah", "Escuché una entrevista sobre el cambio climático.", "I listened to an interview about climate change.", "Improves real-time phonetic recognition.")
            }
            6 -> when (topic) {
                "Storytelling" -> LessonContent("Había una vez en un pueblo", "Once upon a time in a town", "ah-BEE-ah OO-nah vehz", "Mientras caminaba por el bosque, vio una luz.", "While he was walking through the forest, he saw a light.", "Narratives alternate between imperfect and preterite.")
                "Workplace Conversations" -> LessonContent("Presentar el informe trimestral", "Present the quarterly report", "een-FOHR-meh", "Debemos revisar las metas del proyecto.", "We need to review the project's milestones.", "Professional vocabulary for office collaboration.")
                "Social Situations" -> LessonContent("Permíteme presentarte a...", "Allow me to introduce you to...", "pehr-MEE-teh-meh", "Permíteme presentarte a nuestro colega Carlos.", "Allow me to introduce you to our colleague Carlos.", "Formal and semi-formal social introductions.")
                "News & Media" -> LessonContent("Los titulares económicos", "Economic headlines", "tee-too-LAH-rehs", "La prensa destaca la innovación tecnológica.", "The press highlights technological innovation.", "Read newspapers and follow international broadcasts.")
                "Idiomatic Expressions" -> LessonContent("Costar un ojo de la cara", "To cost an arm and a leg", "kohn-sta-RROH-hoh", "Ese coche nuevo cuesta un ojo de la cara.", "That new car costs an arm and a leg.", "Idiomatic expressions demonstrate natural cultural fluency.")
                else -> LessonContent("Gramática intermedia", "Intermediate grammar", "grah-MAH-tee-kah", "Espero que tengas un excelente viaje.", "I hope you have an excellent journey.", "Subjunctive expresses wishes, doubts, and emotions.")
            }
            7 -> when (topic) {
                "Complex Conversations" -> LessonContent("Analizar las repercusiones", "Analyze the repercussions", "reh-pehr-koo-SYOH-nehs", "Debemos analizar las repercusiones a largo plazo.", "We must analyze the long-term repercussions.", "Used in strategic meetings and policy discussions.")
                "Debate & Opinions" -> LessonContent("Discrepo respetuosamente", "I respectfully disagree", "dees-KREH-poh", "Discrepo respetuosamente con esa conclusión.", "I respectfully disagree with that conclusion.", "Cultured debate and diplomatic dialogue.")
                "Abstract Topics" -> LessonContent("La trascendencia y la ética", "Transcendence and ethics", "trahs-sehn-DEHN-syah", "Debatieron sobre el impacto ético de la IA.", "They debated the ethical impact of AI.", "Philosophy, societal trends, and conceptual thought.")
                "Natural Expressions" -> LessonContent("Estar en las nubes", "To have your head in the clouds", "ehs-TAHR noo-BEHS", "Disculpa, no te escuché, estaba en las nubes.", "Sorry, I didn't hear you, I was daydreaming.", "Colloquial ease and native conversational cadence.")
                "Listening Comprehension" -> LessonContent("Podcast de debate político", "Political debate podcast", "POHD-kahst", "Comprendí todos los argumentos de la mesa redonda.", "I understood all arguments from the roundtable.", "Follow rapid native speakers across diverse dialects.")
                else -> LessonContent("Redacción argumentativa", "Argumentative essay writing", "reh-dahk-SYOHN", "Escribió un ensayo persuasivo y elocuente.", "He wrote an eloquent and persuasive essay.", "Structured formal written exposition.")
            }
            8 -> when (topic) {
                "Advanced Conversations" -> LessonContent("Sopesar las alternativas", "Weigh the alternatives", "soh-peh-SAHR", "Es imprescindible sopesar todas las variables.", "It is imperative to weigh all variables.", "High-level managerial and academic discourse.")
                "Cultural Context" -> LessonContent("El Siglo de Oro literario", "The Golden Age of Literature", "SEE-gloh deh OH-roh", "Cervantes transformó la narrativa universal con el Quijote.", "Cervantes transformed world literature with Don Quixote.", "Deep cultural and historic context enrich fluency.")
                "Nuanced Vocabulary" -> LessonContent("Inconmensurable y efímero", "Immeasurable and ephemeral", "een-kohn-mehn-soo-RAH-bleh", "La belleza del amanecer fue efímera pero sublime.", "The beauty of sunrise was fleeting yet sublime.", "Sophisticated stylistic vocabulary.")
                "Advanced Listening" -> LessonContent("Conferencia universitaria", "Academic lecture", "kohn-feh-REHN-syah", "Siguió la ponencia magistral sin dificultad.", "She followed the keynote lecture effortlessly.", "Comprehend technical vocabulary spoken at rapid pace.")
                "Formal vs Informal Speech" -> LessonContent("Tratamiento de Usted vs Tú", "Formal vs Informal register", "oos-TEHD vs too", "Le ruego tenga a bien considerar mi propuesta.", "I request that you kindly consider my proposal.", "Nuance between polite deference and friendly banter.")
                else -> LessonContent("Subjuntivo pluscuamperfecto", "Pluperfect subjunctive", "ploo-skwahm-pehr-FEHK-toh", "Si me lo hubieras dicho antes, te habría ayudado.", "If you had told me sooner, I would have helped you.", "Complex counterfactual condition.")
            }
            9 -> when (topic) {
                "Professional Communication" -> LessonContent("Negociación contractual", "Contractual negotiation", "neh-goh-syah-SYOHN", "Las partes llegaron a un acuerdo bilateral equitativo.", "The parties reached an equitable bilateral agreement.", "Executive corporate and diplomatic precision.")
                "Advanced Reading" -> LessonContent("Prosa contemporánea", "Contemporary prose", "PROH-sah", "Analizó el realismo mágico en García Márquez.", "She analyzed magical realism in García Márquez.", "Appreciate literature and literary subtexts.")
                "Idioms & Expressions" -> LessonContent("Meterse en camisa de once varas", "Bite off more than one can chew", "kah-MEE-sah", "No te compliques ni te metas en camisa de once varas.", "Don't complicate things or bite off more than you can chew.", "Ancient historic Spanish idioms.")
                "Advanced Writing" -> LessonContent("Monografía académica", "Academic monograph", "moh-noh-grah-FEE-ah", "Redactó una tesis rigurosa con aparato crítico.", "He drafted a rigorous thesis with critical apparatus.", "Publishable prose and scholarly precision.")
                "Difficult Listening" -> LessonContent("Debate parlamentario en vivo", "Live parliamentary debate", "pahr-lah-mehn-TAH-ryoh", "Captó las ironías e interrupciones del debate.", "He caught the ironies and quick interruptions in debate.", "Mastery over regional accents and rapid exchanges.")
                else -> LessonContent("Conversación de nivel nativo", "Native-level conversation", "nah-TEE-voh", "Se desenvolvió con espontaneidad y agudeza.", "She spoke with spontaneous wit and eloquence.", "Full natural fluidity.")
            }
            else -> when (topic) {
                "Natural Conversation" -> LessonContent("Fluidez espontánea", "Spontaneous fluency", "floo-ee-DEHZ", "La conversación fluyó con naturalidad absoluta.", "The conversation flowed with complete natural ease.", "Zero hesitation, native cadence.")
                "Humor & Cultural References" -> LessonContent("Juego de palabras y picardía", "Wordplay and cultural wit", "poh-KAH-dee-ah", "Comprendió el doble sentido y el humor sutil.", "He understood the double entendre and subtle humor.", "Humor is the ultimate mark of mastery.")
                "Advanced Debates" -> LessonContent("Retórica y persuasión", "Rhetoric and persuasion", "reh-TOH-ree-kah", "Expuso sus argumentos con impecable elocuencia.", "She delivered her arguments with impeccable eloquence.", "Compelling oratorical excellence.")
                "Professional Fluency" -> LessonContent("Liderazgo multicultural", "Multicultural leadership", "lee-deh-RAHZ-goh", "Dirigió el simposio internacional en Madrid.", "He directed the international symposium in Madrid.", "Lead negotiations and keynote panels.")
                "Native-Speed Listening" -> LessonContent("Cine de autor sin subtítulos", "Art-house cinema without subtitles", "SEE-neh", "Disfrutó la película captando los giros callejeros.", "She enjoyed the film catching all street slang.", "Complete auditory comprehension.")
                else -> LessonContent("Inmersión total", "Total real-world simulation", "een-mehr-SYOHN", "Vivió y trabajó integrado plenamente en la cultura.", "He lived and worked fully integrated into the culture.", "True bilingual mastery.")
            }
        }
    }

    // ================= FRENCH CURRICULUM =================
    private fun getFrenchContent(level: Int, topic: String): LessonContent {
        return when (level) {
            1 -> when (topic) {
                "Greetings" -> LessonContent("Bonjour ! Comment allez-vous ?", "Hello! How are you? (formal)", "bon-ZHOOR, koh-mahn tah-lay VOO", "Bonjour, je suis enchanté de vous rencontrer.", "Hello, I am delighted to meet you.", "Use 'Bonjour' during daytime and 'Bonsoir' in the evening.")
                "Introducing Yourself" -> LessonContent("Je m'appelle...", "My name is...", "zhuh mah-PEL", "Je m'appelle Claire, je suis ravie.", "My name is Claire, I'm delighted.", "Pronounce with nasal vowel elegance.")
                "Yes / No / Basic Responses" -> LessonContent("Oui / Non / S'il vous plaît", "Yes / No / Please", "wee / nohn / seel voo play", "Oui, s'il vous plaît, merci beaucoup.", "Yes, please, thank you very much.", "'S'il vous plaît' is the polite formal expression.")
                "Numbers 1–10" -> LessonContent("Un, Deux, Trois...", "1 to 10 numerals", "uhn, duh, trwah", "Deux croissants et un café au lait, s'il vous plaît.", "Two croissants and a café au lait, please.", "Essential for French bakeries and cafés.")
                "Basic Pronouns" -> LessonContent("Je, Tu, Il, Elle, Nous, Vous", "I, You, He, She, We, You", "zhuh, too, eel, el, noo, voo", "Nous aimons la belle langue française.", "We love the beautiful French language.", "'Vous' is polite singular and also plural 'you'.")
                else -> LessonContent("Mots essentiels", "Essential words", "moh eh-sahn-SYEL", "Merci, pardon, et à bientôt.", "Thank you, excuse me, and see you soon.", "Polite staples of French courtesy.")
            }
            2 -> when (topic) {
                "Family" -> LessonContent("La famille", "The family", "lah fah-MEEL", "Mon frère habite à Lyon.", "My brother lives in Lyon.", "Possessives agree with gender: mon père, ma mère.")
                "Colors" -> LessonContent("Bleu, Blanc, Rouge", "Blue, White, Red", "bluh, blahn, roozh", "Les couleurs du drapeau français.", "The colors of the French flag.", "French adjectives usually follow nouns.")
                "Food" -> LessonContent("Le fromage et le pain", "Cheese and bread", "luh froh-MAHZH", "J'adore déguster une baguette croustillante.", "I love enjoying a crusty baguette.", "France boasts over 1,000 distinct cheese varieties.")
                "Days & Months" -> LessonContent("Lundi à Dimanche", "Monday to Sunday", "luhn-DEE ah dee-MAHNSH", "Nous partons en vacances en juillet.", "We are leaving on vacation in July.", "Days and months are not capitalized in French.")
                "Simple Questions" -> LessonContent("Où ? Quand ? Pourquoi ?", "Where? When? Why?", "oo, kahn, poor-KWAH", "Où se trouve la Tour Eiffel ?", "Where is the Eiffel Tower located?", "Add 'Est-ce que' for questions: 'Où est-ce que...?'")
                else -> LessonContent("Verbes essentiels", "Essential verbs (être, avoir)", "EH-truh, ah-VWAHR", "Je suis prêt et j'ai faim.", "I am ready and I am hungry.", "'Avoir faim' (to have hunger) is used instead of 'to be hungry'.")
            }
            3 -> when (topic) {
                "Daily Routine" -> LessonContent("Je me réveille à sept heures", "I wake up at seven o'clock", "zhuh muh ray-VAY", "Chaque matin, je prends un thé chaud.", "Every morning, I drink a hot tea.", "Reflexive verbs use 'se réveiller'.")
                "Shopping" -> LessonContent("Combien ça coûte ?", "How much does that cost?", "kohn-BYEN sah koot", "Puis-je payer par carte bancaire ?", "May I pay by debit/credit card?", "Common phrase in French boutiques and markets.")
                "Time" -> LessonContent("Quelle heure est-il ?", "What time is it?", "kel UHR eh-teel", "Il est midi et demi à Paris.", "It is 12:30 PM in Paris.", "Use 'midi' for noon and 'minuit' for midnight.")
                "Directions" -> LessonContent("Tournez à gauche", "Turn left", "toor-NAY ah gohsh", "Allez tout droit puis tournez à gauche.", "Go straight ahead then turn left.", "'À droite' is right, 'à gauche' is left.")
                "Simple Sentences" -> LessonContent("Je voudrais réserver une table", "I would like to reserve a table", "zhuh voo-DRAY", "Je voudrais une table pour deux ce soir.", "I would like a table for two tonight.", "'Je voudrais' is much more polite than 'Je veux'.")
                else -> LessonContent("Conversation amicale", "Friendly chat", "ahn-treh-TYEN", "Tu as passé un bon week-end ?", "Did you have a nice weekend?", "Everyday conversational friendly question.")
            }
            4 -> when (topic) {
                "Past Events" -> LessonContent("Hier, j'ai visité le Louvre", "Yesterday I visited the Louvre", "ee-EHR zhay vee-zee-TAY", "Nous avons admiré les chefs-d'œuvre de peinture.", "We admired the masterpieces of painting.", "Passé composé uses 'avoir' or 'être' + past participle.")
                "Future Plans" -> LessonContent("Je vais voyager en Provence", "I am going to travel in Provence", "zhuh vay vwah-yah-ZHAY", "L'été prochain, je vais explorer le Sud.", "Next summer, I am going to explore the South.", "Futur proche uses 'aller + infinitive'.")
                "Travel" -> LessonContent("La gare et le TGV", "The train station and TGV", "lah gahr", "Le TGV pour Marseille part voie numéro trois.", "The high-speed train to Marseille departs track three.", "TGV is France's famous high-speed train.")
                "Restaurants" -> LessonContent("L'addition, s'il vous plaît", "The bill, please", "lah-dee-SYOHN", "Le repas était délicieux, l'addition s'il vous plaît.", "The meal was delicious, the bill please.", "Say 'C'était délicieux' to compliment the chef.")
                "Describing People" -> LessonContent("Elle a les yeux pétillants", "She has sparkling eyes", "pay-tee-YAHN", "C'est une personne généreuse et charmante.", "She is a charming and generous person.", "Rich descriptive adjectives.")
                else -> LessonContent("Discussions quotidiennes", "Everyday discussions", "kwoh-tee-DYEN", "Le temps est doux aujourd'hui.", "The weather is mild today.", "Pleasant seasonal conversation.")
            }
            5 -> when (topic) {
                "Longer Conversations" -> LessonContent("À mon humble avis", "In my humble opinion", "ah mohn uhm-bluh ah-VEE", "À mon avis, ce projet a un fort potentiel.", "In my opinion, this project has strong potential.", "Express reasoned thoughts with nuance.")
                "Opinions" -> LessonContent("Partager son point de vue", "Share one's viewpoint", "pwahn duh voo", "Je pense que la culture enrichit l'esprit.", "I think culture enriches the human spirit.", "Formulate constructive perspectives.")
                "Experiences" -> LessonContent("Une expérience inoubliable", "An unforgettable experience", "ee-noo-blee-AH-bluh", "Ce voyage en Bretagne fut tout à fait mémorable.", "That trip to Brittany was completely memorable.", "Relate personal life memories.")
                "Comparisons" -> LessonContent("Meilleur que / Pire que", "Better than / Worse than", "may-YUHR kuh", "Ce vin rouge est bien meilleur que l'autre.", "This red wine is much better than the other.", "'Meilleur' is the irregular comparative of 'bon'.")
                "More Complex Grammar" -> LessonContent("L'imparfait et le passé simple", "Imperfect and past tense", "lahm-pahr-FEH", "Pendant qu'il pleuvait, nous lisions tranquillement.", "While it was raining, we were reading peacefully.", "Contrasting background descriptions with actions.")
                else -> LessonContent("Pratique de l'écoute", "Listening practice", "ay-KOOT", "J'ai suivi une émission de radio sur France Inter.", "I followed a radio show on France Inter.", "Fine-tunes ear to rapid spoken liaisons.")
            }
            6 -> when (topic) {
                "Storytelling" -> LessonContent("Il était une fois dans un château", "Once upon a time in a castle", "eel eh-TAY oon fwah", "Soudain, une porte secrète s'ouvrit dans la nuit.", "Suddenly, a secret door opened in the night.", "French narrative prose techniques.")
                "Workplace Conversations" -> LessonContent("Rédiger un compte-rendu", "Draft meeting minutes", "kohn-tuh-rahn-DOO", "Nous devons finaliser l'ordre du jour de la réunion.", "We need to finalize the meeting's agenda.", "Corporate French professional vocabulary.")
                "Social Situations" -> LessonContent("Recevoir des convives à dîner", "Host guests for dinner", "kohn-VEEV", "Merci infiniment pour cette merveilleuse soirée.", "Thank you so much for this wonderful evening.", "French dinner party etiquette and gracious compliments.")
                "News & Media" -> LessonContent("La une de l'actualité", "The front-page headlines", "ahk-twa-lee-TAY", "Le journal analyse les réformes économiques.", "The newspaper analyzes the economic reforms.", "Media commentary and editorial reading.")
                "Idiomatic Expressions" -> LessonContent("Avoir le coup de foudre", "Love at first sight", "koo duh FOOD-ruh", "Quand j'ai vu Paris, j'ai eu un coup de foudre.", "When I saw Paris, it was love at first sight.", "Literally 'a strike of lightning'.")
                else -> LessonContent("Grammaire intermédiaire", "Subjunctive mood", "soob-zhohnk-TEEF", "Il faut que nous partions avant la nuit.", "We must leave before nightfall.", "The subjunctive follows expressions of necessity.")
            }
            7 -> when (topic) {
                "Complex Conversations" -> LessonContent("Les enjeux sociétaux contemporains", "Contemporary societal stakes", "ahn-ZHUH", "Ce débat soulève des questions fondamentales.", "This debate raises fundamental questions.", "High-register analytical discussions.")
                "Debate & Opinions" -> LessonContent("Réfuter un argument fallacieux", "Refute a fallacious argument", "ray-foo-TAY", "Je conteste cette affirmation avec des données précises.", "I contest this claim with precise data.", "Rigorous rhetorical argumentation.")
                "Abstract Topics" -> LessonContent("L'existentialisme et la liberté", "Existentialism and freedom", "ehg-zees-tahn-SYAH-leezm", "Sartre et Camus ont marqué la philosophie.", "Sartre and Camus marked modern philosophy.", "Intellectual discourse on literature and philosophy.")
                "Natural Expressions" -> LessonContent("Poser un lapin à quelqu'un", "To stand someone up", "poh-ZAY uhn lah-PAHN", "Il n'est pas venu au rendez-vous, il m'a posé un lapin !", "He didn't show up, he stood me up!", "Iconic French colloquial idiom.")
                "Listening Comprehension" -> LessonContent("Conférence de presse en direct", "Live press conference", "kohn-fay-RAHNS", "J'ai saisi chaque subtilité du discours présidentiel.", "I grasped every nuance of the presidential speech.", "Comprehending rapid high-level French eloquence.")
                else -> LessonContent("Dissertation argumentée", "Structured academic dissertation", "dee-sehr-tah-SYOHN", "Une thèse, une antithèse et une synthèse claire.", "A thesis, antithesis, and clear synthesis.", "The quintessential French academic structure.")
            }
            8 -> when (topic) {
                "Advanced Conversations" -> LessonContent("Articuler une pensée complexe", "Articulate complex thoughts", "ahr-tee-koo-LAY", "Il a synthétisé les diverses perspectives avec brio.", "He synthesized diverse perspectives with brilliance.", "Executive and philosophical nuance.")
                "Cultural Context" -> LessonContent("L'héritage des Lumières", "Legacy of the Enlightenment", "ay-ree-TAHZH", "Voltaire et Rousseau ont inspiré les droits de l'homme.", "Voltaire and Rousseau inspired human rights.", "Deep cultural grounding.")
                "Nuanced Vocabulary" -> LessonContent("La quintessence et l'éphémère", "Quintessence and the ephemeral", "kahn-teh-SAHNS", "Cette œuvre capture la quintessence de la poésie.", "This work captures the quintessence of poetry.", "Stylistic literary vocabulary.")
                "Advanced Listening" -> LessonContent("Débat télévisé contradictoire", "Televised debate with interruptions", "kohn-trah-deek-TWAHR", "Suivre les répliques cinglantes sans hésitation.", "Following snappy retorts without missing a beat.", "Deft listening at full native cadence.")
                "Formal vs Informal Speech" -> LessonContent("Langage soutenu vs Argot familier", "Elevated vs Colloquial slang", "soo-tuh-NOO", "Distinguer le verlan de la haute prose classique.", "Distinguishing back-slang (verlan) from classical prose.", "Navigate every register of French society.")
                else -> LessonContent("Subjonctif imparfait et concordance", "Imperfect subjunctive", "kohn-kohr-DAHNS", "Quoiqu'il en fût, sa détermination demeura intacte.", "Be that as it may, his resolve remained unbroken.", "Classic French literary concordance.")
            }
            9 -> when (topic) {
                "Professional Communication" -> LessonContent("Négociation diplomatique", "Diplomatic negotiation", "dee-ploh-mah-TEEK", "Un accord bilatéral historique a été conclu.", "A historic bilateral agreement was concluded.", "Prestigious diplomatic vocabulary.")
                "Advanced Reading" -> LessonContent("Marcel Proust et Gustave Flaubert", "Proust and Flaubert literary analysis", "PROOST", "À la recherche du temps perdu offre une prose unique.", "In Search of Lost Time offers unparalleled prose.", "Deep literary appreciation.")
                "Idioms & Expressions" -> LessonContent("Tirer des plans sur la comète", "Count your chickens before they hatch", "tee-RAY plahn", "Restons pragmatiques sans tirer des plans sur la comète.", "Let's stay pragmatic without building castles in the air.", "Rich metaphorical French idioms.")
                "Advanced Writing" -> LessonContent("Essai critique et éditorial", "Critical essay and editorial", "ay-dee-toh-RYAHL", "Un article d'opinion perspicace et percutant.", "An insightful and striking op-ed piece.", "Elegant French journalism.")
                "Difficult Listening" -> LessonContent("Théâtre classique de Molière", "Classical theater by Molière", "moh-LYEHR", "Comprendre les vers en alexandrins rythmés.", "Understanding the twelve-syllable alexandrine meter.", "Supreme auditory comprehension.")
                else -> LessonContent("Conversation quasi-maternelle", "Near-native conversation", "kwoh-zee", "S'exprimer avec esprit, répartie et élégance.", "Speaking with wit, repartee, and elegance.", "Flawless bilingual execution.")
            }
            else -> when (topic) {
                "Natural Conversation" -> LessonContent("Aisance absolue", "Effortless fluency", "ay-ZAHNS", "Parler avec le naturel et le charme d'un Parisien.", "Speaking with the natural charm of a native Parisian.", "Natural mastery.")
                "Humor & Cultural References" -> LessonContent("Humour caustique et ironie fine", "Wry humor and fine irony", "ee-roh-NEE", "Apprécier l'ironie subtile et les jeux de mots.", "Appreciating subtle irony and wordplay.", "Mastery over culturally bound humor.")
                "Advanced Debates" -> LessonContent("Éloquence oratoire", "Oratorical eloquence", "ay-loh-KAHNS", "Défendre une cause avec une persuasion irrésistible.", "Defending a cause with irresistible persuasion.", "Commanding rhetoric.")
                "Professional Fluency" -> LessonContent("Leadership exécutif", "Executive leadership", "ehg-zay-koo-TEEF", "Animer un conseil d'administration en français.", "Leading a board of directors meeting in French.", "Flawless international business French.")
                "Native-Speed Listening" -> LessonContent("Compréhension intégrale instantanée", "Instant comprehensive hearing", "kohm-pray-ahn-SYOHN", "Comprendre toutes les allusions en temps réel.", "Catching every cultural allusion in real time.", "Full acoustic mastery.")
                else -> LessonContent("Immersion bilingue complète", "Complete bilingual immersion", "ee-mehr-SYOHN", "Vivre et penser en français avec une parfaite aisance.", "Living and thinking in French with complete natural grace.", "Total fluency.")
            }
        }
    }






    // ================= DEFAULT CURRICULUM =================
    private fun getDefaultContent(langId: String, level: Int, topic: String): LessonContent {
        return LessonContent(
            word = "$topic ($langId)",
            meaning = "Core study of $topic",
            pronunciation = topic.lowercase(),
            exampleSentence = "Learning $topic in $langId expands your proficiency.",
            exampleTranslation = "Mastering this module elevates your overall language capability.",
            tip = "Consistent daily review reinforces vocabulary retention."
        )
    }
}
