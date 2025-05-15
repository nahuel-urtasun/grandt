# GranDT Coach Fantasy PL

**GranDT Coach Fantasy PL** es una aplicación web diseñada para facilitar la búsqueda y consulta de estadísticas de jugadores de la Premier League inglesa. Ideal para entusiastas del Fantasy Football, mas conocido como **Gran DT** en Argentina, esta herramienta permite a los usuarios encontrar rápidamente jugadores por equipo, posición o nacionalidad, y acceder a sus datos para tomar decisiones informadas al armar sus equipos.

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
```

## Frontend

El frontend de la aplicación está construido con React y utiliza axios para comunicarse con la API backend.

**Tecnologías Principales:**

* **React:** Biblioteca de JavaScript para construir interfaces de usuario.
* **axios:** Cliente HTTP basado en promesas para realizar peticiones al backend.
* **SCSS:** Preprocesador de CSS para estilos.
* **React Router (implícito por la navegación con parámetros):** Para la navegación entre diferentes vistas o la manipulación de la URL para el filtrado.
* **Manejo de Estado con Hooks (`useState`, `useEffect`):** Para gestionar el estado de los componentes y realizar efectos secundarios como las llamadas a la API.

**Interacción con la API:**

El frontend realiza peticiones `GET` al endpoint `/api/v1/player` del backend para obtener la información de los jugadores, utilizando parámetros en la URL (`query parameters`) para aplicar filtros:

* **Filtrar por Equipo:** Se añade el parámetro `team` a la URL (ej: `/api/v1/player?team=Liverpool`).
* **Filtrar por Nacionalidad:** Se añade el parámetro `nation` a la URL (ej: `/api/v1/player?nation=Egypt`).
* **Filtrar por Posición:** Se añade el parámetro `position` a la URL (ej: `/api/v1/player?position=Delantero`).
* **Filtrar por Nombre:** Se añade el parámetro `name` a la URL (ej: `/api/v1/player?name=Mohamed%20Salah`).

## Deployment con Jenkins y Docker

El despliegue de la aplicación **GranDT Coach Fantasy PL** se automatiza mediante una **Jenkins Pipeline** que utiliza **Docker** para la contenerización y la gestión de los servicios. La pipeline se define en el `Jenkinsfile` y consta de las siguientes etapas:

### Clonar Repo

* **Objetivo:** Obtener el código fuente del proyecto desde el repositorio de Git en `https://github.com/nahuel-urtasun/grandt.git`, específicamente la rama `master`.
* **Implementación:** Utiliza el plugin `git` de Jenkins para realizar un `checkout` del código.

### Construir Backend

* **Objetivo:** Construir la imagen de Docker para la aplicación backend Spring Boot.
* **Implementación:**
    * Navega al directorio `Backend` dentro del workspace de Jenkins (`dir('Backend')`).
    * Ejecuta el comando `docker build -t grandt-backend:latest .`. Este comando utiliza el `Dockerfile` presente en el directorio `Backend` para construir una imagen de Docker etiquetada como `grandt-backend:latest`.

### Construir Frontend

* **Objetivo:** Construir la imagen de Docker para la aplicación frontend React.
* **Implementación:**
    * Navega al directorio `Frontend` dentro del workspace de Jenkins (`dir('Frontend')`).
    * Ejecuta el comando `docker build -t grandt-frontend:latest .`. Este comando utiliza el `Dockerfile` presente en el directorio `Frontend` para construir una imagen de Docker etiquetada como `grandt-frontend:latest`.

### Levantar Contenedores

* **Objetivo:** Crear y ejecutar los contenedores Docker para la base de datos PostgreSQL, el backend Spring Boot y el frontend React. Además, inicializar la base de datos PostgreSQL con los datos de jugadores.
* **Implementación:** Utiliza un bloque `script` para ejecutar comandos de Docker de forma secuencial:
    * **Eliminar Contenedores Existentes:** Se intenta eliminar los contenedores con los nombres `grandt-backend`, `grandt-frontend` y `grandt-db` si existen (`docker rm -f <nombre> || true`). El `|| true` asegura que la pipeline no falle si el contenedor no existe.
    * **Crear Volumen para Datos (si no existe):** Se asegura de que el volumen `grandt-data` exista. Si no, se puede crear con `docker volume create grandt-data`.
    * **Copiar Archivos de Inicialización al Volumen:** **Antes de levantar el contenedor de PostgreSQL**, los archivos `players.csv` e `init.sql` deben copiarse al volumen `grandt-data`. Esto se puede hacer en una etapa anterior de la pipeline o directamente en el servidor Jenkins. Asumiendo que estos archivos están disponibles en el workspace de Jenkins, podrías usar comandos como:
      ```bash
      sh 'docker run --rm -v $(pwd)/Backend/src/main/resources/db/data:/mount alpine cp /mount/players.csv grandt-data:_data/'
      sh 'docker run --rm -v $(pwd)/Backend/src/main/resources/db/migration:/mount alpine cp /mount/init.sql grandt-data:_data/'
      ```
      **Nota:** Las rutas exactas dependerán de la estructura de tu repositorio. Estos comandos levantan un contenedor temporal de Alpine Linux, montan el directorio local y el volumen de Docker, y luego copian los archivos.
    * **Levantar PostgreSQL:** Se ejecuta un contenedor de la imagen `postgres:latest` con las siguientes configuraciones:
      ```
      docker run -d --name grandt-db \
      -e POSTGRES_PASSWORD=mysecretpassword \
      -p 5433:5432 \
      -v grandt-data:/docker-entrypoint-initdb.d \
      postgres:latest
      ```
      **Nota Importante:** Al montar el volumen `grandt-data` en `/docker-entrypoint-initdb.d`, PostgreSQL **ejecutará automáticamente los scripts `.sh`, `.sql` o `.sql.gz` que encuentre allí al inicializarse.** Esto significa que tu archivo `init.sql` se encargará de crear el esquema de la base de datos y `players.csv` (probablemente mediante un script en `init.sql` o un script `.sh` también dentro del volumen) se utilizará para poblar la tabla de jugadores.
    * **Esperar Inicialización de PostgreSQL:** Se introduce una pausa de 20 segundos (`sleep 20`) para dar tiempo a que el servidor PostgreSQL se inicialice correctamente antes de que el backend intente conectarse.
    * **Levantar Backend:** Se ejecuta un contenedor de la imagen `grandt-backend:latest` con las siguientes configuraciones:
      ```
      docker run -d --name grandt-backend -p 8081:8080 \
      --link grandt-db:db \
      grandt-backend:latest
      ```
      **Nota:** `--link` es una característica legacy de Docker; en redes Docker más modernas, se recomienda usar redes definidas por el usuario. El backend se expone en el puerto 8081 del host.
    * **Levantar Frontend:** Se ejecuta un contenedor de la imagen `grandt-frontend:latest` con las siguientes configuraciones:
      ```
      docker run -d --name grandt-frontend -p 3000:3000 \
      grandt-frontend:latest
      ```
      El frontend se expone en el puerto 3000 del host.

### post (always)

* **Objetivo:** Realizar acciones después de que la pipeline haya finalizado, independientemente del resultado.
* **Implementación:** Simplemente imprime el mensaje `'Pipeline completo.'` en la consola de Jenkins.
