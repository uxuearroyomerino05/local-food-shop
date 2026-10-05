# Local Food Shop

A distributed product-selling application developed in Java as a university project for the **Software Design** course at the University of Deusto.

The application simulates a product marketplace that retrieves products from multiple providers and allows users to interact with the main service through different client interfaces.

## Project Overview

Local Food Shop is organized into several independent application components. The main application manages users, products, and purchases, while external providers simulate separate product-selling services.

The system demonstrates service communication, layered architecture, and the use of abstractions to integrate external providers.

## Main Features

- Product catalogue management.
- User authentication.
- Purchase-related functionality.
- Integration with external product providers.
- REST-based communication.
- Console and web client interfaces.
- Persistence through Spring Data JPA.
- Separate application components with independent responsibilities.

## Architecture

The project is divided into several modules.

### Main Application — `specialFood`

The main application provides the core business functionality.

Responsibilities include:
- Authentication and product-related endpoints.
- Business logic and services.
- Persistence through repositories and entities.
- Integration with external product providers.

### Client Application — `specialFood-client`

Provides user-facing interfaces for interacting with the main application.

Includes:
- A console client.
- A web client.
- Client-side service proxies for communication with the main application.

### APP Provider — `app`

A separate application that simulates an external product provider. It exposes its functionality through a socket-based server.

### FarCoop Provider — `farCoop`

A separate Spring Boot application that simulates another external product provider, using REST endpoints and its own persistence layer.

## Design and Technologies

- **Language:** Java
- **Framework:** Spring Boot
- **API communication:** REST
- **Data persistence:** Spring Data JPA
- **Database:** H2
- **Web client:** Spring Boot and Thymeleaf
- **Build system:** Gradle

The architecture also includes abstractions and design patterns such as:
- Gateway interfaces for external providers.
- Factory-based provider selection.
- Client-side service proxies.
- Separation of controllers, services, repositories, entities, and DTOs.

For dependency and environment details, see [REQUIREMENTS.md](REQUIREMENTS.md).

## Project Structure

- `specialFood/` — Main application.
- `specialFood-client/` — Console and web clients.
- `app/` — APP external provider.
- `farCoop/` — FarCoop external provider.

## Running the Project

The application consists of multiple modules that may need to be started independently.

Before running the complete system:
1. Configure the required Java and Gradle environment.
2. Check the configured ports and application properties.
3. Start the external provider components.
4. Start the main application.
5. Start the console or web client.

Refer to [REQUIREMENTS.md](REQUIREMENTS.md) for versions, configured ports, and additional setup notes.

## Academic Context

This project was developed as part of the **Software Design** course at the University of Deusto in January 2026.

It was designed as an academic exercise in software architecture, design patterns, REST services, and integration between independent application components.

## License

This project is distributed under the MIT License. See [LICENSE](LICENSE) for details.