# Requirements — Local Food Shop

[English](#english) | [Español](#español)

<a id="english"></a>
# English

## Development Environment

| Tool | Version |
|---|---|
| Java JDK | 21 |
| Gradle | 8.14.3 (included Gradle Wrapper) |

Each module includes its own Gradle Wrapper configuration.

## Frameworks and Libraries

### Main Application — `specialFood`

| Dependency | Version |
|---|---|
| Spring Boot | 3.5.8 |
| Spring Dependency Management | 1.1.6 |
| Spring Web | Managed by Spring Boot |
| Spring Data JPA | Managed by Spring Boot |
| SpringDoc OpenAPI UI | 2.8.13 |
| H2 Database | Managed by Spring Boot |

### Client Application — `specialFood-client`

| Dependency | Version |
|---|---|
| Spring Boot | 3.5.9 |
| Spring Dependency Management | 1.1.7 |
| Spring Web | Managed by Spring Boot |
| Spring Boot Thymeleaf | Managed by Spring Boot |

### APP Provider — `app`

| Dependency | Version |
|---|---|
| Jackson Databind | 2.16.1 |
| SLF4J API | 2.0.12 |
| Logback Classic | 1.4.14 |
| JUnit Jupiter | 5.9.1 |

This module uses Java TCP sockets.

### FarCoop Provider — `farCoop`

| Dependency | Version |
|---|---|
| Spring Boot | 3.5.7 |
| Spring Dependency Management | 1.1.6 |
| Spring Web | Managed by Spring Boot |
| Spring Data JPA | Managed by Spring Boot |
| SpringDoc OpenAPI UI | 2.8.13 |
| H2 Database | Managed by Spring Boot |

### Demo — `demo`

| Dependency | Version |
|---|---|
| Spring Boot | 3.5.9 |
| Spring Dependency Management | 1.1.7 |
| Spring Boot Starter | Managed by Spring Boot |
| Spring Boot Test | Managed by Spring Boot |

## Database

The main application and FarCoop provider use H2.

The configured database locations are:

- Main application: `./data/SFdb`
- FarCoop provider: `./data/farCoopdb`

Both modules configure Hibernate schema generation through `create-drop`.

## Network Configuration

| Component | Port | Communication |
|---|---:|---|
| Main application (`specialFood`) | 8082 | HTTP / REST |
| FarCoop provider (`farCoop`) | 8084 | HTTP / REST |
| APP provider (`app`) | 8085 | TCP socket |

The client is configured to access the main application through `http://localhost:8082`.

## Additional Notes

- Java 21 is required by the Gradle toolchain configuration.
- Use the included Gradle Wrapper to build and run each module.
- Each module has its own `build.gradle` file and dependency configuration.
- The Spring Boot versions differ between modules and should be kept consistent with their respective build files.

---

<a id="español"></a>
# Español

## Entorno de desarrollo

| Herramienta | Versión |
|---|---|
| Java JDK | 21 |
| Gradle | 8.14.3 (Gradle Wrapper incluido) |

Cada módulo incluye su propia configuración de Gradle Wrapper.

## Frameworks y librerías

### Aplicación principal — `specialFood`

| Dependencia | Versión |
|---|---|
| Spring Boot | 3.5.8 |
| Spring Dependency Management | 1.1.6 |
| Spring Web | Gestionada por Spring Boot |
| Spring Data JPA | Gestionada por Spring Boot |
| SpringDoc OpenAPI UI | 2.8.13 |
| H2 Database | Gestionada por Spring Boot |

### Aplicación cliente — `specialFood-client`

| Dependencia | Versión |
|---|---|
| Spring Boot | 3.5.9 |
| Spring Dependency Management | 1.1.7 |
| Spring Web | Gestionada por Spring Boot |
| Spring Boot Thymeleaf | Gestionada por Spring Boot |

### Proveedor APP — `app`

| Dependencia | Versión |
|---|---|
| Jackson Databind | 2.16.1 |
| SLF4J API | 2.0.12 |
| Logback Classic | 1.4.14 |
| JUnit Jupiter | 5.9.1 |

Este módulo utiliza sockets TCP de Java.

### Proveedor FarCoop — `farCoop`

| Dependencia | Versión |
|---|---|
| Spring Boot | 3.5.7 |
| Spring Dependency Management | 1.1.6 |
| Spring Web | Gestionada por Spring Boot |
| Spring Data JPA | Gestionada por Spring Boot |
| SpringDoc OpenAPI UI | 2.8.13 |
| H2 Database | Gestionada por Spring Boot |

### Demo — `demo`

| Dependencia | Versión |
|---|---|
| Spring Boot | 3.5.9 |
| Spring Dependency Management | 1.1.7 |
| Spring Boot Starter | Gestionada por Spring Boot |
| Spring Boot Test | Gestionada por Spring Boot |

## Base de datos

La aplicación principal y el proveedor FarCoop utilizan H2.

Las ubicaciones configuradas para las bases de datos son:

- Aplicación principal: `./data/SFdb`
- Proveedor FarCoop: `./data/farCoopdb`

Ambos módulos configuran la generación del esquema de Hibernate mediante `create-drop`.

## Configuración de red

| Componente | Puerto | Comunicación |
|---|---:|---|
| Aplicación principal (`specialFood`) | 8082 | HTTP / REST |
| Proveedor FarCoop (`farCoop`) | 8084 | HTTP / REST |
| Proveedor APP (`app`) | 8085 | Socket TCP |

El cliente está configurado para acceder a la aplicación principal mediante `http://localhost:8082`.

## Notas adicionales

- Java 21 es necesario según la configuración del toolchain de Gradle.
- Utiliza el Gradle Wrapper incluido para compilar y ejecutar cada módulo.
- Cada módulo tiene su propio archivo `build.gradle` y configuración de dependencias.
- Las versiones de Spring Boot varían entre módulos y deben mantenerse de acuerdo con sus respectivos archivos de configuración.

## 9. Verified Environment

The Java, Gradle, Spring Boot, and dependency versions listed above are based on the build configuration files included in the repository. The ports are based on the included application properties and Gradle configuration.

They describe the supplied project configuration and should not be interpreted as a guarantee that every module has been tested together on a clean machine.
