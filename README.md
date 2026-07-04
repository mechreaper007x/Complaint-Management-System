# 🚚 Complaint & Freight Management System (CMS & FMS)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-green.svg)](https://spring.io/projects/spring-boot)

Welcome to the **Complaint & Freight Management System**. This repository contains a production-ready Spring Boot web application designed to track and manage logistics operations (Freights) and handle client disputes/issues (Complaints). 

The project features a dual-interface architecture: a traditional server-side-rendered dashboard built with **Thymeleaf + Chart.js + Bootstrap 5**, and a highly-optimized, futuristic **Neon-Glassmorphism Single Page Application (SPA)** fallback client capable of running fully in-memory if the backend service is offline.

---

## 🌟 Key Features

*   📊 **Interactive Dashboard**: High-level logistics tracking, featuring real-time status breakdowns and customer distribution charts powered by **Chart.js**.
*   📦 **Freight Management**: Complete CRUD lifecycle operations (Origin, Destination, Customer, Carrier, Tracking Number, Dates, and Statuses).
*   🚀 **Futuristic SPA Mockup (Neon UI)**: A client-side web application styled with high-performance CSS glassmorphism. It auto-probes the backend and seamlessly falls back to an interactive client-side Mock API with a built-in automated self-test runner.
*   🔌 **RESTful API**: Clean REST JSON endpoints to interface with third-party networks or client apps.
*   💾 **Self-Contained Storage**: Pre-configured **H2 In-Memory Database** with direct access to the H2 console for rapid local development.
*   🐳 **Containerized & Cloud-Ready**: Out-of-the-box support for **Docker** and **Render.com** deployment (`render.yaml`).
*   🧪 **Robust Testing**: Comprehensive integration and unit testing suite with automated code coverage tracking via **JaCoCo**.

---

## 🛠️ Tech Stack

*   **Backend Core**: Java 21, Spring Boot 3.5.7
*   **Data Access**: Spring Data JPA, Hibernate, MapStruct 1.5.3 (Entity-DTO Mapper)
*   **Web Framework**: Spring Web MVC, Thymeleaf, Thymeleaf Layout Dialect
*   **Database**: H2 In-Memory Database
*   **Testing**: JUnit 5, MockMvc, JaCoCo Coverage Plugin
*   **Frontend**: Bootstrap 5, Bootstrap Icons, Vanilla JS, CSS3 Custom Properties (Glassmorphism), Chart.js
*   **Deployment**: Docker (Multi-stage builds), Render PaaS

---

## 📁 Repository Structure

```text
├── model/                     # Spring Boot Application root
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/cms/
│   │   │   │   ├── CORS/       # CORS configurations for cross-origin frontend clients
│   │   │   │   ├── controller/ # Spring MVC (Thymeleaf) & REST Controllers
│   │   │   │   ├── mapper/     # MapStruct mappers for DTO transformations
│   │   │   │   ├── model/      # Entities (Complaint, Freight) & DTOs
│   │   │   │   ├── repository/ # Spring Data JPA Repositories
│   │   │   │   └── service/    # Business services
│   │   │   └── resources/
│   │   │       ├── static/     # Shared styles (custom.css)
│   │   │       └── templates/  # Thymeleaf HTML views & dashboard.html
│   │   └── test/               # JUnit Integration and Unit Tests
│   ├── pom.xml                 # Maven configuration and dependencies
│   └── mvnw/mvnw.cmd           # Maven wrapper scripts
├── Dockerfile                  # Multi-stage production Docker build
├── render.yaml                 # Configuration for Render Cloud deployment
└── README.md                   # Project documentation (this file)
```

---

## 🚀 Getting Started Locally

### Prerequisites
*   **Java Development Kit (JDK) 21** or higher.
*   **Maven 3.9+** (or use the provided Maven wrapper `mvnw`).

### 1. Build and Run the App
Navigate into the `model` folder and use the Maven wrapper to boot the application:

```bash
cd model
./mvnw spring-boot:run
```
*(On Windows command prompt/PowerShell, use `mvnw.cmd spring-boot:run`)*

The server will start up and listen on port **`8044`** (or the port defined in the environment variable `PORT`).

### 2. Access the Applications
*   **Spring Boot MVC Web Portal**: Open [http://localhost:8044/dashboard](http://localhost:8044/dashboard) in your browser.
*   **Neon SPA Fallback Client**: Directly run or render the Thymeleaf index page at [http://localhost:8044/](http://localhost:8044/).
*   **H2 Database Console**: Go to [http://localhost:8044/h2-console](http://localhost:8044/h2-console)
    *   **JDBC URL**: `jdbc:h2:mem:testdb`
    *   **Username**: `sa`
    *   **Password**: *(leave blank)*

---

## 📡 REST API Reference

All REST endpoints are prefixed with `/api/freights`.

| Method | Endpoint | Description | Query Parameters / Payload |
| :--- | :--- | :--- | :--- |
| **GET** | `/ping` | Health check endpoint | Returns `"pong"` |
| **GET** | `/api/freights` | Retrieves all freights sorted by departure date (descending) | N/A |
| **GET** | `/api/freights/{id}` | Gets details of a single freight | N/A |
| **POST** | `/api/freights` | Creates a new freight record | JSON `FreightDTO` |
| **PUT** | `/api/freights/{id}` | Updates details of an existing freight record | JSON `FreightDTO` |
| **PATCH**| `/api/freights/{id}/status` | Fast status transition | `status` (String), `notes` (Optional String) |
| **DELETE**| `/api/freights/{id}` | Deletes a freight record | N/A |

### Example Payload (`POST /api/freights`)
```json
{
  "origin": "Los Angeles",
  "destination": "Seattle",
  "customer": "Vanguard Logistics",
  "status": "IN_TRANSIT",
  "departureDate": "2026-07-10",
  "arrivalDate": "2026-07-14",
  "carrier": "DHL Express",
  "trackingNumber": "DHL-98217-LA"
}
```

---

## 🧪 Testing & Code Coverage

The project is equipped with robust controllers, models, and mapping tests.

### Run Tests
```bash
cd model
./mvnw clean test
```

### View Coverage Report
Upon completing tests, the **JaCoCo** maven plugin generates a detailed HTML coverage report. Open it at:
`model/target/site/jacoco/index.html`

---

## 🐳 Containerization & Cloud Deployment

### Running with Docker
A multi-stage `Dockerfile` is provided at the repository root. To build and run:

1.  **Build the Docker Image**:
    ```bash
    docker build -t cms-fms-app .
    ```
2.  **Run the Container**:
    ```bash
    docker run -p 8044:8044 cms-fms-app
    ```

### Deploying to Render
This project is configured for one-click deployment on **Render.com** using the `render.yaml` specification.
*   **Runtime**: Docker
*   **Plan**: Free
*   **Environment Variable**: `PORT=8044`

Just link this Git repository to your Render account, and it will deploy automatically.

---

## 🛡️ License

Distributed under the MIT License. See `LICENSE` for more information.
