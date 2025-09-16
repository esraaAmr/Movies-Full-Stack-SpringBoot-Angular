# Movie Backend API

A Spring Boot REST API for managing movies, users, and ratings with integration to the OMDb API for movie data.

## 🚀 Features

- **Movie Management**: Add, search, and manage movies in your database
- **OMDb Integration**: Import movie data from the Open Movie Database (OMDb) API
- **User Authentication**: Basic user login functionality
- **Rating System**: Users can rate movies with scores
- **Database Management**: PostgreSQL with Liquibase for database migrations
- **API Documentation**: Swagger UI for interactive API documentation
- **CORS Support**: Configured for cross-origin requests

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **PostgreSQL**
- **Liquibase** (Database migrations)
- **MapStruct** (Object mapping)
- **Lombok** (Code generation)
- **Swagger/OpenAPI 3** (API documentation)
- **Maven** (Build tool)

## 📋 Prerequisites

Before running the application, make sure you have:

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- An OMDb API key (get one at [OMDb API](http://www.omdbapi.com/apikey.aspx))

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/esraaAmr/Movie-Backend.git
cd Movie-Backend
```


### 2️⃣ Database Setup

1. **Install PostgreSQL**  
   Make sure PostgreSQL is installed and running on your machine.

### 2️⃣ Create the Database and Initial Users

1. **Create the Database**  
   Open your PostgreSQL client or terminal and run:

   ```sql
   CREATE DATABASE moviedb;

2. **Initial user**

   ```sql
   INSERT INTO users (username, password, role)
   VALUES ('admin', 'admin123', 'ADMIN'), ('user', 'user123', 'USER');

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8081`

## 📚 API Documentation

Once the application is running, you can access the interactive API documentation at:
- **Swagger UI**: `http://localhost:8081/docs`

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/example/movie/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── mapper/          # MapStruct mappers
│   │   ├── model/
│   │   │   ├── dto/         # Data Transfer Objects
│   │   │   └── entity/      # JPA entities
│   │   ├── repository/      # JPA repositories
│   │   ├── service/         # Business logic
│   │   └── MovieApplication.java
│   └── resources/
│       ├── application.properties
│       └── db/changelog/    # Liquibase migrations
└── test/                    # Test classes
```
