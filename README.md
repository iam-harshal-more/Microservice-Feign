# How to Run the Application

1️Start Eureka Server

    cd eureka-server

    mvn spring-boot:run

📍 Access Eureka Dashboard: http://localhost:8761

2️⃣ Start User Service

    cd user-service

    mvn spring-boot:run
    
📍 Runs on: http://localhost:8081

3️⃣ Start Order Service

    cd order-service
    
    mvn spring-boot:run
📍 Runs on: http://localhost:8082

# Test APIs with Postman

Create a User (User Service)

      URL: http://localhost:8081/api/users
  	
      Method: POST
  	
      Headers: Content-Type-application.json

      Body: 
          {
          "name": "Harshal More",
          "email": "harshal@gmail.com",
          "phone": "1234567890",
          "address": "Pune,Maharashtra, 411025"
          }
Get All Users

    URL: http://localhost:8081/api/users
    
    Method: GET

Create an Order (Order Service with Feign Client)

      URL: http://localhost:8082/api/orders
      Method: POST
      Headers: Content-Type-application.json
      Body:
        {
          "userId": 2,
          "productName": "Mobile",
          "quantity": 5,
          "price": 10000.99
        }
Get All Orders (with User Info via Feign)

     URL: http://localhost:8082/api/orders
     Method: GET http://localhost:8082/api/orders



