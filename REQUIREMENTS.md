# Requirements — Local Food Shop

## 1. Programming Language

- **Java:** 21

The Gradle build files specify a Java 21 toolchain across the main modules.

A compatible JDK installation is required.

## 2. Build System

- **Build system:** Gradle
- **Gradle wrapper version:** 8.14.3

Each module contains its own Gradle build configuration and wrapper files.

Using the included Gradle wrapper is recommended instead of relying on a separately installed Gradle version.

## 3. Frameworks and Libraries

### Main Application — `specialFood`

- Spring Boot: 3.5.8
- Spring Boot Web
- Spring Data JPA
- SpringDoc OpenAPI UI: 2.8.13
- H2 Database

### Client Application — `specialFood-client`

- Spring Boot: 3.5.9
- Spring Boot Web
- Spring Boot Thymeleaf

The module includes a console client and a web client.

### APP Provider — `app`

- Java 21
- Jackson Databind: 2.16.1
- SLF4J API: 2.0.12
- Logback Classic: 1.4.14
- JUnit 5 for testing

This module uses the Gradle `application` plugin and exposes a socket-based server.

### FarCoop Provider — `farCoop`

- Spring Boot: 3.5.7
- Spring Boot Web
- Spring Data JPA
- SpringDoc OpenAPI UI: 2.8.13
- H2 Database

## 4. Database

The Spring Boot modules use H2 for persistence.

The configuration files specify file-based database locations for the main application and FarCoop provider.

- Main application database: `./data/SFdb`
- FarCoop database: `./data/farCoopdb`

The application properties configure schema generation through Hibernate.

**Note:** Database initialization and schema-generation settings may reset or recreate data. Review the relevant `application.properties` files before using the project with data that must be preserved.

## 5. Configured Ports

The included application configuration files specify the following ports:

| Component | Port | Communication |
|---|---:|---|
| Main application (`specialFood`) | 8082 | HTTP / REST |
| FarCoop provider (`farCoop`) | 8084 | HTTP / REST |
| APP provider (`app`) | 8085 | TCP socket |

The web client is configured to communicate with the main application at `http://localhost:8082`.

Make sure these ports are available before starting the components.

## 6. Running the Application

Each component is built separately. On Windows, the included Gradle wrapper can generally be invoked from the corresponding module directory.

### Main application

From `specialFood/`:

```powershell
.\gradlew.bat bootRun
```

### FarCoop provider

From `farCoop/`:

```powershell
.\gradlew.bat bootRun
```

### APP provider

From `app/`:

```powershell
.\gradlew.bat run
```

### Web client

From `specialFood-client/`:

```powershell
.\gradlew.bat bootRun
```

### Console client

From `specialFood-client/`:

```powershell
.\gradlew.bat runConsoleClient
```

These commands follow the build configurations included in the project. The full system should be tested from a clean environment before publishing these instructions as verified execution steps.

## 7. Startup Order

The following order is a suggested starting point based on the configured component dependencies:

1. Start the APP provider.
2. Start the FarCoop provider.
3. Start the main application.
4. Start the console or web client.

If a component fails to start, check its logs, configured ports, and connection settings.

## 8. Configuration Notes

- Each component has its own Gradle build file.
- Spring Boot versions differ between modules; retain the module-specific versions unless you deliberately update and test the project.
- Database paths are relative to the working directory.
- The APP provider uses TCP sockets, while the main application and FarCoop expose HTTP endpoints.
- Some application settings may need to be adjusted when running the components on different machines.

## 9. Verified Environment

The Java, Gradle, Spring Boot, and dependency versions listed above are based on the build configuration files included in the repository. The ports are based on the included application properties and Gradle configuration.

They describe the supplied project configuration and should not be interpreted as a guarantee that every module has been tested together on a clean machine.