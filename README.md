GranDT Coach Fantasy PL
GranDT Coach Fantasy PL is a web application designed to make it easy to search and view player statistics from the English Premier League. Ideal for Fantasy Football enthusiasts—known as Gran DT in Argentina—this tool allows users to quickly find players by team, position, or nationality and access detailed data to make informed decisions when building their fantasy teams.

Backend

This project uses Spring Boot to build a RESTful API that manages football player information for a Gran DT Fantasy–style application focused on the Premier League.

Main Technologies:

Java: Main programming language.

Spring Boot: Framework for rapid Java application development.

Spring Data JPA: Abstraction layer for data persistence using JPA.

Hibernate: JPA implementation used by Spring Data JPA.

PostgreSQL: Relational database for storing player data.

Maven: Tool for project and dependency management.

Spring Web: For creating REST APIs.

Spring Test: For unit and integration testing.

Main API Endpoints:

GET /api/v1/player: Retrieves the list of players. Supports the following query parameters:

name: Filter by player name (partial, case-insensitive).

team: Filter by team name (case-sensitive).

position: Filter by player position (case-sensitive).

nation: Filter by nationality (partial, case-insensitive).

POST /api/v1/player: Adds a new player. Expects a JSON object in the request body with the player’s information.

PUT /api/v1/player: Updates an existing player’s information. Expects a JSON object with the updated details (usually identified by name).

DELETE /api/v1/player/{playerName}: Deletes a player by name.

Example Player Object (Premier League – JSON):

{
  "name": "Mohamed Salah",
  "nation": "Egypt",
  "position": "Forward",
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

Frontend

The frontend is built with React and uses axios to communicate with the backend API.

Main Technologies:

React: JavaScript library for building user interfaces.

axios: Promise-based HTTP client for making requests to the backend.

SCSS: CSS preprocessor for styling.

React Router (implicit through navigation with parameters): For navigating between views and managing URL-based filtering.

State Management with Hooks (useState, useEffect): For managing component state and performing side effects like API calls.

API Interaction:
The frontend sends GET requests to /api/v1/player to retrieve player data, using URL query parameters for filtering:

Filter by Team: /api/v1/player?team=Liverpool

Filter by Nationality: /api/v1/player?nation=Egypt

Filter by Position: /api/v1/player?position=Forward

Filter by Name: /api/v1/player?name=Mohamed%20Salah

Deployment with Jenkins and Docker

The deployment of GranDT Coach Fantasy PL is automated through a Jenkins Pipeline that uses Docker for containerization and service management.
The pipeline is defined in a Jenkinsfile and consists of the following stages:

Clone Repository

Goal: Retrieve the project source code from the Git repository at
https://github.com/nahuel-urtasun/grandt.git
 (branch master).

Implementation: Uses Jenkins’ Git plugin to check out the code.

Build Backend

Goal: Build the Docker image for the Spring Boot backend.

Implementation:

Navigate to the Backend directory.

Run:

docker build -t grandt-backend:latest .


Uses the Dockerfile inside Backend to build the image.

Build Frontend

Goal: Build the Docker image for the React frontend.

Implementation:

Navigate to the Frontend directory.

Run:

docker build -t grandt-frontend:latest .


Uses the Dockerfile inside Frontend.

Run Containers

Goal: Create and start the Docker containers for PostgreSQL, backend, and frontend, and initialize the database with player data.

Implementation:

Remove Existing Containers:

docker rm -f grandt-backend grandt-frontend grandt-db || true


Ensure Volume Exists:

docker volume create grandt-data


Copy Initialization Files:
Assuming players.csv and init.sql exist in Jenkins’ workspace:

sh 'docker run --rm -v $(pwd)/Backend/src/main/resources/db/data:/mount alpine cp /mount/players.csv grandt-data:_data/'
sh 'docker run --rm -v $(pwd)/Backend/src/main/resources/db/migration:/mount alpine cp /mount/init.sql grandt-data:_data/'


Start PostgreSQL:

docker run -d --name grandt-db \
-e POSTGRES_PASSWORD=mysecretpassword \
-p 5433:5432 \
-v grandt-data:/docker-entrypoint-initdb.d \
postgres:latest


When the volume grandt-data is mounted at /docker-entrypoint-initdb.d, PostgreSQL automatically executes .sh, .sql, or .sql.gz scripts during initialization.

Wait for Database Startup:

sleep 20


Start Backend:

docker run -d --name grandt-backend -p 8081:8080 \
--link grandt-db:db \
grandt-backend:latest


Note: --link is deprecated; modern setups use user-defined networks. The backend is exposed on port 8081.

Start Frontend:

docker run -d --name grandt-frontend -p 3000:3000 \
grandt-frontend:latest


The frontend is exposed on port 3000.

Post (always)

Goal: Run actions after the pipeline completes, regardless of the outcome.

Implementation: Prints a simple message:

Pipeline complete.
