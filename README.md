# 🚂 Train Ticket Booking System (Java + Gradle)

A simple backend project built with **Java** and **Gradle**, simulating a **Train Ticket Booking System**.  
It allows users to register, log in, view available trains, and book tickets, while securely storing user data with **BCrypt password hashing**.

---

## ✨ Features

- 👤 **User Management**
  - Register new users with secure password hashing (`BCrypt`)
  - Login & authentication
  - Stores user details in `users.json`

- 🚂 **Train Management**
  - Train details stored in `trains.json`
  - View available trains
  - Track seat availability

- 🎟 **Ticket Booking**
  - Book tickets for available trains
  - Ticket details stored in memory (and linked with users)

---

## 🗂 Project Structure

src/
└── main/java/
├── model/
│ ├── User.java # User details
│ ├── Train.java # Train details
│ └── Ticket.java # Ticket details
│
├── service/
│ ├── UserBookingService.java # Booking logic
│ └── TrainService.java # Train management
│
└── util/
└── UserServiceUtil.java # Password hashing (BCrypt)

resources/
├── users.json # Stores registered users
└── trains.json # Stores train data

Main.java # Entry point (menu-driven program)
build.gradle # Gradle dependencies & build config


---

## ⚙️ Tech Stack

- **Java** (Core OOP, Collections, File I/O)
- **Gradle** (Build tool, dependency management)
- **BCrypt** (Secure password hashing)
- **JSON** (Simple local DB for trains & users)

---

## 🚀 Getting Started

### 1. Clone the Repository
git clone https://github.com/<your-username>/TrainBookingSystem.git
cd TrainBookingSystem
### 2. Build the Project
./gradlew build
### 3. Run the Application
./gradlew run
