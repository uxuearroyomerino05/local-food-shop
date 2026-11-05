# Local Food Shop

[English](#english) | [Español](#español)

<a id="english"></a>
# English

## Description

**Local Food Shop** is a distributed product-selling application developed in Java as a university project for the **Software Design** course at the University of Deusto.

The application simulates a product marketplace where users can browse products provided by different external services and interact with the main application through client interfaces.

The system is divided into independent components, each with its own responsibilities and communication mechanisms.

## Technologies

- **Programming language:** Java 21
- **Framework:** Spring Boot
- **Build system:** Gradle
- **API communication:** REST
- **Data persistence:** Spring Data JPA / Hibernate
- **Database:** H2
- **Web interface:** Spring Boot and Thymeleaf
- **Socket communication:** Java TCP sockets

See [REQUIREMENTS.md](REQUIREMENTS.md) for the required tools, versions and dependencies.

## Architecture

The project contains several independent components:

### Main Application — `specialFood`

The central application provides the main business functionality.

Responsibilities include:
- Managing products and users.
- Providing REST endpoints.
- Managing persistence through JPA repositories.
- Communicating with external product providers through gateway abstractions.

Main technologies: Spring Boot, Spring Web, Spring Data JPA and H2.

### Client Application — `specialFood-client`

Provides two ways to interact with the main application:

- **Web client:** A browser-based interface developed with Spring Boot and Thymeleaf.
- **Console client:** A command-line interface.

Both clients communicate with the main application through service proxies.

### APP Provider — `app`

A simulated external product provider.

It exposes its functionality through a TCP socket server and communicates with the main application through the corresponding gateway.

### FarCoop Provider — `farCoop`

A second simulated external product provider.

It exposes REST endpoints and manages its own persistence layer.

### Demo — `demo`

An additional Spring Boot demo module included in the project.

## Design and Architecture Concepts

The project applies several software design and architectural concepts:

- Layered architecture.
- Separation of controllers, services, repositories, entities and DTOs.
- Gateway abstractions for external services.
- Factory pattern for provider selection.
- Proxy abstractions for client communication.
- Integration between independent application components.

## Project Structure

```text
Local-Food-Shop/
├── specialFood/
│   └── Main application
├── specialFood-client/
│   └── Web and console clients
├── app/
│   └── APP external provider
├── farCoop/
│   └── FarCoop external provider
├── demo/
│   └── Demo module
├── Diagrams.vpp
├── Diagrams.pdf
├── README.md
├── REQUIREMENTS.md
└── LICENSE
```

## Execution

Each component must be started separately.

The commands below use the Gradle Wrapper included in each module.

### 1. Start the APP provider

Open a terminal in `app/` and run:

```powershell
.\gradlew.bat run
```

The socket server listens on port `8085`.

### 2. Start the FarCoop provider

Open a terminal in `farCoop/` and run:

```powershell
.\gradlew.bat bootRun
```

The REST service listens on port `8084`.

### 3. Start the main application

Open a terminal in `specialFood/` and run:

```powershell
.\gradlew.bat bootRun
```

The main REST service listens on port `8082`.

### 4. Start a client

Open a terminal in `specialFood-client/`.

To start the web client:

```powershell
.\gradlew.bat bootRun
```

To start the console client:

```powershell
.\gradlew.bat runConsoleClient
```

The clients communicate with the main application at `http://localhost:8082`.

**Important:** Start the external providers and the main application before starting a client. Make sure the configured ports are available.

## Academic Context

Developed as a university project for the **Software Design** course at the University of Deusto.

## License

This project is distributed under the MIT License. See [LICENSE](LICENSE) for details.

---

<a id="español"></a>
# Español

## Descripción

**Local Food Shop** es una aplicación distribuida de venta de productos desarrollada en Java como proyecto universitario para la asignatura **Software Design** de la Universidad de Deusto.

La aplicación simula un mercado de productos donde los usuarios pueden consultar artículos proporcionados por distintos servicios externos e interactuar con la aplicación principal mediante diferentes interfaces de cliente.

El sistema está dividido en componentes independientes, cada uno con sus propias responsabilidades y mecanismos de comunicación.

## Tecnologías

- **Lenguaje de programación:** Java 21.
- **Framework:** Spring Boot.
- **Sistema de construcción:** Gradle.
- **Comunicación mediante API:** REST.
- **Persistencia:** Spring Data JPA / Hibernate.
- **Base de datos:** H2.
- **Interfaz web:** Spring Boot y Thymeleaf.
- **Comunicación mediante sockets:** Sockets TCP de Java.

Consulta [REQUIREMENTS.md](REQUIREMENTS.md) para conocer las herramientas, versiones y dependencias necesarias.

## Arquitectura

El proyecto contiene varios componentes independientes:

### Aplicación principal — `specialFood`

La aplicación central proporciona la funcionalidad principal del sistema.

Sus responsabilidades incluyen:
- Gestionar productos y usuarios.
- Proporcionar endpoints REST.
- Gestionar la persistencia mediante repositorios JPA.
- Comunicarse con proveedores externos mediante abstracciones Gateway.

Tecnologías principales: Spring Boot, Spring Web, Spring Data JPA y H2.

### Aplicación cliente — `specialFood-client`

Proporciona dos formas de interactuar con la aplicación principal:

- **Cliente web:** Interfaz accesible desde el navegador desarrollada con Spring Boot y Thymeleaf.
- **Cliente de consola:** Interfaz de línea de comandos.

Ambos clientes se comunican con la aplicación principal mediante proxies de servicio.

### Proveedor APP — `app`

Simulación de un proveedor externo de productos.

Expone su funcionalidad mediante un servidor de sockets TCP y se comunica con la aplicación principal a través del Gateway correspondiente.

### Proveedor FarCoop — `farCoop`

Segundo proveedor externo de productos simulado.

Expone endpoints REST y gestiona su propia capa de persistencia.

### Demo — `demo`

Módulo adicional de demostración desarrollado con Spring Boot e incluido en el proyecto.

## Conceptos de diseño y arquitectura

El proyecto aplica diferentes conceptos de diseño y arquitectura de software:

- Arquitectura por capas.
- Separación de controladores, servicios, repositorios, entidades y DTOs.
- Abstracciones Gateway para servicios externos.
- Patrón Factory para la selección de proveedores.
- Abstracciones Proxy para la comunicación de los clientes.
- Integración entre componentes de aplicaciones independientes.

## Estructura del proyecto

```text
Local-Food-Shop/
├── specialFood/
│   └── Aplicación principal
├── specialFood-client/
│   └── Clientes web y de consola
├── app/
│   └── Proveedor externo APP
├── farCoop/
│   └── Proveedor externo FarCoop
├── demo/
│   └── Módulo de demostración
├── Diagrams.vpp
├── Diagrams.pdf
├── README.md
├── REQUIREMENTS.md
└── LICENSE
```

## Ejecución

Cada componente debe iniciarse por separado.

Los comandos siguientes utilizan el Gradle Wrapper incluido en cada módulo.

### 1. Iniciar el proveedor APP

Abre una terminal en `app/` y ejecuta:

```powershell
.\gradlew.bat run
```

El servidor de sockets escucha en el puerto `8085`.

### 2. Iniciar el proveedor FarCoop

Abre una terminal en `farCoop/` y ejecuta:

```powershell
.\gradlew.bat bootRun
```

El servicio REST escucha en el puerto `8084`.

### 3. Iniciar la aplicación principal

Abre una terminal en `specialFood/` y ejecuta:

```powershell
.\gradlew.bat bootRun
```

El servicio REST principal escucha en el puerto `8082`.

### 4. Iniciar un cliente

Abre una terminal en `specialFood-client/`.

Para iniciar el cliente web:

```powershell
.\gradlew.bat bootRun
```

Para iniciar el cliente de consola:

```powershell
.\gradlew.bat runConsoleClient
```

Los clientes se comunican con la aplicación principal en `http://localhost:8082`.

**Importante:** inicia los proveedores externos y la aplicación principal antes de iniciar un cliente. Comprueba que los puertos configurados estén disponibles.

## Contexto académico

Desarrollado como proyecto universitario para la asignatura **Software Design** de la Universidad de Deusto.

## Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta [LICENSE](LICENSE) para más información.
