/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MSI MODERN
 */

public class Pembayaran {
    private String idPembayaran;
    private TagihanAir tagihan;
    private String tanggalBayar;
    private int jumlahBayar;

    public Pembayaran(String idPembayaran, TagihanAir tagihan, String tanggalBayar, int jumlahBayar) {
        if (idPembayaran == null || idPembayaran.trim().isEmpty()) {
            throw new IllegalArgumentException("ID pembayaran tidak boleh kosong");
        }

        if (tagihan == null) {
            throw new IllegalArgumentException("Tagihan tidak boleh kosong");
        }

        if (tanggalBayar == null || tanggalBayar.trim().isEmpty()) {
            throw new IllegalArgumentException("Tanggal bayar tidak boleh kosong");
        }

        if (jumlahBayar <= 0) {
            throw new IllegalArgumentException("Jumlah bayar harus lebih dari 0");
        }

        this.idPembayaran = idPembayaran;
        this.tagihan = tagihan;
        this.tanggalBayar = tanggalBayar;
        this.jumlahBayar = jumlahBayar;
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(String idPembayaran) {
        if (idPembayaran == null || idPembayaran.trim().isEmpty()) {
            throw new IllegalArgumentException("ID pembayaran tidak boleh kosong");
        }
        this.idPembayaran = idPembayaran;
    }

    public TagihanAir getTagihan() {
        return tagihan;
    }
    public void setTagihan(TagihanAir tagihan) {
        if (tagihan == null) {
            throw new IllegalArgumentException("Tagihan tidak boleh kosong");
        }
        this.tagihan = tagihan;
    }

    public String getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(String tanggalBayar) {
        if (tanggalBayar == null || tanggalBayar.trim().isEmpty()) {
            throw new IllegalArgumentException("Tanggal bayar tidak boleh kosong");
        }
        this.tanggalBayar = tanggalBayar;
    }

    public int getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(int jumlahBayar) {
        if (jumlahBayar <= 0) {
            throw new IllegalArgumentException("Jumlah bayar harus lebih dari 0");
        }
        this.jumlahBayar = jumlahBayar;
    }

    public void tampilkanInfo() {
        System.out.println("ID Pembayaran : " + idPembayaran);
        System.out.println("ID Tagihan    : " + tagihan.getIdTagihan());
        System.out.println("Nama Warga    : " + tagihan.getWarga().getNama());
        System.out.println("Tanggal Bayar : " + tanggalBayar);
        System.out.println("Jumlah Bayar  : Rp" + jumlahBayar);
        System.out.println("Status Tagihan: " + tagihan.getStatus());
    }
}
