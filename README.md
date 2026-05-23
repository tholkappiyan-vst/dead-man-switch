# 🔒 Secure Dead-Man's Switch Engine

An automated fail-safe web application built with **Spring Boot**, **Thymeleaf**, **Docker**, and **Neon PostgreSQL**. This system allows users to create secure data vaults that automatically dispatch sensitive recovery data or encrypted payloads to a trusted beneficiary email address if the owner fails to "check in" within a pre-configured interval window.

🌐 **Live Deployment Link:** [https://dead-man-switch-z7h4.onrender.com](https://dead-man-switch-z7h4.onrender.com)

---

## 🚀 Features

* **Automated Switch Initialization:** Users can create a secure vault by providing an owner email, switch title, a secure payload, a beneficiary email, and a time-critical check-in interval.
* **Dynamic Owner Status Panel:** A real-time dashboard tracking the operational status of all active vaults (`ACTIVE`, `PENDING_PROOF`, `RELEASED`).
* **Asynchronous Cron Scheduler:** A background worker routine monitoring elapsed time deltas to detect missed check-ins.
* **Production Cloud Architecture:** Fully containerized using Docker and continuously deployed on Render.

---

## 🛠️ Tech Stack & Architecture

* **Backend Framework:** Spring Boot 3.2.5 (Java 17)
* **Data Layer:** Spring Data JPA / Hibernate ORM
* **Relational Database:** Neon Serverless PostgreSQL
* **Template UI Engine:** Thymeleaf (HTML5 / CSS3)
* **Containerization:** Docker (Multi-stage Eclipse-Temurin Alpine build)
* **Cloud Hosting Environment:** Render Web Services

---

## 📁 Repository Structure

```text
dead-man-switch/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java         # Application Entry Point
│   │   │   ├── VaultController.java         # Web Routing / HTTP Endpoints
│   │   │   ├── SecretVault.java             # JPA Relational Entity Model
│   │   │   ├── SecretRepository.java        # Database Query Access Interface
│   │   │   └── VaultScheduler.java          # Background Fail-Safe Automation
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── index.html               # Main Dashboard & Creation UI
│   │       │   └── dashboard.html           # Active Switches Status Panel
│   │       └── application.properties       # Core App Profiles
├── Dockerfile                               # Multi-stage Container Settings
└── pom.xml                                  # Maven Dependency Configurations
