**Título:** Autentificación y Registro - AuthController.java

La clase `AuthController` es el responsable de manejar las solicitudes relacionadas con la autenticación y registro de usuarios en el sistema. A continuación, se detallan los métodos de esta clase y se identifican oportunidades de mejora.

**Método `/register`:**

Este método se encarga de registrar un nuevo usuario en el sistema. Recibe una solicitud `RegisterRequest` con la información del usuario (nombre, apellido, email, password y rol) y realiza los siguientes pasos:

1. Verifica si el email ya está registrado en el sistema. Si lo es, devuelve un error 400 (Bad Request).
2. Crea un nuevo objeto `Usuario` con la información proporcionada.
3. Guarda el usuario en la base de datos utilizando el repositorio `UsuarioRepository`.
4. Genera un token JWT para el nuevo usuario utilizando el servicio `JwtUtil`.
5. Devuelve una respuesta `RegisterResponse` con el token, nombre del usuario, email y rol.

**Oportunidad de mejora:** Considerar agregar validación adicional para asegurarse de que la password sea lo suficientemente fuerte antes de guardarlo en la base de datos.

**Método `/login`:**

Este método se encarga de autenticar a un usuario ya registrado en el sistema. Recibe una solicitud `LoginRequest` con la información del usuario (email y password) y realiza los siguientes pasos:

1. Busca al usuario en la base de datos utilizando el repositorio `UsuarioRepository`.
2. Verifica si el usuario existe y está activo. Si no es así, devuelve un error 403 (Forbidden).
3. Verifica que la password proporcionada sea válida mediante el servicio `PasswordEncoder`. Si no lo es, devuelve un error 401 (Unauthorized).
4. Genera un token JWT para el usuario utilizando el servicio `JwtUtil`.
5. Devuelve una respuesta `LoginResponse` con el token y la información del usuario.

**Oportunidad de mejora:** Considerar agregar logueo de sesión expira al token JWT para que los usuarios se deslogueen automáticamente después de un período de inactividad.

**Método `/me`:**

Este método se encarga de devolver la información del usuario actualmente autenticado. Recibe una solicitud `HttpServletRequest` y realiza los siguientes pasos:

1. Busca al usuario actual en el atributo `request.getAttribute("usuarioActual")`.
2. Devuelve una respuesta `LoginResponse` con la información del usuario.

**Oportunidad de mejora:** Considerar agregar validación adicional para asegurarse de que el usuario actualmente autenticado tenga permisos adecuados para acceder a esta información.