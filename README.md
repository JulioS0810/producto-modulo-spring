# Perfumería Violeta - API REST

API REST desarrollada con Spring Boot para la gestión de productos y autenticación de usuarios del sistema Perfumería Violeta.

## Tecnologías utilizadas
- Java 21
- Spring Boot 3.5.13
- Spring Data JPA
- Spring Security
- JWT (jjwt 0.11.5)
- MySQL 8.0 / 9.6
- Maven

## Requisitos previos
- Tener instalado Java 21 o superior.
- Tener MySQL corriendo (versión 8.0+).
- Tener creada la base de datos `PerfumeriaVioleta` con las tablas `productos` y `usuarios` (se adjuntan scripts SQL).

## Configuración de la base de datos
Editar el archivo `src/main/resources/application.properties` con tus credenciales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/PerfumeriaVioleta?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

Endpoints de autenticación
Método	Endpoint	Descripción
POST	/api/auth/register	Registrar un nuevo usuario
POST	/api/auth/login	Iniciar sesión y obtener token JWT
Ejemplo de registro
Petición:

json
POST http://localhost:8080/api/auth/register
{
  "nombre": "Ana",
  "email": "ana@test.com",
  "password": "ana2684"
}
Respuesta (201 Created):

json
{
  "success": true,
  "mensaje": "Usuario registrado exitosamente",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "usuario": {
    "id": 1,
    "nombre": "Ana",
    "email": "ana@test.com"
  }
}
Ejemplo de login exitoso
Petición:

json
POST http://localhost:8080/api/auth/login
{
  "email": "ana@test.com",
  "password": "ana2684"
}
Respuesta (200 OK):

json
{
  "success": true,
  "mensaje": "autenticación satisfactoria",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "usuario": {
    "id": 1,
    "nombre": "Ana",
    "email": "ana@test.com"
  }
}
Ejemplo de login fallido (credenciales incorrectas)
Petición:

json
POST http://localhost:8080/api/auth/login
{
  "email": "ana@test.com",
  "password": "wrong"
}
Respuesta (401 Unauthorized):

json
{
  "success": false,
  "mensaje": "error en la autenticación"
}
Endpoints de productos (catálogo)
Método	Endpoint	Descripción
GET	/api/productos	Listar todos los productos
GET	/api/productos/{id}	Obtener producto por ID
GET	/api/productos/marca/{marca}	Buscar por marca
GET	/api/productos/genero/{genero}	Buscar por género (Hombre/Mujer/Unisex)
GET	/api/productos/buscar?nombre=...	Búsqueda por nombre (parcial)
Pruebas rápidas (productos)
Listar todos: http://localhost:8080/api/productos

Buscar por ID: http://localhost:8080/api/productos/1

Buscar por marca: http://localhost:8080/api/productos/marca/Chanel

Buscar por género: http://localhost:8080/api/productos/genero/Mujer

Búsqueda por nombre: http://localhost:8080/api/productos/buscar?nombre=Afnan

Validaciones implementadas
Email único (no se permiten duplicados en el registro).

Contraseña almacenada encriptada con BCrypt.

Campos obligatorios (nombre, email, password).

Respuestas específicas para cada caso de autenticación (éxito/error).

Herramienta de versionamiento
Git + GitHub: Repositorio

Autor
Julio César Suárez Garavito
Proyecto presentado para las evidencias GA7-220501096-AA3-EV01, GA7-220501096-AA5-EV01 y GA7-220501096-AA5-EV02 del SENA.