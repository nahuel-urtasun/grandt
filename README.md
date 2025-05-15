**GranDT Coach Fantasy** es una aplicación web diseñada para facilitar la búsqueda y consulta de estadísticas de jugadores de la Premier League inglesa. Ideal para entusiastas del Fantasy Football, mas conocido como **Gran DT** en Argentina, 
esta herramienta permite a los usuarios encontrar rápidamente jugadores por equipo, posición o nacionalidad, y acceder a sus datos para tomar decisiones informadas al armar sus equipos.





## Backend

Este proyecto utiliza Spring Boot para construir una API RESTful que gestiona la información de jugadores de fútbol para una aplicación de Gran DT Fantasy, **enfocada en la Premier League**.

**Tecnologías Principales:**

* **Java:** Lenguaje de programación principal.
* **Spring Boot:** Framework para el desarrollo rápido de aplicaciones Java.
* **Spring Data JPA:** Abstracción para la persistencia de datos utilizando JPA.
* **Hibernate:** Implementación de JPA utilizada por Spring Data JPA.
* **PostgreSQL:** Base de datos relacional para almacenar la información de los jugadores.
* **Maven:** Herramienta de gestión de proyectos y dependencias.
* **Spring Web:** Para la creación de la API REST.
* **Spring Test:** Para la realización de pruebas unitarias e de integración.

**Endpoints Principales de la API:**

* `GET /api/v1/player`: Obtiene la lista de jugadores. Admite los siguientes parámetros para filtrar:
    * `name`: Filtra por nombre del jugador (parcial, no sensible a mayúsculas).
    * `team`: Filtra por nombre del equipo (sensible a mayúsculas).
    * `position`: Filtra por posición del jugador (sensible a mayúsculas).
    * `nation`: Filtra por nacionalidad del jugador (parcial, no sensible a mayúsculas).
* `POST /api/v1/player`: Añade un nuevo jugador. Espera un objeto JSON en el cuerpo de la solicitud con la información del jugador (ver ejemplo en la sección de Frontend si está disponible).
* `PUT /api/v1/player`: Actualiza la información de un jugador existente. Espera un objeto JSON en el cuerpo de la solicitud con la información actualizada del jugador (generalmente se identifica por el nombre).
* `DELETE /api/v1/player/{playerName}`: Elimina un jugador por su nombre.

**Ejemplo de Objeto Jugador (JSON - Premier League):**

```json
{
  "name": "Mohamed Salah",
  "nation": "Egypt",
  "position": "Delantero",
  "age": 29,
  "mp": 38,
  "starts": 37,
  "min": 3078.0,
  "goals": 23.0,
  "assists": 13.0,
  "penalties": 5.0,
  "yellow_cards": 1.0,
  "red_cards": 0.0,
  "expected_goals": 20.5,
  "expected_assists": 11.8,
  "teamName": "Liverpool"
}


## Frontend
El frontend de la aplicación está construido con React y utiliza axios para comunicarse con la API backend.

Tecnologías Principales:

React: Biblioteca de JavaScript para construir interfaces de usuario.
axios: Cliente HTTP basado en promesas para realizar peticiones al backend.
SCSS: Preprocesador de CSS para estilos.
React Router (implícito por la navegación con parámetros): Para la navegación entre diferentes vistas o la manipulación de la URL para el filtrado.
Manejo de Estado con Hooks (useState, useEffect): Para gestionar el estado de los componentes y realizar efectos secundarios como las llamadas a la API.
Interacción con la API:

El frontend realiza peticiones GET al endpoint /api/v1/player del backend para obtener la información de los jugadores, utilizando parámetros en la URL (query parameters) para aplicar filtros:

Filtrar por Equipo: Se añade el parámetro team a la URL (ej: /api/v1/player?team=Liverpool).
Filtrar por Nacionalidad: Se añade el parámetro nation a la URL (ej: /api/v1/player?nation=Egypt).
Filtrar por Posición: Se añade el parámetro position a la URL (ej: /api/v1/player?position=Delantero).
Filtrar por Nombre: Se añade el parámetro name a la URL (ej: /api/v1/player?name=Mohamed%20Salah).
