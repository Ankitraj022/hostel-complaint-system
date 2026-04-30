# 🏠 Hostel Complaint System (Spring Boot Backend)

A RESTful backend application built using Spring Boot that allows hostel students to register complaints and enables administrators to manage and resolve them efficiently.

---

## 🚀 Features
- 📝 Submit new complaints  
- 📋 View all complaints  
- 🔄 Update complaint status (Pending → Resolved)  
- ❌ Delete complaints  
- ⚙️ REST API-based architecture  
- 🧠 In-memory data handling  

---

## 🛠️ Tech Stack
- Java  
- Spring Boot  
- Maven  
- REST APIs  

---

## 📂 Project Structure
src/main/java/com/hostel/  
│── controller/  
│── service/  
│── model/  
│── HostelComplaintSystemApplication.java  

---

## ⚙️ How to Run

1. Clone the repository  
git clone https://github.com/Ankitraj022/hostel-complaint-system.git  

2. Go to project folder  
cd hostel-complaint-system  

3. Run application  

Windows:  
mvnw.cmd spring-boot:run  

Mac/Linux:  
./mvnw spring-boot:run  

---

## 🌐 Base URL
http://localhost:8080  

---

## 📡 API Endpoints

POST /complaints  
{
  "studentName": "Ankit",
  "roomNumber": "A-101",
  "issue": "Fan not working"
}

GET /complaints  

PUT /complaints/{id}?status=Resolved  

DELETE /complaints/{id}  

---

## 🧪 Testing
Use Postman or any API testing tool  

---

## ⚠️ Limitations
- Data stored in memory (resets on restart)  
- No authentication  
- No database  

---

## 🔮 Future Improvements
- Add MySQL/MongoDB  
- Add authentication (JWT)  
- Build frontend  
- Admin dashboard  

---

## 👨‍💻 Author
Ankit Raj  

---

## ⭐ Support
If you like this project, give it a ⭐ on GitHub
