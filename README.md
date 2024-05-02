# Logistics Management System (Backend)

----

## Description

This is the backend of the Logistics Management System.
It provides a RESTful API for the frontend to interact with the database.

## Installation

1. Clone the repository and prepare an environment with Java 17+ and maven.
2. Open it in your favorite IDE and run the main method in the Application class.
3. Create an Azure Database for MySQL database and connect it to the project.
4. Remember to **_load .env environment variables_** before running the application, otherwise the _Datasource_ bean
   won't be created successfully.
5. If you are using [IntelliJ IDEA](https://www.jetbrains.com/idea/), you can run the application
   by using the saved run configuration - Application.
6. open the browser and navigate to `http://localhost:8080/` and it should lead you to the login page
7. Use one of the following credentials to login:
    - Username: `admin`, Password: `password`
    - Username: `user`, Password: `password`
8. If you login with the right credentials, you should be able to see an index page
   rendered by [Thymeleaf](https://www.thymeleaf.org/).
9. You can also use api testing tool like [Postman](https://www.postman.com/) or IntelliJ IDEA's built-in tool
   [HttpClient](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html) to test the API.

## API Documentation

This project contains an OpenAPI documentation at the root of the project.
If you use IntelliJ IDEA, you can open the file with [Swagger](https://swagger.io/) or
[Redoc](https://redocly.com/redoc/) rendering.

## Database Schema

Don't worry about the database schema, the application will create the tables automatically by
[Spring Data JPA](https://spring.io/projects/spring-data-jpa/) when you run it.

## Technologies

This project uses the following key dependencies:

- Spring Boot starter 3.2
- Spring Data JPA
- Spring Security
- Spring Web
- Spring Validation
- Lombok
- Thymeleaf

and other dependencies for database connection, logging, etc.

## Contributors

By now, the main contributor is [Frank-Xiao](https://github.com/Frank-Xiao2002), an undergraduate student at
[BJTU](https://www.bjtu.edu.cn/).