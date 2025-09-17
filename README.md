# 🎬 Movies Full Stack – Spring Boot + Angular  

A complete movie management system built with **Spring Boot** (Backend) and **Angular** (Frontend).  
It features user authentication, role-based dashboards, OMDb API integration, and a responsive UI.  

---

## 🎥 Demo

[![Movie Full Stack Demo](<img width="1917" height="883" alt="image" src="https://github.com/user-attachments/assets/938dd7c6-fcc3-423f-8c37-2415582d5201" />
)](https://www.youtube.com/watch?v=1o4w-zwLe0I&t=3s)

*Click the image above to watch the demo video on YouTube*  

---

## 🚀 Features  

- Add, search, and manage movies  
- Import movie data from OMDb API  
- User authentication (Admin/User roles)  
- Movie ratings system  
- Responsive Angular dashboard (Admin & User views)  
- Confirmation dialogs for delete operations  
- Swagger UI for interactive API docs  

---

## 🛠️ Tech Stack  

| Layer     | Technology |
|-----------|------------|
| **Backend** | Java 17, Spring Boot 3, Spring Data JPA, PostgreSQL, Liquibase, MapStruct, Lombok, Swagger/OpenAPI 3 |
| **Frontend**| Angular 18, TypeScript 5, Responsive UI |
| **Other**   | Maven (build), OMDb API integration |

---

## 📋 Prerequisites  

- **Java 17** or higher  
- **Maven 3.6** or higher  
- **PostgreSQL 12** or higher  
- **Node.js 18+ / npm**  
- **OMDb API Key** (get one at [OMDb API](http://www.omdbapi.com/apikey.aspx))  

---

## ⚙️ Setup Guide  

### 1️⃣ Clone the Repository  

```bash
git clone https://github.com/esraaAmr/Movies-Full-Stack-SpringBoot-Angular.git
cd Movies-Full-Stack-SpringBoot-Angular
```

### 2️⃣ Backend Setup

Navigate to the backend folder

```bash
cd Movie-backend
```

Install PostgreSQL (if not already installed).

Create the Database

```sql
CREATE DATABASE moviedb;
```

Update Database Credentials
Edit `src/main/resources/application.properties` if needed:

```properties
spring.datasource.username=postgres
spring.datasource.password=password
```

Build & Run Backend

```bash
mvn clean install
mvn spring-boot:run
```

Backend runs at: http://localhost:8081

Swagger UI: http://localhost:8081/docs

### 3️⃣ Frontend Setup

Navigate to the frontend folder

```bash
cd ../Movie-frontend
```

Install Dependencies

```bash
npm install
```

Run Frontend

```bash
npm start
```

Frontend runs at: http://localhost:4200

### Backend URL
The Angular app uses http://localhost:8081 as its API URL (configured in `src/environments/environment.ts`):

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8081'
};
```

### 4️⃣ Default Login Credentials

| Role  | Username | Password |
|-------|----------|----------|
| Admin | admin    | admin123 |
| User  | user     | user123  |

---

## 🏗️ Project Structure

```bash
Movies-Full-Stack-SpringBoot-Angular/
├── Movie-backend/   # Spring Boot backend
└── Movie-frontend/  # Angular frontend
```

## ✅ Quick Start

```bash
# 1. Start PostgreSQL and create moviedb
# 2. cd Movie-backend && mvn spring-boot:run
# 3. cd Movie-frontend && npm start
# 4. Open http://localhost:4200 and login with admin/user credentials
```
