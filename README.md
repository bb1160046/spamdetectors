# 📱 Spam Detector API

A **Spring Boot REST API** to:
- Register & Login users  
- Mark phone numbers as spam  
- Search users & contacts  

Built with:
- **Spring Security (JWT)**  
- **Spring Data JPA** (H2 in-memory database)  
- **Swagger** for API documentation  

---

## 🚀 Getting Started

### 1. Import as Maven Project
1. Open **Eclipse**  
2. Navigate to: `File -> Import -> Maven -> Existing Maven Projects`  
3. Select your project folder → **Finish**

### 2. Build the Project
1. Right-click on the project → `Run As -> Maven clean`  
2. Then → `Run As -> Maven install`

### 3. Run the Application
- Right-click on **`SpamdetectorApplication.java`** → `Run As -> Java Application`  

The app will start on:  
👉 [http://localhost:8080](http://localhost:8080)

---

## 🔗 Important URLs

| Feature      | URL |
|--------------|--------------------------------------------|
| Swagger UI   | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| H2 Console   | [http://localhost:8080/h2-console](http://localhost:8080/h2-console) |

---

## 🗄️ H2 Database Settings
- **JDBC URL**: `jdbc:h2:mem:spamdb`  
- **Username**: `udboy`  
- **Password**: `udboy`  

Click **Connect** to access the in-memory DB.

---

## ✅ Usage
1. Open **Swagger UI**  
2. Register & Login  
3. Copy your **JWT token**  
4. Click **Authorize** in Swagger to test secured APIs  
5. View all stored data in the **H2 Console**

---

## 📌 Tech Stack
- Java 21+
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- H2 Database
- Swagger (OpenAPI)

---

## 📖 License
This project is licensed under the **MIT License**.
