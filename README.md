# Proyecto de Sistema de Login Seguro con Cookies

Este proyecto es una aplicación web construida con Java y Spring Boot que implementa un sistema de inicio de sesión (login) minimalista, seguro y con un diseño de interfaz de usuario de tema oscuro (dark-themed).

## Tecnologías Utilizadas

*   **Java 21:** Lenguaje de programación principal.
*   **Spring Boot 4.0.6:** Framework para facilitar la creación de la aplicación web.
*   **Spring Security:** Para la autenticación y protección de rutas.
*   **Spring Session JDBC:** Para manejar las sesiones de los usuarios guardándolas en la base de datos, usando cookies de forma segura.
*   **Spring Data JPA (Hibernate):** Para la comunicación con la base de datos mediante objetos de Java.
*   **PostgreSQL:** Motor de base de datos relacional para persistir los datos de los usuarios y las sesiones.
*   **Thymeleaf:** Motor de plantillas para generar el HTML y las vistas (la interfaz gráfica).
*   **Maven:** Herramienta de gestión de dependencias y construcción del proyecto.

## Requisitos Previos

Para ejecutar este proyecto, necesitarás tener instalado:

1.  **Java Development Kit (JDK) 21**
2.  **PostgreSQL** (configurado y en ejecución)
3.  **Maven** (opcional, el proyecto incluye un envoltorio `mvnw`)

## Configuración y Ejecución

1.  **Base de Datos:** Asegúrate de crear una base de datos en tu servidor PostgreSQL que coincida con la configuración en `application.properties` (normalmente ubicado en `src/main/resources/application.properties`).
2.  **Configuración:** Actualiza las credenciales de la base de datos (usuario y contraseña) en el archivo `application.properties`.
3.  **Ejecución:** Puedes ejecutar el proyecto utilizando el comando de Maven desde la raíz del proyecto:
    ```bash
    ./mvnw spring-boot:run
    ```
    *(En Windows, usa `mvnw.cmd spring-boot:run`)*
4.  **Acceso:** Una vez iniciada, la aplicación estará disponible en `http://localhost:8080`.

## Características Principales

*   Inicio de sesión seguro usando sesiones almacenadas en base de datos.
*   Interfaz de usuario minimalista con diseño "Dark Theme" (Tema Oscuro).
*   Gestión automática de la base de datos (creación de tablas para usuarios y sesiones).
*   Protección contra vulnerabilidades web comunes.
