# Proyecto de Sistema de Login Seguro con Cookies

Este proyecto es una aplicación web construida con Java y Spring Boot que implementa un sistema de inicio de sesión (login) minimalista, seguro y con un diseño de interfaz de usuario de tema oscuro (dark-themed).

## ¿De qué trata el proyecto? (Explicación Sencilla)

Imagina que este proyecto es como un **club privado muy exclusivo**:

*   **Java y Spring Boot (El Edificio y el Personal):** Son los cimientos y toda la estructura que hace que el club funcione. Controlan la energía, el agua y organizan a todo el personal.
*   **Spring Security (El Guardia de Seguridad):** Es el portero en la entrada que verifica tu identificación (usuario y contraseña) para decidir si puedes entrar o no.
*   **Sesiones basadas en Cookies y Base de Datos (La Pulsera VIP):** Cuando entras al club, te ponen una pulsera especial (una "cookie" en tu navegador de internet). En lugar de solo confiar en la pulsera, el club anota en un registro muy seguro (una base de datos) a quién le dio esa pulsera. Así, si sales un momento al patio y vuelves a entrar, el guardia solo revisa tu pulsera y la compara con su registro para dejarte pasar sin pedirte tu identificación de nuevo.
*   **PostgreSQL (La Bóveda):** Es una caja fuerte gigante y muy segura donde se guardan las cuentas de todos los miembros del club y el registro de las pulseras VIP.
*   **Thymeleaf (El Diseñador de Interiores):** Es el encargado de pintar las paredes, acomodar los muebles y asegurarse de que todo se vea moderno y elegante (en este caso, un diseño con colores oscuros y premium).

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
