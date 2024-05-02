package dev.xxj.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Application is the starting point of this Spring Boot application.
 * It is annotated with {@link SpringBootApplication} and {@link EnableCaching}.
 * The main method is used to run the application.
 * <p>
 * The application is a logistics system that manages goods and warehouses.
 * It provides basic CRUD operations for goods and warehouses.
 * Moreover, it enables the user to move goods between warehouses and check the storage status.
 * The system is designed to be simple and easy to use.
 * The user can interact with the system through a RESTful API.
 * The system is built with Spring Security, Spring Data JPA, and Azure Database for MySQL,
 * integrated with Swagger for API documentation,
 * and use Spring's default cache mechanism to improve performance.
 * <p>
 *
 * @author Frank-Xiao
 * @version 1.0
 */
@SpringBootApplication
@EnableCaching
public class Application {

    /**
     * Starting point of the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
