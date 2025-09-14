# 🛒 Ecommerce_Monolithic

A **Monolithic E-Commerce Backend Application** built with **Spring Boot** and **MySQL**.  
This project implements a simple **Order Management System** with core e-commerce features, designed as the initial phase before migrating to a **microservices architecture**.

---

## 🚀 Project Overview
This backend handles all major functionalities of an e-commerce platform inside **one Spring Boot application**:

- **User Management**
    - Register new users
    - Login authentication
    - Profile updates

- **Product Catalog**
    - Add new products
    - Update stock quantities
    - Search products by name

- **Order Service**
    - Place orders
    - Update order status (e.g., shipped, delivered)
    - Cancel orders

- **Payment Service (Dummy)**
    - Mark orders as paid
    - Get total payment per user

---

## 🗄️ Database
- **MySQL** is used as the database.
- Enabled `spring.jpa.hibernate.ddl-auto=update`, so **tables are auto-created** (no need to manually run SQL scripts).
- Tables:
    - `users`
    - `products`
    - `orders`
    - `payments`

---

## ⚙️ Tech Stack
- **Backend:** Spring Boot (Monolithic Architecture)
- **Database:** MySQL (with Spring Data JPA / Hibernate)
- **Security:** Spring Security (for login/authentication)
- **Build Tool:** Maven
- **Testing:** JUnit + Mockito

---

## ▶️ How to Run

### Prerequisites
- Install [Java 17+](https://adoptium.net/)
- Install [MySQL](https://dev.mysql.com/downloads/) and create a database `ecommerce_db`
- Install [Maven](https://maven.apache.org/)

### Steps
1. Clone the repo:
   ```bash
   git clone https://github.com/pratham1c1/Ecommerce_Monolithic.git
   cd Ecommerce_Monolithic
