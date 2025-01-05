<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Text and Popup</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css">
    <style>
   
   button {
    padding: 6px 12px; 
    font-size: 0.85em;  
    border-radius: 4px;
    background-color: #007bff; 
    color: white; 
    border: none;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #0056b3; 
}

    
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }

        header {
            text-align: center;
            padding: 20px;
        }

        #text-container {
            font-size: 1.2em;
            line-height: 1.6;
            max-width: 600px;
            margin: 20px auto;
        }

        .word {
            cursor: pointer;
            text-decoration: underline;
            transition: color 0.3s ease;
        }

        .word:hover {
            color: #0056b3;
        }

        .line-number {
            cursor: pointer;
            color: #28a745;
            font-weight: bold;
            transition: color 0.3s ease;
        }

        .line-number:hover {
            color: #218838;
        }

        #info-popup, #translation-popup {
            position: fixed;
            top: 20px;
             color: #333; 

            right: 20px;
            background: black;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            max-width: 250px;
            font-size: 0.9em;
            z-index: 1000;
        }

        #translation-popup {
            top: 100px;  
                color: #333;  

        }

        .hidden {
            display: none;
        }

        #info-popup, #translation-popup {
            animation: fadeIn 0.5s ease-in-out;
            
        }

        @keyframes fadeIn {
            0% { opacity: 0; }
            100% { opacity: 1; }
        }

    </style>
</head>
<body>
    <header>
        <h1>classics parser</h1>
        <p>Select a work and click on individual words or line numbers for translations.</p>
    </header>
    
    <section>
        <button onclick="loadText('aeneid')">Load Aeneid</button>
        <button onclick="loadText('iliad')">Load Iliad</button>
        <button onclick="loadText('debellogallico')">Load De Bello Gallico</button>


    </section>

    <div id="text-container"></div>
    <div id="info-popup" class="hidden"></div>
    <div id="translation-popup" class="hidden"></div>

    <script src="scripts.js"></script>
</body>
</html>
