# Real-Time Chat Application

## Overview
A robust Spring Boot-based real-time chat application with WebSocket support, providing secure messaging functionality.

## Features
- Real-time messaging using WebSocket
- Secure authentication and authorization
- Message persistence
- WebSocket configuration
- Role-based access control

## Technologies Used
- Java 17
- Spring Boot
- Spring Security
- WebSocket
- MySQL
- JPA/Hibernate
- Maven

## Project Structure
- `com.woromedia.api.task.config`: WebSocket and Security configurations
- `com.woromedia.api.task.controller`: REST and WebSocket controllers
- `com.woromedia.api.task.entity`: Data models
- `com.woromedia.api.task.repository`: Database repositories
- `com.woromedia.api.task.service`: Business logic services

## Prerequisites
- Java 17
- Maven
- MySQL Database

## Configuration
1. Configure database connection in `application.properties`
2. Ensure WebSocket and security settings are correctly set

## MySQL Database Configuration

### Prerequisites
- MySQL Server (8.0 or later recommended)
- MySQL Workbench or similar database management tool (optional)

### Database Setup

1. **Create Database**
   ```sql
   CREATE DATABASE chat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **Create Database User**
   ```sql
   CREATE USER 'chatapp'@'localhost' IDENTIFIED BY 'MCG2024#pannel';
   GRANT ALL PRIVILEGES ON chat.* TO 'chatapp'@'localhost';
   FLUSH PRIVILEGES;
   ```

### Connection Configuration

Update `src/main/resources/application.properties` with the following settings:

```properties
# Database Connection
spring.datasource.url=jdbc:mysql://localhost:3306/chat?useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=chatapp
spring.datasource.password=MCG2024#pannel
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# App Properties
app.jwt-secret=JWTSecretKey
app.jwt-expiration-milliseconds=604800000

## How to Run the Project

### Prerequisites
- Java 17 JDK installed
- MySQL 8.0+ installed and configured
- Maven 3.6+ installed
- Git (optional, for cloning)

### 1. Clone the Repository
```bash
git clone https://github.com/largefullmoon/springbootbackend.git
cd real-time-chat-application
```

### 2. Database Setup
1. Create MySQL database and user as described in the MySQL Database Configuration section
2. Update `src/main/resources/application.properties` with your database credentials

### 3. Running the Application

#### Method A: Using Maven
```bash
# Clean and compile the project
mvn clean install

# Run the application
mvn spring-boot:run
```

#### Method B: Using IDE (IntelliJ IDEA, Eclipse)
1. Import the project as a Maven project
2. Resolve Maven dependencies
3. Run `src/main/java/com/woromedia/api/task/TaskApplication.java` as a Java application

#### Method C: Build Executable JAR
```bash
# Build executable JAR
mvn clean package

# Run the JAR
java -jar target/task-0.0.1-SNAPSHOT.jar
```

### 4. Accessing the Application
- Default Server Port: 8001
- Base URL: `http://localhost:8001`
- WebSocket Endpoint: `/ws/chat`

### 5. Environment Variables
You can override default configurations using environment variables:
- `SERVER_PORT`: Change the default server port
- `SPRING_DATASOURCE_URL`: Custom database URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password

### 6. Docker Deployment (Optional)
```bash
# Build Docker image
docker build -t chat-application .

# Run Docker container
docker run -p 8001:8001 chat-application
```

### Troubleshooting
- Ensure all prerequisites are installed
- Check MySQL connection settings
- Verify Java and Maven versions
- Review application logs for specific errors

### Recommended Development Workflow
1. Set up development environment
2. Configure database
3. Run tests: `mvn test`
4. Start the application
5. Use Postman or WebSocket client to test endpoints