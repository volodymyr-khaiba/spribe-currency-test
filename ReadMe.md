# Currency exchange rates

## Overview
This project is a Spring Boot application designed to display currency exchange rates. It includes functionality such as:
- get a list of currencies used in the project;
- add new currency for getting exchange rates;
- get exchange rates for a currency;
- get exchange rate for a base and to currencies.

## Prerequisites
Before running the project, ensure you have the following installed:
- Java 17 (or your required version)
- Gradle
- PostgreSQL, H2(for testing)
- Docker
- Postman (for API testing)

## Running the Application

1. **Clone the repository**:
```bash
   https://github.com/volodymyr-khaiba/spribe-currency-test.git
```
2.	**Set up the database**:
      Run docker with PostgreSQL, you can start it using docker-compose:
```bash 
docker-compose up
```
3.	**Build the project**:
      To build the project using Gradle, run:
```bash 
./gradlew build
```
4.	**Run the application**:
      After building the project, you can run the application using:
```bash 
./gradlew bootRun
```
5.	**Access the application**:
      Once the application is running, you can access it at http://localhost:8080/api/v1/currency/all

### Running Tests

To run the tests, execute:
```bash 
./gradlew test
```
### Database Configuration
```yaml
spring.datasource.url=jdbc:postgresql://localhost:5432/currency
spring.datasource.username=asd
spring.datasource.password=asd
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Liquibase Database Migrations
This project uses Liquibase for managing database migrations. Migration scripts are stored in the src/main/resources/db/changelog/version directory.

### Using Postman for API Testing
#### Importing the Postman Collection

1.	Download and install Postman.
2. Open Postman and import the **Currency collection.postman_collection.json** file provided in this repository.
3. This collection contains requests for CRUD operations on the Currency API.

#### Available Endpoints

- GET All Currencies:
- -	URL: http://localhost:8080/api/v1/currency/all
- -	Method: GET
- -	Description: Fetches all currency data from the system.
- -	Response: Status 200 OK with JSON data of currencies.
- GET Specific Currency Rates:
- - URL: http://localhost:8080/api/v1/exchange-rate/{currency}/rates
- - Method: GET
- - Description: This retrieves currency rates (currently set to fetch all, can be modified to fetch specific rates).
- - Response: Status 200 OK with JSON data.
- POST Add a Currency:
- - URL: http://localhost:8080/api/v1/currency
- - Method: POST

## Retry Mechanism with `@Retryable`

This project includes a retry mechanism using Spring's `@Retryable` annotation, allowing certain methods to retry upon encountering specific exceptions. This is particularly useful when making HTTP calls to external services that might experience intermittent failures.


## Caching Mechanism

This project includes caching as it was requested in the task.
Caching implemented through the Map based cache, but it can be easily replaced with Redis or other cache providers.

### Testing Caching with Postman
You can use Postman to test the caching mechanism by:
1.	Fetching All Currencies (GET): The first time you call the GET /api/v1/currency/all endpoint, it will retrieve data from the database and cache it. Subsequent calls will return the cached result.
```bash 
GET http://localhost:8080/api/v1/currency/all
```
2.	**Fetching Exchange Rate by Currency Code (GET):** This endpoint is also cached based on the currency code you provide. For example:
```bash 
GET http://localhost:8080/api/v1/currency/rate?currencyCode=USD
```

3.	Adding New Currency (POST): When you add a new currency using the POST /api/v1/currency/add endpoint, the cache is cleared to ensure updated data.

```bash 
POST http://localhost:8080/api/v1/currency/add
```

## Swagger 3 (OpenAPI) documentation

Run your Spring Boot application, then navigate to the following URL to access the Swagger UI:

```bash 
http://localhost:8080/swagger-ui/index.html
```

This will allow you to interact with your API directly through a user-friendly interface.