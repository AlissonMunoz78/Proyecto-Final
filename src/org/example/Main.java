package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String URL = "jdbc:mysql://localhost:3306/estudiantes2024A";
        String USER = "root";
        String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE estudiantes SET b1=? WHERE cedula=?")) {

            // Prepara la sentencia SQL
            pstmt.setInt(1, 100);
            pstmt.setString(2, "1726195207");
            System.out.println(pstmt.toString());

            // Ejecuta la actualización
            int n = pstmt.executeUpdate();
            System.out.println("Se modificaron: " + n + " líneas");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
