REQUISITOS PREVIOS: 

1. Python instalado (Idealmente Python 3.9+)
2. Que pip este funcionando, probalo con el siguiente comando (Windows, Linux, MacOS): 

-> python -m pip --version

Para poder correr el script de forma local, segui los siguientes pasos:

1. Instala Ollama: 

(Windows)
-> https://ollama.com/

(MacOS (Si usas brew))
-> brew install ollama

(Linux (Debian/Ubuntu))
-> curl -fsSL https://ollama.com/install.sh | sh

2. Luego, corre el siguiente comando por consola (Windows, Linux, MacOS): 

-> ollama run llama3

En caso de que no tengas llama3 instalado, lo descarga, si ya lo tenes, solamente lo va a ejecutar. 

3. Vas a necesitar estas dependencias para correr el script: 

-> llama-index
-> llama-index-llms-ollama
-> llama-index-embeddings-huggingface
-> sentence-transformers

Instalalas con el siguiente comando (Windows, Linux, MacOS):

-> pip install llama-index llama-index-llms-ollama llama-index-embeddings-huggingface sentence-transformers
