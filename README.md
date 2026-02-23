# E-commerce Microservices

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Liquibase
- WebClient
- JUnit 5
- Mockito

## How to Run

1. mvn clean install
2. Run InventoryServiceApplication
3. Run OrderServiceApplication

Inventory → http://localhost:8081
Order → http://localhost:8082

## API Documentation

GET /inventory/{productId}
POST /inventory/update
POST /order

## Running Tests

mvn test