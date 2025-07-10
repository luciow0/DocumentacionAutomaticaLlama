## REQUISITOS PREVIOS

1. Tener **Python** instalado (idealmente Python 3.9 o superior).
2. Que **`pip`** esté funcionando. Probalo con el siguiente comando (Windows, Linux, MacOS):

   ```bash
   python -m pip --version
   ```

---

## Para poder correr el script de forma local, seguí los siguientes pasos:

### 1. Instalá **Ollama**

* **Windows**
  [https://ollama.com/](https://ollama.com/)

* **MacOS** (si usás `brew`)

  ```bash
  brew install ollama
  ```

* **Linux** (Debian/Ubuntu)

  ```bash
  curl -fsSL https://ollama.com/install.sh | sh
  ```

---

### 2. Ejecutá el siguiente comando en consola (Windows, Linux, MacOS):

```bash
ollama run llama3
```

> En caso de que no tengas `llama3` instalado, lo descarga automáticamente.
> Si ya lo tenés, solamente lo va a ejecutar.

---

### 3. Instalá las siguientes dependencias:

* `llama-index`
* `llama-index-llms-ollama`
* `llama-index-embeddings-huggingface`
* `sentence-transformers`

Instalalas con el siguiente comando (Windows, Linux, MacOS):

```bash
pip install llama-index llama-index-llms-ollama llama-index-embeddings-huggingface sentence-transformers
```

---
