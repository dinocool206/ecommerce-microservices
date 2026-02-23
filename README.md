# 🛒 E-Commerce Microservices

This repository contains two independent Spring Boot microservices:

- 📦 **Order Service**
- 🏬 **Inventory Service**

The system demonstrates a simple e-commerce backend architecture using microservices, REST APIs, database migrations, and integration testing.

---

# 🏗️ Project Structure

```
ecommerce-microservices/
│
├── order-service/
│   ├── src/
│   ├── pom.xml
│
├── inventory-service/
│   ├── src/
│   ├── pom.xml
│
└── README.md
```

Each service is independently buildable and runnable.

---

# ⚙️ Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (In-Memory)
- Liquibase (Database Migrations)
- JUnit 5
- Maven

---

# 🚀 Project Setup Instructions

## 1️⃣ Clone Repository

```bash
git clone https://github.com/dinocool206/ecommerce-microservices
cd ecommerce-microservices
```

---

## 2️⃣ Run Order Service

```bash
cd order-service
mvn clean install
mvn spring-boot:run
```

Order Service runs on:

```
http://localhost:8080
```

---

## 3️⃣ Run Inventory Service

```bash
cd inventory-service
mvn clean install
mvn spring-boot:run
```

Inventory Service runs on:

```
http://localhost:8081
```

---

# 📘 API Documentation

---

# 🧾 Order Service APIs

### ➤ Place Order

**Endpoint**

```
POST /orders
```

**Request Body**

```json
{
  "productId": 1005,
  "productName": "Smartwatch",
  "quantity": 2,
  "status": "NEW",
  "orderDate": "2025-12-04"
}
```

**Response**

```
200 OK
```

---

### ➤ Get All Orders

```
GET /orders
```

Returns list of all orders.

---

# 📦 Inventory Service APIs

### ➤ Get Inventory Batches Sorted by Expiry Date

**Endpoint**

```
GET /inventory/{productId}
```

**Description**

Returns inventory batches sorted by expiry date (ascending order).

**Example**

```
GET /inventory/1005
```

**Response**

```json
[
  {
    "batchId": 1,
    "productId": 1005,
    "quantity": 50,
    "expiryDate": "2026-01-01"
  },
  {
    "batchId": 2,
    "productId": 1005,
    "quantity": 30,
    "expiryDate": "2026-03-01"
  }
]
```

---

# 🗄 Database Configuration

- H2 in-memory database
- Liquibase manages:
    - Table creation
    - Initial seed data
    - Identity sequence handling

Database resets automatically on application restart.

---

# 🧪 Testing Instructions

Both services include unit and integration tests.

## Run Tests

```bash
mvn clean test
```

Tests include:

- Controller layer validation
- Service layer logic
- Repository integration
- Database migration verification
- Full integration testing using SpringBootTest

H2 in-memory database is used during testing.

---

# 📌 Design Highlights

- Clean layered architecture:
    - Controller → Service → Repository
- Database versioning using Liquibase
- Proper identity sequence management after seed data load
- Independent microservice deployment
- RESTful API design

---

# 🏁 How to Build Both Services

From root folder:

```bash
mvn clean install
```

---

# 📄 Author

Dino Kundukulam

---
