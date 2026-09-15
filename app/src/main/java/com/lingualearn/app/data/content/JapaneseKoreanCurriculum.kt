package com.lingualearn.app.data.content

/**
 * Japanese (ja) and Korean (ko) comprehensive curriculum content.
 * 10 levels × 6 topics = 60 distinct lessons per language.
 */
object JapaneseCurriculum {
    fun getContent(level: Int, topic: String): CurriculumLessonContent {
        return when (level) {
            1 -> when (topic) {
                "Greetings" -> CurriculumLessonContent("こんにちは / おはようございます", "Hello / Good morning", "kohn-nee-chee-wah / oh-hah-yoh goh-zye-mahs", "おはようございます、今日も一日頑張りましょう。", "Good morning, let's do our best today as well.", "Bow slightly when greeting to show respect.")
                "Introducing Yourself" -> CurriculumLessonContent("はじめまして、田中と申します", "Nice to meet you, I am Tanaka", "hah-jee-meh-mash-teh, tah-nah-kah toh moh-she-mahs", "はじめまして、アメリカから来ました。", "Nice to meet you, I came from America.", "'~to mōshimasu' is the humble form of self-introduction.")
                "Yes / No / Basic Responses" -> CurriculumLessonContent("はい / いいえ / お願いします", "Yes / No / Please", "hye / ee-eh / oh-neh-gye-she-mahs", "はい、これをお願いします。", "Yes, this one please.", "'Onegaishimasu' is essential when requesting anything.")
                "Numbers 1–10" -> CurriculumLessonContent("いち、に、さん、し、ご...", "1 to 10 numerals", "ee-chee, nee, sahn, shee, goh", "切符を二枚ください。", "Two tickets, please.", "Counters in Japanese change depending on object shape.")
                "Basic Pronouns" -> CurriculumLessonContent("わたし、あなた、かれ、かのじょ", "I, you, he, she", "wah-tah-shee, ah-nah-tah, kah-reh, kah-noh-joh", "私たちは日本語を熱心に勉強しています。", "We are studying Japanese enthusiastically.", "Pronouns are often omitted when context is understood.")
                else -> CurriculumLessonContent("すみません と ありがとう", "Excuse me and Thank you", "soo-mee-mah-sen / ah-ree-gah-toh", "すみません、お会計をお願いします。", "Excuse me, check please.", "'Sumimasen' works for excuse me, sorry, and thank you.")
            }
            2 -> when (topic) {
                "Family" -> CurriculumLessonContent("家族（父、母、兄、妹）", "Family (father, mother, brother, sister)", "kah-zoh-koo", "私の兄は東京の大学に通っています。", "My older brother attends university in Tokyo.", "Use 'ani/chichi' for your family, 'oniisan/otousan' for others'.")
                "Colors" -> CurriculumLessonContent("赤、青、黄色、白、黒", "Red, Blue, Yellow, White, Black", "ah-kah, ah-oh, kee-ee-roh, shee-roh, koo-roh", "あの青い傘はどなたのものですか？", "Whose blue umbrella is that over there?", "Colors ending in -i function as true adjectives: 'aoi'.")
                "Food" -> CurriculumLessonContent("美味しいお寿司とラーメン", "Delicious sushi and ramen", "oy-shee oh-soo-shee", "いただきます！このラーメンは絶品です。", "Thank you for the meal! This ramen is exquisite.", "Say 'Itadakimasu' before eating and 'Gochisousama' after.")
                "Days & Months" -> CurriculumLessonContent("月曜日から日曜日", "Monday to Sunday", "getsoo-yoh-bee kah-rah nee-chee-yoh-bee", "今週の金曜日に映画を見に行きませんか？", "Would you like to go watch a movie this Friday?", "Days are named after celestial elements (Sun, Moon, Fire, Water).")
                "Simple Questions" -> CurriculumLessonContent("だれ？ なに？ どこ？ いつ？", "Who? What? Where? When?", "dah-reh, nah-nee, doh-koh, eet-soo", "郵便局はどこにありますか？", "Where is the post office located?", "Questions end with the question particle 'ka' (か).")
                else -> CurriculumLessonContent("食べる、飲む、行く、来る", "Eat, drink, go, come", "tah-beh-roo, noh-moo, ee-koo, koo-roo", "友達とカフェへ行って緑茶を飲みます。", "I go to a café with a friend and drink green tea.", "Japanese verbs conjugate into polite '~masu' forms.")
            }
            3 -> when (topic) {
                "Daily Routine" -> CurriculumLessonContent("朝七時に起きます", "I get up at 7 AM", "ah-sah shee-chee-jee nee oh-kee-mahs", "毎朝七時に起きてジョギングをします。", "Every morning I wake up at 7 and go jogging.", "The te-form (起きて) connects sequential actions.")
                "Shopping" -> CurriculumLessonContent("これはいくらですか？", "How much is this?", "koh-reh wah ee-koo-rah des kah", "すみません、この白いシャツはいくらですか？", "Excuse me, how much is this white shirt?", "Handy phrase in department stores and souvenir shops.")
                "Time" -> CurriculumLessonContent("いま何時ですか？", "What time is it now?", "ee-mah nahn-jee des kah", "いま午後三時半です。", "It is 3:30 PM right now.", "'Han' (半) signifies half past the hour.")
                "Directions" -> CurriculumLessonContent("まっすぐ行って、右に曲がります", "Go straight and turn right", "mahs-soo-goo eet-teh, mee-gee nee mah-gah-ree-mahs", "交差点を左に曲がると駅が見えます。", "Turn left at the intersection and you'll see the station.", "'Migi' is right, 'hidari' is left.")
                "Simple Sentences" -> CurriculumLessonContent("本を読みたいです", "I want to read a book", "hohn oh yoh-mee-tye des", "週末は家で静かに小説を読みたいです。", "On the weekend I want to quietly read a novel at home.", "Attach '~tai' to verb stems to express desire.")
                else -> CurriculumLessonContent("最近どうですか？", "How have you been lately?", "sye-keen doh des kah", "元気ですよ、仕事も順調です。", "I'm doing well, work is going smoothly too.", "Natural friendly check-in with acquaintances.")
            }
            4 -> when (topic) {
                "Past Events" -> CurriculumLessonContent("昨日京都へ行きました", "Yesterday I went to Kyoto", "kee-noh kyoh-toh eh ee-kee-mahsh-tah", "昨日は有名な金閣寺を観光しました。", "Yesterday I toured the famous Golden Pavilion.", "Past tense of polite verbs ends in '~mashita'.")
                "Future Plans" -> CurriculumLessonContent("来月富士山に登る予定です", "I plan to climb Mt. Fuji next month", "rye-geh-tsoo foo-jee-sahn nee noh-boh-roo yoh-tey des", "夏休みに北海道へ旅行するつもりです。", "I intend to travel to Hokkaido during summer vacation.", "'~tsumori desu' expresses future intention.")
                "Travel" -> CurriculumLessonContent("新幹線の指定席切符", "Shinkansen reserved-seat ticket", "sheen-kahn-sehn noh shee-tey-seh-kee", "新大阪行きの新幹線は何番線から出ますか？", "Which track does the bullet train to Shin-Osaka depart from?", "Shinkansen offers reserved (shiteiseki) and non-reserved cars.")
                "Restaurants" -> CurriculumLessonContent("ご注文はお決まりですか？", "Are you ready to order?", "goh-choo-mohn wah oh-kee-mah-ree des kah", "日替わり定食を一つお願いします。", "One daily special set meal, please.", "'Teishoku' (定食) is a balanced traditional set meal.")
                "Describing People" -> CurriculumLessonContent("背が高くて親切な人", "A tall and kind person", "seh gah tah-kah-koo-teh sheen-seh-tsoo nah", "新しい先生は親切で教え方がとても上手です。", "The new teacher is kind and very good at teaching.", "Use '~kute' to link i-adjectives and '~de' for na-adjectives.")
                else -> CurriculumLessonContent("今日はいいお天気ですね", "Nice weather today, isn't it?", "kyoh wah ee oh-ten-kee des neh", "ぽかぽかして散歩日和ですね。", "It's pleasantly mild, perfect for a walk.", "'Ne' at the end invites agreement from the listener.")
            }
            5 -> when (topic) {
                "Longer Conversations" -> CurriculumLessonContent("少し伺ってもよろしいですか？", "May I ask you something briefly?", "soo-koh-shee oo-kah-gaht-teh", "企画についてご意見を伺ってもよろしいですか？", "May I ask for your opinion regarding the project plan?", "Humble expression 'ukagau' demonstrates polite etiquette.")
                "Opinions" -> CurriculumLessonContent("私の考えでは...", "In my opinion...", "wah-tah-shee noh kahn-gah-eh deh-wah", "私の考えでは、環境保護が最優先課題です。", "In my view, environmental conservation is the top priority.", "'~to omoimasu' (I think that...) softens personal assertions.")
                "Experiences" -> CurriculumLessonContent("日本へ行ったことがあります", "I have been to Japan before", "nee-hohn eh eet-tah koh-toh gah ah-ree-mahs", "伝統的な旅館に泊まったことがあります。", "I have stayed at a traditional Japanese inn before.", "'Ta-form + koto ga aru' expresses past life experience.")
                "Comparisons" -> CurriculumLessonContent("東京は京都より賑やかです", "Tokyo is livelier than Kyoto", "toh-kyoh wah kyoh-toh yoh-ree", "電車はバスより速くて便利です。", "Trains are faster and more convenient than buses.", "Pattern: A wa B yori [adjective] desu.")
                "More Complex Grammar" -> CurriculumLessonContent("〜たら / 〜ば (条件表現)", "Conditional forms (If / When)", "tah-rah / bah", "雨が降ったら、美術館に行きましょう。", "If it rains, let's go to the art museum.", "'~tara' conditional is versatile for both conditions and timing.")
                else -> CurriculumLessonContent("日本語のリスニング練習", "Japanese listening practice", "ree-soo-neen-goo ren-shoo", "NHKのニュースを字幕なしで聴く練習をします。", "I practice listening to NHK news without subtitles.", "Pay attention to pitch accent differences in spoken Japanese.")
            }
            6 -> when (topic) {
                "Storytelling" -> CurriculumLessonContent("昔々、あるところに...", "Long, long ago, in a certain place...", "moo-kah-shee moo-kah-shee", "昔々、心優しいおじいさんが住んでいました。", "Long, long ago, there lived a kind-hearted old man.", "The classic opening line for traditional Japanese folklore.")
                "Workplace Conversations" -> CurriculumLessonContent("お疲れ様です / 恐れ入ります", "Thank you for your hard work / Excuse me", "oh-tsoo-kah-reh-sah-mah des", "本日の会議資料をメールで送付いたしました。", "I have sent today's meeting materials via email.", "'Otsukaresama desu' acknowledges team dedication daily.")
                "Social Situations" -> CurriculumLessonContent("お招きいただきありがとうございます", "Thank you very much for having me", "oh-mah-neh-kee ee-tah-dah-kee", "心のこもったおもてなしに深く感謝します。", "I am deeply grateful for your heartfelt hospitality.", "'Omotenashi' embodies selfless Japanese hospitality.")
                "News & Media" -> CurriculumLessonContent("経済ニュースの見出し", "Economic news headlines", "kay-zye nyoo-soo", "新聞は新技術の開発と市場の動向を報じています。", "The paper reports on new tech development and market trends.", "Written Japanese news uses formal concise styles.")
                "Idiomatic Expressions" -> CurriculumLessonContent("猫の手も借りたい", "Extremely busy (would even borrow a cat's paw)", "neh-koh noh teh moh kah-ree-tye", "年末の繁忙期で、猫の手も借りたい忙しさです。", "During year-end peak season, we are impossibly busy.", "Vivid Japanese idiom for being overwhelmed with work.")
                else -> CurriculumLessonContent("使役表現と受身表現", "Causative and Passive voice", "shee-eh-kee / oo-keh-mee", "先生に論文を添削していただきました。", "I had my thesis reviewed and corrected by the professor.", "Giving and receiving verbs (ageru, morau, kureru) show nuance.")
            }
            7 -> when (topic) {
                "Complex Conversations" -> CurriculumLessonContent("少子高齢化社会の課題", "Challenges of the aging society", "shoh-shee koh-rey-kah", "持続可能な社会保障制度の構築が求められています。", "The construction of a sustainable social security system is demanded.", "Discuss high-register societal issues in formal Japanese.")
                "Debate & Opinions" -> CurriculumLessonContent("一概には賛同しかねます", "I cannot unconditionally agree", "ee-chee-gye nee-wah sahn-doh shee-kah-neh-mahs", "そのご指摘はもっともですが、別の側面も考慮すべきです。", "That point is valid, but other aspects must also be weighed.", "'~kane-masu' politely expresses inability or disagreement.")
                "Abstract Topics" -> CurriculumLessonContent("侘び寂び（わびさび）の美意識", "Wabi-sabi aesthetic of impermanence", "wah-bee sah-bee", "不完全さや移ろいゆくものの中に美を見出す精神。", "The spirit of finding beauty in imperfection and impermanence.", "A foundational philosophical concept of traditional arts.")
                "Natural Expressions" -> CurriculumLessonContent("口が重い / 目がない", "Taciturn / To have a huge weakness for something", "koo-chee gah oh-moy / meh gah nye", "彼は甘いものに目がなく、いつも大喜びします。", "He has a huge sweet tooth and is always overjoyed.", "Idioms built on body parts (eyes, mouth, nose, ears).")
                "Listening Comprehension" -> CurriculumLessonContent("大学の学術講義の聴講", "Attending university academic lectures", "gahk-joo-tsoo koh-gee", "専門用語が飛び交う討論の論点を正確に把握する。", "Accurately grasping the core points of technical academic debates.", "Follow spoken academic discourse with rapid shifts.")
                else -> CurriculumLessonContent("小論文の構成と論理展開", "Short essay structure and logic", "shoh-rohn-boon", "序論、本論、結論を論理的に組み立てる。", "Logically structuring the introduction, body, and conclusion.", "Essays in Japanese employ the 'Ki-shō-ten-ketsu' flow.")
            }
            8 -> when (topic) {
                "Advanced Conversations" -> CurriculumLessonContent("慎重を期する検討", "Prudent deliberation and examination", "sheen-choh oh kee-soo-roo", "リスクマネジメントの観点から慎重を期すべきです。", "We must exercise utmost prudence from a risk-management perspective.", "Executive-level strategic vocabulary.")
                "Cultural Context" -> CurriculumLessonContent("歌舞伎と能楽の伝統芸能", "Kabuki and Noh performing arts", "kah-boo-kee toh noh-gah-koo", "何百年にもわたり継承されてきた様式美を鑑賞する。", "Appreciating stylized beauty handed down over centuries.", "Deep historic roots elevate linguistic appreciation.")
                "Nuanced Vocabulary" -> CurriculumLessonContent("情緒的（じょうちょてき）", "Emotional, evocative, and poignant", "joh-choh-teh-kee", "夕暮れの古都には情緒的な趣が漂っています。", "An evocative and nostalgic charm drifts through the old capital at dusk.", "Literary expressions create poetic atmosphere.")
                "Advanced Listening" -> CurriculumLessonContent("方言混じりの早口対話", "Fast dialogue with regional dialects", "hoh-gen mah-jee-ree", "関西弁や東北弁の微妙なイントネーションを聞き分ける。", "Distinguishing subtle Kansai and Tohoku dialect intonations.", "Understanding diverse regional spoken Japanese.")
                "Formal vs Informal Speech" -> CurriculumLessonContent("尊敬語と謙譲語の使い分け", "Distinguishing Sonkeigo and Kenjougo", "sohn-kay-goh toh ken-joh-goh", "社長がおっしゃいました。私が参ります。", "The president spoke (respectful). I shall go (humble).", "Correct Keigo usage is the hallmark of professional maturity.")
                else -> CurriculumLessonContent("複合動詞の高度な運用", "Advanced use of compound verbs", "foo-koo-goh doh-shee", "問題点を徹底的に洗い出し、改善策を導き出す。", "Thoroughly flushing out problems and deriving improvement plans.", "Mastering compounds like 'arai-dasu' and 'michibiki-dasu'.")
            }
            9 -> when (topic) {
                "Professional Communication" -> CurriculumLessonContent("提携契約の合意締結", "Consensus and signing of partnership agreements", "tey-kay kay-yah-koo", "双方の信頼関係に基づき包括的な覚書を交わした。", "Based on mutual trust, we concluded a comprehensive memorandum.", "High corporate and diplomatic consensus building (Nemawashi).")
                "Advanced Reading" -> CurriculumLessonContent("夏目漱石と芥川龍之介", "Sōseki Natsume and Ryūnosuke Akutagawa", "nah-tsoo-meh soh-seh-kee", "近代文学の傑作を通じて人間の心理の深層を探る。", "Exploring the depths of human psychology through modern literary classics.", "Reading kanji-heavy classical modern prose.")
                "Idioms & Expressions" -> CurriculumLessonContent("石の上にも三年", "Perseverance prevails (three years on a cold stone)", "ee-shee noh oo-eh nee moh sahn-nen", "辛抱強く努力を続ければ、必ず道は開けます。", "If you persevere patiently, the path will surely open.", "Four-character idioms (Yojijukugo) reflect timeless wisdom.")
                "Advanced Writing" -> CurriculumLessonContent("学術論文と論評の執筆", "Writing academic dissertations and op-eds", "gahk-joo-tsoo rohn-boon", "客観的証拠に基づき、独創的な視点を展開する。", "Developing an original thesis based on empirical evidence.", "Rigorous scholarly registers (Da/Dearu style).")
                "Difficult Listening" -> CurriculumLessonContent("国会答弁と論客の討論", "Diet parliamentary sessions and intellectual debates", "kohk-kye toh-ben", "巧みな言葉遣いに隠された真意を的確に読み取る。", "Accurately reading the true intent veiled in diplomatic phrasing.", "Hearing the unstated nuances (Kuuki wo yomu).")
                else -> CurriculumLessonContent("母語話者に迫る表現力", "Expressive ability rivaling native speakers", "boh-goh wah-shah", "時と場に応じた変幻自在のコミュニケーション。", "Chameleon-like communication adapting effortlessly to time and place.", "Bilingual mastery.")
            }
            else -> when (topic) {
                "Natural Conversation" -> CurriculumLessonContent("当意即妙の受け答え", "Ready wit and spontaneous repartee", "toh-ee soh-koo-myoh", "淀みない言葉のやり取りで場を和ませる。", "Soothing the atmosphere with smooth, effortless conversational exchanges.", "Effortless natural timing and humor.")
                "Humor & Cultural References" -> CurriculumLessonContent("落語の洒脱な笑いと小噺", "Rakugo comedic storytelling and witty vignettes", "rah-koo-goh", "言葉の掛け合いとオチの妙味を心から楽しむ。", "Wholeheartedly enjoying the wordplay and sublime punchlines.", "Rakugo mastery represents the peak of cultural humor.")
                "Advanced Debates" -> CurriculumLessonContent("説得力ある弁論", "Persuasive and dignified oratory", "ben-rohn", "理路整然とした弁論で聴衆の共感を勝ち取る。", "Winning the audience's empathy with logical, articulate rhetoric.", "Commanding oration in symposiums.")
                "Professional Fluency" -> CurriculumLessonContent("グローバル経営陣でのリーダーシップ", "Leadership in global Japanese boardrooms", "ree-dah-sheep", "国際シンポジウムの議長を流暢な日本語で務める。", "Serving as chairperson of the international symposium in fluent Japanese.", "Executive stewardship.")
                "Native-Speed Listening" -> CurriculumLessonContent("瞬時の聴解とニュアンス把握", "Instant hearing and nuance perception", "choh-kye", "早口の関西弁の掛け合いも一言一句逃さず理解する。", "Understanding fast-paced banter without missing a single beat.", "Flawless acoustic comprehension.")
                else -> CurriculumLessonContent("完全な日本語の体得", "Total mastery of Japanese life and mindset", "tye-toh-koo", "日本の文化、歴史、感性を深く宿した真のバイリンガル。", "A true bilingual embodying Japanese culture, history, and aesthetic sensibility.", "Total cultural and linguistic harmony.")
            }
        }
    }
}

object KoreanCurriculum {
    fun getContent(level: Int, topic: String): CurriculumLessonContent {
        return when (level) {
            1 -> when (topic) {
                "Greetings" -> CurriculumLessonContent("안녕하세요! / 안녕히 가세요", "Hello! / Goodbye", "ahn-nyeong-hah-seh-yoh / ahn-nyeong-hee gah-seh-yoh", "안녕하세요! 오늘 만나서 정말 반갑습니다.", "Hello! I am truly glad to meet you today.", "Say 'annyeonghi gaseyo' to the person leaving, and 'gyeseyo' to the one staying.")
                "Introducing Yourself" -> CurriculumLessonContent("저는 민수라고 합니다", "My name is Minsu", "jeo-neun meen-soo-rah-goh hahp-nee-dah", "저는 한국어를 배우는 학생입니다.", "I am a student learning Korean.", "'Jeo' is the polite form of 'I'.")
                "Yes / No / Basic Responses" -> CurriculumLessonContent("네 / 아니요 / 감사합니다", "Yes / No / Thank you", "neh / ah-nee-yoh / gahm-sah-hahp-nee-dah", "네, 정말 감사합니다! 도와주셔서 감사해요.", "Yes, thank you so much! Thank you for helping.", "'Gamsahamnida' is polite and formal.")
                "Numbers 1–10" -> CurriculumLessonContent("하나, 둘, 셋, 넷, 다섯...", "Native Korean numerals 1–10", "hah-nah, dool, set, net, dah-seot", "커피 두 잔 주세요.", "Two cups of coffee, please.", "Korean uses Native numbers for counting items/hours, and Sino-Korean for money/minutes.")
                "Basic Pronouns" -> CurriculumLessonContent("저, 너, 그 사람, 우리", "I, you, he/she, we", "jeo, neo, geu sah-rahm, oo-ree", "우리는 다 함께 서울을 여행합니다.", "We travel around Seoul together.", "'Uri' (our/we) is a warm cultural concept applied to school, family, and country.")
                else -> CurriculumLessonContent("죄송합니다 와 잠시만요", "I am sorry and Just a moment", "jweh-sohng-hahp-nee-dah / jahm-shee-mahn-yoh", "잠시만요, 길 좀 지나가겠습니다.", "Excuse me, I'm passing through for a moment.", "Essential for crowded subways and public spots.")
            }
            2 -> when (topic) {
                "Family" -> CurriculumLessonContent("가족 (부모님, 형, 누나, 동생)", "Family (parents, older brother, older sister, younger sibling)", "gah-johk", "우리 형은 회사원이고 여동생은 대학생입니다.", "My older brother is an office worker and my younger sister is a college student.", "Terms for older siblings differ based on the speaker's gender (Hyung/Oppa, Nuna/Unnie).")
                "Colors" -> CurriculumLessonContent("빨간색, 파란색, 노란색, 하얀색", "Red, Blue, Yellow, White", "ppahl-gahn-sek, pah-rahn-sek, noh-rahn-sek", "저는 파란색 셔츠를 즐겨 입습니다.", "I enjoy wearing blue shirts.", "Colors end with '-sek' (색) meaning color.")
                "Food" -> CurriculumLessonContent("비빔밥과 김치찌개", "Bibimbap and kimchi stew", "bee-beem-bbahp / geem-chee-jjee-geh", "얼큰한 김치찌개는 한국의 대표 음식입니다.", "Spicy kimchi stew is a representative Korean dish.", "'Jal meokgesseumnida' is said before meals.")
                "Days & Months" -> CurriculumLessonContent("월요일부터 일요일까지", "Monday through Sunday", "weol-yoh-eel-boo-teo eel-yoh-eel-kkah-jee", "이번 주 토요일에 친구와 만나요.", "I am meeting a friend this Saturday.", "Days of the week are named after the solar elements (Moon, Fire, Water, Wood).")
                "Simple Questions" -> CurriculumLessonContent("누구? 무엇? 어디? 언제? 왜?", "Who? What? Where? When? Why?", "noo-goo, moo-eot, oh-dee, ohn-jeh, weh", "지하철역이 어디에 있습니까?", "Where is the subway station located?", "The question marker is indicated by intonation or '~kka' endings.")
                else -> CurriculumLessonContent("가다, 오다, 먹다, 마시다", "Go, come, eat, drink", "gah-dah, oh-dah, meok-dah, mah-shee-dah", "식당에 가서 불고기를 맛있게 먹었어요.", "I went to a restaurant and ate delicious bulgogi.", "Korean verbs conjugate with honorific suffixes like '-a/eo yo'.")
            }
            3 -> when (topic) {
                "Daily Routine" -> CurriculumLessonContent("아침 일찍 일어나요", "I wake up early in the morning", "ah-cheem eel-jjeek eel-oh-nah-yoh", "보통 일곱 시에 일어나서 샤워를 해요.", "I usually get up at seven o'clock and take a shower.", "Connecting actions sequentially with '~go' (고).")
                "Shopping" -> CurriculumLessonContent("이거 얼마예요?", "How much is this?", "ee-geo eol-mah-yeh-yoh", "사장님, 이거 조금만 깎아 주세요.", "Boss, could you please give me a little discount?", "Calling shopkeepers 'Sajangnim' (boss) is friendly and customary.")
                "Time" -> CurriculumLessonContent("지금 몇 시예요?", "What time is it now?", "jee-geum myeot shee-yeh-yoh", "지금 오후 두 시 삼십 분이에요.", "It is 2:30 PM right now.", "Hours use Native Korean numbers; minutes use Sino-Korean numbers.")
                "Directions" -> CurriculumLessonContent("오른쪽으로 돌아가세요", "Turn right", "oh-reun-jjohk-eu-roh", "사거리에서 왼쪽으로 돌아서 직진하세요.", "Turn left at the four-way intersection and go straight.", "'Oenjjok' is left, 'oreunjjok' is right.")
                "Simple Sentences" -> CurriculumLessonContent("한국 영화를 보고 싶어요", "I want to watch a Korean movie", "bo-goh sheep-eo-yoh", "주말에는 집에서 편하게 영화를 보고 싶어요.", "On the weekend I want to comfortably watch a movie at home.", "Attach '-go sipeoyo' to verb stems to express desire.")
                else -> CurriculumLessonContent("요즘 어떻게 지내세요?", "How have you been doing lately?", "yoh-jeum oh-tteoh-keh jee-neh-seh-yoh", "덕분에 아주 잘 지내고 있습니다.", "Thanks to you, I've been doing very well.", "'Deokbune' graciously credits the other person.")
            }
            4 -> when (topic) {
                "Past Events" -> CurriculumLessonContent("어제 경복궁에 갔어요", "Yesterday I went to Gyeongbokgung Palace", "oh-jeh gyeong-bohk-koong-eh gaht-seo-yoh", "어제 한복을 입고 고궁을 관람했어요.", "Yesterday I wore Hanbok and toured the ancient palace.", "Past tense inflects with '-ass/eoss-eo-yo'.")
                "Future Plans" -> CurriculumLessonContent("내년에 부산으로 여행 갈 거예요", "I will travel to Busan next year", "neh-nyeon-eh boo-sahn-eu-roh", "이번 여름 휴가 때는 제주도에 갈 계획입니다.", "During this summer vacation I plan to go to Jeju Island.", "'-eul geo-ye-yo' is the standard future construction.")
                "Travel" -> CurriculumLessonContent("KTX 고속열차 예매", "Reserving KTX high-speed train tickets", "koy-sohk-yeol-chah", "부산행 KTX 열차는 몇 번 플랫폼에서 출발하나요?", "Which platform does the KTX train to Busan depart from?", "KTX links Seoul to Busan in just over two hours.")
                "Restaurants" -> CurriculumLessonContent("여기 주문할게요!", "We'd like to order here!", "yeo-gee joo-moon-hahl-geh-yoh", "삼겹살 2인분과 된장찌개 하나 주세요.", "Two portions of pork belly and one soybean paste stew, please.", "Order meat portions with 'in-bun' (인분).")
                "Describing People" -> CurriculumLessonContent("키가 크고 다정한 성격", "Tall and warm-hearted personality", "kee-gah keu-goh dah-jeong-hahn", "제 친구는 유머 감각이 뛰어나고 다정해요.", "My friend has a great sense of humor and is warm-hearted.", "Compound descriptions pair '-go' across adjectives.")
                else -> CurriculumLessonContent("오늘 날씨가 정말 화창하네요", "The weather is truly sunny today, isn't it?", "nahss-shee-gah hwah-chahng-hah-neh-yoh", "미세먼지도 없고 하늘이 정말 맑아요.", "There is no fine dust and the sky is crystal clear.", "Weather check-ins frequently mention air quality.")
            }
            5 -> when (topic) {
                "Longer Conversations" -> CurriculumLessonContent("의견을 여쭤봐도 될까요?", "May I ask for your opinion?", "yoh-jjwoh-bwahn-doh doel-kkah-yoh", "이 프로젝트의 방향성에 대해 의견을 듣고 싶습니다.", "I'd like to hear your thoughts regarding this project's direction.", "'Yeojjupda' is the honorific equivalent of 'mutda' (to ask).")
                "Opinions" -> CurriculumLessonContent("제 생각에는...", "In my opinion...", "jeh seng-gahk-eh-neun", "제 생각에는 대중교통 이용을 장려해야 합니다.", "In my view, we must encourage the use of public transit.", "Softening assertions respectfully with '-eun geot gat-a-yo'.")
                "Experiences" -> CurriculumLessonContent("한국에서 살아본 경험이 있어요", "I have experience living in Korea", "sah-rah-bohn gyeong-heom", "한국 문화를 직접 체험하며 시야가 넓어졌어요.", "Experiencing Korean culture directly broadened my perspective.", "'-eun jeogi itda' expresses past experiential milestones.")
                "Comparisons" -> CurriculumLessonContent("서울은 대구보다 훨씬 커요", "Seoul is much bigger than Daegu", "seh-ool-eun deh-goo-boh-dah", "지하철이 택시보다 훨씬 빠르고 저렴합니다.", "Subways are much faster and cheaper than taxis.", "Pattern: A-neun B-boda [adjective] hada.")
                "More Complex Grammar" -> CurriculumLessonContent("~면 / ~(으)ㄹ 때 (조건 및 시점)", "If / When conditionals", "myeon / eul tteh", "시간이 있으면 같이 전통 시장에 구경 가요.", "If you have time, let's go explore the traditional market together.", "Essential conditional connectors for compound sentences.")
                else -> CurriculumLessonContent("한국어 듣기 연습", "Korean listening comprehension", "deud-gee yeon-seub", "자막 없이 한국 드라마와 뉴스를 시청합니다.", "I watch Korean dramas and news broadcasts without subtitles.", "Pay attention to consonant assimilation and liaison rules.")
            }
            6 -> when (topic) {
                "Storytelling" -> CurriculumLessonContent("옛날 옛적 어느 마을에...", "Once upon a time in a certain village...", "yet-nahl yet-jeok", "호랑이 담배 피우던 시절의 전래 동화.", "A traditional fairy tale from the days when tigers smoked pipes.", "Iconic opening phrase of ancient Korean folklore.")
                "Workplace Conversations" -> CurriculumLessonContent("수고하셨습니다 / 확인 부탁드립니다", "Thank you for your hard work / Please review", "soo-goh-hah-shyeot-seum-nee-dah", "오전에 요청하신 기획안을 메일로 송부드렸습니다.", "I have forwarded the proposal requested this morning via email.", "'Su-go-ha-syeot-seum-ni-da' honours collective effort at work.")
                "Social Situations" -> CurriculumLessonContent("초대해 주셔서 진심으로 감사드립니다", "Thank you sincerely for inviting me", "choh-deh-heh joo-shyeo-seo", "따뜻한 환대와 맛있는 식사에 깊이 감동했습니다.", "I was deeply touched by the warm hospitality and delicious food.", "Bringing housewarming gifts like fruit or rolled paper (hyuji) is custom.")
                "News & Media" -> CurriculumLessonContent("종합 뉴스 헤드라인", "Comprehensive news headlines", "jong-hahp nyoo-soo", "신문 사설은 디지털 경제 혁신을 집중 조명합니다.", "The newspaper editorial highlights digital economic innovation.", "Written journalistic style uses '~da' plain speech endings.")
                "Idiomatic Expressions" -> CurriculumLessonContent("발이 넓다 / 눈코 뜰 새 없다", "To be well-connected / Incredibly busy", "bahl-ee neolp-dah", "그분은 발이 넓어서 아는 사람이 아주 많아요.", "That person is so well-connected, they know almost everyone.", "Metaphorical idioms derived from parts of the body.")
                else -> CurriculumLessonContent("간접 화법 (~고 하다)", "Indirect speech reports", "gahn-jeop hwah-beop", "내일 비가 올 거라고 일기예보에서 들었어요.", "I heard on the weather forecast that it will rain tomorrow.", "Reported speech varies by statement, question, or imperative.")
            }
            7 -> when (topic) {
                "Complex Conversations" -> CurriculumLessonContent("사회적 양극화와 복지 정책", "Social polarization and welfare policy", "yahng-geuk-hwah", "지속 가능한 복지 모델에 대한 사회적 합의가 필요합니다.", "A social consensus on a sustainable welfare model is essential.", "Articulate nuanced societal arguments with formal vocabulary.")
                "Debate & Opinions" -> CurriculumLessonContent("저는 다른 시각에서 접근하고 싶습니다", "I'd like to approach this from a different angle", "shee-gahk", "그 주장의 타당성을 인정하지만, 보완할 점이 있습니다.", "I acknowledge the validity of that claim, but there are areas to complement.", "Polite disagreement preserves interpersonal harmony.")
                "Abstract Topics" -> CurriculumLessonContent("한국의 정(情)과 공동체 의식", "The concept of 'Jeong' and community spirit", "jeong-gwah gohng-dohng-cheh", "한국 사회를 하나로 묶는 깊고 따뜻한 연대감.", "The deep and warm solidarity that binds Korean society together.", "'Jeong' is the foundational affective bond in Korean culture.")
                "Natural Expressions" -> CurriculumLessonContent("손이 크다 / 입이 무겁다", "Generous host / Trustworthy confidant", "sohn-ee keu-dah / eep-ee moo-geop-dah", "우리 어머니는 손이 크셔서 항상 음식을 넉넉히 하세요.", "My mother is so generous, she always cooks food in abundant plenty.", "'Soni keuda' literally means large hands, idiomatically generous.")
                "Listening Comprehension" -> CurriculumLessonContent("시사 토론 프로그램 청취", "Listening to current affairs debate programs", "shee-sah toh-rohn", "패널들의 첨예한 대립과 논리적 반박을 완벽히 이해하다.", "Completely understanding the sharp clashes and logical rebuttals of panelists.", "High-speed analytical comprehension.")
                else -> CurriculumLessonContent("논설문 작성 및 논증 구조", "Editorial composition and argumentation", "non-seol-moon", "서론, 본론, 결론을 체계적으로 서술하여 독자를 설득하다.", "Persuading the reader by systematically laying out intro, body, and conclusion.", "Standard TOPIK II Level 6 essay structure.")
            }
            8 -> when (topic) {
                "Advanced Conversations" -> CurriculumLessonContent("심층적인 다각도 분석", "In-depth multi-angled analysis", "dah-gahk-doh boon-seok", "시장 불확실성을 최소화하기 위해 다각도로 검토해야 합니다.", "We must examine this from multiple angles to minimize market uncertainty.", "High corporate and executive management terminology.")
                "Cultural Context" -> CurriculumLessonContent("조선 왕조 실록과 훈민정음", "Joseon Dynasty Annals and Hunminjeongeum", "hoon-meen-jeong-eum", "세종대왕이 창제한 한글의 과학적 원리와 애민 정신.", "King Sejong's scientific principles and love for the people in creating Hangul.", "Hangul is celebrated internationally for its phonological brilliance.")
                "Nuanced Vocabulary" -> CurriculumLessonContent("정취(情趣)와 아련함", "Romantic atmosphere and wistful poignancy", "jeong-chwee / ah-ryeon-hahm", "가을비 내리는 고즈넉한 한옥 마을의 아련한 정취.", "The wistful poignancy of a quiet traditional Hanok village in autumn rain.", "Poetic native and Sino-Korean expressive aesthetics.")
                "Advanced Listening" -> CurriculumLessonContent("지역 방언과 빠른 구어체", "Regional dialects and rapid colloquial speech", "bahng-eon", "경상도와 전라도 사투리의 독특한 억양을 정확히 파악하다.", "Accurately identifying the distinctive intonations of Gyeongsang and Jeolla dialects.", "Fluid understanding of regional variations.")
                "Formal vs Informal Speech" -> CurriculumLessonContent("격식체와 비격식체의 완벽한 조화", "Perfect harmonization of formal and informal speech", "gyeok-sheek-cheh", "상황에 맞춰 하십시오체와 해요체를 자유자재로 구사하다.", "Freely deploying formal hasipsio-che and polite haeyo-che according to context.", "Mastery over social hierarchy and linguistic distance.")
                else -> CurriculumLessonContent("고급 사동사와 피동사", "Advanced causative and passive verbs", "sah-dohng-sah", "진실이 밝혀지고 오해가 말끔히 풀렸습니다.", "The truth was brought to light and misunderstandings were completely cleared.", "Mastery over irregular causative/passive verbs.")
            }
            9 -> when (topic) {
                "Professional Communication" -> CurriculumLessonContent("글로벌 비즈니스 전략 협상", "Global business strategic negotiation", "hyeop-sahng", "양사는 상호 호혜적인 전략적 제휴 협약을 체결했습니다.", "Both companies concluded a mutually reciprocal strategic alliance agreement.", "Corporate negotiation, contracts, and board presentations.")
                "Advanced Reading" -> CurriculumLessonContent("현대 한국 문학의 걸작", "Masterpieces of modern Korean literature", "moon-hahk", "윤동주의 서시와 박경리의 토지를 원문으로 감상하다.", "Appreciating Yun Dong-ju's prologue poem and Pak Kyong-ni's epic Toji in the original.", "Reading acclaimed literary classics.")
                "Idioms & Expressions" -> CurriculumLessonContent("천리 길도 한 걸음부터", "A journey of a thousand miles begins with a single step", "cheon-ree geel-doh", "위대한 성취도 꾸준한 작은 실천에서 비롯됩니다.", "Great achievements also originate from consistent small steps.", "Sino-Korean idioms (Gosaseong-eo) provide intellectual color.")
                "Advanced Writing" -> CurriculumLessonContent("전문 정책 보고서 및 학술 논문", "Specialized policy reports and academic theses", "boh-goh-seo", "방대한 통계 자료를 바탕으로 정밀한 정책 제언을 도출하다.", "Deriving precise policy recommendations based on comprehensive statistical data.", "High-standard scholarly and administrative prose.")
                "Difficult Listening" -> CurriculumLessonContent("국회 청문회 및 법정 공방", "Parliamentary hearings and courtroom arguments", "cheong-moon-hweh", "증인의 진술 속 숨겨진 맥락과 법리적 공방을 실시간 포착.", "Capturing real-time hidden context and legal exchanges in witness testimonies.", "Supreme auditory discernment.")
                else -> CurriculumLessonContent("원어민 수준의 자연스러운 언어 구사", "Native-level natural Korean fluency", "won-eo-meen", "상황과 상대에 따라 완벽한 어휘와 품격을 드러내다.", "Displaying flawless vocabulary and dignity suited to any context and interlocutor.", "Effortless bilingual fluency.")
            }
            else -> when (topic) {
                "Natural Conversation" -> CurriculumLessonContent("거침없는 유려한 대화", "Unimpeded eloquent conversation", "yoo-ryeo-hahn deh-hwah", "원어민과 구별되지 않는 자연스러운 억양과 흐름.", "Natural intonation and rhythm indistinguishable from a native speaker.", "Zero hesitation, full communicative grace.")
                "Humor & Cultural References" -> CurriculumLessonContent("해학과 풍자, K-컬처의 위트", "Humor, satire, and K-culture wit", "heh-hwahk-gwah poong-jah", "시대적 밈(Meme)과 풍자적 유머를 완벽히 이해하고 활용하다.", "Completely understanding and utilizing contemporary memes and satirical humor.", "Humor reflects supreme cultural fluency.")
                "Advanced Debates" -> CurriculumLessonContent("논리정연한 웅변과 설득", "Articulate eloquence and persuasion", "oong-byeon", "치밀한 논리와 카리스마로 청중을 완전히 압도하다.", "Completely captivating the audience with rigorous logic and charisma.", "Mastery of public oration.")
                "Professional Fluency" -> CurriculumLessonContent("국제 무대에서의 리더십", "Leadership on the international stage", "ree-dah-sheep", "한미 경제 포럼의 기조연설을 품격 있는 한국어로 이끌다.", "Delivering keynote speeches at the Korea-US economic forum in dignified Korean.", "Global executive leadership.")
                "Native-Speed Listening" -> CurriculumLessonContent("전 방위적 청각적 직관", "All-encompassing auditory intuition", "jeong-chwee", "동시에 쏟아지는 빠른 토론 속에서도 맥락을 완벽히 장악하다.", "Completely grasping context even amidst simultaneous rapid-fire debate.", "Flawless real-time auditory processing.")
                else -> CurriculumLessonContent("완벽한 문화적·언어적 일체화", "Complete cultural and linguistic unity", "eel-cheh-hwah", "한국의 정서, 역사, 사상을 온전히 품은 진정한 언어의 달인.", "A true master of language fully embodying Korean sentiments, history, and thought.", "Ultimate linguistic and cultural mastery.")
            }
        }
    }
}
