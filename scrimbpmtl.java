<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="color-scheme" content="light dark" />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css"
    />
    <title>Guess the Script</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background-color: #222;
            color: #e0e0e0;
        }
        
        #guess-input {
            width: 200px;
            height: 30px;
            padding: 5px;
            font-size: 16px;
            border-radius: 5px;
            border: 1px solid #ccc;
            background-color: transparent;
            color: #e0e0e0;
        }

        #wordle-grid {
            display: grid;
            grid-template-columns: 1fr;
            grid-gap: 10px;
            margin-bottom: 20px;
        }

        .tile {
            width: 300px;
            height: 50px;
            border: 1px solid #ccc;
            text-align: center;
            line-height: 50px;
            font-size: 24px;
            font-weight: bold;
            text-transform: uppercase;
            color: #e0e0e0;
        }

        .correct {
            background-color: #6aaa64;
        }

        .absent {
            background-color: #787c7e;
        }

        #message {
            font-size: 18px;
            margin-top: 10px;
            color: #bbb;
        }

        #hint {
            font-size: 16px;
            margin-top: 10px;
            color: #bbb;
        }

        #share-result {
            margin-top: 20px;
            font-size: 16px;
            white-space: pre-wrap;
        }

        button {
            padding: 10px 20px;
            margin-top: 10px;
            border: none;
            background-color: #0077cc;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background-color: #005fa3;
        }
    </style>
</head>
<body>

    <h1>Scriptle</h1>
    <h2>Guess the script!</h2>
    <div id="wordle-grid">
        <div class="tile" id="script-display"></div>
    </div>
     <input type="text" id="guess-input" list="script-options" placeholder="Enter SCRIPT name" autofocus>
    <datalist id="script-options"></datalist> <!-- List of possible options -->
    <button onclick="submitGuess()">Submit Guess</button>
    <div id="message"></div>
    <div id="hint"></div>
    <div id="share-result"></div>

    <script>
        const wordleGrid = document.getElementById("wordle-grid");
        const scriptDisplay = document.getElementById("script-display");
        const guessInput = document.getElementById("guess-input");
        const messageDiv = document.getElementById("message");
        const hintDiv = document.getElementById("hint");
        const shareResultDiv = document.getElementById("share-result");

         const scripts = [
    { script: 'مرحبا', language: 'arabic', hints: ['Spoken in the Middle East', 'Written from right to left', 'Uses a complex system of calligraphy'] },
    { script: 'ԱԲԳԴ', language: 'armenian', hints: ['Spoken in Armenia and parts of Turkey', 'An ancient alphabet', 'Uses 38 letters'] },
    { script: 'ᬅᬘᬂ', language: 'balinese', hints: ['Spoken in Bali, Indonesia', 'Used in Hindu religious texts', 'Has syllabic characters'] },
    { script: 'မင်္ဂလာပါ', language: 'myanmar', hints: ['Spoken in Myanmar (Burma)', 'A complex script for tonal languages', 'Written vertically and horizontally'] },
    { script: 'ᓀᐦᐃᔭᐍᐏᐣ', language: 'cree syllabics', hints: ['Used by Cree and other indigenous people in Canada', 'A syllabic script', 'A unique system of writing'] },
    { script: '漢字', language: 'chinese', hints: ['Used in Chinese, Japanese, and Korean', 'Logographic script', 'Represents words or morphemes'] },
    { script: 'Ⲙⲉⲧⲛⲁ', language: 'coptic', hints: ['Used in Egypt for Christian liturgy', 'Developed from Greek alphabet', 'Ancient Egyptian roots'] },
    { script: '𒀸𒄿𒌇𒋢𒀀', language: 'cuneiform', hints: ['One of the earliest forms of writing', 'Used by ancient Sumerians', 'Composed of wedge-shaped marks'] },
    { script: 'Привет', language: 'cyrillic', hints: ['Used in Russia and other Slavic countries', 'Adapted from the Greek alphabet', 'Has over 30 letters'] },
    { script: 'नमस्ते', language: 'devanagari', hints: ['Used in Hindi and Sanskrit', 'Written left to right', 'Composed of syllabic characters'] },
    { script: 'বাংলা', language: 'bengali', hints: ['Spoken in Bangladesh and India', 'Uses Eastern Nagari script', 'An abugida writing system'] },
    { script: 'ፊደል', language: 'ge’ez', hints: ['Used in Ethiopia and Eritrea', 'An ancient script for Semitic languages', 'Used in religious texts'] },
    { script: 'ႠႡႢႣႤ', language: 'georgian', hints: ['Spoken in Georgia', 'An ancient alphabet with 33 letters', 'Distinct from Cyrillic and Latin alphabets'] },
    { script: 'ΑΒΓΔ', language: 'greek', hints: ['Spoken in Greece', 'An ancient and influential script', 'Has been used for over 3,000 years'] },
    { script: 'ગુજરાતી', language: 'gujarati', hints: ['Spoken in India, particularly Gujarat', 'Written in a unique script', 'Uses no diacritical marks'] },
    { script: 'ਗੁਰਮੁਖੀ', language: 'gurmukhi', hints: ['Spoken in Punjab, India', 'Used by Sikhs for religious texts', 'Has a distinct set of characters'] },
    { script: 'שׁלוֹם', language: 'hebrew', hints: ['Used in Israel and Jewish communities worldwide', 'Written from right to left', 'Used for religious and modern texts'] },
    { script: 'こんにちは', language: 'japanese', hints: ['Spoken in Japan', 'Uses Kanji and Kana scripts', 'Famous for anime and sushi'] },
    { script: 'ᮊᮌᮔᮔ᮪', language: 'sundanese', hints: ['Spoken in Indonesia', 'A Brahmic script', 'Historically used for literature and administration'] },
    { script: 'ꦲꦏ꧀ꦱꦫꦗꦮ', language: 'javanese', hints: ['Spoken in Java, Indonesia', 'A Brahmic script', 'Historically used for literature and administration'] },
    { script: '𑂀𑂍𑂰𑂨𑂮𑂹𑂟𑂲', language: 'kaithi', hints: ['Used in the Hindi Belt of India', 'A historical script', 'Often used in legal and administrative contexts'] },
    { script: 'ಕನ್ನಡ', language: 'kannada', hints: ['Spoken in the southern state of Karnataka', 'A Dravidian language', 'Has round-shaped characters'] },
    { script: 'ᡴᡥᡟ', language: 'kharosthi', hints: ['Used for Gandhari and Sanskrit', 'An ancient script', 'Written from right to left'] },
    { script: '𑊔𑊖𑊙𑊘', language: 'khojki', hints: ['Used for Sindhi, Punjabi, and other languages', 'Influenced by Arabic and Persian scripts', 'Used in South Asia'] },
    { script: '안녕하세요', language: 'hangeul', hints: ['Used for the Korean language', 'A featural alphabet', 'Represents sounds directly'] },
    { script: 'ສະບາຍດີ', language: 'lao', hints: ['Spoken in Laos', 'A tonal language', 'Written using an abugida system'] },
    { script: 'abcdefghijklmnopqrstuvwxyz', language: 'latin', hints: ['Used globally', 'The most common script', 'Based on the Roman alphabet'] },
    { script: 'ᨕᨘᨕᨘᨑ', language: 'lontara', hints: ['Used in Sulawesi, Indonesia', 'A syllabic script', 'Used for the Makassarese and Bugis languages'] },
    { script: 'മലയാളം', language: 'malayalam', hints: ['Spoken in Kerala, India', 'A Dravidian language', 'Has a circular script'] },
    { script: '𐬬𐬆𐬌𐬍𐬙', language: 'manchu', hints: ['Used for the Manchu language', 'A vertical script', 'Related to the Mongolian script'] },
    { script: 'ᠮᠡᠩᠭᠤᠯ᠎ᠠ', language: 'mongolian', hints: ['Spoken in Mongolia', 'Written vertically', 'An ancient script that has been revived'] },
    { script: 'ꡏꡡꡃꡣꡡꡙꡐꡜꡞ', language: 'mwangwego', hints: ['Used by the Mwangwego people', 'Indigenous African script', 'Represents syllables'] },
    { script: 'ߒߞߏ', language: 'n\'ko', hints: ['Used in West Africa', 'A script for the Manding languages', 'Created in 1949'] },
    { script: 'ଓଡ଼ିଆ', language: 'odia', hints: ['Spoken in Odisha, India', 'A part of the Eastern Indic script family', 'Used for writing Odiya language'] },
    { script: 'ᚁ', language: 'ogham', hints: ['Used in ancient Ireland', 'A script carved into stone', 'Represents letters through lines and notches'] },
    { script: '𐽦𐽨𐽪', language: 'phags-pa', hints: ['Used for Mongolian and Tibetan languages', 'Created by Kublai Khan’s court', 'Written vertically'] },
    { script: 'ꜰꜿꜺꜻꜼ', language: 'pollard', hints: ['An experimental alphabet', 'Created for a specific linguistic purpose', 'Incorporates modern letterforms'] },
    { script: 'ᚠᚢᚦᚨᚱᚲ', language: 'runic', hints: ['Used by ancient Germanic peoples', 'A script used for inscriptions', 'Also known as Futhark'] },
    { script: '𑋦𑆯𑆳𑆫𑆢𑆳', language: 'sharda', hints: ['Used for Kashmiri and Sanskrit', 'A historical Brahmic script', 'Used from the 8th to 16th centuries'] },
    { script: '𑪁𑩖𑩻𑩖𑪌𑩰𑩖 𑩰𑩑𑩢𑩑𑪊', language: 'soyombo', hints: ['Used in Mongolia', 'A unique script for the Mongolian language', 'Combines alphabetic and ideographic elements'] },
    { script: 'ܫܠܡܐ', language: 'syriac', hints: ['Used in Christian liturgies', 'A Semitic script', 'Written from right to left'] },
    { script: 'ꪎꪳ ꪼꪕ', language: 'tai viet', hints: ['Spoken in Southeast Asia', 'Used by the Tai people', 'A tonal writing system'] },
    { script: '𑘁𑘖𑘄𑘖', language: 'takri', hints: ['Used in Northern India', 'A historical script', 'Influenced by the Sharada script'] },
    { script: 'தமிழ்', language: 'tamil', hints: ['Spoken in Tamil Nadu, India', 'A Dravidian language', 'Uses a round-shaped script'] },
    { script: 'తెలుగు', language: 'telugu', hints: ['Spoken in Andhra Pradesh, India', 'A Dravidian language', 'Has a round-shaped, cursive script'] },
    { script: 'สวัสดี', language: 'thai', hints: ['Spoken in Thailand', 'A tonal language', 'Has a complex set of characters'] },
    { script: 'བོད་ཡིག', language: 'tibetan', hints: ['Spoken in Tibet and parts of India', 'A classical script', 'Used for religious and literary texts'] },
    { script: 'ⵜⵉⴼⵉⵏⵖ', language: 'tifinagh', hints: ['Used in North Africa', 'A script for the Berber languages', 'One of the oldest African scripts'] }
     
        ];

       function populateSuggestions() {
            const datalist = document.getElementById('script-options');
            datalist.innerHTML = ''; // Clear previous options
            scripts.forEach(scriptObj => {
                const option = document.createElement('option');
                option.value = scriptObj.language;
                datalist.appendChild(option);
            });
        }
        populateSuggestions();

        function seedRandom(seed) {
            var x = Math.sin(seed) * 10000;
            return x - Math.floor(x);
        }

        function getScriptForToday() {
            const today = new Date();
            const dayOfYear = Math.floor((today - new Date(today.getFullYear(), 0, 0)) / 1000 / 60 / 60 / 24);
            const seed = today.getFullYear() * 1000 + dayOfYear; 
            const randomIndex = Math.floor(seedRandom(seed) * scripts.length);
            return scripts[randomIndex];
        }

        const todayScript = getScriptForToday();
        scriptDisplay.textContent = todayScript.script;

        let attempts = 0;
        const maxAttempts = 6;
        let guessResults = [];

        function showHint(attempt) {
            if (attempt < todayScript.hints.length) {
                hintDiv.textContent = `Hint: ${todayScript.hints[attempt]}`;
            } else {
                hintDiv.textContent = '';
            }
        }

        function submitGuess() {
            const guess = guessInput.value.toLowerCase();
            const correctLanguage = todayScript.language;

            if (guess === correctLanguage) {
                showMessage("Congratulations! You guessed the correct script.");
                scriptDisplay.classList.add("correct");
                hintDiv.textContent = '';
                guessResults.push('O');
                displayShareResult();
            } else {
                attempts++;
                guessResults.push('X');
                if (attempts < maxAttempts) {
                    showMessage(`Wrong! You have ${maxAttempts - attempts} attempts left.`);
                    showHint(attempts - 1);
                } else {
                    showMessage(`Game over! The correct script was "${correctLanguage}".`);
                    scriptDisplay.classList.add("absent");
                    hintDiv.textContent = '';
                    displayShareResult();
                }
            }

            guessInput.value = "";
        }

        function showMessage(msg) {
            messageDiv.textContent = msg;
        }

       function getGameNumber() {
    const startDate = new Date("2024-01-01"); 
    const today = new Date();
    const diffInTime = today - startDate;
    const gameNumber = Math.floor(diffInTime / (1000 * 60 * 60 * 24)) + 1; // Adding 1 to start from #1
    return gameNumber;
}

function displayShareResult() {
    const gameNumber = getGameNumber();
    const result = `Scriptle #${gameNumber}\nGuesses:\n${guessResults.join(' ')}\n https://scriptle.sites.tjhsst.edu/`;
    shareResultDiv.textContent = result;
}

    </script>

</body>
</html>
