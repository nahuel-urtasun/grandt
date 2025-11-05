🏆 GranDT Coach Fantasy PL

GranDT Coach Fantasy PL is a web application designed to simplify the search and visualization of player statistics from the English Premier League.
Ideal for Fantasy Football enthusiasts — known in Argentina as Gran DT — this tool allows users to quickly find players by team, position, or nationality, and access detailed data to make informed decisions when building their fantasy teams.

⚙️ Backend

The backend is built with Spring Boot, providing a RESTful API that manages football player information for the Premier League Fantasy environment.

🧩 Main Technologies

Java – Main programming language

Spring Boot – Rapid application development framework

Spring Data JPA – Data persistence abstraction layer

Hibernate – JPA implementation

PostgreSQL – Relational database for player data

Maven – Build and dependency management tool

Spring Web – REST API creation

Spring Test – Unit and integration testing

📡 Main API Endpoints
GET /api/v1/player

Retrieves a list of players with optional filters:

Parameter	Description	Case Sensitivity
name	Filters by player name (partial match)	No
team	Filters by team name	Yes
position	Filters by player position	Yes
nation	Filters by nationality (partial match)	No
POST /api/v1/player

Adds a new player.
Expects a JSON object in the request body with player details.

PUT /api/v1/player

Updates an existing player’s information.
Expects a JSON object with the updated data (usually identified by name).

DELETE /api/v1/player/{playerName}

Deletes a player by name.

🧠 Example Player Object (Premier League)
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

💻 Frontend

The frontend is built with React and uses axios for communication with the backend API.

🧩 Main Technologies

React – JavaScript library for building user interfaces

axios – HTTP client for API requests

SCSS – CSS preprocessor for component styling

React Router – (implicit) for navigation and URL-based filtering

Hooks (useState, useEffect) – For state management and side effects

🌐 API Interaction

The frontend sends GET requests to /api/v1/player with query parameters for filtering:

Filter Type	Example
Team	/api/v1/player?team=Liverpool
Nationality	/api/v1/player?nation=Egypt
Position	/api/v1/player?position=Forward
Name	/api/v1/player?name=Mohamed%20Salah
🚀 Deployment with Jenkins & Docker

Deployment is automated via a Jenkins Pipeline that uses Docker for containerization and service orchestration.
The process is defined in a Jenkinsfile with the following stages:

🧾 1. Clone Repository

Goal: Retrieve the project from GitHub
📦 Repo: https://github.com/nahuel-urtasun/grandt.git

Branch: master

Implementation:
Uses Jenkins’ Git plugin for checkout.

🏗️ 2. Build Backend

Goal: Build Docker image for the Spring Boot backend.

Commands:

cd Backend
docker build -t grandt-backend:latest .

🖥️ 3. Build Frontend

Goal: Build Docker image for the React frontend.

Commands:

cd Frontend
docker build -t grandt-frontend:latest .

🐳 4. Run Containers

Goal: Start containers for PostgreSQL, backend, and frontend, and initialize the database.

Steps:

🧹 Remove Existing Containers
docker rm -f grandt-backend grandt-frontend grandt-db || true

💾 Ensure Volume Exists
docker volume create grandt-data

📂 Copy Initialization Files
docker run --rm -v $(pwd)/Backend/src/main/resources/db/data:/mount alpine cp /mount/players.csv grandt-data:_data/
docker run --rm -v $(pwd)/Backend/src/main/resources/db/migration:/mount alpine cp /mount/init.sql grandt-data:_data/

🛢️ Start PostgreSQL
docker run -d --name grandt-db \
-e POSTGRES_PASSWORD=mysecretpassword \
-p 5433:5432 \
-v grandt-data:/docker-entrypoint-initdb.d \
postgres:latest


PostgreSQL will automatically execute any .sh, .sql, or .sql.gz scripts inside /docker-entrypoint-initdb.d.

⏳ Wait for Database Initialization
sleep 20

⚙️ Start Backend
docker run -d --name grandt-backend -p 8081:8080 \
--link grandt-db:db \
grandt-backend:latest


Note: --link is deprecated; user-defined Docker networks are preferred.

🌍 Start Frontend
docker run -d --name grandt-frontend -p 3000:3000 \
grandt-frontend:latest

🧩 5. Post Actions (always)

Goal: Execute final steps regardless of pipeline result.

Implementation:

echo "Pipeline complete."

✅ Result

After the pipeline finishes:

The database is initialized with player data.

The backend API runs on port 8081.

The frontend React app runs on port 3000.

Jenkins provides logs and status for every stage.
