function loadText(work) {
    // Load the appropriate JSON file based on the selected work
    fetch(`data/${work}.json`)
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById("text-container");
            container.innerHTML = ""; // Clear previous text

            data.lines.forEach(line => {
                // Create line number as clickable element
                const lineNumberSpan = document.createElement("span");
                lineNumberSpan.textContent = `[${line.lineNumber}] `;
                lineNumberSpan.classList.add("line-number");
                lineNumberSpan.onclick = () => showTranslation(line);
                container.appendChild(lineNumberSpan);

                // Display each word in the line
                line.words.forEach(wordData => {
                    const wordSpan = document.createElement("span");
                    wordSpan.textContent = wordData.word;
                    wordSpan.classList.add("word");
                    wordSpan.onclick = () => showInfo(wordData);
                    container.appendChild(wordSpan);
                    container.appendChild(document.createTextNode(" "));
                });

                // Add a line break after each sentence for readability
                container.appendChild(document.createElement("br"));
            });
        });
}

function showInfo(wordData) {
    const popup = document.getElementById("info-popup");
    popup.innerHTML = `
        <p><strong>Word:</strong> ${wordData.word}</p>
        <p><strong>Parsing:</strong> ${wordData.parsing}</p>
        <p><strong>Usage:</strong> ${wordData.usage}</p>
        <p><strong>Meaning:</strong> ${wordData.meaning}</p>
    `;
    popup.classList.remove("hidden");
}

function showTranslation(line) {
    const translationPopup = document.getElementById("translation-popup");
    translationPopup.innerHTML = `
        <p><strong>Line ${line.lineNumber}:</strong> ${line.sentence}</p>
        <p><strong>Translation:</strong> ${line.translation}</p>
    `;
    translationPopup.classList.remove("hidden");
}

document.addEventListener("click", (event) => {
    const popup = document.getElementById("info-popup");
    const translationPopup = document.getElementById("translation-popup");
    if (!popup.contains(event.target) && !event.target.classList.contains("word")) {
        popup.classList.add("hidden");
    }
    if (!translationPopup.contains(event.target) && !event.target.classList.contains("line-number")) {
        translationPopup.classList.add("hidden");
    }
});
