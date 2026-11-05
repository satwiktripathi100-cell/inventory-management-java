# Inventory Management System

A console-based Inventory Management System developed using **Java, JDBC, and MySQL**. This project allows users to manage product records, update stock quantities, search products, calculate inventory value, and delete products through a menu-driven interface.

## Technologies Used

- **Java** – Core programming language
- **JDBC** – Java Database Connectivity
- **MySQL** – Relational database
- **IntelliJ IDEA** – Development environment
- **Maven** – Dependency management

## Features

1. **Add Product:** Add new products with product ID, name, category, quantity, and price.
2. **View Products:** Display all available products from the database.
3. **Update Stock:** Increase the stock quantity of an existing product.
4. **Search by Category:** Display products belonging to a particular category with quantity below 20, sorted by price in descending order.
5. **Update Product Price:** Update the price of an existing product after validating that the new price is greater than zero.
6. **Total Inventory Value:** Calculate the total inventory value using the formula:

   `SUM(quantity * price)`

7. **Delete Product:** Delete a product only when its available quantity is zero.

## Database Structure

**Database Name:** `retail_db`

**Table Name:** `Product`

| Column Name | Data Type | Description |
|---|---|---|
| product_id | INT | Primary Key |
| product_name | VARCHAR(100) | Product Name |
| category | VARCHAR(50) | Product Category |
| quantity | INT | Available Stock |
| price | DOUBLE | Product Price |

## Project Structure

```text
InventoryManagement1/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── inventorymanagement1/
│                       └── InventoryManagement.java
│
├── pom.xml
└── README.md
```

## Setup and Installation

### 1. Install Requirements

- Java JDK
- MySQL Server
- IntelliJ IDEA

### 2. Create Database

Open MySQL Workbench and execute:

```sql
CREATE DATABASE retail_db;

USE retail_db;

CREATE TABLE Product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    price DOUBLE
);
```

### 3. Configure Database Connection

Open `InventoryManagement.java` and update the database credentials:

```java
static String url = "jdbc:mysql://localhost:3306/retail_db";
static String user = "root";
static String password = "YOUR_MYSQL_PASSWORD";
```

Replace `YOUR_MYSQL_PASSWORD` with your own MySQL password.

### 4. Run the Project

1. Open the project in IntelliJ IDEA.
2. Make sure the MySQL Connector/J dependency is available through Maven.
3. Configure your database credentials.
4. Run `InventoryManagement.java`.
5. Select an operation from the menu.

## Sample Menu

```text
--- INVENTORY MANAGEMENT SYSTEM ---

1. Add Product
2. View Products
3. Update Product Stock
4. Search by Category (Quantity < 20)
5. Update Product Price
6. Total Inventory Value
7. Delete Product (Quantity = 0)
8. Exit

Enter your choice:
```

## Concepts Implemented

- Java Classes and Methods
- Exception Handling
- JDBC Connectivity
- PreparedStatement
- ResultSet
- SQL CRUD Operations
- SQL Aggregate Functions
- Conditional Queries
- Sorting using ORDER BY
- Maven Dependency Management

## Author

**Developed as an academic project using Java, JDBC, and MySQL.**
