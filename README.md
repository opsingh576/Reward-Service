
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
- RESTful API endpoints to get rewards and customer details.
- Validation and exception handling with clean API error responses.
- Sample data loader for quick startup.
- Unit tests with JUnit and Mockito. 
- added swagger for api documentation

- Project Structure:
  - src/main/java/com/rewardservice
    - controller
    - model
    - repository
    - service
    - exception
    - config
  - src/test/java/com/rewardservice
    - controller
    - service

## Api Endpoints
- GET /api/rewards - Get reward summaries for all customers.
- GET /api/rewards/customer/{customerId} - Get rewards for specific customer.
- GET /api/rewards/customer/details/{customerId} - Get customer details.
- GET /api/rewards/customer/AllCustomerDetails  - Get all customer details.


## API Endpoint-1 Example (rewards for all customers)
```
GET /api/rewards
## Response Format
```
[
  {
     "customerId": 1,
     "customerName": "Alice",
     "monthlyPoints": {
       "May": 90,
       "April": 25
     },
     "totalPoints": 115
   },
  ...
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