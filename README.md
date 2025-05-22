
/*
# Reward Points Program

## Description
This Spring Boot application calculates reward points per customer per month and total,
based on their purchase transactions in 3 months.

## Points Calculation Rules
- 2 points per dollar spent over $100.
- 1 point per dollar spent between $50-$100.
- $120 purchase = (2 * 20) + (1 * 50) = 90 points

## Features
- Calculate monthly and total reward points per customer.
- RESTful API endpoints to get getAllCustomerRewards/getCustomerRewardsById
- Validation and exception handling with clean API error responses.
- Sample data loader for quick startup.
- Unit tests with JUnit and Mockito. 
- added swagger for api documentation

- Project Structure:

```
reward-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── retailer/
│   │   │           └── reward_service/
│   │   │               ├── controller/
│   │   │               │   └── RewardController.java
│   │   │               ├── service/
│   │   │               │   ├── RewardService.java
│   │   │               │   └── RewardServiceImpl.java
│   │   │               ├── entities/
│   │   │               │   ├── Customer.java
│   │   │               │   └── Transaction.java
│   │   │               ├── dtos/
│   │   │               │   └── RewardResponse.java
│   │   │               ├── exceptions/
│   │   │               │   ├── CustomerNotFoundException.java
│   │   │               │   ├── ErrorDetails.java
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   └── InvalidTransactionAmountException.java
│   │   │               ├── repositories/
│   │   │               │   ├── TransactionRepository.java
│   │   │               │   └── CustomerRepository.java
│   │   │               ├── config/
│   │   │               │   └── DataLoader.java
│   │   │               └── RewardServiceApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │      
│   └── test/
│       └── java/
│           └── com/
│               └── retailer/
│                   └── reward_service/
│                       ├── service/
│                       │   └── RewardServiceImplApplicationTest.java
│                       └── controller/
│                           └── RewardControllerIntegrationTest.java (optional)
├── .gitignore
├── README.md
├── pom.xml / build.maven

```


## Api Endpoints
- GET /api/rewards/customers

### Description
This endpoint returns reward point details for customers:

- If a `customer ID` is **provided**, it returns reward points for that specific customer.
- If no `customer ID` is provided, it returns reward points for **all customers**.

### 🔄 Response

- **HTTP 200 OK**
- Returns a JSON array of reward information (`RewardResponse` objects).
    - If `id` is provided, the array contains one item.
    - If `id` is not provided, it contains rewards for all customers.

## Response Body Example (for all customers)
```json
[
  {
    "customerId": 101,
    "customerName": "Alice",
    "monthlyRewards": {
      "March": 120,
      "April": 90
    },
    "totalRewards": 210
  },
  {
    "customerId": 102,
    "customerName": "Bob",
    "monthlyRewards": {
      "March": 150,
      "April": 75
    },
    "totalRewards": 225
  }
]

## Response Body Example (for specific customer)
```json

[
  {
    "customerId": 101,
    "customerName": "Alice",
    "monthlyRewards": {
      "March": 120,
      "April": 90
    },
    "totalRewards": 210
  }
]
```


## How to Run
1. Clone repository
2. Run `mvn clean install`
3. Run `mvn spring-boot:run`

## Tech Stack
- Java 17
- Spring Boot 3
- H2 Database
- JPA, REST
- JUnit, Mockito

## Notes
- Target and bin directories excluded from Git
*/