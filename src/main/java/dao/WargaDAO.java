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
import model.Warga;

public class WargaDAO {
    public ArrayList<Warga> getAll() {
        ArrayList<Warga> daftarWarga = new ArrayList<>();
        String sql = "SELECT id_warga, nama, alamat, nomor_rumah FROM warga";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery()
        ) {
            while (result.next()) {
                Warga warga = new Warga(
                    result.getString("id_warga"),
                    result.getString("nama"),
                    result.getString("alamat"),
                    result.getString("nomor_rumah")
                );
                daftarWarga.add(warga);
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil data warga.");
            System.out.println(e.getMessage());
        }
        return daftarWarga;
    }

    public Warga findById(String id) {
        String sql = "SELECT id_warga, nama, alamat, nomor_rumah FROM warga WHERE id_warga = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Warga(
                        result.getString("id_warga"),
                        result.getString("nama"),
                        result.getString("alamat"),
                        result.getString("nomor_rumah")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal mencari data warga.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean insert(Warga warga) {
        String sql = "INSERT INTO warga (id_warga, nama, alamat, nomor_rumah) VALUES (?, ?, ?, ?)";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, warga.getIdUser());
            statement.setString(2, warga.getNama());
            statement.setString(3, warga.getAlamat());
            statement.setString(4, warga.getNomorRumah());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal menambahkan data warga.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(Warga warga) {
        String sql = "UPDATE warga SET nama = ?, alamat = ?, nomor_rumah = ? WHERE id_warga = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, warga.getNama());
            statement.setString(2, warga.getAlamat());
            statement.setString(3, warga.getNomorRumah());
            statement.setString(4, warga.getIdUser());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal mengubah data warga.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM warga WHERE id_warga = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal menghapus data warga.");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
