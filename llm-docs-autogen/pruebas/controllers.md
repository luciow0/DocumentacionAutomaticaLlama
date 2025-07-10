**Autenticación y Registro**

El archivo `AuthController.java` es el responsable de gestionar la autenticación y registro de usuarios en el sistema. A continuación, se presenta una descripción detallada de cada método:

* **register**: Este método permite al usuario registrarse en el sistema. Recibe un objeto `RegisterRequest` que contiene los datos del nuevo usuario (nombre, apellido, email, password y rol). Si el email ya está registrado, devuelve un estado de respuesta con un mensaje de error. De lo contrario, crea un nuevo usuario y lo almacena en la base de datos. Finalmente, genera un token JWT para el nuevo usuario y devuelve una respuesta de registro exitosa.
* **login**: Este método permite a los usuarios iniciar sesión en el sistema. Recibe un objeto `LoginRequest` que contiene el email y password del usuario. Si no se encuentra al usuario o si la contraseña es incorrecta, devuelve un estado de respuesta con un mensaje de error. De lo contrario, verifica que el usuario esté activo y genera un token JWT para él. Finalmente, devuelve una respuesta de inicio de sesión exitosa.
* **me**: Este método permite a los usuarios obtener información sobre su cuenta actual. Recibe la solicitud HTTP `HttpServletRequest` y extrae la información del usuario actual guardada en ella. Devuelve una respuesta con la información del usuario actual (email y rol).

En cuanto a posibles mejoras, se podrían considerar las siguientes:

* Agregar soporte para múltiples roles: Actualmente, el sistema solo tiene un rol por defecto (`Rol.CLIENTE`). Se podría agregar soporte para otros roles y permitir que los usuarios tengan varios roles asignados.
* Implementar autenticación de tipo "remember me": Permitir a los usuarios mantener una sesión activa después de cerrar la aplicación o reiniciar el dispositivo.
* Agregar validaciones adicionales: Verificar si el email y password proporcionados son válidos antes de intentar buscar al usuario en la base de datos.