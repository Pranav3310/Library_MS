# 📚 Library Management System (Java CLI)

A **console-based Library Management System** built using **Core Java**, applying **Object-Oriented Programming (OOP)** principles and modular architecture.  
This project allows efficient management of books, members, and borrowing/returning processes with file-based storage.

---

## 🚀 Features
- ➕ **Add Books & Members** (Students / Faculty)
- 📖 **Search Books** by Title or Author
- 🔄 **Borrow & Return Books** with automatic availability update
- ⏳ **Due Date Tracking** based on member type:
  - Student → 14 days
  - Faculty → 30 days
- 💰 **Fine Calculation** for late returns (₹5/day)
- 📂 **Persistent Storage** using File I/O (`CSV` files)
- ⚡ Organized into multiple **packages** (`models`, `services`, `exceptions`, `util`) for clean code and maintainability

---

## 🛠️ Tech Stack
- **Language:** Java (Core Java, OOP, Exception Handling, File I/O)
- **Tools:** VS Code / Eclipse
- **Version Control:** Git & GitHub

---

## 📂 Project Structure
Library_MS/
├─ src/
│ └─ library/
│ ├─ Main.java
│ ├─ models/
│ │ ├─ Book.java
│ │ ├─ Member.java
│ │ ├─ Student.java
│ │ ├─ Faculty.java
│ │ └─ Loan.java
│ └─ services/
│ ├─ FileStorage.java
│ └─ LibraryService.java
├─ out/ # compiled files
├─ data/ # auto-created; stores books.csv, members.csv, loans.csv
└─ README.md
