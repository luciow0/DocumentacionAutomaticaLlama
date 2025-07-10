from llama_index.core import SimpleDirectoryReader, VectorStoreIndex, Settings
from llama_index.llms.ollama import Ollama
from llama_index.embeddings.huggingface import HuggingFaceEmbedding
from llama_index.core.schema import Document
import os
import time 

start_time = time.time()

# 1. Configurar LLM y embeddings
llm = Ollama(model="llama3", request_timeout=120.0)
Settings.llm = llm
Settings.embed_model = HuggingFaceEmbedding(model_name="all-MiniLM-L6-v2")

# 2. Ruta raíz
source_path = "src/main/java/com/martelli/meatmarket/controller"
os.makedirs("pruebas", exist_ok=True)

# 3. Recorrer recursivamente y procesar cada archivo
for ruta_actual, _, archivos in os.walk(source_path):
    for archivo in archivos:
        ruta_completa = os.path.join(ruta_actual, archivo)

        # Leer archivo como documento de texto
        with open(ruta_completa, "r", encoding="utf-8") as f:
            contenido = f.read()
        
        nombre_carpeta = os.path.basename(ruta_actual)  # nombre de la carpeta contenedora
        documento = Document(text=contenido, metadata={"filename": archivo, "folder": nombre_carpeta})

        # Crear índice por archivo
        index = VectorStoreIndex.from_documents([documento])
        query_engine = index.as_query_engine()

        # Prompt personalizado con nombre de archivo
        prompt = f"""
        Primero poné un título a lo que estás generando, incluyendo el nombre del archivo que estás analizando
        ({archivo}) y la carpeta en la que se encuentra ({nombre_carpeta}).
        Luego, generá una descripción detallada de cada controlador de este backend en Spring Boot.
        También indicá qué podría mejorarse, y que posibles problemas de seguridad podria presentar. En castellano.
        """

        # Nombre del archivo Markdown de salida
        nombre_salida = f"pruebas/{archivo}.md"
        print(f"📝 Generando {nombre_salida}...")

        response = query_engine.query(prompt)

        # Guardar resultado
        with open(nombre_salida, "w", encoding="utf-8") as f:
            f.write(response.response)

print("✅ ¡Documentación generada exitosamente!")
print(f"⏱️ Tiempo total de ejecución: {time.time() - start_time:.2f} segundos")

