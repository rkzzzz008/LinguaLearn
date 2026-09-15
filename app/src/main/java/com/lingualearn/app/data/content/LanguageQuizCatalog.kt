package com.lingualearn.app.data.content

import com.lingualearn.app.model.QuizQuestion

object LanguageQuizCatalog {

    val quizPool: List<QuizQuestion> = listOf(
        // Spanish (es)
        QuizQuestion("q_es_1", "es", "What does 'Gracias' mean in English?", listOf("Please", "Thank you", "Good night", "Excuse me"), 1, "'Gracias' translates to 'Thank you'."),
        QuizQuestion("q_es_2", "es", "Which verb is used for temporary states or locations in Spanish?", listOf("Ser", "Hacer", "Estar", "Tener"), 2, "'Estar' is used for temporary states, emotions, and locations."),
        QuizQuestion("q_es_3", "es", "How do you say 'Good morning' in Spanish?", listOf("Buenas noches", "Buenos días", "Hasta luego", "Por favor"), 1, "'Buenos días' means 'Good morning'."),
        QuizQuestion("q_es_4", "es", "What does 'Agua' mean?", listOf("Fire", "Bread", "Water", "Sky"), 2, "'Agua' means water."),
        QuizQuestion("q_es_5", "es", "How do you say 'How much does it cost?' in Spanish?", listOf("¿Dónde está?", "¿Cómo te llamas?", "¿Cuánto cuesta?", "¿Qué hora es?"), 2, "'¿Cuánto cuesta?' asks for price."),
        QuizQuestion("q_es_6", "es", "What is the Spanish word for 'Friend'?", listOf("Hermano", "Amigo", "Vecino", "Maestro"), 1, "'Amigo' means friend."),

        // French (fr)
        QuizQuestion("q_fr_1", "fr", "How do you say 'Please' formally in French?", listOf("Merci", "Bonjour", "S'il vous plaît", "Au revoir"), 2, "'S'il vous plaît' is the formal way to say 'Please'."),
        QuizQuestion("q_fr_2", "fr", "What does 'Gare' mean in French?", listOf("Airport", "Train station", "Library", "Market"), 1, "'Gare' translates to train station."),
        QuizQuestion("q_fr_3", "fr", "Which definite article is used for feminine singular nouns?", listOf("Le", "La", "Les", "Un"), 1, "'La' is feminine singular definite article."),
        QuizQuestion("q_fr_4", "fr", "What is 'Une baguette'?", listOf("A hat", "A traditional French bread", "A train ticket", "A musical flute"), 1, "A baguette is the iconic long French bread."),
        QuizQuestion("q_fr_5", "fr", "How do you say 'Thank you very much' in French?", listOf("Merci beaucoup", "De rien", "Bonne nuit", "À bientôt"), 0, "'Merci beaucoup' means 'Thank you very much'."),
        QuizQuestion("q_fr_6", "fr", "What does 'Bonjour' mean?", listOf("Good evening", "Good morning / Hello", "Goodbye", "Please"), 1, "'Bonjour' means 'Good morning' or 'Hello'."),

        // German (de)
        QuizQuestion("q_de_1", "de", "What is the polite German greeting for 'Good day'?", listOf("Gute Nacht", "Guten Tag", "Auf Wiedersehen", "Tschüss"), 1, "'Guten Tag' means 'Good day'."),
        QuizQuestion("q_de_2", "de", "How many grammatical genders are there in German?", listOf("One", "Two", "Three (Der, Die, Das)", "Four"), 2, "German features masculine, feminine, and neuter."),
        QuizQuestion("q_de_3", "de", "What does 'Danke schön' mean?", listOf("You are welcome", "Thank you very much", "Excuse me", "Good luck"), 1, "'Danke schön' means 'Thank you very much'."),
        QuizQuestion("q_de_4", "de", "What is 'Die Brezel'?", listOf("A sausage", "A pretzel", "A cold beverage", "A mustard sauce"), 1, "'Die Brezel' is a pretzel."),
        QuizQuestion("q_de_5", "de", "What does 'Bahnhof' mean?", listOf("Library", "Airport", "Train station", "City hall"), 2, "'Bahnhof' translates to train station."),
        QuizQuestion("q_de_6", "de", "How do you count 1, 2, 3 in German?", listOf("Uno, Dos, Tres", "Un, Deux, Trois", "Eins, Zwei, Drei", "Bir, İki, Üç"), 2, "'Eins, Zwei, Drei' is 1, 2, 3 in German."),

        // Japanese (ja)
        QuizQuestion("q_ja_1", "ja", "What does 'ありがとう' (Arigatou) mean?", listOf("Hello", "Goodbye", "Thank you", "Delicious"), 2, "'Arigatou' translates to 'Thank you'."),
        QuizQuestion("q_ja_2", "ja", "What flower does 'Sakura' (桜) refer to?", listOf("Rose", "Sunflower", "Cherry Blossom", "Lotus"), 2, "'Sakura' is Japan's revered cherry blossom."),
        QuizQuestion("q_ja_3", "ja", "What is said before starting a meal in Japan?", listOf("Sayonara", "Itadakimasu", "Konnichiwa", "Oyasumi"), 1, "'Itadakimasu' is said before eating with gratitude."),
        QuizQuestion("q_ja_4", "ja", "What is the high-speed bullet train called in Japan?", listOf("Shinkansen", "Metro", "Torii", "Kabuki"), 0, "The high-speed bullet train is the 'Shinkansen'."),
        QuizQuestion("q_ja_5", "ja", "How do you count 1, 2, 3 in Japanese?", listOf("Ichi, Ni, San", "Hana, Dul, Set", "Yī, Èr, Sān", "Ek, Do, Teen"), 0, "'Ichi, Ni, San' means 1, 2, 3 in Japanese."),
        QuizQuestion("q_ja_6", "ja", "What is 'Konnichiwa'?", listOf("Good night", "Hello / Good afternoon", "Delicious", "Excuse me"), 1, "'Konnichiwa' is the standard polite daytime greeting."),

        // Korean (ko)
        QuizQuestion("q_ko_1", "ko", "How do you say 'Hello' in polite Korean?", listOf("감사합니다", "안녕하세요", "잘 가요", "미안해요"), 1, "'안녕하세요' (Annyeonghaseyo) is polite hello."),
        QuizQuestion("q_ko_2", "ko", "What does '친구' (Chingu) mean in Korean?", listOf("Teacher", "Friend", "Book", "Music"), 1, "'친구' means friend."),
        QuizQuestion("q_ko_3", "ko", "What is the Korean writing system called?", listOf("Kanji", "Hangul", "Devanagari", "Cyrillic"), 1, "Hangul was created by King Sejong the Great in 1443."),
        QuizQuestion("q_ko_4", "ko", "What does '감사합니다' (Gamsahamnida) mean?", listOf("Excuse me", "I am sorry", "Thank you", "Goodbye"), 2, "'감사합니다' means 'Thank you'."),
        QuizQuestion("q_ko_5", "ko", "What does '맛있어요' (Masisseoyo) mean?", listOf("It is spicy", "It is delicious", "It is cold", "It is expensive"), 1, "'맛있어요' means delicious."),
        QuizQuestion("q_ko_6", "ko", "How do you say 1, 2, 3 in Sino-Korean?", listOf("Il, I, Sam", "Ichi, Ni, San", "Ek, Do, Teen", "Bir, Iki, Uc"), 0, "'Il, I, Sam' is 1, 2, 3 in Sino-Korean."),

        // English (en)
        QuizQuestion("q_en_1", "en", "What does 'Serendipity' mean?", listOf("Extreme grief", "Finding pleasant things by chance", "Hard labor", "A wild storm"), 1, "'Serendipity' is finding fortunate things unexpectedly."),
        QuizQuestion("q_en_2", "en", "What does the idiom 'Piece of cake' signify?", listOf("A sweet dessert", "Something very easy to do", "A complicated math puzzle", "A birthday party"), 1, "'Piece of cake' means very easy."),
        QuizQuestion("q_en_3", "en", "Which word is an antonym of 'Ancient'?", listOf("Historic", "Modern", "Classic", "Antique"), 1, "'Modern' is the opposite of 'Ancient'."),
        QuizQuestion("q_en_4", "en", "Which tense is used in 'I have lived in London for three years'?", listOf("Simple Past", "Present Perfect", "Past Continuous", "Future Simple"), 1, "It is the Present Perfect tense."),
        QuizQuestion("q_en_5", "en", "What is a polite way to accept gratitude?", listOf("No problem / You are welcome", "Never mind", "Forget it", "Stop talking"), 0, "'You are welcome' is the courteous response."),

        // Italian (it)
        QuizQuestion("q_it_1", "it", "What does 'Grazie mille' mean in English?", listOf("Good morning", "A thousand thanks", "Please leave", "See you tomorrow"), 1, "'Grazie mille' translates to 'A thousand thanks'."),
        QuizQuestion("q_it_2", "it", "When is cappuccino traditionally enjoyed in Italy?", listOf("Late at night", "In the morning", "After heavy dinner", "Never"), 1, "Italians traditionally enjoy cappuccino during morning breakfast."),
        QuizQuestion("q_it_3", "it", "What does 'Ciao' mean?", listOf("Only hello", "Both hello and goodbye", "Only thank you", "Good night"), 1, "'Ciao' is friendly for both hello and goodbye."),
        QuizQuestion("q_it_4", "it", "What does 'Dov'è' mean in Italian?", listOf("Who is", "Where is", "How much is", "Why is"), 1, "'Dov'è' means 'Where is'."),
        QuizQuestion("q_it_5", "it", "How do you count 1, 2, 3 in Italian?", listOf("Uno, Due, Tre", "Un, Deux, Trois", "Eins, Zwei, Drei", "Bir, İki, Üç"), 0, "'Uno, Due, Tre' is 1, 2, 3 in Italian."),

        // Portuguese (pt)
        QuizQuestion("q_pt_1", "pt", "How does a female speaker say 'Thank you' in Portuguese?", listOf("Obrigado", "Obrigada", "Por favor", "De nada"), 1, "Female speakers say 'Obrigada', male speakers say 'Obrigado'."),
        QuizQuestion("q_pt_2", "pt", "What does the famous word 'Saudade' convey?", listOf("Sudden anger", "Deep nostalgic longing", "Great joy", "Confusion"), 1, "'Saudade' describes loving longing for someone or something distant."),
        QuizQuestion("q_pt_3", "pt", "What does 'Tudo bem?' mean in Portuguese?", listOf("Where are you?", "Is everything good?", "What time is it?", "How much is this?"), 1, "'Tudo bem?' is the universal friendly greeting check-in."),
        QuizQuestion("q_pt_4", "pt", "What is 'Pastel de nata'?", listOf("A spicy meat pie", "A traditional custard tart", "A cold lemonade", "A sweet bread loaf"), 1, "It is Portugal's world-famous egg custard tart pastry."),
        QuizQuestion("q_pt_5", "pt", "How do you say 'Good morning' in Portuguese?", listOf("Bom dia", "Boa tarde", "Boa noite", "Tchau"), 0, "'Bom dia' means 'Good morning'."),

        // Russian (ru)
        QuizQuestion("q_ru_1", "ru", "What does 'Спасибо' (Spasibo) mean?", listOf("Please", "Thank you", "Goodbye", "Excuse me"), 1, "'Спасибо' translates to 'Thank you' in Russian."),
        QuizQuestion("q_ru_2", "ru", "Which alphabet is used to write Russian?", listOf("Latin", "Greek", "Cyrillic", "Devanagari"), 2, "Russian is written in the Cyrillic script."),
        QuizQuestion("q_ru_3", "ru", "What is a traditional Russian tea urn called?", listOf("Borscht", "Samovar", "Matryoshka", "Balalaika"), 1, "A 'Samovar' is the heated metal vessel used to boil tea water."),
        QuizQuestion("q_ru_4", "ru", "What does 'Здравствуйте' (Zdravstvuyte) mean?", listOf("Good night", "Formal Hello / Be in good health", "Thank you", "Delicious"), 1, "It is the polite, formal greeting meaning 'Be healthy'."),
        QuizQuestion("q_ru_5", "ru", "How do you say 1, 2, 3 in Russian?", listOf("Odin, Dva, Tri", "Uno, Dos, Tres", "Eins, Zwei, Drei", "Il, I, Sam"), 0, "'Один, Два, Три' (Odin, Dva, Tri) is 1, 2, 3 in Russian."),

        // Mandarin Chinese (zh)
        QuizQuestion("q_zh_1", "zh", "What does '你好' (Nǐ hǎo) mean?", listOf("Goodbye", "Hello", "Thank you", "How much?"), 1, "'你好' (Nǐ hǎo) means 'Hello' in Mandarin."),
        QuizQuestion("q_zh_2", "zh", "How many standard tones are there in Mandarin Chinese?", listOf("Two", "Three", "Four (plus neutral)", "Eight"), 2, "Mandarin has four distinct tones plus a light neutral tone."),
        QuizQuestion("q_zh_3", "zh", "What does '谢谢' (Xièxie) mean?", listOf("Please", "Thank you", "You are welcome", "Excuse me"), 1, "'谢谢' (Xièxie) translates to 'Thank you'."),
        QuizQuestion("q_zh_4", "zh", "What does the encouraging cheer '加油' (Jiāyóu) literally mean?", listOf("Drink tea", "Add oil / Add fuel", "Good morning", "Walk quickly"), 1, "It literally means 'add oil/fuel', used to cheer 'keep going!'."),
        QuizQuestion("q_zh_5", "zh", "How do you count 1, 2, 3 in Mandarin?", listOf("Yī, Èr, Sān", "Ichi, Ni, San", "Il, I, Sam", "Ek, Do, Teen"), 0, "'Yī, Èr, Sān' is 1, 2, 3 in Mandarin Chinese."),

        // Hindi (hi)
        QuizQuestion("q_hi_1", "hi", "What does the greeting 'नमस्ते' (Namaste) express?", listOf("Goodbye forever", "I bow to the divine spirit in you", "Hurry up", "Be quiet"), 1, "'Namaste' is a respectful greeting recognizing the inner light."),
        QuizQuestion("q_hi_2", "hi", "Which script is used to write Hindi?", listOf("Devanagari", "Perso-Arabic", "Gurmukhi", "Tamil"), 0, "Hindi is written in the historic Devanagari script."),
        QuizQuestion("q_hi_3", "hi", "What does 'धन्यवाद' (Dhanyawad) mean?", listOf("Please", "Thank you", "Welcome", "Excuse me"), 1, "'धन्यवाद' translates to 'Thank you' in Hindi."),
        QuizQuestion("q_hi_4", "hi", "How do you count 1, 2, 3 in Hindi?", listOf("Ek, Do, Teen", "Ichi, Ni, San", "Uno, Dos, Tres", "Bir, Iki, Uc"), 0, "'Ek, Do, Teen' is 1, 2, 3 in Hindi."),
        QuizQuestion("q_hi_5", "hi", "What does the cultural philosophy 'अतिथि देवो भव' teach?", listOf("Work is worship", "The guest is equivalent to God", "Knowledge is power", "Time is precious"), 1, "It establishes sacred hospitality toward guests."),

        // Tamil (ta)
        QuizQuestion("q_ta_1", "ta", "What is the classical greeting in Tamil?", listOf("Namaskaram", "Vanakkam", "Khuda Hafiz", "Sat Sri Akal"), 1, "'வணக்கம்' (Vanakkam) is the revered greeting in Tamil."),
        QuizQuestion("q_ta_2", "ta", "What does 'மிக்க நன்றி' (Mikka Nandri) mean?", listOf("Good morning", "Many thanks", "Safe journey", "Delicious feast"), 1, "'மிக்க நன்றி' means 'Many thanks' in Tamil."),
        QuizQuestion("q_ta_3", "ta", "What is the ancient Tamil ethical work containing 1,330 couplets?", listOf("Ramayana", "Thirukkural", "Mahabharata", "Silappatikaram"), 1, "The Thirukkural by sage Thiruvalluvar is a pinnacle of ethics."),
        QuizQuestion("q_ta_4", "ta", "How do you count 1, 2, 3 in Tamil?", listOf("Onru, Irandu, Moonru", "Ek, Do, Teen", "Il, I, Sam", "Uno, Dos, Tres"), 0, "'ஒன்று, இரண்டு, மூன்று' is 1, 2, 3 in Tamil."),
        QuizQuestion("q_ta_5", "ta", "What does 'அன்பு' (Anbu) mean in Tamil?", listOf("Anger", "Love / Kindness", "Fear", "Wealth"), 1, "'அன்பு' means love, kindness, and deep affection."),

        // Telugu (te)
        QuizQuestion("q_te_1", "te", "What does 'ధన్యవాదాలు' (Dhanyavadalu) mean?", listOf("Please", "Thank you", "Good night", "Excuse me"), 1, "'ధన్యవాదాలు' means 'Thank you' in Telugu."),
        QuizQuestion("q_te_2", "te", "Why was Telugu called 'The Italian of the East'?", listOf("It originated in Rome", "Nearly all words end in sweet vowels", "It has Roman letters", "It is spoken in Venice"), 1, "Italian explorer Niccolò de' Conti noted its sweet vowel endings."),
        QuizQuestion("q_te_3", "te", "How do you say 'Hello' in Telugu?", listOf("Vanakkam", "Namaskaram", "Konnichiwa", "Salam"), 1, "'నమస్కారం' (Namaskaram) is the polite greeting."),
        QuizQuestion("q_te_4", "te", "What does 'చాలా బాగుంది' (Chala baagundi) mean?", listOf("It is very late", "It is very good / delicious", "It is raining", "It is very loud"), 1, "'చాలా బాగుంది' expresses that something is very good or delicious."),
        QuizQuestion("q_te_5", "te", "How do you count 1, 2, 3 in Telugu?", listOf("Okati, Rendu, Moodu", "Ek, Do, Teen", "Onru, Irandu, Moonru", "Eins, Zwei, Drei"), 0, "'ఒకటి, రెండు, మూడు' is 1, 2, 3 in Telugu."),

        // Malayalam (ml)
        QuizQuestion("q_ml_1", "ml", "What does 'വളരെ നന്ദി' (Valare Nandi) mean in Malayalam?", listOf("Good morning", "Thank you very much", "Come quickly", "Sleep well"), 1, "'വളരെ നന്ദി' means 'Thank you very much' in Malayalam."),
        QuizQuestion("q_ml_2", "ml", "What is the traditional feast served on a banana leaf in Kerala?", listOf("Biryani", "Sadya", "Dosa", "Ramen"), 1, "'Sadya' is Kerala's traditional vegetarian festival banquet."),
        QuizQuestion("q_ml_3", "ml", "How do you greet someone in Malayalam?", listOf("Vanakkam", "Namaskaram", "Merhaba", "Shalom"), 1, "'നമസ്കാരം' (Namaskaram) is the warm respectful greeting."),
        QuizQuestion("q_ml_4", "ml", "How do you count 1, 2, 3 in Malayalam?", listOf("Onnu, Randu, Moonnu", "Okati, Rendu, Moodu", "Ek, Do, Teen", "Yī, Èr, Sān"), 0, "'ഒന്ന്, രണ്ട്, മൂന്ന്' is 1, 2, 3 in Malayalam."),
        QuizQuestion("q_ml_5", "ml", "What does 'സന്തോഷം' (Santhosham) mean?", listOf("Happiness / Joy", "Sadness", "Speed", "Doubt"), 0, "'സന്തോഷം' means joy and happiness."),

        // Turkish (tr)
        QuizQuestion("q_tr_1", "tr", "What does the thoughtful Turkish wish 'Kolay gelsin' mean?", listOf("Have sweet dreams", "May your work come easy", "Good appetite", "Safe travels"), 1, "'Kolay gelsin' is warmly said to anyone working or studying."),
        QuizQuestion("q_tr_2", "tr", "What does 'Teşekkür ederim' mean in Turkish?", listOf("Please", "Thank you", "Welcome", "Excuse me"), 1, "'Teşekkür ederim' translates to 'Thank you'."),
        QuizQuestion("q_tr_3", "tr", "What is the circular sesame bread ring beloved in Turkey?", listOf("Baguette", "Simit", "Pretzel", "Naan"), 1, "'Simit' is the iconic Turkish sesame bread."),
        QuizQuestion("q_tr_4", "tr", "How do you say 'Hello' in Turkish?", listOf("Merhaba", "Ciao", "Hola", "Konnichiwa"), 0, "'Merhaba' means 'Hello' in Turkish."),
        QuizQuestion("q_tr_5", "tr", "How do you count 1, 2, 3 in Turkish?", listOf("Bir, İki, Üç", "Uno, Due, Tre", "Eins, Zwei, Drei", "Ek, Do, Teen"), 0, "'Bir, İki, Üç' is 1, 2, 3 in Turkish."),

        // Dutch (nl)
        QuizQuestion("q_nl_1", "nl", "What does the untranslatable Dutch word 'Gezellig' describe?", listOf("Severe cold weather", "Cozy, warm, friendly atmosphere", "Fast highway driving", "A tall skyscraper"), 1, "'Gezellig' encapsulates cozy comfort and friendly togetherness."),
        QuizQuestion("q_nl_2", "nl", "What does 'Dank je wel' mean in Dutch?", listOf("Please", "Thank you very much", "Good evening", "See you later"), 1, "'Dank je wel' translates to 'Thank you very much'."),
        QuizQuestion("q_nl_3", "nl", "What is a 'Stroopwafel'?", listOf("A savory cheese ball", "A thin waffle filled with caramel syrup", "A dark beer", "A wooden shoe"), 1, "A stroopwafel is a beloved Dutch caramel waffle delicacy."),
        QuizQuestion("q_nl_4", "nl", "How do you say 'Good morning' in Dutch?", listOf("Goedemorgen", "Guten Tag", "Bonjour", "God morgon"), 0, "'Goedemorgen' means 'Good morning' in Dutch."),
        QuizQuestion("q_nl_5", "nl", "How do you count 1, 2, 3 in Dutch?", listOf("Een, Twee, Drie", "Eins, Zwei, Drei", "One, Two, Three", "Ett, Två, Tre"), 0, "'Een, Twee, Drie' is 1, 2, 3 in Dutch."),

        // Swedish (sv)
        QuizQuestion("q_sv_1", "sv", "What is the cherished Swedish social ritual of 'Fika'?", listOf("A sauna session", "Coffee break with sweet pastry and chat", "Skiing race", "Midsummer dance"), 1, "'Fika' is the essential Swedish coffee and sweet treat social pause."),
        QuizQuestion("q_sv_2", "sv", "What does 'Tack så mycket' mean in Swedish?", listOf("Excuse me", "Thank you very much", "Good night", "You're welcome"), 1, "'Tack så mycket' means 'Thank you very much'."),
        QuizQuestion("q_sv_3", "sv", "What does the cultural philosophy of 'Lagom' mean?", listOf("Maximum luxury", "Not too little, not too much; just right", "Extreme speed", "Complete silence"), 1, "'Lagom' signifies balanced moderation and harmony in all things."),
        QuizQuestion("q_sv_4", "sv", "What is 'Kanelbulle'?", listOf("A Swedish cinnamon bun", "Smoked salmon", "A hot lingonberry juice", "A chocolate cake"), 0, "Kanelbulle is Sweden's national cinnamon and cardamom bun."),
        QuizQuestion("q_sv_5", "sv", "How do you count 1, 2, 3 in Swedish?", listOf("Ett, Två, Tre", "Een, Twee, Drie", "Eins, Zwei, Drei", "Uno, Dos, Tres"), 0, "'Ett, Två, Tre' is 1, 2, 3 in Swedish."),

        // Arabic (ar)
        QuizQuestion("q_ar_1", "ar", "What does 'شكراً' (Shukran) mean in Arabic?", listOf("Please", "Thank you", "Goodbye", "Welcome"), 1, "'شكراً' (Shukran) is Arabic for 'Thank you'."),
        QuizQuestion("q_ar_2", "ar", "What is the traditional greeting meaning 'Peace be upon you'?", listOf("Marhaban", "As-salamu alaykum", "Sabah al-khair", "Ahlan wa sahlan"), 1, "'السلام عليكم' (As-salamu alaykum) means peace be upon you."),
        QuizQuestion("q_ar_3", "ar", "In which direction is Arabic text written?", listOf("Left-to-right", "Right-to-left", "Top-to-bottom", "Circular"), 1, "Arabic script is written gracefully from right to left."),
        QuizQuestion("q_ar_4", "ar", "What spiced beverage is traditionally served to guests with dates?", listOf("Mint Lemonade", "Arabic Coffee (Qahwa)", "Iced hibiscus tea", "Sweet lassi"), 1, "Cardamom-infused Qahwa served with sweet dates is the hallmark of hospitality."),
        QuizQuestion("q_ar_5", "ar", "How do you count 1, 2, 3 in Arabic?", listOf("Wahid, Ithnan, Thalatha", "Ek, Do, Teen", "Bir, Iki, Uc", "Onru, Irandu, Moonru"), 0, "'واحد، اثنان، ثلاثة' is 1, 2, 3 in Arabic.")
    )
}
