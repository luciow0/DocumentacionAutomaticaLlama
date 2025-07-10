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

print("* Ingrese la ruta ABSOLUTA de el paquete que desea documentar")
print("* Asegurese de pegar la ruta correcta, en caso de notar que la ruta especificada no es correcta")
print("* ya habiendo empezado la ejecucion, presione 'Ctrl + C' para detener el programa ")
source_path = input(".. ")
while(source_path == '' or source_path == ' '):
    source_path = input("Ingrese la ruta ABSOLUTA de el paquete que desea documentar")

source_path = source_path.strip('"')

print(" ")
nombre_carpeta_salida_documentacion = input("Ingrese el nombre de la carpeta en la que desea guardar la documentacion: ")

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

        fecha_hora_actual = time.strftime("%Y-%m-%d %H:%M:%S")

        # Prompt personalizado con nombre de archivo
        prompt = f"""
        Primero poné un título a lo que estás generando, incluyendo el nombre del archivo que estás analizando
        ({archivo}) y la carpeta en la que se encuentra ({nombre_carpeta}) y la fecha y hora de generación ({fecha_hora_actual}).
        Luego, generá una descripción detallada de cada controlador de este backend en Spring Boot.
        También indicá qué podría mejorarse, y que posibles problemas de seguridad podria presentar. En castellano.
        """

        # Nombre del archivo Markdown de salida
        nombre_salida = f"{nombre_carpeta_salida_documentacion}/{archivo}.md"
        print(f"📝 Generando {nombre_salida}...")

        response = query_engine.query(prompt)

        # Asegurarse de que la carpeta de salida existe
        os.makedirs(nombre_carpeta_salida_documentacion, exist_ok=True)

        # Guardar resultado
        with open(nombre_salida, "w", encoding="utf-8") as f:
            f.write(response.response)

print("✅ ¡Documentación generada exitosamente!")
print(f"⏱️ Tiempo total de ejecución: {time.time() - start_time:.2f} segundos")

