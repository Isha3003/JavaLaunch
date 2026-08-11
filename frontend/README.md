# 🚀 JavaLaunch

JavaLaunch is a full-stack deployment management platform that allows users to deploy, run, monitor, stop, restart, and delete Spring Boot applications directly from GitHub repositories.

The platform automates the application deployment lifecycle by cloning a GitHub repository, building the project, running the generated JAR on an available port, tracking its process ID, and providing deployment controls through a React dashboard.

---

## ✨ Features

- 🚀 Deploy Spring Boot applications directly from GitHub
- 🔨 Automatically build Maven projects
- 🌐 Automatically assign available ports
- ▶️ Start deployed JAR applications
- ⏹️ Stop running applications
- 🔄 Restart deployments
- 🗑️ Delete deployments
- 📋 View application logs
- 🆔 Track running process IDs
- ❤️ Backend health monitoring
- 📊 Deployment statistics dashboard
- 🖥️ React-based web interface
- 🛡️ Environment-based database password configuration

---

## 🏗️ Architecture

JavaLaunch follows a layered Spring Boot architecture.

```text
                    ┌──────────────────────┐
                    │     React Frontend   │
                    │                      │
                    │  Dashboard / UI      │
                    └──────────┬───────────┘
                               │
                               │ REST API
                               ▼
                    ┌──────────────────────┐
                    │   Spring Boot API    │
                    │                      │
                    │    Controller        │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │       Service        │
                    │                      │
                    │ DeploymentService    │
                    │ BuildService         │
                    │ GitCloneService      │
                    │ PortService          │
                    │ RunService           │
                    └──────────┬───────────┘
                               │
                 ┌─────────────┼─────────────┐
                 ▼             ▼             ▼
          ┌────────────┐ ┌────────────┐ ┌────────────┐
          │   GitHub   │ │    Maven   │ │   Process  │
          │ Repository │ │    Build   │ │ Management │
          └────────────┘ └────────────┘ └────────────┘
                               │
                               ▼
                       ┌───────────────┐
                       │     MySQL     │
                       │   Database    │
                       └───────────────┘
🛠️ Tech Stack
Backend
Java 21
Spring Boot
Spring Web
Spring Data JPA
Hibernate
Maven
MySQL
Frontend
React.js
JavaScript
HTML
CSS
Vite
Tools
Git
GitHub
VS Code
Postman / Thunder Client
🔄 Deployment Workflow

When a user provides a GitHub repository, JavaLaunch follows this workflow:

GitHub Repository
       ↓
Clone Repository
       ↓
Build Maven Project
       ↓
Generate JAR
       ↓
Find Available Port
       ↓
Start JAR Process
       ↓
Capture Process ID
       ↓
Save Deployment Information
       ↓
Application Running

The dashboard then allows the user to manage the deployed application.

🎮 Deployment Lifecycle
        ┌─────────────┐
        │   DEPLOY    │
        └──────┬──────┘
               ↓
        ┌─────────────┐
        │   RUNNING   │
        └──────┬──────┘
               │
        ┌──────┼───────────┐
        ↓      ↓           ↓
      OPEN    LOGS       STOP
                           ↓
                       STOPPED
                           │
                         RESTART
                           ↓
                        RUNNING
                           │
                         DELETE
                           ↓
                        REMOVED
📊 Deployment Information

Each deployment stores information such as:

Deployment ID
Project Name
GitHub Repository URL
Assigned Port
Process ID
JAR Path
Deployment Status

Example:

{
  "id": 14,
  "projectName": "PetClinic-Final-Test",
  "port": 8081,
  "status": "RUNNING",
  "processId": 4128
}
📁 Project Structure
javalaunch/
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── DeployForm.jsx
│   │   │   ├── DeploymentCard.jsx
│   │   │   ├── Navbar.jsx
│   │   │   └── StatsCard.jsx
│   │   │
│   │   ├── pages/
│   │   │   └── Dashboard.jsx
│   │   │
│   │   └── services/
│   │       └── api.js
│   │
│   └── package.json
│
├── src/
│   └── main/
│       ├── java/com/javalaunch/
│       │
│       └── resources/
│           └── application.properties
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── .gitignore
🔌 Backend Components
Controller Layer

Handles HTTP requests from the frontend.

DeploymentController
HealthController
Service Layer

Contains the main deployment logic.

DeploymentService
BuildService
GitCloneService
PortService
RunService
Repository Layer

Handles database operations using Spring Data JPA.

DeploymentRepository
Entity

Deployment information is represented using:

Deployment
DTOs

Data transfer objects are used to transfer request/response data between layers.

DeploymentRequest
ApiResponse
❤️ Health Check

JavaLaunch provides a backend health endpoint:

GET /api/deployments/health

Example response:

{
  "status": "UP",
  "message": "JavaLaunch Backend Running Successfully"
}
⚙️ Local Setup
1. Clone the repository
git clone https://github.com/Isha3003/JavaLaunch.git
cd JavaLaunch
2. Configure MySQL

Create a MySQL database:

CREATE DATABASE javalaunch_db;
3. Configure database credentials

The application uses an environment variable for the database password.

spring.datasource.url=jdbc:mysql://localhost:3306/javalaunch_db
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

Set the environment variable before starting the backend.

Windows PowerShell
$env:DB_PASSWORD="YOUR_DATABASE_PASSWORD"
4. Start the backend
.\mvnw.cmd spring-boot:run

Backend runs on:

http://localhost:8080
5. Start the frontend

Open another terminal:

cd frontend

Install dependencies:

npm install

Start React:

npm run dev

Frontend runs on:

http://localhost:5173
🧪 Testing

The deployment lifecycle has been tested through:

Deploy
Open Application
Application Logs
Stop
Restart
Delete

The application also tracks the operating-system process ID of deployed Java applications.

🔐 Security

Sensitive credentials are not hard-coded into the repository.

Database credentials are supplied using environment variables:

DB_PASSWORD

The following are excluded from Git:

.env
.env.*
target/
frontend/node_modules/
frontend/dist/
projects/
🚀 Future Improvements

Possible future improvements include:

Docker-based deployments
User authentication and authorization
Deployment history
Real-time deployment logs
Application health monitoring
CPU and memory monitoring
Cloud deployment support
Multiple database support
Deployment rollback
CI/CD pipeline integration
Production process isolation
👩‍💻 Author

Isha Pandey

B.Tech Computer Science & Engineering

GitHub:
https://github.com/Isha3003

⭐ Project Goal

JavaLaunch was built to understand and implement real-world concepts involved in application deployment, process management, REST APIs, full-stack development, GitHub integration, and backend system design.
