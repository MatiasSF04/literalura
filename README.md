# Literalura

Literalura es una aplicación desarrollada en Java con Spring Boot que permite consultar y gestionar un catálogo de libros obtenidos desde la API [Gutendex](https://gutendex.com/), con persistencia en una base de datos PostgreSQL.

## Funcionalidades

- Consultar libros desde la API de Gutendex por título
- Guardar libros y sus autores en la base de datos
- Listar libros registrados
- Listar autores registrados
- Consultar estadísticas por idioma
- Filtrar libros por idioma (con selección interactiva)
- Ver autores vivos hasta un año específico

## Tecnologías utilizadas

- Java 24
- Spring Boot
- PostgreSQL
- JPA / Hibernate
- Gutendex API (https://gutendex.com/)
- Maven

## Instalación y uso

### 1. Clona el repositorio

```bash
git clone https://github.com/tu-usuario/literalura.git
cd literalura
```

### 2. Configura la base de datos
Asegúrate de tener PostgreSQL corriendo y crea una base de datos, por ejemplo:

```bash
CREATE DATABASE literalura;
```

### 3. Configura las variables de entorno o application.properties
Edita src/main/resources/application.properties con tu configuración:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```
Recomendación: no subir application.properties con tus credenciales. Usa .gitignore para evitar compartir datos sensibles.

### 4. Ejecuta la aplicación
Desde terminal o IDE:

```bash
./mvnw spring-boot:run
```
Se abrirá un menú interactivo por consola.

## Estructura del proyecto

```bash
src/
├── main/
│   ├── java/
│   │   └── com.matisf04.literalura/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── Principal.java
│   └── resources/
│       └── application.properties
```
