/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author MSI MODERN
 */
import java.util.ArrayList;
import model.Warga;
import model.TagihanAir;
import model.Pembayaran;
import model.Petugas;
import dao.WargaDAO;
import dao.TagihanAirDAO;
import dao.PembayaranDAO;
import dao.PetugasDAO;

public class PengelolaanService {
    private final WargaDAO wargaDAO;
    private final TagihanAirDAO tagihanDAO;
    private final PembayaranDAO pembayaranDAO;
    private final PetugasDAO petugasDAO;
    public PengelolaanService() {
        wargaDAO = new WargaDAO();
        tagihanDAO = new TagihanAirDAO();
        pembayaranDAO = new PembayaranDAO();
        petugasDAO = new PetugasDAO();
    }
    public boolean tambahWarga(Warga warga) {
        if (warga == null) {
            return false;
        }
        if (cariWarga(warga.getIdUser()) != null) {
            return false;
        }
        return wargaDAO.insert(warga);
    }

    public ArrayList<Warga> getDaftarWarga() {
        return wargaDAO.getAll();
    }

    public Warga cariWarga(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return wargaDAO.findById(id);
    }

    public boolean ubahWarga(Warga warga) {
        if (warga == null) {
            return false;
        }
        if (cariWarga(warga.getIdUser()) == null) {
            return false;
        }
        return wargaDAO.update(warga);
    }

    public boolean hapusWarga(String id) {
        Warga warga = cariWarga(id);
        if (warga == null) {
            return false;
        }
        if (punyaTagihan(warga)) {
            return false;
        }
        return wargaDAO.delete(id);
    }

    public boolean tambahTagihan(TagihanAir tagihan) {
        if (tagihan == null) {
            return false;
        }
        if (cariTagihan(tagihan.getIdTagihan()) != null) {
            return false;
        }
        if (cariWarga(tagihan.getWarga().getIdUser()) == null) {
            return false;
        }
        return tagihanDAO.insert(tagihan);
    }

    public ArrayList<TagihanAir> getDaftarTagihan() {
        return tagihanDAO.getAll();
    }
    public TagihanAir cariTagihan(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return tagihanDAO.findById(id);
    }

    public boolean ubahTagihan(TagihanAir tagihan) {
        if (tagihan == null) {
            return false;
        }
        if (cariTagihan(tagihan.getIdTagihan()) == null) {
            return false;
        }
        if (cariWarga(tagihan.getWarga().getIdUser()) == null) {
            return false;
        }
        return tagihanDAO.update(tagihan);
    }

    public boolean hapusTagihan(String id) {
        TagihanAir tagihan = cariTagihan(id);
        if (tagihan == null) {
            return false;
        }
        if (sudahAdaPembayaran(tagihan)) {
            return false;
        }
        return tagihanDAO.delete(id);
    }

    public boolean tambahPembayaran(Pembayaran pembayaran) {
        if (pembayaran == null) {
            return false;
        }
        if (cariPembayaran(pembayaran.getIdPembayaran()) != null) {
            return false;
        }
        TagihanAir tagihan = cariTagihan(
                pembayaran.getTagihan().getIdTagihan()
        );
        if (tagihan == null) {
            return false;
        }
        if ("Lunas".equalsIgnoreCase(tagihan.getStatus())) {
            return false;
        }
        if (tagihan.getTotalTagihan() == 0) {
            return false;
        }
        if (pembayaran.getJumlahBayar() != tagihan.getTotalTagihan()) {
            return false;
        }
        boolean berhasil = pembayaranDAO.insertAndMarkLunas(pembayaran);
        if (berhasil) {
            pembayaran.getTagihan().setStatus("Lunas");
        }
        return berhasil;
    }

    public ArrayList<Pembayaran> getDaftarPembayaran() {
        return pembayaranDAO.getAll();
    }

    public Pembayaran cariPembayaran(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return pembayaranDAO.findById(id);
    }

    public ArrayList<Petugas> getDaftarPetugas() {
        return petugasDAO.getAll();
    }

    public boolean sudahAdaPembayaran(TagihanAir tagihan) {
        if (tagihan == null) {
            return false;
        }
        for (Pembayaran pembayaran : getDaftarPembayaran()) {
            if (pembayaran.getTagihan().getIdTagihan()
                    .equalsIgnoreCase(tagihan.getIdTagihan())) {
                return true;
            }
        }
        return false;
    }

    public boolean punyaTagihan(Warga warga) {
        if (warga == null) {
            return false;
        }
        for (TagihanAir tagihan : getDaftarTagihan()) {
            if (tagihan.getWarga().getIdUser()
                    .equalsIgnoreCase(warga.getIdUser())) {
                return true;
            }
        }
        return false;
    }
}