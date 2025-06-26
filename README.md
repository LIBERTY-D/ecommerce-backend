# 🛍️ E-commerce Backend

This project is a backend service for an e-commerce platform, built using **Java 21**, **Spring Boot**, **PostgreSQL**, **JWT**, and **Docker**.

---

## 🛠️ Technologies Used

- **Java 21**
- **Spring Boot 3.5.0**
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-security`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-mail`
  - `spring-boot-starter-thymeleaf`
  - `spring-boot-starter-test` (test scope)
- **PostgreSQL 42.7.7**
- **Lombok 1.18.38**
- **Java JWT (auth0:java-jwt:4.5.0)**
- **Docker & Docker Compose**
- **Maven** as the build tool

---
## 🚀 Starter Guide

### ✅ Prerequisites

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)
- Java 21 JDK
- Maven

### 🔨 Build Tool

- **Maven** using `spring-boot-maven-plugin`

---

## ⚙️ Application Configuration

Create a `.env` file in the root directory with the following variables:

### 🔐 App Environment Variables

| Category       | Key                    | Description                                  |
|----------------|------------------------|----------------------------------------------|
| **Spring**     | `ACTIVE_PROFILE`       | Active Spring profile (`dev`, `prod`)        |
| **App Config** | `APP_PORT`             | Application port                             |
|                | `APP_HOST`             | Application hostname                         |
|                | `APP_SECURE`           | Use `http` or `https`                        |
| **PostgreSQL** | `POST_USER`            | PostgreSQL username                          |
|                | `POST_PASS`            | PostgreSQL password                          |
|                | `POST_DB`              | PostgreSQL database name                     |
|                | `POST_PORT`            | PostgreSQL internal port                     |
|                | `POST_SERVICE`         | Hostname for PostgreSQL                      |
| **Email**      | `MAIL_PORT`            | SMTP server port                             |
|                | `MAIL_HOST`            | SMTP server host                             |
|                | `MAIL_USERNAME`        | SMTP username                                |
|                | `MAIL_PASSWORD`        | SMTP password                                |
|                | `VERIFY_EMAIL_URL`     | Email verification callback URL              |
| **JWT**        | `JWT_SECRET`           | Secret key to sign JWT tokens                |
|                | `JWT_ACCESS_EXP`       | Access token expiration in ms                |
|                | `JWT_REFRESH_EXP`      | Refresh token expiration in ms               |
| **Admin**      | `ADMIN_USER`           | Admin email                                  |
|                | `ADMIN_PASSWORD`       | Admin password                               |
|                | `ADMIN_ROLES`          | Comma-separated roles (e.g., `ROLE_ADMIN`)   |
| **Demo Users** | `DEMO_USER`            | Demo username                                |
|                | `DEMO_PASSWORD`        | Demo password                                |
|                | `DEMO_ROLE`            | Role for demo user                           |
| **CORS**       | `WEBSITE_URLS`         | Allowed origins                              |
| **JPA**        | `DDL_AUTO`             | Schema strategy (e.g., `update`)             |
| **Security**   | `logging.level.org.springframework.security` | Security logging level (e.g., DEBUG) |

### 🧪 Sample `application-dev.yml`

```yaml
POST_USER=your_db_user
POST_PASS=your_db_password
POST_DB=ecommerce_db
POST_PORT=5432
POST_SERVICE=postgres

APP_PORT=8080
APP_HOST=localhost
APP_SECURE=http

MAIL_PORT=587
MAIL_HOST=smtp.mailtrap.io
MAIL_USERNAME=your_mail_username
MAIL_PASSWORD=your_mail_password
VERIFY_EMAIL_URL=http://localhost:8080/api/v1/users/verify

JWT_SECRET=your_jwt_secret_key
JWT_ACCESS_EXP=900000
JWT_REFRESH_EXP=604800000

ADMIN_USER=admin@example.com
ADMIN_PASSWORD=adminpassword
ADMIN_ROLES=ROLE_ADMIN,ROLE_DEMO
DDL_AUTO=update
```

## Docker Compose Setup

| Variable           | Description                                | Example Value           |
|--------------------|--------------------------------------------|------------------------|
| POST_PASS          | Password for the PostgreSQL database       | mypassword             |
| POST_USER          | Username for the PostgreSQL database       | postgres               |
| POST_DB            | Name of the PostgreSQL database             | ecommerce_db           |
| POST_PORT          | Internal port PostgreSQL listens on         | 5432                   |
| POST_PORT_EXTERNAL | External port mapped to PostgreSQL container | 5432                   |
| PG_EMAIL           | Email for PgAdmin login                      | admin@example.com      |
| PG_PASS            | Password for PgAdmin login                   | adminpass              |
| PG_EXTERNAL_PORT   | External port mapped to PgAdmin container   | 5050                   |
| PG_INTERNAL_PORT   | Internal port PgAdmin listens on             | 80                     |
| APP_PORT           | Internal port Spring Boot app listens on    | 8080                   |
| APP_PORT_EXTERNAL  | External port mapped to Spring Boot container| 8080                   |
| ENV_FILE           | Path to the environment file for the app    | .env                   |

---

### Important

- You **must create a `.env` file** in the root directory of your project.
- This `.env` file should define all the variables listed above.
- Docker Compose will automatically load these variables from the `.env` file during deployment.

### 🧪 Sample of docker-compose.yml
```yaml
services:
  postgres_db_sc:
    image: postgres
    restart: always
    environment:
      POSTGRES_PASSWORD: ${POST_PASS}
      POSTGRES_USER: ${POST_USER}
      POSTGRES_DB: ${POST_DB}
    ports:
      - "${POST_PORT_EXTERNAL}:${POST_PORT}"
    volumes:
      - ./postgres-init:/docker-entrypoint-initdb.d
    networks:
      - ecommerceAppNetwork

  pgadmin_sc:
    image: dpage/pgadmin4
    restart: always
    environment:
      PGADMIN_DEFAULT_EMAIL: ${PG_EMAIL}
      PGADMIN_DEFAULT_PASSWORD: ${PG_PASS}
    ports:
      - "${PG_EXTERNAL_PORT}:${PG_INTERNAL_PORT}"
    depends_on:
      - postgres_db_sc
    networks:
      - ecommerceAppNetwork

  ecommerce-spring-boot-app_sc:
    build: .
    depends_on:
      - postgres_db_sc
    ports:
      - "${APP_PORT_EXTERNAL}:${APP_PORT}"
    networks:
      - ecommerceAppNetwork
    env_file:
      - ${ENV_FILE}

networks:
  ecommerceAppNetwork:
    driver: bridge

```
## 🧪 Running the Application

You can start the e-commerce backend using a simple script or manually with Docker Compose.

---

### ✅ Recommended: Use the `.prod.sh` Script

After building your project with Maven:

```bash
mvn clean install
```
This script will:

- Load environment variables from `.env`
- Use Docker Compose to spin up all required services
- Build and start the application in production mode

> **Note:** Make sure the `.prod.sh` script is executable. If not, run:
>
> ```bash
> chmod +x .prod.sh
> ```
## 🛠 Alternative: Manual Docker Compose Command

If you prefer running the application manually without the `.prod.sh` script:

```bash
ENV_FILE=.env docker-compose --env-file .env up --build
```
## 🔧 Local Development (No Docker)

If you'd rather run the app directly on your machine:

1. Start PostgreSQL manually or via Docker.
2. Set up environment variables via `application-dev.yml` or your IDE.
3. Run the application:

```bash
mvn spring-boot:run
```
# ✅ REST API Documentation
All responses are wrapped in a generic HttpResponse<T> format that includes  the following.
```java
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse<T> {
    protected int length;
    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected String developerMessage;
    protected String path;
    protected String requestMethod;
    protected T data;
    protected Collection<T> results;
    protected Map<?, ?> errors;
}

```

## 📦 UserController API Endpoints

This controller manages user-related operations including creation, retrieval, update, deletion, password change, admin updates, user verification, and token refresh.

| HTTP Method | Endpoint                        | Description                                         | Request Body           | Response Type                         |
|-------------|--------------------------------|-----------------------------------------------------|------------------------|-------------------------------------|
| `GET`       | `/api/v1/users`                 | Retrieve all users                                  | None                   | `HttpResponse<Collection<UserResponseDto>>` |
| `GET`       | `/api/v1/users/{id}`            | Retrieve a single user by ID                         | None                   | `HttpResponse<UserResponseDto>`     |
| `POST`      | `/api/v1/users`                 | Create a new user                                   | `UserDto`              | `HttpResponse<UserResponseDto>`     |
| `PATCH`     | `/api/v1/users/{id}`            | Update an existing user                             | `UserDto`              | `HttpResponse<UserResponseDto>`     |
| `PATCH`     | `/api/v1/users/password/{id}`  | Update a user’s password                            | `PasswordDto`          | `HttpResponse<UserResponseDto>`     |
| `PATCH`     | `/api/v1/users/admin/{id}`     | Update admin-specific user details                  | `UserAdminDto`         | `HttpResponse<UserResponseDto>`     |
| `DELETE`    | `/api/v1/users/{id}`            | Delete a user by ID                                 | None                   | `HttpResponse<UserResponseDto>`     |
| `GET`       | `/api/v1/users/verify` | Verify a user account via token                     | None                   | `HttpResponse<UserResponseDto>`     |
| `GET`       | `/api/v1/users/refresh/token`  | Refresh JWT access token using refresh token (Authorization header required) | None (Authorization header with Bearer token) | Writes JSON directly to `HttpServletResponse` |

### Notes

- All responses are wrapped inside a reusable `HttpResponse<T>` object including timestamp, status, statusCode, message, and optionally data.
- ID parameters are expected as path variables.
- Endpoints that modify user details (`PATCH` and `POST`) expect validated JSON request bodies.
- The refresh token endpoint returns tokens directly in the HTTP response output stream and requires a Bearer token in the `Authorization` header.
- Exceptions like missing authorization headers throw `AuthHeaderRequired`.
- Base API path is `/api/v1/` inherited from `BaseController`.


## 📦 ProductController API Endpoints

This controller handles CRUD operations for `Product` entities in the e-commerce application.

| HTTP Method | Endpoint         | Description                     | Request Body     | Response Type           |
|-------------|------------------|---------------------------------|------------------|--------------------------|
| `GET`       | `/api/v1/products`      | Retrieve all products           | None             | `HttpResponse<Collection<Product>>` |
| `GET`       | `/api/v1/products/{id}` | Retrieve a single product by ID | None             | `HttpResponse<Product>` |
| `POST`      | `/api/v1/products`      | Create a new product            | `ProductDto`     | `HttpResponse<Product>` |
| `PATCH`     | `/api/v1/products/{id}` | Update an existing product      | `ProductDto`     | `HttpResponse<Product>` |
| `DELETE`    | `/api/v1/products/{id}` | Delete a product by ID          | None             | `HttpResponse<Product>` |

### Notes
- All responses are wrapped in a standardized `HttpResponse<T>` object.
- Controller uses a service implementation `ProductServiceImp` for business logic.
- Endpoints follow RESTful conventions and use appropriate HTTP status codes.

## 📦 OrderController API Endpoints

This controller handles operations related to `OrderItemDto` and manages e-commerce order workflows.

| HTTP Method | Endpoint        | Description                        | Request Body | Response Type                   |
|-------------|-----------------|------------------------------------|--------------|----------------------------------|
| `GET`       | `/api/v1/orders`       | Retrieve all orders                | None         | `HttpResponse<Collection<OrderItemDto>>` |
| `GET`       | `/api/v1/orders/{id}`  | Retrieve a single order by ID      | None         | `HttpResponse<OrderItemDto>`     |
| `POST`      | `/api/v1/orders`       | Create a new order (one or more)   | `OrderDto`   | `HttpResponse<OrderItemDto>`     |
| `PATCH`     | `/api/v1/orders/{id}`  | Update an existing order *(not implemented yet)* | `OrderDto` | `null` (currently)               |
| `DELETE`    | `/api/v1/orders/{id}`  | Delete an order by ID              | None         | `HttpResponse<OrderItemDto>`     |

### Notes
- Responses are wrapped in a reusable `HttpResponse<T>` object.
- `PATCH /api/v1/orders/{id}` is currently not implemented — consider finishing the logic or commenting it out.
- The `POST` endpoint returns a list of order items wrapped inside the `results()` field.
- `OrderDto` represents the incoming request, and `OrderItemDto` is the outgoing response DTO.

## 🗂️ FileController API Endpoints

This controller manages file operations such as upload, view, and listing files, linked to specific products.

| HTTP Method | Endpoint              | Description                              | Request Params / Body                        | Response Type                          |
|-------------|-----------------------|------------------------------------------|----------------------------------------------|----------------------------------------|
| `POST`      | `/api/v1/files/upload`       | Upload a file associated with a product  | `MultipartFile file`, `Long productId`       | `HttpResponse<String>` (file name)     |
| `GET`       | `/api/v1/files`              | Retrieve metadata for all uploaded files | None                                         | `HttpResponse<Collection<FileModel>>`  |
| `GET`       | `/api/v1/files/view/{fileId}`| View/download a specific file by ID      | Path variable: `fileId`                      | `Resource` (raw file content stream)   |

### Notes
- Files are stored and returned as `FileModel` objects containing content, metadata, and association to products.
- `uploadFile()` expects a `MultipartFile` along with a `productId` to link the file.
- `viewFile()` serves raw file data using `ByteArrayResource`, and sets the correct content type using `MediaType.parseMediaType()` for accurate viewing/downloading.
- `HttpResponse<T>` is used to standardize API responses for `upload` and `getAllFiles` endpoints.
## 📬 ContactController API Endpoint

This controller handles **contact form submissions** from users.

| HTTP Method | Endpoint    | Description                     | Request Body             | Response Type                |
|-------------|-------------|---------------------------------|--------------------------|------------------------------|
| `POST`      | `/api/v1/contact`  | Submit a contact form/message   | `ContactDto` (validated) | `HttpResponse<Object>` with message |

### Notes
- Expects a `ContactDto` JSON payload in the request body, validated with `@Valid`.
- Returns a generic `HttpResponse<Object>` with a confirmation message and HTTP 200 status.
- Internally calls `contactServiceImp.ContactUs(contactDto)` to handle business logic such as saving the message or sending emails.
## 📂 CategoryController API Endpoints

This controller manages **product categories** within the ecommerce platform.

| HTTP Method | Endpoint          | Description                          | Request Body     | Response Type                    |
|-------------|-------------------|--------------------------------------|------------------|----------------------------------|
| `GET`       | `/api/v1/categories`     | Retrieve all categories              | –                | `HttpResponse<Collection<Category>>` |
| `GET`       | `/api/v1/categories/{id}`| Retrieve a category by its ID        | –                | `HttpResponse<Category>`         |
| `POST`      | `/api/v1/categories`     | Create a new category                | `CategoryDto`    | `HttpResponse<Category>`         |
| `PATCH`     | `/api/v1/categories/{id}`| Update an existing category by ID    | `CategoryDto`    | `HttpResponse<Category>`         |
| `DELETE`    | `/api/v1/categories/{id}`| Delete a category by ID              | –                | `HttpResponse<Category>`         |

### Notes
- All endpoints return a custom `HttpResponse<T>` object with `status`, `message`, and `timestamp`.
- `POST` and `PATCH` use the `CategoryDto` as the request payload.
- Interacts with `CategoryServiceImp` for business logic and persistence.

## 🏠 AddressController API Endpoints

This controller handles **user addresses** for the ecommerce application, supporting full CRUD operations.

| HTTP Method | Endpoint             | Description                         | Request Body   | Response Type                     |
|-------------|----------------------|-------------------------------------|----------------|-----------------------------------|
| `GET`       | `/api/v1/addresses`         | Retrieve all addresses              | –              | `HttpResponse<Collection<Address>>` |
| `GET`       | `/api/v1/addresses/{id}`    | Retrieve a single address by ID     | –              | `HttpResponse<Address>`           |
| `POST`      | `/api/v1/addresses`         | Create a new address                | `AddressDto`   | `HttpResponse<Address>`           |
| `PATCH`     | `/api/v1/addresses/{id}`    | Update an existing address by ID    | `AddressDto`   | `HttpResponse<Address>`           |
| `DELETE`    | `/api/v1/addresses/{id}`    | Delete an address by ID             | –              | `HttpResponse<Address>`           |

### Additional Notes
- Each response is wrapped in a standardized `HttpResponse<T>` structure.
- The controller relies on `AddressServiceImp` for business logic and persistence.
- `POST` and `PATCH` operations accept an `AddressDto` object.

## 🎯 Final Remarks

Throughout this project, I gained hands-on experience with a variety of important technologies and concepts, including:

- **Managing Database Relationships:**  
  I learned how to design and implement relationships between entities in a relational database using PostgreSQL and JPA/Hibernate with Spring Boot. This included handling one-to-many, many-to-one, and many-to-many relationships effectively.

- **Building RESTful APIs with Spring Boot:**  
  I developed and structured backend REST APIs for an e-commerce application, incorporating best practices such as DTOs, service layers, and exception handling.

- **Implementing Authentication and Authorization:**  
  I used JWT (JSON Web Tokens) to secure the application, managing user authentication and role-based access control within Spring Security.

- **Containerization with Docker and Docker Compose:**  
  I containerized the application and its dependencies (PostgreSQL and PgAdmin) using Docker, and orchestrated multi-container setups with Docker Compose. This simplified deployment and ensured consistent environments.

- **Testing APIs with Postman:**  
  I practiced testing APIs using Postman, allowing me to validate endpoints, verify authentication workflows, and troubleshoot responses efficiently.

- **Configuration and Environment Management:**  
  I managed environment variables and application configuration for different environments (development and production), including creating `.env` files and using Spring profiles.

- **File Upload and Management:**  
  I handled file upload, storage, and retrieval linked to products, enhancing the application’s functionality.

Overall, this project has equipped me with a robust full-stack backend skill set — from database design and API development to security and containerized deployment — essential for building scalable, secure, and maintainable applications.

---
