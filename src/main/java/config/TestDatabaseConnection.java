/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author MSI MODERN
 */
import java.sql.Connection;

public class TestDatabaseConnection {

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();

            System.out.println("Koneksi database berhasil.");

            connection.close();

        } catch (Exception e) {
            System.out.println("Koneksi database gagal.");
            System.out.println(e.getMessage());
        }
    }
}
