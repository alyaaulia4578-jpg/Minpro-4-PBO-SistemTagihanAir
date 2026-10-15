/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author MSI MODERN
 */
import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Petugas;

public class PetugasDAO {
    public ArrayList<Petugas> getAll() {
        ArrayList<Petugas> daftarPetugas = new ArrayList<>();
        String sql = "SELECT id_petugas, nama, jabatan FROM petugas";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery()
        ) {
            while (result.next()) {
                Petugas petugas = new Petugas(
                    result.getString("id_petugas"),
                    result.getString("nama"),
                    result.getString("jabatan")
                );
                daftarPetugas.add(petugas);
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil data petugas.");
            System.out.println(e.getMessage());
        }
        return daftarPetugas;
    }

    public Petugas findById(String id) {
        String sql = "SELECT id_petugas, nama, jabatan FROM petugas WHERE id_petugas = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Petugas(
                        result.getString("id_petugas"),
                        result.getString("nama"),
                        result.getString("jabatan")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal mencari data petugas.");
            System.out.println(e.getMessage());
        }
        return null;
    }
}