# Módulo de Productos - Perfumería Violeta

API REST desarrollada con Spring Boot para la gestión de productos del sistema Perfumería Violeta.

## Tecnologías utilizadas
- Java 21
- Spring Boot 3.5.11
- Spring Data JPA
- MySQL 8.0
- Maven

## Requisitos previos
- Tener instalado Java 21 o superior.
- Tener MySQL corriendo (versión 8.0+).
- Tener creada la base de datos `PerfumeriaVioleta` con la tabla `productos` poblada.

## Configuración de la base de datos
Editar el archivo `src/main/resources/application.properties` con tus credenciales:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/PerfumeriaVioleta?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root0810
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialecto

