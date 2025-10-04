# Smart-Check
**SmartCheck** is a **secure and efficient Test Management System** built using **Spring Boot**.
It allows administrators to **create, manage, and monitor online tests** while ensuring data **integrity and user authentication** through **Spring Security and JWT tokens**.

<br/>

## 📘 Table of Content

### [1. Endpoint Details](#1-endpoint-details-1)
### [2. Technologies & Dependencies](#2-technologies--dependencies-1)
### [3. Installation](#3-installation-guide)

## 📘 Table of Content

<br />

---

<br />

## 1. Endpoint Details
### [🧑‍💻 User Endpoints](#user-endpoints)

| **S.No** | **Endpoint Title** | **Method** | **URL** | **Auth Type** | **Access** |
|-----------|--------------------|-------------|----------|----------------|-------------|
| 1 | [Register a New User](#1-register-a-new-user) | `POST` | `/api/v1/p/user/register` | No Auth | Public |
| 2 | [User Login](#2-user-login) | `POST` | `/api/v1/p/user/login` | No Auth | Public |
| 3 | [Get New JWT Token via Refresh Token](#3-get-new-jwt-token-via-refresh-token) | `POST` | `/api/v1/p/user/refresh-token` | No Auth | Public |

---

### [🧩 Test Endpoints](#test-endpoints)

| **S.No** | **Endpoint Title** | **Method** | **URL** | **Auth Type** | **Access** |
|-----------|--------------------|-------------|----------|----------------|-------------|
| 1 | [Create a Test](#1-create-a-test) | `POST` | `/api/v1/a/test/create` | Bearer Token | Admin |
| 2 | [Get a Test by ID](#2-get-a-test-by-id) | `GET` | `/api/v1/p/test?id={testid}` | No Auth | Public |
| 3 | [Get All Tests by Email](#3-get-all-tests-by-email) | `GET` | `/api/v1/p/test/all?email={email}` | No Auth | Public |

---

### [👥 Participant Endpoints](#participant-endpoints)

| **S.No** | **Endpoint Title** | **Method** | **URL** | **Auth Type** | **Access** |
|-----------|--------------------|-------------|----------|----------------|-------------|
| 1 | [Register a Participant](#1-register-a-participant) | `POST` | `/api/v1/c/participant/register` | Bearer Token | User, Admin |
| 2 | [Get All Participants of a Test](#2-get-all-participants-of-a-test) | `GET` | `/api/v1/c/participant/all?testid={testid}` | Bearer Token | User, Admin |
| 3 | [Get Participant by Test ID (Self)](#3-get-participant-by-test-id-self) | `GET` | `/api/v1/c/participant/get?testid={testid}` | Bearer Token | User, Admin |
| 4 | [Submit Test](#4-submit-test) | `GET` | `/api/v1/c/participant/submit/{pid}?testid={testid}` | Bearer Token | User, Admin |
| 5 | [Get All Unchecked Participants](#5-get-all-unchecked-participants) | `GET` | `/api/v1/c/participant/unchecked?testid={testid}` | Bearer Token | User, Admin |
| 6 | [Get All Checked Participants](#6-get-all-checked-participants) | `GET` | `/api/v1/c/participant/checked?testid={testid}` | Bearer Token | User, Admin |
| 7 | [Get Result of a Participant](#7-get-result-of-a-participant) | `GET` | `/api/v1/c/participant/result/{pid}?testid={testid}` | Bearer Token | User, Admin |
| 8 | [Get Results of All Participants](#8-get-results-of-all-participants) | `GET` | `/api/v1/c/participant/result/all?testid={testid}` | Bearer Token | User, Admin |

---

### [🧮 Problem Endpoints](#problem-endpoints)

| **S.No** | **Endpoint Title** | **Method** | **URL** | **Auth Type** | **Access** |
|-----------|--------------------|-------------|----------|----------------|-------------|
| 1 | [Get All Problems of a Test](#1-get-all-problems-of-a-test) | `GET` | `/api/v1/c/problem/all?testid={testid}` | Bearer Token | User, Admin |
| 2 | [Get Problem by ID](#2-get-problem-by-id) | `GET` | `/api/v1/c/problem?id={id}` | Bearer Token | User, Admin |
| 3 | [Create a New Problem](#3-create-a-new-problem) | `POST` | `/api/v1/a/problem/create` | Bearer Token | Admin |

---

### [📝 Response Endpoints](#response-endpoints)

| **S.No** | **Endpoint Title** | **Method** | **URL** | **Auth Type** | **Access** |
|-----------|--------------------|-------------|----------|----------------|-------------|
| 1 | [Create a Response](#1-create-a-response) | `POST` | `/api/v1/c/response/create` | Bearer Token | User, Admin |
| 2 | [Get All Responses of a Participant for a Test](#2-get-all-responses-of-a-participant-for-a-test) | `GET` | `/api/v1/c/response/all/{pid}?testid={testid}` | Bearer Token | User, Admin |
| 3 | [Check All Responses of a Participant](#3-check-all-responses-of-a-participant) | `GET` | `/api/v1/a/response/check/{pid}?testid={testid}` | Bearer Token | Admin |
| 4 | [Check All Participants’ Responses for a Test](#4-check-all-participants-responses-for-a-test) | `GET` | `/api/v1/a/response/check/all?testid={testid}` | Bearer Token | Admin |

---

 **Notes:**
 - `p` = Public endpoint (no authentication required).  
 - `c` = Candidate/User endpoint (requires user authentication).  
 - `a` = Admin endpoint (requires admin privileges).  
 - All Bearer Token endpoints use JWT-based authentication in the header.

---

## User Endpoints

### 1. Register a New User

```http
URL: POST /api/v1/p/user/register
```

    Auth Type: No Auth | Accessibility: Public

#### Request Body:
    json
    
    {
        "name": "Prasant Poddar",
        "email": "user@gmail.com",
        "phone": "7878554455",
        "gender": "m",
        "password": "1234",
        "role": "ROLE_USER"
    }

#### Response:
    json

    {
        "name": "Prasant Poddar",
        "email": "user@gmail.com",
        "phone": "7878554455",
        "gender": "m",
        "role": "ROLE_USER"
    }

Registers a new user and stores their details securely with password encryption.

### 2. User Login

```http
URL: POST /api/v1/p/user/login

```
    Auth Type: No Auth | Accessibility: Public

#### Request Body:
    json
    
    {
        "email": "user@gmail.com",
        "password": "1234"
    }

#### Response:
    json

    {
        "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3NTk1NjYxNTAsImV4cCI6MTc1OTU2NzA1MH0.A68dpXisuIEjHNatene4sklfAIWDGMnzy-ePE2HFja8",
        "refreshToken": "1403c824-3d3e-44df-bc23-ea615305b1ff"
    }

Authenticates a registered user using email and password, returning both JWT and Refresh Token for secure access.

### 3. Get New JWT Token via Refresh Token

```http
URL: POST /api/v1/p/user/refresh-token

```
    Auth Type: No Auth | Accessibility: Public

#### Request Body:
    json
    
    {
        "refreshToken": "1403c824-3d3e-44df-bc23-ea615305b1ff"
    }

#### Response:
    json

    {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3NTk1NjYyNDAsImV4cCI6MTc1OTU2NzE0MH0.7aHfakNlBD6Gf6Hrq_kDr4W87lMN9sdn34fTOQxcB-o"
    }

Issues a new JWT access token using a valid refresh token, allowing continued session without re-login.

<br />

## Test Endpoints

### 1. Create a Test

```http
URL: POST /api/v1/a/test/create
```

    Auth Type: Bearer Token | Accessibility: Admin

#### Request Body:
    json
    
    {
        "title": "Demo Test",
        "description": "This is a sample description for the Demo Test.",
        "startsAt": "06-10-2025 10:30:00 PM",
        "registeredBy": "05-10-2025 09:30:00 PM",
        "expiredAfter": 2,
        "duration": 30
    }

#### Response:
    json

    {
        "testid": "T1759561919671",
        "createdBy": "admin@gmail.com"
    }

Creates a new test and stores the creator’s email automatically from the authenticated admin user.

### 2. Get a Test by ID

```http
URL: GET /api/v1/p/test?id={testid}
```

    Auth Type: No Auth | Accessibility: Public

#### Request Parameter:

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Test ID to fetch |
    

#### Response:
    json

    {
        "id": 14,
        "testid": "T1759561919671",
        "createdBy": "admin@gmail.com",
        "title": "Demo Test",
        "description": "This is a sample description for the Demo Test.",
        "createdAt": "04-10-2025 10:31:35 AM",
        "startsAt": "06-10-2025 10:30:00 PM",
        "expiredAt": "07-10-2025 12:30:00 AM",
        "registeredBy": "05-10-2025 09:30:00 PM",
        "duration": 30
    }

Fetches details of a single test using its unique ID.

### 3. Get All Tests by Email

```http
URL: GET /api/v1/p/test/all?email={email}
```

    Auth Type: No Auth | Accessibility: Public

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `email`      | `string` | **Required**. Email of test creator |
    

#### Response:
    json

    [
        {
            "id": 14,
            "testid": "T1759554095568",
            "createdBy": "admin1@gmail.com",
            "title": "Demo Test",
            "description": "This is a sample description for the Demo Test.",
            "createdAt": "04-10-2025 10:31:35 AM",
            "startsAt": "06-10-2025 10:30:00 PM",
            "expiredAt": "07-10-2025 12:30:00 AM",
            "registeredBy": "05-10-2025 09:30:00 PM",
            "duration": 30
        },
        {
            "id": 15,
            "testid": "T1759561919671",
            "createdBy": "admin1@gmail.com",
            "title": "Demo Test",
            "description": "This is a sample description for the Demo Test.",
            "createdAt": "04-10-2025 12:41:59 PM",
            "startsAt": "06-10-2025 10:30:00 PM",
            "expiredAt": "07-10-2025 12:30:00 AM",
            "registeredBy": "05-10-2025 09:30:00 PM",
            "duration": 30
        }
    ]

Fetches all tests created by a specific user using their email.

<br />

## Participant Endpoints

### 1. Register a Participant

```http
URL: POST /api/v1/c/participant/register
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Body:
    json
    
    {
        "testid": "T1759561919671"
    }

#### Response:
    json

    {
        "id": 7,
        "testid": "T1759561919671",
        "pid": "user@gmail.com",
        "testGiven": false,
        "totalMarks": 0,
        "givenAt": null,
        "registeredAt": "2025-10-04T12:42:22.2599474",
        "checked": false
    }

Registers the authenticated user as a participant for the specified test.

### 2. Get All Participants of a Test

```http
URL: GET /api/v1/c/participant/all?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID to fetch participants |
   

#### Response:
    json

    [
        {
            "id": 5,
            "testid": "T1759554095568",
            "pid": "admin1@gmail.com",
            "testGiven": false,
            "totalMarks": 0,
            "givenAt": null,
            "registeredAt": "2025-10-04T10:49:04.81792",
            "checked": false
        },
        {
            "id": 6,
            "testid": "T1759554095568",
            "pid": "user2@gmail.com",
            "testGiven": false,
            "totalMarks": 0,
            "givenAt": null,
            "registeredAt": "2025-10-04T12:17:07.444673",
            "checked": false
        }
    ]

Fetches all participants for a specific test.

### 3. Get Participant by Test ID (Self)

```http
URL: GET /api/v1/c/participant/get?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID to fetch |
   

#### Response:
    json

    {
        "id": 6,
        "testid": "T1759554095568",
        "pid": "user2@gmail.com",
        "testGiven": false,
        "totalMarks": 0,
        "givenAt": null,
        "registeredAt": "2025-10-04T12:17:07.444673",
        "checked": false
    }

Fetches the participant details for the authenticated user for a given test.

### 4. Submit Test

```http
URL: GET /api/v1/c/participant/submit/{pid}?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID to fetch participants |
| `pid`      | `string` | **Required**. Participant ID |
   

#### Response:
    json

    {
        "message": "Test submitted successfully",
        "pid": "user2@gmail.com",
        "testid": "T1759264877921"
    }

Marks the participant’s test as submitted.

### 5. Get All Unchecked Participants

```http
URL: GET /api/v1/c/participant/unchecked?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID to fetch participants |
   

#### Response:
    json

    [
        {
            "id": 5,
            "testid": "T1759554095568",
            "pid": "admin1@gmail.com",
            "testGiven": true,
            "totalMarks": 0,
            "givenAt": "2025-10-04T12:17:07.444673",
            "registeredAt": "2025-10-04T10:49:04.81792",
            "checked": false
        },
        {
            "id": 6,
            "testid": "T1759554095568",
            "pid": "user2@gmail.com",
            "testGiven": true,
            "totalMarks": 0,
            "givenAt": "2025-10-04T12:17:07.444673",
            "registeredAt": "2025-10-04T12:17:07.444673",
            "checked": false
        }
    ]

Fetches participants who have submitted but not yet checked.

### 6. Get All Checked Participants

```http
URL: GET /api/v1/c/participant/checked?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID to fetch participants |
   

#### Response:
    json

    [
        {
            "id": 5,
            "testid": "T1759554095568",
            "pid": "admin1@gmail.com",
            "testGiven": true,
            "totalMarks": 70,
            "givenAt": "2025-10-04T12:17:07.444673",
            "registeredAt": "2025-10-04T10:49:04.81792",
            "checked": true
        },
        {
            "id": 6,
            "testid": "T1759554095568",
            "pid": "user2@gmail.com",
            "testGiven": true,
            "totalMarks": 85,
            "givenAt": "2025-10-04T12:17:07.444673",
            "registeredAt": "2025-10-04T12:17:07.444673",
            "checked": true
        }
    ]

Fetches participants whose tests have been checked.

### 7. Get Result of a Participant

```http
URL: GET /api/v1/c/participant/result/{pid}?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID to fetch participants |
| `pid`      | `string` | **Required**. Participant ID |
   

#### Response:
    json

    {
        "id": 5,
        "testid": "T1759554095568",
        "pid": "admin1@gmail.com",
        "testGiven": true,
        "totalMarks": 70,
        "givenAt": "2025-10-04T12:17:07.444673",
        "registeredAt": "2025-10-04T10:49:04.81792",
        "checked": true
    }

Fetches the result of a specific participant.

### 8. Get Results of All Participants

```http
URL: GET /api/v1/c/participant/result/all?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID to fetch participants |
   

#### Response:
    json

    [
        {
            "id": 5,
            "testid": "T1759554095568",
            "pid": "admin1@gmail.com",
            "testGiven": true,
            "totalMarks": 70,
            "givenAt": "2025-10-04T12:17:07.444673",
            "registeredAt": "2025-10-04T10:49:04.81792",
            "checked": true
        },
        {
            "id": 6,
            "testid": "T1759554095568",
            "pid": "user2@gmail.com",
            "testGiven": true,
            "totalMarks": 85,
            "givenAt": "2025-10-04T12:17:07.444673",
            "registeredAt": "2025-10-04T12:17:07.444673",
            "checked": true
        }
    ]

Fetches results of all checked participants for a given test.

<br />

## Problem Endpoints

### 1. Get All Problems of a Test

```http
URL: GET /api/v1/c/problem/all?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID to fetch all problems |


#### Response:
    json

    [
        {
            "id": 9,
            "testid": "T1759554095568",
            "statement": "In Ramayana, who is the youngest brother?",
            "marks": 10,
            "negativeMarks": -5,
            "option1": "Ram",
            "option2": "Laxman",
            "option3": "Bharat",
            "option4": "Satrughana",
            "correctAnswer": "Satrughana"
        },
        {
            "id": 10,
            "testid": "T1759554095568",
            "statement": "In Ramayana, who is the eldest brother?",
            "marks": 10,
            "negativeMarks": -5,
            "option1": "Ram",
            "option2": "Laxman",
            "option3": "Bharat",
            "option4": "Satrughana",
            "correctAnswer": "Ram"
        }
    ]

Fetches all problems/questions for a given test.

### 2. Get Problem by ID

```http
URL: GET /api/v1/c/problem?id={id}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `id`      | `string` | **Required**. Problem ID to be fetch problem |


#### Response:
    json

    {
        "id": 10,
        "testid": "T1759554095568",
        "statement": "In Ramayana, who is the eldest brother?",
        "marks": 10,
        "negativeMarks": -5,
        "option1": "Ram",
        "option2": "Laxman",
        "option3": "Bharat",
        "option4": "Satrughana",
        "correctAnswer": "Ram"
    }

Fetches a single problem by its ID.

### 3. Create a New Problem

```http
URL: POST /api/v1/a/problem/create
```

    Auth Type: Bearer Token | Accessibility: Admin

#### Request Body:

    json

    {
        "testid": "T1759561919671",
        "statement": "In Ramayana, who is the eldest brother?",
        "marks": 20,
        "negativeMarks": -5,
        "option1": "Ram",
        "option2": "Laxman",
        "option3": "Bharat",
        "option4": "Satrughana",
        "correctAnswer": "Ram"
    }

### OR


    json

    {
        "testid": "T1759561919671",
        "statement": "In Ramayana, who is the eldest brother?",
        "marks": 20,
        "option1": "Ram",
        "option2": "Laxman",
        "correctAnswer": "Ram"
    }

#### Response:
    json

    {
        "id": 11,
        "testid": "T1759561919671",
        "statement": "In Ramayana, who is the eldest brother?",
        "marks": 20,
        "negativeMarks": -5,
        "option1": "Ram",
        "option2": "Laxman",
        "option3": "Bharat",
        "option4": "Satrughana",
        "correctAnswer": "Ram"
    }

Creates a new problem/question for a specified test by the authenticated admin.

## Response Endpoints

### 1. Create a Response

```http
URL: POST /api/v1/c/response/create
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Body:

    json

    {
        "qid": "11",
        "testid": "T1759561919671",
        "selectedAnswer": "Ram" 
    }


#### Response:

    json

    {
        "id": 9,
        "qid": 11,
        "pid": "admin1@gmail.com",
        "testid": "T1759561919671",
        "selectedAnswer": "Ram"
    }

Creates a new response for a participant for a given problem in a test.

### 2. Get All Responses of a Participant for a Test

```http
URL: GET /api/v1/c/response/all/{pid}?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: User, Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `pid`      | `string` | **Required**. Participant ID |
| `testid`      | `string` | **Required**. Test ID |

#### Response:

    json

    [
        {
            "id": 1,
            "qid": 101,
            "pid": "user2@gmail.com",
            "testid": "T1759264877921",
            "selectedAnswer": "Ram"
        },
        {
            "id": 2,
            "qid": 102,
            "pid": "user2@gmail.com",
            "testid": "T1759264877921",
            "selectedAnswer": "Four"
        }
    ]

Fetches all responses submitted by a participant for a particular test.

### 3. Check All Responses of a Participant

```http
URL: GET /api/v1/a/response/check/{pid}?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `pid`      | `string` | **Required**. Participant ID |
| `testid`      | `string` | **Required**. Test ID |

#### Response:

    json

    {
        "id": 7,
        "testid": "T1759561919671",
        "pid": "admin1@gmail.com",
        "testGiven": true,
        "totalMarks": 20,
        "givenAt": "2025-10-05T12:42:22.259947",
        "registeredAt": "2025-10-04T12:42:22.259947",
        "checked": true
    }

Admin checks all responses of a specific participant for a test and evaluates marks.


### 4. Check All Participants’ Responses for a Test

```http
URL: GET /api/v1/a/response/check/all?testid={testid}
```

    Auth Type: Bearer Token | Accessibility: Admin

#### Request Parameter:

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `testid`      | `string` | **Required**. Test ID |

#### Response:

    json

    [
        {
            "id": 7,
            "testid": "T1759561919671",
            "pid": "admin1@gmail.com",
            "testGiven": true,
            "totalMarks": 20,
            "givenAt": null,
            "registeredAt": "2025-10-04T12:42:22.259947",
            "checked": true
        },
        {
            "id": 8,
            "testid": "T1759561919671",
            "pid": "user2@gmail.com",
            "testGiven": true,
            "totalMarks": 18,
            "givenAt": "2025-10-04T13:05:41.112947",
            "registeredAt": "2025-10-04T12:50:23.748932",
            "checked": true
        }
    ]


Returns a list of all participants of a specific test along with their marks and status.
Each record represents one participant's evaluation result, including total marks, timestamps, and whether their responses have been checked.

<br>

---

<br>

## 2. Technologies & Dependencies

**🏗 Core Framework**
 - **Spring Boot Starter Web** – Provides the foundation for building RESTful web services and APIs.

 - **Spring Boot Starter Data JPA** – Simplifies database operations using JPA and Hibernate ORM.

**🧰 Development & Testing**

- **Spring Boot DevTools** – Enables live reload and faster development workflow.

- **Spring Boot Starter Test** – Provides tools for unit and integration testing.

**🗄 Database**

- **MySQL Connector/J** – JDBC driver for connecting the application to a MySQL database.

**✅ Validation**

- **Spring Boot Starter Validation** – Supports Java Bean Validation (annotations like @NotNull, @Email, etc.) for input data checks.

**🔐 Security**

- **Spring Boot Starter Security** – Adds authentication and authorization capabilities to secure the APIs.

- **JBCrypt (org.mindrot.jbcrypt)** – Used for securely hashing and verifying user passwords.

**🔑 JWT (JSON Web Token)**

- **JJWT (io.jsonwebtoken)** – Used for generating, parsing, and validating JWT tokens for stateless authentication.

- **jjwt-api** – Core JWT interfaces and APIs.

- **jjwt-impl** – Implementation of JWT logic.

- **jjwt-jackson** – Integrates JWT with Jackson for JSON parsing.

<br/>


## 3. Installation Guide

Follow these steps to set up the **Smart-Check** project locally and run it.

---

### 1. Clone the Project from GitHub

Open your terminal or command prompt and run:

```bash
git clone https://github.com/mr-prasant/Smart-Check.git
```

### 2. Install Required Tools

> #### 2.1. IntelliJ IDEA (Latest Version)
> 
> Download from: https://www.jetbrains.com/idea/download/


> #### 2.2. Java JDK 21
> 
> Download from: https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html


> #### 2.3. MySQL 14.14
> 
> Download from: https://dev.mysql.com/downloads/mysql/


> #### 2.4. Postman (optional, for testing endpoints)
> 
> Download from: https://www.postman.com/downloads/


### 3. Create the Database

> 3.1 Open MySQL Workbench or MySQL CLI.

> 3.2 Execute the following command to create the database:
>
> ```sql
> CREATE DATABASE smartcheck;
> ```


### 4. Configure `application.properties`
Open `src/main/resources/application.properties` and update the following configurations:

```properties

spring.application.name=Smart-Check

# ===============================
# DATABASE CONFIGURATION
# ===============================
spring.datasource.url=jdbc:mysql://localhost:3306/smartcheck
spring.datasource.username=root
spring.datasource.password=your-db-password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ===============================
# JPA / HIBERNATE CONFIGURATION
# ===============================
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT SECRET
jwt-secret=spring-security-jwt-secret-token
```

⚠️ Make sure the MySQL username and password match your local setup.

### 5. Open Project in IntelliJ IDEA

5.1 Open IntelliJ IDEA.

5.2 Select **File > Open** and choose the cloned `smart-check` folder.

5.3 Let IntelliJ import the Maven dependencies.


### 6. Run the Application

6.1 Navigate to `SmartCheckApplication.java`.

6.2 Right-click and select Run.

6.3 The application should start on `http://localhost:8080`.


### 7. Test Endpoints

Use Postman to test all endpoints:

> Base URL: `http://localhost:8080/api/v1`
>
> Example: `POST /p/user/register` for registering a new user.

### Notes

- Always create the database first before running the application.

- If you change database credentials, update the application.properties accordingly.

- Make sure JDK 21 is properly set in IntelliJ IDEA.
