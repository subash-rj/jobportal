# 💼 Job Portal System

A Spring Boot backend project for job posting and job applications.

## 🛠 Tech Stack
- Java 17
- Spring Boot 3
- Spring Security (JWT)
- PostgreSQL / H2
- JUnit & Mockito

## 🎯 Features

### 👤 Candidate
- Register/Login
- Apply to jobs (with resume upload)
- View application status

### 👨‍💼 Admin
- Post jobs
- View all applications
- Accept/Reject applications

## 📂 Resume Upload
- Files are saved to `/resumes/` folder

## 🚀 How to Run

```bash
mvn clean install
mvn spring-boot:run
