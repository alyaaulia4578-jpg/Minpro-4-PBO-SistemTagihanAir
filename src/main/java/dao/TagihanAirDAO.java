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
import model.TagihanAir;
import model.Warga;

public class TagihanAirDAO {
    public ArrayList<TagihanAir> getAll() {
        ArrayList<TagihanAir> daftarTagihan = new ArrayList<>();
        String sql = "SELECT t.id_tagihan, t.id_warga, t.pemakaian, t.total_tagihan, t.status, t.tarif_air, "
                + "w.nama, w.alamat, w.nomor_rumah "
                + "FROM tagihan_air t "
                + "JOIN warga w ON t.id_warga = w.id_warga";
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
                TagihanAir tagihan = new TagihanAir(
                    result.getString("id_tagihan"),
                    warga,
                    result.getInt("pemakaian")
                );
                tagihan.setStatus(result.getString("status"));
                daftarTagihan.add(tagihan);
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil data tagihan.");
            System.out.println(e.getMessage());
        }
        return daftarTagihan;
    }

    public TagihanAir findById(String id) {
        String sql = "SELECT t.id_tagihan, t.id_warga, t.pemakaian, t.total_tagihan, t.status, t.tarif_air, "
                + "w.nama, w.alamat, w.nomor_rumah "
                + "FROM tagihan_air t "
                + "JOIN warga w ON t.id_warga = w.id_warga "
                + "WHERE t.id_tagihan = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Warga warga = new Warga(
                        result.getString("id_warga"),
                        result.getString("nama"),
                        result.getString("alamat"),
                        result.getString("nomor_rumah")
                    );
                    TagihanAir tagihan = new TagihanAir(
                        result.getString("id_tagihan"),
                        warga,
                        result.getInt("pemakaian")
                    );
                    tagihan.setStatus(result.getString("status"));
                    return tagihan;
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal mencari data tagihan.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean insert(TagihanAir tagihan) {
        String sql = "INSERT INTO tagihan_air "
                + "(id_tagihan, id_warga, pemakaian, total_tagihan, status, tarif_air) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, tagihan.getIdTagihan());
            statement.setString(2, tagihan.getWarga().getIdUser());
            statement.setInt(3, tagihan.getPemakaian());
            statement.setInt(4, tagihan.getTotalTagihan());
            statement.setString(5, tagihan.getStatus());
            statement.setInt(6, TagihanAir.getTarifAir());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal menambahkan data tagihan.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean update(TagihanAir tagihan) {
        String sql = "UPDATE tagihan_air "
                + "SET id_warga = ?, pemakaian = ?, total_tagihan = ?, status = ?, tarif_air = ? "
                + "WHERE id_tagihan = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, tagihan.getWarga().getIdUser());
            statement.setInt(2, tagihan.getPemakaian());
            statement.setInt(3, tagihan.getTotalTagihan());
            statement.setString(4, tagihan.getStatus());
            statement.setInt(5, TagihanAir.getTarifAir());
            statement.setString(6, tagihan.getIdTagihan());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal mengubah data tagihan.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM tagihan_air WHERE id_tagihan = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal menghapus data tagihan.");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
