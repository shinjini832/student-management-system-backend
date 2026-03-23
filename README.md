# 🎓 Student Management System (Spring Boot Backend API)

## 🚀 Overview
This project is a RESTful backend application built using Spring Boot to manage student records. It provides complete CRUD functionality along with advanced backend features like validation, global exception handling, filtering, and pagination. The application follows a layered architecture and uses DTOs to ensure clean separation between API and database models.

---

## 🛠️ Tech Stack
- **Language:** Java  
- **Framework:** Spring Boot  
- **Database:** MySQL  
- **ORM:** Spring Data JPA (Hibernate)  
- **Build Tool:** Maven  
- **API Testing:** Postman  

---

## 📂 Project Architecture

The project follows a clean layered architecture:
Controller → Service → Repository → Database


### 📌 Layers Explained
- **Controller:** Handles HTTP requests and responses  
- **Service:** Contains business logic  
- **Repository:** Handles database interaction using JPA  
- **DTO:** Used to transfer data between layers safely  

---

## 📌 Features Implemented

### ✅ 1. CRUD Operations
- Create student
- Retrieve all students
- Retrieve student by ID
- Update student
- Delete student

---

### ✅ 2. DTO (Data Transfer Object)
- Implemented `StudentRequestDTO` for input
- Implemented `StudentResponseDTO` for output
- Prevents direct exposure of entity
- Improves API security and flexibility

---

### ✅ 3. Validation
- Added validation using annotations:
  - `@NotBlank`
  - `@Email`
  - `@Min`, `@Max`
- Ensures only valid data enters the system

---

### ✅ 4. Global Exception Handling
- Implemented using `@RestControllerAdvice`
- Custom exception: `StudentNotFoundException`
- Handles:
  - Resource not found
  - Validation errors
- Returns structured error responses

---

### ✅ 5. Filtering
Dynamic filtering using query parameters:
``GET /students/filter?department=CSE
GET /students/filter?year=3
GET /students/filter?department=CSE&year=3``

## 🔗 API Endpoints

### 🔹 Create Student
``POST /students``

### 🔹 Get All Students
``GET /students``

### 🔹 Get Student by ID
``GET /students/{id}``

### 🔹 Update Student
``PUT /students/{id}``

### 🔹 Delete Student
``DELETE /students/{id}``

### 🔹 Filter Students
``GET /students/filter?department=CSE&year=3``
