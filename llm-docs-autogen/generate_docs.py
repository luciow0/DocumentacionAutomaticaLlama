from llama_index.core import SimpleDirectoryReader, VectorStoreIndex, Settings
from llama_index.llms.ollama import Ollama
from llama_index.embeddings.huggingface import HuggingFaceEmbedding
import os

# 👉 1. Usar modelo Ollama (LLaMA 3)
llm = Ollama(model="llama3", request_timeout=120.0)

# 👉 2. Configurar embeddings locales de HuggingFace
Settings.llm = llm  # también aseguramos que el LLM se use globalmente
Settings.embed_model = HuggingFaceEmbedding(model_name="all-MiniLM-L6-v2")

# 👉 3. Leer código fuente del proyecto
source_path = "src/main/java/com/martelli/meatmarket/"
docs = SimpleDirectoryReader(source_path).load_data()

# 👉 4. Indexar el contenido con embeddings locales
index = VectorStoreIndex.from_documents(docs)

# 👉 5. Motor de consulta a la IA
query_engine = index.as_query_engine()

# 👉 6. Prompts para generar cada archivo de documentación
questions = {
    "docs/README.md": "Generá un README general para este proyecto backend con Spring Boot en castellano.",
    "docs/API_REFERENCE.md": "Documentá todos los endpoints definidos en este backend en castellano.",
    "docs/ARCHITECTURE.md": "Explicá la arquitectura general del sistema y cómo se conectan los módulos en castellano.",
    "docs/DATABASE.md": "Describí las entidades principales, sus campos y relaciones en castellano.",
}

# 👉 7. Crear carpeta `docs` si no existe
os.makedirs("docs", exist_ok=True)

# 👉 8. Ejecutar cada pregunta y guardar el resultado como Markdown
for file, prompt in questions.items():
    print(f"📝 Generando {file}...")
    response = query_engine.query(prompt)
    with open(file, "w", encoding="utf-8") as f:
        f.write(response.response)

print("✅ ¡Documentación generada exitosamente!")
