# GitHubApi
## Overview
This project is a Spring Boot 3 application built with Java 21 that provides a RESTful API for interacting with GitHub's API. The application is containerized using Docker for easy deployment and scalability.
## Features
- Java 21 and Spring Boot 3: Leverage the latest features of Java and Spring Boot.
- GitHub API Integration: Fetch and manage GitHub repositories and users.
- Docker: Containerized application for consistent deployment.
## Technologies used
- Java 21
- Spring Boot 3
- Maven
- Docker
## Getting Started
#### Clone the Repository
```
git clone https://github.com/oneday1302/GitHubApi.git
```
#### Build and Run the Application
1. Build the project:
   ```
   mvn package
   ```
2. Build the Docker image:
   ```
   docker build -t github-api .
   ```
3. Run the Docker container:
   ```
   docker run -p 8080:8080 github-api
   ```
## Endpoints
- GET /api/v1/github/{username}: Retrieve repositories for a specific GitHub user.
#### Example Request
  ```
  curl -X GET "http://localhost:8080/api/v1/github/oneday1302" -H "accept: application/json"
  ```
#### Example Response
  ```
  [
    {
        "name": "Anagram",
        "owner_login": "oneday1302",
        "branches": [
            {
                "name": "master",
                "last_commit_sha": "facd1dec169c7c400c25cd07cf3c04ecc299f27f"
            }
        ]
    },
    {
        "name": "CarManager",
        "owner_login": "oneday1302",
        "branches": [
            {
                "name": "master",
                "last_commit_sha": "a813d97caf4a852cec5940a1e5cce860b0b50085"
            }
        ]
    }
]
```
## Error Handling
The API uses standard HTTP status codes to indicate the success or failure of an API request.
Common status codes include:
- 200 OK: The request was successful.
- 404 Not Found: The requested resource could not be found.
