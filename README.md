# Spring Dynamic CronJob

A Spring Boot application that enables **dynamic scheduling of cron jobs at runtime** — create, update, enable, and disable scheduled tasks without restarting the application. Job configurations are persisted in a PostgreSQL database, making schedules survive application restarts.

---

## Features

- **Dynamic scheduling**: Add or modify cron jobs at runtime via REST API
- **Persistent configuration**: Job schedules are stored in PostgreSQL — no config file changes, no redeploys
- **Enable / Disable jobs**: Toggle individual jobs on or off without removing them
- **Spring Task Scheduler integration**: Built on top of Spring's native scheduling infrastructure
- **WAR deployment ready**: Packaged as a WAR for deployment on external Tomcat servers

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4.0 |
| Build Tool | Gradle |
| Database | PostgreSQL |
| ORM | Spring Data JPA + Hibernate 6.6 |
| Packaging | WAR (Tomcat provided) |
| Utilities | Lombok, ModelMapper |

---

## Prerequisites

- Java 17+
- PostgreSQL (running locally or remotely)
- Gradle (or use the included `./gradlew` wrapper)
- (Optional) Tomcat server if deploying as WAR

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/nguyenlyminhman/spring.dynamic.cronjob.git
cd spring.dynamic.cronjob
```

### 2. Set up the database

Run the SQL script in the `database/` folder to create the required schema:

```bash
psql -U <your_user> -d <your_database> -f database/<script_name>.sql
```

### 3. Configure the application

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build and run

**Run directly (embedded Tomcat):**
```bash
./gradlew bootRun
```

**Build WAR for external Tomcat:**
```bash
./gradlew build
# Deploy the generated WAR from build/libs/ to your Tomcat webapps folder
```

---

## How It Works

Instead of hardcoding cron expressions with `@Scheduled`, this project stores job definitions in the database. On startup, all active jobs are loaded and registered with Spring's `TaskScheduler`. Any changes made via the API are applied immediately at runtime.

```
REST API → Service Layer → TaskScheduler (add/cancel tasks)
                        ↘ Database (persist cron config)
```

When the application restarts, it re-reads all active jobs from the database and re-registers them — no configuration is lost.

---

## REST API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/cronjobs` | List all registered cron jobs |
| `POST` | `/api/cronjobs` | Create a new cron job |
| `PUT` | `/api/cronjobs/{id}` | Update an existing cron job |
| `DELETE` | `/api/cronjobs/{id}` | Delete a cron job |
| `PATCH` | `/api/cronjobs/{id}/toggle` | Enable or disable a job |

### Example: Create a cron job

```http
POST /api/cronjobs
Content-Type: application/json

{
  "name": "daily-report",
  "cronExpression": "0 0 8 * * ?",
  "description": "Send daily report at 8 AM",
  "enabled": true
}
```

---

## Project Structure

```
spring.dynamic.cronjob/
├── database/                   # SQL scripts for schema setup
├── src/
│   └── main/
│       ├── java/
│       │   └── spring/dynamic/cronjob/
│       │       ├── controller/ # REST controllers
│       │       ├── service/    # Business logic & scheduling
│       │       ├── repository/ # Spring Data JPA repositories
│       │       ├── entity/     # JPA entities
│       │       └── dto/        # Data Transfer Objects
│       └── resources/
│           └── application.properties
├── build.gradle
└── settings.gradle
```

---

## Cron Expression Reference

| Expression | Meaning |
|---|---|
| `0 * * * * ?` | Every minute |
| `0 0 * * * ?` | Every hour |
| `0 0 8 * * ?` | Every day at 8:00 AM |
| `0 0 0 * * MON` | Every Monday at midnight |
| `0 0 12 1 * ?` | 1st of every month at noon |

> Spring uses 6-field cron expressions: `second minute hour day month weekday`

---

## License

This project is open source. Feel free to use and adapt it for your own needs.

---

## Author

**Nguyen Ly Minh Man** — [github.com/nguyenlyminhman](https://github.com/nguyenlyminhman)
