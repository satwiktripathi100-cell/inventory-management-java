
package com.example.inventorymanagement1;

import java.sql.*;
import java.util.Scanner;

public class InventoryManagement {

    static String url = "jdbc:mysql://localhost:3306/retail_db";
    static String user = "root";
    static String password = "YOUR_MYSQL_PASSWORD";

    static Scanner sc = new Scanner(System.in);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // 1. Add Product
    public static void addProduct() {

        String query = "INSERT INTO Product VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Category: ");
            String category = sc.nextLine();

            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();

            System.out.print("Enter Price: ");
            double price = sc.nextDouble();

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, category);
            ps.setInt(4, quantity);
            ps.setDouble(5, price);

            ps.executeUpdate();

            System.out.println("Product Added Successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Product ID already exists!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 2. View All Products
    public static void viewProducts() {

        String query = "SELECT * FROM Product";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("product_id") + " | " +
                                rs.getString("product_name") + " | " +
                                rs.getString("category") + " | " +
                                rs.getInt("quantity") + " | " +
                                rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 3. Update Product Stock
    public static void updateStock() {

        String query =
                "UPDATE Product SET quantity = quantity + ? WHERE product_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();

            System.out.print("Enter Quantity to Add: ");
            int quantity = sc.nextInt();

            ps.setInt(1, quantity);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Stock Updated Successfully!");
            } else {
                System.out.println("Product Does Not Exist.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 4. Search Category Products with Quantity Below 20
    // Sort by price in descending order
    public static void searchByCategory() {

        String query =
                "SELECT * FROM Product " +
                        "WHERE category = ? AND quantity < 20 " +
                        "ORDER BY price DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            System.out.print("Enter Category: ");
            String category = sc.nextLine();

            ps.setString(1, category);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println(
                            rs.getInt("product_id") + " | " +
                                    rs.getString("product_name") + " | " +
                                    rs.getString("category") + " | " +
                                    rs.getInt("quantity") + " | " +
                                    rs.getDouble("price")
                    );
                }

                if (!found) {
                    System.out.println("No matching products found.");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 5. Update Product Price
    // Product must exist and new price must be greater than 0
    public static void updatePrice() {

        try (Connection con = getConnection()) {

            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();

            // First, check whether product exists
            String checkQuery =
                    "SELECT * FROM Product WHERE product_id = ?";

            try (PreparedStatement checkPs =
                         con.prepareStatement(checkQuery)) {

                checkPs.setInt(1, id);

                try (ResultSet rs = checkPs.executeQuery()) {

                    if (!rs.next()) {
                        System.out.println("Product Does Not Exist.");
                        return;
                    }
                }
            }

            System.out.print("Enter New Price: ");
            double newPrice = sc.nextDouble();

            if (newPrice <= 0) {
                System.out.println("Price must be greater than 0.");
                return;
            }

            String updateQuery =
                    "UPDATE Product SET price = ? WHERE product_id = ?";

            try (PreparedStatement updatePs =
                         con.prepareStatement(updateQuery)) {

                updatePs.setDouble(1, newPrice);
                updatePs.setInt(2, id);

                updatePs.executeUpdate();

                System.out.println("Price Updated Successfully!");
            }

            // Display updated product details
            String displayQuery =
                    "SELECT * FROM Product WHERE product_id = ?";

            try (PreparedStatement displayPs =
                         con.prepareStatement(displayQuery)) {

                displayPs.setInt(1, id);

                try (ResultSet rs = displayPs.executeQuery()) {

                    if (rs.next()) {
                        System.out.println("Updated Product Details:");

                        System.out.println(
                                rs.getInt("product_id") + " | " +
                                        rs.getString("product_name") + " | " +
                                        rs.getString("category") + " | " +
                                        rs.getInt("quantity") + " | " +
                                        rs.getDouble("price")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 6. Display Total Inventory Value
    public static void totalInventoryValue() {

        String query =
                "SELECT SUM(quantity * price) AS total_value FROM Product";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                double total = rs.getDouble("total_value");

                System.out.println("Total Inventory Value: " + total);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 7. Delete Product Only If Quantity Is 0
    public static void deleteProduct() {

        String query =
                "DELETE FROM Product WHERE product_id = ? AND quantity = 0";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            System.out.print("Enter Product ID: ");
            int id = sc.nextInt();

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Product Deleted Successfully!");
            } else {
                System.out.println(
                        "Product not found or quantity is not zero."
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Main Menu
    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n--- INVENTORY MANAGEMENT SYSTEM ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product Stock");
            System.out.println("4. Search by Category (Quantity < 20)");
            System.out.println("5. Update Product Price");
            System.out.println("6. Total Inventory Value");
            System.out.println("7. Delete Product (Quantity = 0)");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addProduct();
                    break;

                case 2:
                    viewProducts();
                    break;

                case 3:
                    updateStock();
                    break;

                case 4:
                    searchByCategory();
                    break;

                case 5:
                    updatePrice();
                    break;

                case 6:
                    totalInventoryValue();
                    break;

                case 7:
                    deleteProduct();
                    break;

                case 8:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 8);

        sc.close();
    }
}
