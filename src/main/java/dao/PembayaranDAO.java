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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import model.Pembayaran;
import model.TagihanAir;
import model.Warga;

public class PembayaranDAO {
    private static final DateTimeFormatter FORMAT_TANGGAL =
            DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
    public ArrayList<Pembayaran> getAll() {
        ArrayList<Pembayaran> daftarPembayaran = new ArrayList<>();
        String sql = "SELECT p.id_pembayaran, p.id_tagihan, p.tanggal_bayar, p.jumlah_bayar, "
                + "t.id_warga, t.pemakaian, t.status, "
                + "w.nama, w.alamat, w.nomor_rumah "
                + "FROM pembayaran p "
                + "JOIN tagihan_air t ON p.id_tagihan = t.id_tagihan "
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

                String tanggal = result.getDate("tanggal_bayar")
                        .toLocalDate()
                        .format(FORMAT_TANGGAL);
                Pembayaran pembayaran = new Pembayaran(
                    result.getString("id_pembayaran"),
                    tagihan,
                    tanggal,
                    result.getInt("jumlah_bayar")
                );
                daftarPembayaran.add(pembayaran);
            }
        } catch (Exception e) {
            System.out.println("Gagal mengambil data pembayaran.");
            System.out.println(e.getMessage());
        }
        return daftarPembayaran;
    }

    public Pembayaran findById(String id) {
        String sql = "SELECT p.id_pembayaran, p.id_tagihan, p.tanggal_bayar, p.jumlah_bayar, "
                + "t.id_warga, t.pemakaian, t.status, "
                + "w.nama, w.alamat, w.nomor_rumah "
                + "FROM pembayaran p "
                + "JOIN tagihan_air t ON p.id_tagihan = t.id_tagihan "
                + "JOIN warga w ON t.id_warga = w.id_warga "
                + "WHERE p.id_pembayaran = ?";
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
                    String tanggal = result.getDate("tanggal_bayar")
                            .toLocalDate()
                            .format(FORMAT_TANGGAL);
                    return new Pembayaran(
                        result.getString("id_pembayaran"),
                        tagihan,
                        tanggal,
                        result.getInt("jumlah_bayar")
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal mencari data pembayaran.");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean insert(Pembayaran pembayaran) {
        String sql = "INSERT INTO pembayaran "
                + "(id_pembayaran, id_tagihan, tanggal_bayar, jumlah_bayar) "
                + "VALUES (?, ?, ?, ?)";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            LocalDate tanggal = LocalDate.parse(
                    pembayaran.getTanggalBayar(),
                    FORMAT_TANGGAL
            );
            statement.setString(1, pembayaran.getIdPembayaran());
            statement.setString(2, pembayaran.getTagihan().getIdTagihan());
            statement.setDate(3, Date.valueOf(tanggal));
            statement.setInt(4, pembayaran.getJumlahBayar());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Gagal menambahkan data pembayaran.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean insertAndMarkLunas(Pembayaran pembayaran) {
        String insertSql = "INSERT INTO pembayaran "
                + "(id_pembayaran, id_tagihan, tanggal_bayar, jumlah_bayar) "
                + "VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE tagihan_air "
                + "SET status = ? "
                + "WHERE id_tagihan = ?";
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (
                PreparedStatement insertStatement =
                        connection.prepareStatement(insertSql);
                PreparedStatement updateStatement =
                        connection.prepareStatement(updateSql)
            ) {
                LocalDate tanggal = LocalDate.parse(
                        pembayaran.getTanggalBayar(),
                        FORMAT_TANGGAL);
                insertStatement.setString(1, pembayaran.getIdPembayaran()
                );
                insertStatement.setString(2, pembayaran.getTagihan().getIdTagihan()
                );
                insertStatement.setDate(3, Date.valueOf(tanggal)
                );
                insertStatement.setInt(4, pembayaran.getJumlahBayar()
                );
                int hasilInsert = insertStatement.executeUpdate();
                updateStatement.setString(1, "Lunas");
                updateStatement.setString(2, pembayaran.getTagihan().getIdTagihan()
                );
                int hasilUpdate = updateStatement.executeUpdate();
                if (hasilInsert == 1 && hasilUpdate == 1) {
                    connection.commit();
                    return true;
                }
                connection.rollback();
                return false;
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("Gagal menyimpan pembayaran.");
            System.out.println(e.getMessage());
            return false;
        }
    }
}