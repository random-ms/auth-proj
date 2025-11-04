# 🚀 Full-Stack Authentication Project (Spring Boot & React)

This is a simple full-stack application demonstrating **user authentication** (sign up, log in, password reset, account verification) using a **Spring Boot** backend and a **React** frontend.

## Key Features
* User **Registration/Sign Up**
* User **Login/Sign In**
* **Token-based** Authentication (via JWT/Cookies)
* **Password Reset** (using OTP)
* **Account Verification** (using OTP)
* **Protected Routes** on the frontend

---

## 💻 Tech Stack

### Backend
| Technology | Description |
| :--- | :--- |
| **Spring Boot** | Framework for building the robust API. |
| **Spring Security** | Handles authentication, authorization, and password hashing. |
| **Java** | Core programming language. |
| **Database** | MySQL |

### Frontend
| Technology | Description |
| :--- | :--- |
| **React** | JavaScript library for building the user interface. |
| **Axios** (or `fetch`) | For making HTTP requests to the Spring API. |
| **React Router** | For handling client-side routing and protected routes. |
| **npm/yarn** | Package manager. |

---

## 🛠️ Getting Started

### Prerequisites

You'll need the following installed on your machine:

* **Java Development Kit (JDK) 17+**
* **Node.js (LTS version)**
* **npm** or **yarn**
* A **Git** client

### Backend Setup

1.  **Clone the repository:**
    ```bash
    git clone [Your Repository URL]
    cd [repository-name]/backend
    ```
2.  **Configure the database:**
    * Open `src/main/resources/application.properties` (or `application.yml`).
    * **Update the database connection details.**
3.  **Run the application:**
    ```bash
    # Use your IDE (e.g., IntelliJ, VS Code) to run the main class, OR
    ./mvnw spring-boot:run
    ```
    The backend should start on `http://localhost:8080`.

### Frontend Setup

1.  **Navigate to the frontend directory:**
    ```bash
    cd ../frontend
    ```
2.  **Install dependencies:**
    ```bash
    npm install  # or yarn install
    ```
3.  **Run the client:**
    ```bash
    npm start  # or yarn start
    ```
    The React application should open in your browser on `http://localhost:3000`.

---

## ⚙️ API Endpoints

This application exposes several endpoints for user **authentication**, **registration**, **password management**, and **profile viewing**.

| Method | Endpoint | Protection | Description |
| :--- | :--- | :--- | :--- |
| **POST** | `/register` | Public | Creates a **new user** profile (Sign Up). |
| **POST** | `/login` | Public | Authenticates the user and returns a **JWT token** (via Cookie/Body). |
| **POST** | `/logout` | Public | **Clears the JWT cookie** to log the user out. |
| **GET** | `/profile` | **Protected** | Retrieves the **current authenticated user's profile** details. |
| **GET** | `/is-authenticated` | Public | Checks if a **valid authentication token** is present. |
| **POST** | `/send-reset-otp` | Public | Sends a **One-Time Password (OTP)** to the user's email for password reset. |
| **POST** | `/reset-password` | Public | Resets the user's password using the **email, OTP, and new password**. |
| **POST** | `/account/send-verification-otp` | **Protected** | Sends an OTP to **verify the account's email** address. |
| **POST** | `/account/verify-otp` | **Protected** | Verifies the account using the **provided OTP**. |
| **GET** | `/test` | **Protected** | Simple endpoint to **verify authentication** is working correctly. |

---

## 🤝 Contribution

Feel free to **fork** this repository and submit pull requests!
