package client.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Main Spring Boot application class for the Special Foods Web Client
 * This application provides a web interface using Thymeleaf for users to:
 * - Login to the Special Foods platform
 * - Search for products by various criteria
 * - View product details
 * - Purchase products
 */
@SpringBootApplication
@ComponentScan(basePackages = "client")
public class WebClientApplication {
    
    /**
     * Creates a RestTemplate bean for making HTTP requests
     * @return RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(WebClientApplication.class, args);
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     Special Foods - Web Client Application Started!        ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  Web Interface: http://localhost:8080                      ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  Demo Users:                                               ║");
        System.out.println("║    - A / B                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}