# YUva Intern — Week 2 Blog REST API

A Java Spring Boot REST API for a blog platform, covering user registration, post CRUD, comment CRUD, relational persistence, validation, and error handling.

## Stack
- Java 21, Spring Boot 3.5.5, Maven
- Spring Web, Spring Data JPA / Hibernate
- H2 file-based relational database
- Jakarta Bean Validation, BCrypt password hashing
- JUnit 5 / Spring Boot Test

## Prerequisites
Install JDK 21 and Maven. Verify with `java -version` and `mvn -version`.

## Run
From the project root:
```bash
mvn spring-boot:run
```
API base URL: `http://localhost:8080`. Run tests using `mvn test`.

The H2 database persists to `./data/blogdb`. Local H2 console: `http://localhost:8080/h2-console`; JDBC URL `jdbc:h2:file:./data/blogdb`, username `sa`, blank password. Keep the console local-only.

## Endpoints
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/users/register` | Register user |
| POST | `/api/posts` | Create post |
| GET | `/api/posts` | List posts |
| GET | `/api/posts/{id}` | Get post |
| PUT | `/api/posts/{id}` | Update post |
| DELETE | `/api/posts/{id}` | Delete post |
| POST | `/api/posts/{postId}/comments` | Create comment |
| GET | `/api/posts/{postId}/comments` | List post comments |
| GET | `/api/comments/{id}` | Get comment |
| PUT | `/api/comments/{id}` | Update comment |
| DELETE | `/api/comments/{id}` | Delete comment |

## Try with Postman
### 1. Register
`POST http://localhost:8080/api/users/register` with JSON:
```json
{"name":"Asha Rao","email":"asha@example.com","password":"examplePass123"}
```
Save the returned user ID.

### 2. Create post
`POST http://localhost:8080/api/posts`:
```json
{"title":"My First Blog Post","content":"My first post using Spring Boot.","authorId":1}
```
Use the actual user ID returned from registration.

### 3. List posts
`GET http://localhost:8080/api/posts`

### 4. Add comment
`POST http://localhost:8080/api/posts/1/comments`:
```json
{"content":"Great post!","authorId":1}
```
Replace IDs with actual values.

### 5. Update post
`PUT http://localhost:8080/api/posts/1`:
```json
{"title":"Updated title","content":"Updated content."}
```

### 6. Delete comment
`DELETE http://localhost:8080/api/comments/1` (use a real comment ID); success returns `204 No Content`.

## Validation and errors
- Required fields and string lengths are validated.
- Duplicate emails return `409 Conflict`.
- Missing users/posts/comments return `404 Not Found`.
- Invalid request bodies return `400 Bad Request` with field-level errors.
- Unexpected failures return a generic `500` response without internal details.
- Passwords are BCrypt-hashed and are never returned by the API.

## Data relationships
A user can author many posts and comments; each post can have many comments. JPA relationships enforce the foreign-key relationships.

## Scope and security note
This is a learning-task implementation. Login, token authentication, and ownership authorization are not included. The request includes `authorId` to keep this CRUD task straightforward, so do not expose this version publicly as a production service. A production version should authenticate users and derive the author from the authenticated identity instead of trusting a caller-provided ID.

## Test checklist
1. Register valid user → `201`.
2. Register duplicate email → `409`.
3. Register invalid email/short password → `400`.
4. Create post with existing author → `201`; unknown author → `404`.
5. List posts → `200`.
6. Get unknown post → `404`.
7. Update post → `200`; delete post → `204`.
8. Create/list/update/delete comments and verify missing IDs return `404`.

## Project status
Source code and run instructions are provided. Run the application and complete the test checklist before submission; this repository does not claim deployment.
